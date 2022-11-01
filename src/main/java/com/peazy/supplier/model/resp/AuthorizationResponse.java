package com.peazy.supplier.model.resp;

import java.io.Serializable;

public class AuthorizationResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2834409284828426814L;
	private UserProfile userProfile;

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "AuthorizationResponse [userProfile=" + userProfile + "]";
	}

}