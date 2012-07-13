/*
 * Copyright (c) 2011, Sundog Interactive.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of Sundog Interactive nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of Sundog Interactive.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.sundog.chatternotifier;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.salesforce.androidsdk.app.ForceApp;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.ClientManager.AccountInfoNotFoundException;
import com.salesforce.androidsdk.rest.ClientManager.LoginOptions;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestClient.AsyncRequestCallback;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;
import com.salesforce.androidsdk.util.EventsObservable;
import com.salesforce.androidsdk.util.EventsObservable.EventType;
import com.sonyericsson.extras.liveware.extension.util.ExtensionService;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.notification.NotificationUtil;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;

/**
 * The Class SampleService.
 */
public class NotificationService extends ExtensionService {

	/** The Constant EXTENSION_KEY. */
	public static final String EXTENSION_KEY = "com.sonyericsson.extras.liveware.extension.chatternotification.key";

	/** The Constant EXTENSION_SPECIFIC_ID. */
	public static final String EXTENSION_SPECIFIC_ID = "EXTENSION_SPECIFIC_ID_CHATTER_NOTIFICATION";

	/** Starts periodic insert of data handled in onStartCommand(). */
	public static final String INTENT_ACTION_START = "com.sonyericsson.extras.liveware.extension.chatternotification.action.start";

	/** Stop periodic insert of data, handled in onStartCommand(). */
	public static final String INTENT_ACTION_STOP = "com.sonyericsson.extras.liveware.extension.chatternotification.action.stop";

	/** The is connected to smart watch. */
	private Boolean isConnectedToSmartWatch = false;

	/** The notification message. */
	private String notificationMessage = "";

	/** The api version. */
	private String apiVersion;

	/** The client. */
	private RestClient client;

	/** The tag. */
	private String TAG;

	/** The dh. */
	private DataHelper dh;

	/** The prefs. */
	private SharedPreferences prefs;

	/** The m nm. */
	private NotificationManager mNM;

	/** The ctx. */
	public Context ctx = this;

	/**
	 * The NOTIFICATION. Unique Identification Number for the Notification. We
	 * use it on Notification start, and to cancel it.
	 * */
	private int NOTIFICATION = 1;

	/**
	 * Instantiates a new notification service.
	 */
	public NotificationService() {
		super(EXTENSION_KEY);
	}

	/**
	 * Class for clients to access. Because we know this service always runs in
	 * the same process as its clients, we don't need to deal with Inter-Process
	 * Communication.
	 */
	public class LocalBinder extends Binder {

		/**
		 * Gets the service.
		 * 
		 * @return the service
		 */
		NotificationService getService() {
			return NotificationService.this;
		}
	}

