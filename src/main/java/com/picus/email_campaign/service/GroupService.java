package com.picus.email_campaign.service;

import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.dto.GroupDTO;
import com.picus.email_campaign.entity.Contact;
import com.picus.email_campaign.entity.Group;
import com.picus.email_campaign.mapper.ContactMapper;
import com.picus.email_campaign.mapper.GroupMapper;
import com.picus.email_campaign.repository.ContactRepository;
import com.picus.email_campaign.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

	private final GroupMapper groupMapper;
	private final ContactMapper contactMapper;
	private final GroupRepository groupRepository;
	private final ContactRepository contactRepository;

	public GroupDTO addGroup(GroupDTO groupDTO) throws Exception{
		Group group = groupMapper.toGroupEntity(groupDTO);
		Group groupEntity = groupRepository.save(group);
		log.info("Group created : {}", groupEntity.toString());
		return groupMapper.toGroupDto(groupEntity);
	}

	public List<List<ContactDTO>> getGroups() {
		List<Group> groupList = groupRepository.findAll();
		List<List<ContactDTO>> groupsWithContacts = new ArrayList<>();
		for(Group group : groupList ) {
			List<ContactDTO> thisGroupContacts = new ArrayList<>();
			for(UUID id: group.getContactsIdList()) {
				// Suppose this id exists in database
				thisGroupContacts.add(contactMapper.toContactDTO(contactRepository.findById(id).get()));
			}
			groupsWithContacts.add(thisGroupContacts);
		}
		log.info("Group list with contacts retrieved");
		return groupsWithContacts;
	}

	public GroupDTO updateGroup(GroupDTO groupDTO) throws Exception {

		Group origEntity = groupRepository.findById(groupDTO.getId()).orElseThrow(Exception::new);
		groupMapper.updateEntity(groupDTO, origEntity);
		groupRepository.save(origEntity);
		log.info("Group updated: {}", origEntity.toString());

		return groupDTO;
	}

	public UUID deleteGroup(UUID id){
		if (groupRepository.existsById(id)) {
			groupRepository.deleteById(id);
			log.info("Group deleted: {}", id.toString());

		} else {
			throw new NullPointerException("Group with id " + id + " not found!");
		}
		return id;
	}
}
