package com.picus.email_campaign.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Data
public class ContactDTO {
	private UUID id;
	private String name;
	private String surname;
	private String emailAddress;
	private boolean isMailSentToThisContact;
	private boolean isThisContactClickedTheLink;
	private Long elapsedTimeUntilClick;
}
