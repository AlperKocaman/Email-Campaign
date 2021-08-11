package com.picus.email_campaign.controller;


import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.service.ContactService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(value = "api")
@RequiredArgsConstructor
@Slf4j
@Data
public class ApiController {

	private final ContactService contactService;

	@GetMapping("/contacts")
	public List<ContactDTO> getContactList() {
		return contactService.getAllContacts();
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
}
