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

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.View;

import com.flurry.android.FlurryAgent;
import com.salesforce.androidsdk.app.ForceApp;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.ClientManager.LoginOptions;
import com.salesforce.androidsdk.rest.ClientManager.RestClientCallback;
import com.salesforce.androidsdk.rest.RestClient;

/**
 * The Class MainActivity.
 */
public class MainActivity extends PreferenceActivity {

	/** The client. */
	private RestClient client;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add the preferences from our predefined xml file
		addPreferencesFromResource(R.xml.preferences);
		setContentView(R.layout.preflayout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

		// Hide everything until we are logged in
		findViewById(R.id.root).setVisibility(View.INVISIBLE);

		// Login options
		String accountType = ForceApp.APP.getAccountType();
		LoginOptions loginOptions = new LoginOptions(
				null, // login host is chosen by user through the server picker
				ForceApp.APP.getPasscodeHash(),
				getString(R.string.oauth_callback_url),
				getString(R.string.oauth_client_id), new String[] { "api" });

		// Get a rest client
		new ClientManager(this, accountType, loginOptions).getRestClient(this,
				new RestClientCallback() {
					@Override
					public void authenticatedRestClient(RestClient c) {
						if (c == null) {
							ForceApp.APP.logout(MainActivity.this);
							return;
						}

						client = c;

						// Show everything
						findViewById(R.id.root).setVisibility(View.VISIBLE);
						if (client != null) {
						}
					}
				});
	}

	/**
	 * Called when key's are pressed.
	 * 
	 * @param keyCode
	 *            the key code
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check to see if it was the back key
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (myServiceIsRunning()) {
				stopService(new Intent(this, ChatterService.class));
			}
			this.startService(new Intent(this, ChatterService.class));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Prevents the activity from restarted on orientation change.
	 * 
	 * @param newConfig
	 *            the new config
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * Called when "Logout" button is clicked.
	 * 
	 * @param v
	 *            the v
	 */
	public void onLogoutClick(View v) {
		ForceApp.APP.logout(this);
	}

	/**
	 * My service is running.
	 * 
	 * @return true, if successful
	 */
	private boolean myServiceIsRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.sundog.chatternotifier.ChatterService"
					.equalsIgnoreCase(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, getString(R.string.flurry_key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.preference.PreferenceActivity#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

}
