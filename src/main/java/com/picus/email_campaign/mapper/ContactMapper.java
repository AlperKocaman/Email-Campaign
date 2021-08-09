package com.picus.email_campaign.mapper;

import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.entity.Contact;
import org.mapstruct.MappingTarget;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ContactMapper {
	ContactDTO toContactDTO(Contact contact);
	Contact toContactEntity(ContactDTO dto);
	void updateContactEntity(ContactDTO dto, @MappingTarget Contact entity);
	List<ContactDTO> toContactDTOList(List<Contact> entity);
	List<Contact> toContactEntityList(List<ContactDTO> entity);
}
