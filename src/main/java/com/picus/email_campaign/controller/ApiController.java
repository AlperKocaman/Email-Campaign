package com.picus.email_campaign.controller;


import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.dto.EmailDTO;
import com.picus.email_campaign.dto.GroupDTO;
import com.picus.email_campaign.dto.SentMailDTO;
import com.picus.email_campaign.entity.Contact;
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

	@GetMapping("/contacts")
	public List<ContactDTO> getContactList() {
		return contactService.getAllContacts();
	}

	@GetMapping("/emailList")
	public List<String> getContactsEmailList() {
		return contactService.getContactEmailList();
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

	@PostMapping("/addContact")
	public ContactDTO addContact(@RequestBody ContactDTO contact) throws Exception {
		return contactService.addContact(contact);
	}

	@PostMapping("/addContacts")
	public List<ContactDTO> addContacts(@RequestBody List<ContactDTO> contactList) throws Exception {
		return contactService.addContacts(contactList);
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
	public GroupDTO updateProblem(@PathVariable UUID id, @RequestBody GroupDTO groupDTO) throws Exception {
		return groupService.updateGroup(groupDTO);
	}

	// Delete
	@DeleteMapping("/deleteGroup/{id}")
	public UUID deleteGroup(@PathVariable UUID id) throws Exception {
		return groupService.deleteGroup(id);
	}
}
