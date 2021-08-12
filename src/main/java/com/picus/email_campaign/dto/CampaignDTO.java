package com.picus.email_campaign.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CampaignDTO {
	private UUID id;
	private String campaignExplanation;
}
