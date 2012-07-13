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

import org.acra.ACRA;

import android.app.Activity;
import android.webkit.CookieSyncManager;

import com.salesforce.androidsdk.app.ForceApp;
import com.salesforce.androidsdk.auth.HttpAccess;
import com.salesforce.androidsdk.security.Encryptor;
import com.salesforce.androidsdk.ui.SalesforceR;
import com.salesforce.androidsdk.util.EventsObservable;
import com.salesforce.androidsdk.util.EventsObservable.EventType;

/**
 * Application class for our application.
 */
public class ChatterNotifier extends ForceApp {

	/** The salesforce r. */
	private SalesforceR salesforceR = new SalesforceRImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.app.ForceApp#onCreate()
	 */
	@Override
	public void onCreate() {
		// The following line triggers the initialization of ACRA
		ACRA.init(this);

		// Initialize encryption module
		Encryptor.init(this);

		// Initialize the http client
		String extendedUserAgent = getUserAgent() + " Native";
		HttpAccess.init(this, extendedUserAgent);

		// Ensure we have a CookieSyncManager
		CookieSyncManager.createInstance(this);

		// Done
		APP = this;
		EventsObservable.get().notifyEvent(EventType.AppCreateComplete);

		super.onCreate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.app.ForceApp#getMainActivityClass()
	 */
	@Override
	public Class<? extends Activity> getMainActivityClass() {
		return MainActivity.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.app.ForceApp#getLockTimeoutMinutes()
	 */
	@Override
	public int getLockTimeoutMinutes() {
		return 0; // return a non-zero value to use passcode
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.app.ForceApp#getKey(java.lang.String)
	 */
	@Override
	protected String getKey(String name) {
		return Encryptor.hash(name + "x;lksalk1jsadihh23lia;lsdhasd2", name
				+ "112;kaslkxs0-12;skcxn1203ph");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.app.ForceApp#getSalesforceR()
	 */
	@Override
	public SalesforceR getSalesforceR() {
		return salesforceR;
	}
}
