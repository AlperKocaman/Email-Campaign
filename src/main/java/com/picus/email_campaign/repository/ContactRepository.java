package com.picus.email_campaign.repository;

import com.picus.email_campaign.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

	Optional<Contact> findByEmailAddress(String emailAddress);

	boolean existsByEmailAddress(String emailAddress);
}
