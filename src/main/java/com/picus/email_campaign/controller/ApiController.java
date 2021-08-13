package com.picus.email_campaign.controller;

import com.picus.email_campaign.dto.*;
import com.picus.email_campaign.service.CampaignService;
import com.picus.email_campaign.service.ContactService;
import com.picus.email_campaign.service.EmailService;
import com.picus.email_campaign.service.GroupService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api")
@RequiredArgsConstructor
@Slf4j
@Data
public class ApiController {

	private final ContactService contactService;
	private final EmailService emailService;
	private final GroupService groupService;
	private final CampaignService campaignService;

	// Contact related APIs

	// Create

	@PostMapping("/addContact")
	public ContactDTO addContact(@RequestBody ContactDTO contact) throws Exception {
		return contactService.addContact(contact);
	}

	@PostMapping("/addContacts")
	public List<ContactDTO> addContacts(@RequestBody List<ContactDTO> contactList) throws Exception {
		return contactService.addContacts(contactList);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(MultipartFile file) {
		try {
			contactService.extractContactListFromFile(file);
			return ResponseEntity.status(HttpStatus.OK).body("OK");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("FAILED");
		}
	}

	// Read

	@GetMapping("/contacts")
	public List<ContactDTO> getContactList() {
		return contactService.getAllContacts();
	}

	@GetMapping("/getContact/{id}")
	public ContactDTO getContact(@PathVariable UUID id) {
		return contactService.getContact(id);
	}

	// Update

	@PutMapping("/updateContact/{id}")
	public ContactDTO updateContact(@PathVariable UUID id, @RequestBody ContactDTO contactDTO) throws Exception {
		return contactService.updateContact(contactDTO);
	}

	// Delete

	@DeleteMapping("/deleteContact/{id}")
	public UUID deleteContact(@PathVariable UUID id) throws Exception {
		return contactService.deleteContact(id);
	}

	// Email related APIs

	@GetMapping("/emailList")
	public List<String> getContactsEmailList() {
		return contactService.getContactEmailList();
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/send")
	public List<SentMailDTO> sendMail(@RequestBody EmailDTO emailDTO)
			throws Exception {
		return emailService.sendEmailToMultipleContacts(emailDTO);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/click/{id}")
	public void handleLinkClick(@PathVariable String id) {
		emailService.handleLinkClick(id);
	}

	// Group related APIs

	// Create
	@PostMapping("/addGroup")
	public GroupDTO addGroup(@RequestBody GroupDTO group) throws Exception {
		return groupService.addGroup(group);
	}

	// Read
	@GetMapping("/getGroups")
	public List<List<ContactDTO>> getGroupList() {
		return groupService.getGroups();
	}

	// Update
	@PutMapping("/updateGroup/{id}")
	public GroupDTO updateGroup(@PathVariable UUID id, @RequestBody GroupDTO groupDTO) throws Exception {
		return groupService.updateGroup(groupDTO);
	}

	// Delete
	@DeleteMapping("/deleteGroup/{id}")
	public UUID deleteGroup(@PathVariable UUID id) throws Exception {
		return groupService.deleteGroup(id);
	}

	// Campaign related APIs

	// Create
	@PostMapping("/addCampaign")
	public CampaignDTO addCampaign(@RequestBody CampaignDTO campaign) throws Exception {
		return campaignService.addCampaign(campaign);
	}

	// Read
	@GetMapping("/getCampaigns")
	public List<CampaignDTO> getCampaigns() {
		return campaignService.getCampaigns();
	}

	// Update
	@PutMapping("/updateCampaign/{id}")
	public CampaignDTO updateCampaign(@PathVariable UUID id, @RequestBody CampaignDTO campaignDTO) throws Exception {
		return campaignService.updateCampaign(campaignDTO);
	}

	// Delete
	@DeleteMapping("/deleteCampaign/{id}")
	public UUID deleteCampaign(@PathVariable UUID id) throws Exception {
		return campaignService.deleteCampaign(id);
	}
}
