package com.picus.email_campaign.mapper;

import com.picus.email_campaign.dto.SentMailDTO;
import com.picus.email_campaign.entity.SentMail;

@org.mapstruct.Mapper(componentModel = "spring")
public interface SentMailMapper {

	SentMailDTO toSentMailDTO(SentMail sentMail);
	SentMail toSentMailEntity(SentMailDTO sentMailDTO);
}
