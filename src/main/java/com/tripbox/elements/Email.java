package com.tripbox.elements;

import java.util.ArrayList;

public class Email {
	private String invitationUrl;
	private ArrayList<String> emails = new ArrayList<String>();
	
	public Email(){};
	
	public Email(String invitationUrl, ArrayList<String> emails) {
		super();
		this.invitationUrl = invitationUrl;
		this.emails = emails;
	}
	public String getInvitationUrl() {
		return invitationUrl;
	}
	public void setInvitationUrl(String invitationUrl) {
		this.invitationUrl = invitationUrl;
	}
	public ArrayList<String> getEmails() {
		return emails;
	}
	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}
}
