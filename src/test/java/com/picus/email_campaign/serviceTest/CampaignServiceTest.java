package com.picus.email_campaign.serviceTest;

import com.picus.email_campaign.dto.CampaignDTO;
import com.picus.email_campaign.entity.Campaign;
import com.picus.email_campaign.mapper.CampaignMapper;
import com.picus.email_campaign.repository.CampaignRepository;
import com.picus.email_campaign.service.CampaignService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignServiceTest {

	@Autowired
	CampaignService campaignService;

	@Autowired
	CampaignMapper campaignMapper;

	@MockBean
	CampaignRepository campaignRepository;

	@Test
	public void addCampaignTest() throws Exception {
		// CampaignDTO to be saved with no id
		CampaignDTO campaignDTOtoBeSaved = new CampaignDTO();
		campaignDTOtoBeSaved.setCampaignExplanation("This is the campaign we'll save");

		// Campaign Entity
		Campaign campaign = new Campaign();
		campaign.setId(UUID.randomUUID());
		campaign.setCampaignExplanation(campaignDTOtoBeSaved.getCampaignExplanation());

		// Test whether campaign explanations are exactly the same or not
		when(campaignRepository.save(campaignMapper.toCampaignEntity(campaignDTOtoBeSaved))).thenReturn(campaign);
		CampaignDTO returnCampaignDTO = campaignService.addCampaign(campaignDTOtoBeSaved);
		Assert.assertEquals(true, returnCampaignDTO.getCampaignExplanation().equals(campaignDTOtoBeSaved.getCampaignExplanation()));
	}

	@Test
	public void getCampaignsTest() {
		// Campaign 1
		Campaign campaign1 = new Campaign();
		campaign1.setId(UUID.randomUUID());
		campaign1.setCampaignExplanation("This is the first campaign we offer to you!");

		// Campaign 2
		Campaign campaign2 = new Campaign();
		campaign2.setId(UUID.randomUUID());
		campaign2.setCampaignExplanation("This is the second campaign we -only- offer to you!");

		// Return list of campaigns
		List<Campaign> campaignList = new ArrayList<>();
		campaignList.add(campaign1);
		campaignList.add(campaign2);

		// Test whether campaign lists are same or not
		when(campaignRepository.findAll()).thenReturn(campaignList);
		List<CampaignDTO> returnedList = campaignService.getCampaigns();
		Assert.assertEquals(true, campaignMapper.toCampaignDTOList(campaignList).equals(returnedList));
	}

	@Test
	public void updateCampaignTest() {
		// CampaignDTO which will update entity
		CampaignDTO campaignDTOtoUpdate = new CampaignDTO();
		campaignDTOtoUpdate.setId(UUID.randomUUID());
		campaignDTOtoUpdate.setCampaignExplanation("This field is new and will update entity!");

		// Campaign entity which will be updated by dto
		Campaign oldCampaign = new Campaign();
		oldCampaign.setId(campaignDTOtoUpdate.getId()); // ids should be same
		oldCampaign.setCampaignExplanation("This field is outdated!");

		// Test to update outdated entity
		when(campaignRepository.findById(campaignDTOtoUpdate.getId())).thenReturn(java.util.Optional.of(oldCampaign));
		campaignMapper.updateEntity(campaignDTOtoUpdate, oldCampaign); // if update is successful, the remainder work is repository work
		Assert.assertEquals(campaignDTOtoUpdate.getCampaignExplanation(), oldCampaign.getCampaignExplanation());
	}

	@Test
	public void deleteCampaignTest() {
		// id of the campaign
		UUID id = UUID.randomUUID();

		// Campaign to be deleted
		Campaign campaign = new Campaign();
		campaign.setId(id);
		campaign.setCampaignExplanation("This is the campaign to be deleted.");

		when(campaignRepository.existsById(id)).thenReturn(true);
		campaignService.deleteCampaign(id);
		verify(campaignRepository, times(1)).deleteById(id);
	}
}