	/**
	 * Creates the service.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		TAG = getString(R.string.tag);
		apiVersion = getString(R.string.api_version);
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		ctx = this;
	}

	/**
	 * onStartCommand is called when service is first created. This is where we
	 * put the code that does the work for the service
	 * 
	 * @param intent
	 *            the intent
	 * @param flags
	 *            the flags
	 * @param startId
	 *            the start id
	 * @return the int
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		FlurryAgent.onStartSession(this, getString(R.string.flurry_key));

		int retVal = 0;
		try {
			retVal = super.onStartCommand(intent, flags, startId);
		} catch (Exception e) {
			retVal = START_STICKY;
			Log.e(TAG, e.toString());
		}
		if (intent != null) {
			if (INTENT_ACTION_START.equals(intent.getAction())) {
				startDoingStuff();
				stopSelfCheck();
			}
		}

		return retVal;
	}

	/**
	 * Start doing stuff.
	 */
	private void startDoingStuff() {
		// Login options
		String accountType = ForceApp.APP.getAccountType();
		LoginOptions loginOptions = new LoginOptions(
				null, // login host is chosen by user through the server picker
				ForceApp.APP.getPasscodeHash(),
				getString(R.string.oauth_callback_url),
				getString(R.string.oauth_client_id), new String[] { "api" });

		// Get a rest client
		ClientManager cm = new ClientManager(this, accountType, loginOptions);

		try {
			client = cm.peekRestClient();
		} catch (AccountInfoNotFoundException e1) {
		}
		if (client != null) {
			RestRequest request = null;
			try {
				request = RestRequest.getRequestForChatter(apiVersion);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (client != null) {
				sendRequest(request);
			}
		}
	}

	/**
	 * Send request.
	 * 
	 * @param request
	 *            the request
	 */
	public void sendRequest(RestRequest request) {

		client.sendAsync(request, new AsyncRequestCallback() {

			@Override
			public void onError(Exception exception) {
				// displayError(exception.getMessage());
				EventsObservable.get().notifyEvent(EventType.RenditionComplete);
			}

			@Override
			public void onSuccess(RestRequest request, RestResponse response) {
				try {
					/*
					 * REST API call was successful. Add business logic. For
					 * e.g. to process the query results...
					 */
					if (response == null || response.asJSONObject() == null)
						return;

					JSONArray records = response.asJSONObject().getJSONArray(
							"items");

					if (records.length() == 0)
						return;

					List<Chatter> chatterResponseList = new ArrayList<Chatter>();
					List<Comment> commentResponseList = new ArrayList<Comment>();

					for (int i = 0; i < records.length(); i++) {
						Chatter r = new Chatter();
						JSONObject item = (JSONObject) records.get(i);
						JSONObject body = item.getJSONObject("body");
						JSONObject actor = item.getJSONObject("actor");
						JSONObject comments = item.getJSONObject("comments");
						JSONObject likes = item.getJSONObject("likes");

						r.setActor(actor.getString("name"));
						r.setChatterId(item.getString("id"));
						r.setCommentCount(comments.getInt("total"));
						r.setLikeCount(likes.getInt("total"));
						r.setPost(Html.fromHtml(body.getString("text"))
								.toString());

						Comment c = new Comment();

						if (r.getCommentCount() > 0) {
							JSONArray commentBody = comments
									.getJSONArray("comments");
							for (int j = 0; j < commentBody.length(); j++) {
								c = new Comment();
								JSONObject commentItem = (JSONObject) commentBody
										.get(j);
								JSONObject user = commentItem
										.getJSONObject("user");
								c.setAuthor(user.getString("name"));
								JSONObject commentText = commentItem
										.getJSONObject("body");
								c.setBody(Html.fromHtml(
										commentText.getString("text"))
										.toString());
								c.setChatterId(r.getChatterId());
								c.setCommentId(commentItem.getString("id"));
								commentResponseList.add(c);
							}
						}

						r.setComments(c);

						chatterResponseList.add(r);
					}
					dh = new DataHelper(ctx);
					Map<String, Chatter> savedChatterMap = dh
							.selectAllChatterMap();
					Map<String, Comment> savedCommentMap = dh
							.selectAllCommentMap();
					dh.close();

					List<String> smartWatchNotifications = new ArrayList<String>();

					Integer commentCount = prefs.getInt("commentCount", 0);
					Integer postCount = prefs.getInt("postCount", 0);
					Integer likeCount = prefs.getInt("likeCount", 0);
					String likeDesc = "";
					String postDesc = "";
					String commentDesc = "";
					Boolean shouldWriteLikeDesc = true;
					Boolean shouldWritePostDesc = true;
					Boolean shouldWriteCommentDesc = true;

					for (Chatter c : chatterResponseList) {
						if (savedChatterMap.containsKey(c.getChatterId())) {
							if (c.getCommentCount() != savedChatterMap.get(
									c.getChatterId()).getCommentCount()) {
								commentCount++;
							}
							if (c.getLikeCount() != savedChatterMap.get(
									c.getChatterId()).getLikeCount()) {
								int numOfLikes = c.getLikeCount()
										- savedChatterMap.get(c.getChatterId())
												.getLikeCount();
								if (prefs.getBoolean("checkLikes", false)) {
									smartWatchNotifications.add(String
											.valueOf(numOfLikes)
											+ " new like/s for: "
											+ c.getActor()
											+ " - "
											+ c.getPost());
								}
								likeCount += numOfLikes;
							}
						} else {
							if (c.getCommentCount() > 0) {
								commentCount++;
							}
							if (c.getLikeCount() > 0) {
								likeCount += c.getLikeCount();
							}
							if (prefs.getBoolean("checkPosts", true)) {
								smartWatchNotifications.add("New Post from:  "
										+ c.getActor() + " - " + c.getPost()
										+ "... Number of Likes: + "
										+ String.valueOf(c.getLikeCount()));
							}
							postCount++;
							savedChatterMap.put(c.getChatterId(), c);
						}
						if (((postCount) == 1) && shouldWritePostDesc) {
							shouldWritePostDesc = false;
							postDesc = c.getActor() + " - " + c.getPost();
						}
						if (((likeCount) == 1) && shouldWriteLikeDesc) {
							shouldWriteLikeDesc = false;
							likeDesc = c.getActor() + " - " + c.getPost();
						}

						if (((commentCount) == 1)
								&& shouldWriteCommentDesc
								&& (prefs.getInt("commentCount", 0) != commentCount)) {
							shouldWriteCommentDesc = false;
							try {
								commentDesc = c.getActor()
										+ " - "
										+ c.getPost().substring(
												0,
												Math.min(c.getPost().length(),
														20))
										+ "... Comment from: "
										+ c.getComments().getAuthor() + " - "
										+ c.getComments().getBody();
							} catch (Exception e) {
								commentDesc = "";
							}
						}
					}

					if (prefs.getBoolean("checkComments", true)) {
						for (Comment comment : commentResponseList) {
							if (!savedCommentMap.containsKey(comment
									.getCommentId())) {
								smartWatchNotifications.add("New Comment on Post:  "
										+ savedChatterMap.get(
												comment.getChatterId())
												.getActor()
										+ " - "
										+ savedChatterMap
												.get(comment.getChatterId())
												.getPost()
												.substring(
														0,
														Math.min(
																savedChatterMap
																		.get(comment
																				.getChatterId())
																		.getPost()
																		.length(),
																20))
										+ "... Comment from:  "
										+ comment.getAuthor()
										+ " - "
										+ comment.getBody());
							}
						}
					}

					String notificationTitle = "";
					String sysNotificationMessage = "";
					notificationMessage = "";
					if (prefs.getBoolean("checkPosts", true) && postCount > 0) {
						if (postCount == 1) {
							notificationTitle += String.valueOf(postCount)
									+ " new post. ";
							notificationMessage += String.valueOf(postCount)
									+ " new post. " + postDesc;
							sysNotificationMessage += postDesc;
						} else {
							sysNotificationMessage += String.valueOf(postCount)
									+ " new posts. ";
							notificationMessage += String.valueOf(postCount)
									+ " new posts. ";
							notificationTitle += String.valueOf(postCount)
									+ " new posts. ";
						}
					}
					if (prefs.getBoolean("checkComments", true)
							&& commentCount > 0) {
						if (commentCount == 1) {
							notificationTitle += String.valueOf(commentCount)
									+ " new comment. ";
							notificationMessage += String.valueOf(commentCount)
									+ " new comment. " + commentDesc;
							sysNotificationMessage += commentDesc;
						} else {
							notificationTitle += String.valueOf(commentCount)
									+ " new comments. ";
							notificationMessage += String.valueOf(commentCount)
									+ " new comments. ";
							sysNotificationMessage += String
									.valueOf(commentCount) + " new comments. ";
						}
					}
					if (prefs.getBoolean("checkLikes", false) && likeCount > 0) {
						if (likeCount == 1) {
							notificationTitle += String.valueOf(likeCount)
									+ " new like. ";
							notificationMessage += String.valueOf(likeCount)
									+ " new like. " + likeDesc;
							sysNotificationMessage += likeDesc;
						} else {
							notificationTitle += String.valueOf(likeCount)
									+ " new likes. ";
							notificationMessage += String.valueOf(likeCount)
									+ " new likes. ";
							sysNotificationMessage += String.valueOf(likeCount)
									+ " new likes. ";
						}
					}
					Boolean shouldShowSysNotification = (prefs.getInt(
							"commentCount", 0) != commentCount
							|| prefs.getInt("postCount", 0) != postCount || prefs
							.getInt("likeCount", 0) != likeCount) ? true
							: false;

					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("commentCount", commentCount);
					editor.putInt("postCount", postCount);
					editor.putInt("likeCount", likeCount);
					editor.commit();

					if (postCount + likeCount + commentCount != 0) {
						dh = new DataHelper(ctx);
						dh.deleteAllChatter();
						dh.insertChatterList(chatterResponseList);
						dh.insertCommentList(commentResponseList);
						dh.close();
					}

					if (!notificationMessage.equals("")) {
						if (prefs.getBoolean("firstRun", true)) {
							editor.putBoolean("firstRun", false);
							editor.putInt("commentCount", 0);
							editor.putInt("postCount", 0);
							editor.putInt("likeCount", 0);
							editor.commit();
						} else {
							if (prefs.getBoolean("displayNotification", true)
									&& shouldShowSysNotification) {
								showNotification(sysNotificationMessage,
										notificationTitle);
							}
							Log.d(TAG,
									"isConnectedToSmartWatch = "
											+ String.valueOf(isConnectedToSmartWatch));
							for (String msg : smartWatchNotifications) {
								showNotificationToSmartWatch(msg);
							}
						}
					}

					EventsObservable.get().notifyEvent(
							EventType.RenditionComplete);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Show notification to smart watch.
	 * 
	 * @param message
	 *            the message
	 */
	private void showNotificationToSmartWatch(String message) {
		long time = System.currentTimeMillis();
		long sourceId = NotificationUtil.getSourceId(this,
				EXTENSION_SPECIFIC_ID);
		if (sourceId == NotificationUtil.INVALID_ID) {
			Log.e(TAG, "sourceId:  " + String.valueOf(sourceId)
					+ "Failed to insert data");
			return;
		}
		String profileImage = ExtensionUtils.getUriString(this,
				R.drawable.widget_default_userpic_bg);

		ContentValues eventValues = new ContentValues();
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.EVENT_READ_STATUS,
						false);
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.DISPLAY_NAME,
						"Chatter Activity");
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.MESSAGE,
						message);
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.PERSONAL,
						1);
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.PROFILE_IMAGE_URI,
						profileImage);
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.PUBLISHED_TIME,
						time);
		eventValues
				.put(com.sonyericsson.extras.liveware.aef.notification.Notification.EventColumns.SOURCE_ID,
						sourceId);

		try {
			getContentResolver()
					.insert(com.sonyericsson.extras.liveware.aef.notification.Notification.Event.URI,
							eventValues);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "Failed to insert event", e);
		} catch (SecurityException e) {
			Log.e(TAG,
					"Failed to insert event, is Live Ware Manager installed?",
					e);
		} catch (SQLException e) {
			Log.e(TAG, "Failed to insert event", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sonyericsson.extras.liveware.extension.util.ExtensionService#onViewEvent
	 * (android.content.Intent)
	 */
	@Override
	protected void onViewEvent(Intent intent) {
		String action = intent
				.getStringExtra(com.sonyericsson.extras.liveware.aef.notification.Notification.Intents.EXTRA_ACTION);
		if (com.sonyericsson.extras.liveware.aef.notification.Notification.SourceColumns.ACTION_1
				.equals(action)) {
			Intent myIntent = new Intent(this, ChatterLauncherActivity.class);
			myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			NotificationService.this.startActivity(myIntent);
		}
	}

	/**
	 * Ran when when the service is destroyed.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		FlurryAgent.onEndSession(this);
	}

	/**
	 * Return the local binder.
	 * 
	 * @param intent
	 *            the intent
	 * @return the i binder
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// This is the object that receives interactions from clients. See
	// RemoteService for a more complete example.
	/** The m binder. */
	private final IBinder mBinder = new LocalBinder();

	/**
	 * Show a notification while this service is running.
	 * 
	 * @param message
	 *            the message
	 * @param title
	 *            the title
	 */
	private void showNotification(String message, String title) {
		CharSequence text = title;
		Notification notification = new Notification(R.drawable.icon_small,
				text, System.currentTimeMillis());
		// This is the intent that is fired off after the user clicks the
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, ChatterLauncherActivity.class), 0);
		notification.setLatestEventInfo(this, text, message, contentIntent);
		notification.defaults |= Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		mNM.notify(NOTIFICATION, notification);
	}

	/**
	 * Called when extension and sources has been successfully registered.
	 * Override this method to take action after a successful registration.
	 * 
	 * @param result
	 *            the result
	 */
	@Override
	public void onRegisterResult(boolean result) {
		super.onRegisterResult(result);

		isConnectedToSmartWatch = (result) ? true : false;
		Log.d(TAG,
				"isConnectedToSmartWatch = "
						+ String.valueOf(isConnectedToSmartWatch));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.ExtensionService#
	 * getRegistrationInformation()
	 */
	@Override
	protected RegistrationInformation getRegistrationInformation() {
		return new ChatterRegistrationInformation(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.ExtensionService#
	 * keepRunningWhenConnected()
	 */
	@Override
	protected boolean keepRunningWhenConnected() {
		return false;
	}
}
