package com.picus.email_campaign.serviceTest;

import com.picus.email_campaign.entity.Contact;

import java.util.Optional;
import java.util.UUID;

public class ContactUtil {

	public static Optional<Contact> getContact1() {
		Contact contact1 = new Contact();
		contact1.setName("Name1");
		contact1.setSurname("Surname1");
		contact1.setEmailAddress("emailAddress1@mail.com");
		contact1.setMailSentToThisContact(false);
		contact1.setThisContactClickedTheLink(false);
		contact1.setElapsedTimeUntilClick((long) 0);
		contact1.setId(getContactId(1));
		return Optional.of(contact1);
	}

	public static  Optional<Contact> getContact2() {
		Contact contact2 = new Contact();
		contact2.setName("Name2");
		contact2.setSurname("Surname2");
		contact2.setEmailAddress("emailAddress2@mail.com");
		contact2.setMailSentToThisContact(true);
		contact2.setThisContactClickedTheLink(false);
		contact2.setElapsedTimeUntilClick((long) 0);
		contact2.setId(getContactId(2));
		return Optional.of(contact2);
	}

	public static  Optional<Contact> getContact3() {
		Contact contact3 = new Contact();
		contact3.setName("Name3");
		contact3.setSurname("Surname3");
		contact3.setEmailAddress("emailAddress3@mail.com");
		contact3.setMailSentToThisContact(true);
		contact3.setThisContactClickedTheLink(true);
		contact3.setElapsedTimeUntilClick((long) 45632178);
		contact3.setId(getContactId(3));
		return Optional.of(contact3);
	}

	public static  UUID getContactId(int contactIndex) {
		if(contactIndex == 1) {
			return UUID.fromString("f1c64a61-6da6-4748-b6a3-96ab4889cab4");
		} else if(contactIndex == 2) {
			return UUID.fromString("6dd059d0-9365-482d-a2ba-e9d9a3327dc0");
		} else {
			return UUID.fromString("6e1c044d-72ed-481e-8b80-d58f3426302d");
		}
	}
}
