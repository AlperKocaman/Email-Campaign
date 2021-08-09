package com.picus.email_campaign.service;

import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.entity.Contact;
import com.picus.email_campaign.mapper.ContactMapper;
import com.picus.email_campaign.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {
	private final ContactMapper contactMapper;
	private final ContactRepository contactRepository;

	public ContactDTO addContact(ContactDTO contactDTO) throws Exception {
		Contact contact = contactMapper.toContactEntity(contactDTO);
		if(checkEmailAddressIsUnique(contact.getEmailAddress())){
			Contact entity=contactRepository.save(contact);
			log.info("Contact saved: {}", contact.toString());
			return contactMapper.toContactDTO(entity);
		}
		else throw new Exception();
	}

	public List<ContactDTO> addContacts(List<ContactDTO> contactDTOList) throws Exception {
		for(ContactDTO contactDTO: contactDTOList) {
			this.addContact(contactDTO);
		}
		return contactDTOList;
	}

	public boolean checkEmailAddressIsUnique(String emailAddress) throws Exception {
		if(contactRepository.existsByEmailAddress(emailAddress)){
			throw new Exception();
		}
		else {
			return true;
		}
	}
}
