package com.picus.email_campaign.mapper;

import com.picus.email_campaign.dto.CampaignDTO;
import com.picus.email_campaign.dto.GroupDTO;
import com.picus.email_campaign.entity.Campaign;
import com.picus.email_campaign.entity.Group;
import org.mapstruct.MappingTarget;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CampaignMapper {
	CampaignDTO toCampaignDto(Campaign campaign);
	Campaign toCampaignEntity(CampaignDTO dto);
	List<CampaignDTO> toCampaignDTOList(List<Campaign> entity);
	List<Campaign> toCampaignEntityList(List<CampaignDTO> entity);
	void updateEntity(CampaignDTO dto, @MappingTarget Campaign entity);
}
