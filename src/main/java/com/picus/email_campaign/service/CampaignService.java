package com.picus.email_campaign.service;

import com.picus.email_campaign.dto.CampaignDTO;
import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.dto.CampaignDTO;
import com.picus.email_campaign.entity.Campaign;
import com.picus.email_campaign.mapper.CampaignMapper;
import com.picus.email_campaign.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampaignService {

	private final CampaignMapper campaignMapper;
	private final CampaignRepository campaignRepository;

	public CampaignDTO addCampaign(CampaignDTO campaignDto) throws Exception{
		Campaign campaign = campaignMapper.toCampaignEntity(campaignDto);
		Campaign campaignEntity = campaignRepository.save(campaign);
		log.info("Campaign created : {}", campaignEntity.toString());
		return campaignMapper.toCampaignDto(campaignEntity);
	}

	public List<CampaignDTO> getCampaigns() {
		List<Campaign> campaignList = campaignRepository.findAll();
		log.info("Campaign list retrieved");
		return campaignMapper.toCampaignDTOList(campaignList);
	}

	public CampaignDTO updateCampaign(CampaignDTO campaignDto) throws Exception {

		Campaign origEntity = campaignRepository.findById(campaignDto.getId()).orElseThrow(Exception::new);
		campaignMapper.updateEntity(campaignDto, origEntity);
		campaignRepository.save(origEntity);
		log.info("Campaign updated: {}", origEntity.toString());

		return campaignDto;
	}

	public UUID deleteCampaign(UUID id){
		if (campaignRepository.existsById(id)) {
			campaignRepository.deleteById(id);
			log.info("Campaign deleted: {}", id.toString());

		} else {
			throw new NullPointerException("Campaign with id " + id + " not found!");
		}
		return id;
	}
}
