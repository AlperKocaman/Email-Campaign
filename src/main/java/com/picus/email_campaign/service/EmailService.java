package com.picus.email_campaign.service;

import com.picus.email_campaign.dto.EmailDTO;
import com.picus.email_campaign.dto.SentMailDTO;
import com.picus.email_campaign.entity.Contact;
import com.picus.email_campaign.entity.SentMail;
import com.picus.email_campaign.mapper.SentMailMapper;
import com.picus.email_campaign.repository.ContactRepository;
import com.picus.email_campaign.repository.SentMailRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EmailService {

	@Autowired
	private SentMailMapper sentMailMapper;

	@Autowired
	private SentMailRepository sentMailRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmailAddress;

	private String customLinkBody = "http://localhost:3000/click/%s";

	public List<SentMailDTO> sendEmailToMultipleContacts(EmailDTO emailDTO) {
		List<SentMailDTO> sentMailDTOList = new ArrayList<>();
		for(String contactEmailAddress : emailDTO.getContactEmailAddressList()) {
			sentMailDTOList.add(this.sendEmailToContact(contactEmailAddress, emailDTO.getTopic(), emailDTO.getBody()));
		}
		return sentMailDTOList;
	}

	public SentMailDTO sendEmailToContact(String contactEmailAddress, String topic, String body) {
		// TODO : divide this method into methods
		// TODO : using contact service might be better

		SimpleMailMessage message = new SimpleMailMessage();

		SentMailDTO sentMailDTO = new SentMailDTO();
		sentMailDTO.setSentEmailAddress(contactEmailAddress);
		String hashString = UUID.randomUUID().toString();
		sentMailDTO.setHashString(hashString);

		message.setFrom(fromEmailAddress);
		message.setTo(contactEmailAddress);
		message.setSubject(topic);

		String customLink = String.format(customLinkBody, hashString);
		message.setText(Jsoup.parse(body).text() + "\n" + customLink);

		mailSender.send(message);

		sentMailDTO.setSentTime(System.currentTimeMillis());
		SentMail sentMail = sentMailMapper.toSentMailEntity(sentMailDTO);
		SentMail sentMailEntity = sentMailRepository.save(sentMail);
		log.info("Mail sent: {}", sentMailEntity.toString());

		Optional<Contact> contact = contactRepository.findByEmailAddress(sentMailEntity.getSentEmailAddress());
		if(contact.isPresent()) {
			Contact contactEntity = contact.get();
			contactEntity.setMailSentToThisContact(true);
			contactRepository.save(contactEntity);
		}else {
			log.error("The recipient email address is not registered in system!");
		}

		return sentMailMapper.toSentMailDTO(sentMailEntity);
	}

	public void handleLinkClick(String id) {
		Optional<SentMail> sentMail = sentMailRepository.findByHashString(id);
		if(sentMail.isPresent()) {
			SentMail sentMailEntity = sentMail.get();
			Optional<Contact> contact = contactRepository.findByEmailAddress(sentMailEntity.getSentEmailAddress());
			if(contact.isPresent()) {
				Contact contactEntity = contact.get();
				contactEntity.setElapsedTimeUntilClick(System.currentTimeMillis() - sentMailEntity.getSentTime());
				contactEntity.setThisContactClickedTheLink(true);
				contactRepository.save(contactEntity);
			}else {
				log.error("Sent mail found, but owner of the email address is not registered in system!");
			}
		} else {
			log.error("Sent mail not found!");
		}
	}
}
