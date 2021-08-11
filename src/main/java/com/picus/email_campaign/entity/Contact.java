package com.picus.email_campaign.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "contacts")
public class Contact {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String surname;
	private String emailAddress;
	private boolean isMailSentToThisContact;
	private boolean isThisContactClickedTheLink;
	private Long elapsedTimeUntilClick;
}
