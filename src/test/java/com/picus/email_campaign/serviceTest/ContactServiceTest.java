package com.picus.email_campaign.serviceTest;

import com.picus.email_campaign.EmailCampaignApplication;
import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.entity.Contact;
import com.picus.email_campaign.mapper.ContactMapper;
import com.picus.email_campaign.repository.ContactRepository;
import com.picus.email_campaign.service.ContactService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.picus.email_campaign.serviceTest.ContactUtil.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmailCampaignApplication.class)
public class ContactServiceTest {

	@Autowired
	ContactService contactService;

	@Autowired
	ContactMapper contactMapper;

	@MockBean
	ContactRepository contactRepository;

	@Test
	public void addContactTest() throws Exception {
		ContactDTO contactDTOtoBeSaved = contactMapper.toContactDTO(getContact1().get());
		contactDTOtoBeSaved.setId(null); // delete previous id
		when(contactRepository.save(contactMapper.toContactEntity(contactDTOtoBeSaved))).thenReturn(getContact1().get());
		ContactDTO returnContactDTO = contactService.addContact(contactDTOtoBeSaved);
		returnContactDTO.setId(null); // because first dto id is null
		Assert.assertEquals(returnContactDTO, contactDTOtoBeSaved);
	}

	@Test
	public void getContactTest() throws Exception {
		ContactDTO contactDTOshouldBeRetrieved = contactMapper.toContactDTO(getContact1().get());
		when(contactRepository.findById(getContactId(1))).thenReturn(getContact1());
		ContactDTO returnContactDTO = contactService.getContact(getContactId(1));
		Assert.assertEquals(returnContactDTO, contactDTOshouldBeRetrieved);
	}

	@Test
	public void getAllContactsTest() throws Exception {
		List<ContactDTO> contactDTOListshouldBeRetrieved = getExampleContactDTOList();
		when(contactRepository.findAll()).thenReturn(getExampleContactList());
		List<ContactDTO>  returnContactDTOList = contactService.getAllContacts();
		Assert.assertEquals(contactDTOListshouldBeRetrieved, returnContactDTOList);
	}

	@Test
	public void updateContactTest() {
		// ContactDTO which will update entity
		ContactDTO contactDTOtoUpdate = contactMapper.toContactDTO(getContact1().get());
		contactDTOtoUpdate.setName("Contact 1");

		// Contact entity which will be updated by dto
		Contact oldContact = getContact1().get();

		// Test to update outdated entity
		when(contactRepository.findById(getContactId(1))).thenReturn(getContact1());
		contactMapper.updateContactEntity(contactDTOtoUpdate, oldContact); // if update is successful, the remainder work is repository work
		Assert.assertEquals(contactDTOtoUpdate, contactMapper.toContactDTO(oldContact));
	}

	@Test
	public void deleteContactTest() {
		// Contact to be deleted
		Contact contact = getContact1().get();

		when(contactRepository.existsById(getContactId(1))).thenReturn(true);
		contactService.deleteContact(getContactId(1));
		verify(contactRepository, times(1)).deleteById(getContactId(1));
	}

	@Test
	public void getContactEmailListTest() {
		when(contactRepository.findAll()).thenReturn(getExampleContactList());
		List<String> returnedList = contactService.getContactEmailList();
		Assert.assertEquals(contactEmailListTestAnswer(), returnedList);
	}

	public List<ContactDTO> getExampleContactDTOList(){
		List<ContactDTO> contactDTOList = new ArrayList<>();
		contactDTOList.add(contactMapper.toContactDTO(getContact1().get()));
		contactDTOList.add(contactMapper.toContactDTO(getContact2().get()));
		return contactDTOList;
	}

	public List<Contact> getExampleContactList(){
		List<Contact> contactList = new ArrayList<>();
		contactList.add(getContact1().get());
		contactList.add(getContact2().get());
		return contactList;
	}

	public List<String> contactEmailListTestAnswer() {
		List<String> emailList = new ArrayList<>();
		emailList.add("emailAddress1@mail.com");
		emailList.add("emailAddress2@mail.com");
		return  emailList;
	}
}
