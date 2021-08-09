package com.picus.email_campaign.controller;


import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.service.ContactService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "api")
@RequiredArgsConstructor
@Slf4j
@Data
public class ApiController {

	private final ContactService contactService;

	@PostMapping("/addContact")
	public ContactDTO addContact(@RequestBody ContactDTO contact) throws Exception {
		return contactService.addContact(contact);
	}

	@PostMapping("/addContacts")
	public List<ContactDTO> addContacts(@RequestBody List<ContactDTO> contactList) throws Exception {
		return contactService.addContacts(contactList);
	}
}
