package com.picus.email_campaign.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "groups")
public class Group {
	@Id
	@GeneratedValue
	private UUID id;
	@ElementCollection
	private List<UUID> contactsIdList;
}
