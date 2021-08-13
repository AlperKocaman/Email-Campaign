package com.picus.email_campaign.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "sent_mails")
public class SentMail {
	@Id
	@GeneratedValue
	private UUID id;
	private String sentEmailAddress;
	private Long sentTime;
	private String hashString;
}
