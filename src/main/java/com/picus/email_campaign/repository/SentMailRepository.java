package com.picus.email_campaign.repository;

import com.picus.email_campaign.entity.Contact;
import com.picus.email_campaign.entity.SentMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SentMailRepository extends JpaRepository<SentMail, UUID> {
	Optional<SentMail> findByHashString(String hashString);

	Optional<SentMail> findBySentEmailAddress(String sentEmailAddress);
}
