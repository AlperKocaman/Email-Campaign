package com.picus.email_campaign.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GroupDTO {
	private UUID id;
	private List<UUID> contactsIdList;
}
