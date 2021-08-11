package com.picus.email_campaign.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmailDTO {
	private List<String> contactEmailAddressList;
	private String topic;
	private String body;
}
