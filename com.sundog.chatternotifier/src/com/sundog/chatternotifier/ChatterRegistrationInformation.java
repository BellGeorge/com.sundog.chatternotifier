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

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.sonyericsson.extras.liveware.aef.notification.Notification;
import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation;
import com.sonyericsson.extras.liveware.sdk.R;

/**
 * The Class ChatterRegistrationInformation.
 */
public class ChatterRegistrationInformation extends RegistrationInformation {

	/** The m context. */
	final Context mContext;

	/**
	 * Create notification registration object.
	 * 
	 * @param context
	 *            The context
	 */
	protected ChatterRegistrationInformation(Context context) {
		if (context == null) {
			throw new IllegalArgumentException("context == null");
		}
		mContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.registration.
	 * RegistrationInformation#getRequiredNotificationApiVersion()
	 */
	@Override
	public int getRequiredNotificationApiVersion() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.registration.
	 * RegistrationInformation#getRequiredWidgetApiVersion()
	 */
	@Override
	public int getRequiredWidgetApiVersion() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.registration.
	 * RegistrationInformation#getRequiredControlApiVersion()
	 */
	@Override
	public int getRequiredControlApiVersion() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.registration.
	 * RegistrationInformation#getRequiredSensorApiVersion()
	 */
	@Override
	public int getRequiredSensorApiVersion() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.registration.
	 * RegistrationInformation#getExtensionRegistrationConfiguration()
	 */
	@Override
	public ContentValues getExtensionRegistrationConfiguration() {
		String extensionIcon = ExtensionUtils.getUriString(mContext,
				R.drawable.icn_30x30_message_notification);
		String iconHostapp = ExtensionUtils.getUriString(mContext,
				R.drawable.ic_launcher);

		String configurationText = mContext
				.getString(R.string.configuration_text);
		String extensionName = mContext.getString(R.string.extension_name);

		ContentValues values = new ContentValues();
		values.put(Registration.ExtensionColumns.CONFIGURATION_TEXT,
				configurationText);
		values.put(Registration.ExtensionColumns.EXTENSION_ICON_URI,
				extensionIcon);
		values.put(Registration.ExtensionColumns.EXTENSION_KEY,
				NotificationService.EXTENSION_KEY);
		values.put(Registration.ExtensionColumns.HOST_APP_ICON_URI, iconHostapp);
		values.put(Registration.ExtensionColumns.NAME, extensionName);
		values.put(Registration.ExtensionColumns.NOTIFICATION_API_VERSION,
				getRequiredNotificationApiVersion());
		values.put(Registration.ExtensionColumns.PACKAGE_NAME,
				mContext.getPackageName());

		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sonyericsson.extras.liveware.extension.util.registration.
	 * RegistrationInformation#getSourceRegistrationConfigurations()
	 */
	@Override
	public ContentValues[] getSourceRegistrationConfigurations() {
		List<ContentValues> bulkValues = new ArrayList<ContentValues>();
		bulkValues
				.add(getSourceRegistrationConfiguration(NotificationService.EXTENSION_SPECIFIC_ID));
		return bulkValues.toArray(new ContentValues[bulkValues.size()]);
	}

	/**
	 * Get source configuration associated with extensions specific id.
	 * 
	 * @param extensionSpecificId
	 *            the extension specific id
	 * @return The source configuration
	 */
	public ContentValues getSourceRegistrationConfiguration(
			String extensionSpecificId) {
		ContentValues sourceValues = null;

		String iconSource1 = ExtensionUtils.getUriString(mContext,
				R.drawable.icn_30x30_message_notification);
		String iconSource2 = ExtensionUtils.getUriString(mContext,
				R.drawable.icn_18x18_message_notification);
		String iconBw = ExtensionUtils.getUriString(mContext,
				R.drawable.icn_18x18_black_white_message_notification);
		String actionEvent1 = mContext.getString(R.string.action_event_1);
		// String textToSpeech = mContext.getString(R.string.text_to_speech);

		sourceValues = new ContentValues();
		sourceValues.put(Notification.SourceColumns.ACTION_1, actionEvent1);
		sourceValues.put(Notification.SourceColumns.ENABLED, true);
		sourceValues.put(Notification.SourceColumns.ICON_URI_1, iconSource1);
		sourceValues.put(Notification.SourceColumns.ICON_URI_2, iconSource2);
		sourceValues.put(Notification.SourceColumns.ICON_URI_BLACK_WHITE,
				iconBw);
		sourceValues.put(Notification.SourceColumns.UPDATE_TIME,
				System.currentTimeMillis());
		sourceValues.put(Notification.SourceColumns.NAME,
				mContext.getString(R.string.source_name));
		sourceValues.put(Notification.SourceColumns.EXTENSION_SPECIFIC_ID,
				extensionSpecificId);
		sourceValues.put(Notification.SourceColumns.PACKAGE_NAME,
				mContext.getPackageName());

		return sourceValues;
	}

}
