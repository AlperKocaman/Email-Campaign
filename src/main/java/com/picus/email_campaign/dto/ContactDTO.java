package com.picus.email_campaign.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ContactDTO {
	private UUID id;
	private String name;
	private String surname;
	private String emailAddress;
	private boolean isMailSentToThisContact;
	private boolean isThisContactClickedTheLink;
}
