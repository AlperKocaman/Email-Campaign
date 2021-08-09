package com.picus.email_campaign.service;

import com.picus.email_campaign.mapper.ContactMapper;
import com.picus.email_campaign.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {
	private final ContactMapper contactMapper;
	private final ContactRepository contactRepository;


}
