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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sonyericsson.extras.liveware.extension.util.notification.NotificationUtil;

/**
 * The Class ChatterLauncherActivity.
 */
public class ChatterLauncherActivity extends Activity {

	/** The tag. */
	private String TAG;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String packageName = "com.salesforce.chatter";
		TAG = getString(R.string.tag);

		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext()).edit();
		editor.putInt("commentCount", 0);
		editor.putInt("postCount", 0);
		editor.putInt("likeCount", 0);
		editor.commit();

		try {
			Intent LaunchIntent = getPackageManager()
					.getLaunchIntentForPackage(packageName);
			startActivity(LaunchIntent);
		} catch (Exception e) {
			try {
				Uri marketUri = Uri.parse("market://details?id=" + packageName);
				Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
				startActivity(marketIntent);
			} catch (Exception ex) {

			}
		}
		try {
			new ClearEventsTask().execute();
		} catch (Exception e) {

		}
		finish();
	}

	/**
	 * Clear all messaging events.
	 */
	private class ClearEventsTask extends AsyncTask<Void, Void, Integer> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		protected void onPreExecute() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		protected Integer doInBackground(Void... params) {
			int nbrDeleted = 0;
			nbrDeleted = NotificationUtil
					.deleteAllEvents(ChatterLauncherActivity.this);
			return nbrDeleted;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(Integer id) {
			if (id != NotificationUtil.INVALID_ID) {
				Log.d(TAG, "Clear notifications was successful");
			} else {
				Log.d(TAG, "Clear notifications was not successful");
			}
		}

	}

}
