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

import com.salesforce.androidsdk.ui.SalesforceR;

/**
 * Since the SalesforceSDK is packaged as a jar, it can't have resources. Class
 * that allows references to resources defined outside the SDK.
 */
public class SalesforceRImpl extends SalesforceR {
	/* Login */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringAccountType()
	 */
	public int stringAccountType() {
		return R.string.account_type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#layoutLogin()
	 */
	public int layoutLogin() {
		return R.layout.login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idLoginWebView()
	 */
	public int idLoginWebView() {
		return R.id.oauth_webview;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringGenericError()
	 */
	public int stringGenericError() {
		return R.string.generic_error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#
	 * stringGenericAuthenticationErrorTitle()
	 */
	public int stringGenericAuthenticationErrorTitle() {
		return R.string.generic_authentication_error_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringGenericAuthenticationErrorBody
	 * ()
	 */
	public int stringGenericAuthenticationErrorBody() {
		return R.string.generic_authentication_error;
	}

	/* Passcode */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#layoutPasscode()
	 */
	public int layoutPasscode() {
		return R.layout.passcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idPasscodeTitle()
	 */
	public int idPasscodeTitle() {
		return R.id.passcode_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idPasscodeError()
	 */
	public int idPasscodeError() {
		return R.id.passcode_error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idPasscodeInstructions()
	 */
	public int idPasscodeInstructions() {
		return R.id.passcode_instructions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idPasscodeText()
	 */
	public int idPasscodeText() {
		return R.id.passcode_text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeCreateTitle()
	 */
	public int stringPasscodeCreateTitle() {
		return R.string.passcode_create_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeEnterTitle()
	 */
	public int stringPasscodeEnterTitle() {
		return R.string.passcode_enter_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeConfirmTitle()
	 */
	public int stringPasscodeConfirmTitle() {
		return R.string.passcode_confirm_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeEnterInstructions
	 * ()
	 */
	public int stringPasscodeEnterInstructions() {
		return R.string.passcode_enter_instructions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeCreateInstructions
	 * ()
	 */
	public int stringPasscodeCreateInstructions() {
		return R.string.passcode_create_instructions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeConfirmInstructions
	 * ()
	 */
	public int stringPasscodeConfirmInstructions() {
		return R.string.passcode_confirm_instructions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeMinLength()
	 */
	public int stringPasscodeMinLength() {
		return R.string.passcode_min_length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeTryAgain()
	 */
	public int stringPasscodeTryAgain() {
		return R.string.passcode_try_again;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringPasscodeFinal()
	 */
	public int stringPasscodeFinal() {
		return R.string.passcode_final;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringPasscodesDontMatch()
	 */
	public int stringPasscodesDontMatch() {
		return R.string.passcodes_dont_match;
	}

	/* Server picker */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idPickerCustomLabel()
	 */
	public int idPickerCustomLabel() {
		return R.id.picker_custom_label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idPickerCustomUrl()
	 */
	public int idPickerCustomUrl() {
		return R.id.picker_custom_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringServerUrlDefaultCustomLabel
	 * ()
	 */
	public int stringServerUrlDefaultCustomLabel() {
		return R.string.server_url_default_custom_label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.salesforce.androidsdk.ui.SalesforceR#stringServerUrlDefaultCustomUrl
	 * ()
	 */
	public int stringServerUrlDefaultCustomUrl() {
		return R.string.server_url_default_custom_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringServerUrlAddTitle()
	 */
	public int stringServerUrlAddTitle() {
		return R.string.server_url_add_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringServerUrlEditTitle()
	 */
	public int stringServerUrlEditTitle() {
		return R.string.server_url_edit_title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#layoutCustomServerUrl()
	 */
	public int layoutCustomServerUrl() {
		return R.layout.custom_server_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idApplyButton()
	 */
	public int idApplyButton() {
		return R.id.apply_button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idCancelButton()
	 */
	public int idCancelButton() {
		return R.id.cancel_button;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringInvalidServerUrl()
	 */
	public int stringInvalidServerUrl() {
		return R.string.invalid_server_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idServerListGroup()
	 */
	public int idServerListGroup() {
		return R.id.server_list_group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#layoutServerPicker()
	 */
	public int layoutServerPicker() {
		return R.layout.server_picker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringAuthLoginProduction()
	 */
	public int stringAuthLoginProduction() {
		return R.string.auth_login_production;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#stringAuthLoginSandbox()
	 */
	public int stringAuthLoginSandbox() {
		return R.string.auth_login_sandbox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#menuClearCustomUrl()
	 */
	public int menuClearCustomUrl() {
		return R.menu.clear_custom_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idMenuClearCustomUrl()
	 */
	public int idMenuClearCustomUrl() {
		return R.id.menu_clear_custom_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#drawableEditIcon()
	 */
	public int drawableEditIcon() {
		return R.drawable.edit_icon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.salesforce.androidsdk.ui.SalesforceR#idShowCustomUrlEdit()
	 */
	public int idShowCustomUrlEdit() {
		return R.id.show_custom_url_edit;
	}
}
