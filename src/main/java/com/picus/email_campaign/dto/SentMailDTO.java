package com.picus.email_campaign.dto;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class SentMailDTO {
	private UUID id;
	private String sentEmailAddress;
	private Long sentTime;
	private String hashString;
}
