package com.picus.email_campaign.service;

import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.entity.Contact;
import com.picus.email_campaign.mapper.ContactMapper;
import com.picus.email_campaign.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

	public List<ContactDTO> getAllContacts() {
		List<Contact> contactList = contactRepository.findAll();
		log.info("Contact list retrieved: {}", contactList.toString());
		return contactMapper.toContactDTOList(contactList);
	}

	public ContactDTO getContact(UUID id) {
		Optional<Contact> optionalContact = contactRepository.findById(id);
		if(optionalContact.isPresent()){
			Contact contactEntity =  optionalContact.get();
			log.info("Contact retrieved: {}", contactEntity.toString());
			return contactMapper.toContactDTO(contactEntity);
		}
		log.info("Contact with Id" + id + " not found");
		return null;
	}

	public ContactDTO updateContact(ContactDTO contactDTO) throws Exception {
		Contact origEntity = contactRepository.findById(contactDTO.getId()).orElseThrow(Exception::new);
		contactMapper.updateContactEntity(contactDTO, origEntity);
		contactRepository.save(origEntity);
		log.info("Contact updated: {}", origEntity.toString());

		return contactDTO;
	}

	public UUID deleteContact(UUID id) {
		if (contactRepository.existsById(id)) {
			contactRepository.deleteById(id);
			log.info("Contact deleted: {}", id.toString());
		} else {
			throw new NullPointerException("Contact with id " + id + " not found!");
		}
		return id;
	}

	public void extractContactListFromFile(MultipartFile uploadedFile) throws IOException {
		InputStream initialStream = uploadedFile.getInputStream();
		byte[] buffer = new byte[initialStream.available()];
		initialStream.read(buffer);
		addContactsFromUploadedFile(new String(buffer, StandardCharsets.UTF_8));
	}

	public void addContactsFromUploadedFile(String contactListFromFile) {
		String[] contacts = contactListFromFile.split(System.getProperty("line.separator"));
		List<ContactDTO> contactDTOList = new ArrayList<>();
		for(String contact:contacts){
			// TODO : lots of string operations here, change it!
			String[] contactDetails = contact.split("<");
			int i = contactDetails[0].substring(0, contactDetails[0].length()-1).lastIndexOf(" ");
			String[] nameAndSurnameOfContact =  {contactDetails[0].substring(0, i), contactDetails[0].substring(i)};
			String email = contactDetails[1].substring(0, contactDetails[1].length()-1);
			ContactDTO contactDTO = new ContactDTO();
			contactDTO.setName(nameAndSurnameOfContact[0]);
			contactDTO.setSurname(nameAndSurnameOfContact[1].substring(1,nameAndSurnameOfContact[1].length()-1));
			contactDTO.setEmailAddress(email);
			contactDTO.setMailSentToThisContact(false);
			contactDTO.setThisContactClickedTheLink(false);
			contactDTO.setElapsedTimeUntilClick((long) 0);
			contactDTOList.add(contactDTO);
		}
		try {
			this.addContacts(contactDTOList);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public boolean checkEmailAddressIsUnique(String emailAddress) throws Exception {
		if(contactRepository.existsByEmailAddress(emailAddress)){
			throw new Exception();
		}
		else {
			return true;
		}
	}

	public List<String> getContactEmailList() {
		List<Contact> contactList = contactRepository.findAll();
		List<String> emailList = new ArrayList<>();
		for (Contact contact:contactList) {
			emailList.add(contact.getEmailAddress());
		}
		log.info("Email address list retrieved: {}", emailList.toString());
		return emailList;
	}
}
