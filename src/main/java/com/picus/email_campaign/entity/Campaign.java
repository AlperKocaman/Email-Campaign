package com.picus.email_campaign.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "campaign")
public class Campaign {
	@Id
	@GeneratedValue
	private UUID id;
	private String campaignExplanation;
}
