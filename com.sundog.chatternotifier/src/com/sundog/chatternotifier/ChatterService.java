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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.flurry.android.FlurryAgent;

/**
 * The Class SampleService.
 */
public class ChatterService extends Service {

	/** The ctx. */
	public Context ctx = this;

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
		ChatterService getService() {
			return ChatterService.this;
		}
	}

	/**
	 * Creates the service.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
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

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		// This is the method we would put the code that does the work
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(this, NotificationService.class);
		i.setAction(NotificationService.INTENT_ACTION_START);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				Long.valueOf(prefs.getString("interval", "1800000")), pi);
		return START_STICKY;
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
}
