package com.picus.email_campaign.serviceTest;

import com.picus.email_campaign.EmailCampaignApplication;
import com.picus.email_campaign.dto.ContactDTO;
import com.picus.email_campaign.dto.GroupDTO;
import com.picus.email_campaign.entity.Group;
import com.picus.email_campaign.mapper.ContactMapper;
import com.picus.email_campaign.mapper.GroupMapper;
import com.picus.email_campaign.repository.ContactRepository;
import com.picus.email_campaign.repository.GroupRepository;
import com.picus.email_campaign.service.GroupService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.picus.email_campaign.serviceTest.ContactUtil.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmailCampaignApplication.class)
public class GroupServiceTest {
	@Autowired
	GroupService groupService;

	@Autowired
	GroupMapper groupMapper;

	@Autowired
	ContactMapper contactMapper;

	@MockBean
	GroupRepository groupRepository;

	@MockBean
	ContactRepository contactRepository;

	@Test
	public void addGroupTest() throws Exception {
		// GroupDTO to be saved with no id
		GroupDTO groupDTOtoBeSaved = new GroupDTO();
		List<UUID> contactsIds = new ArrayList<>();
		contactsIds.add(UUID.randomUUID());
		contactsIds.add(UUID.randomUUID());
		contactsIds.add(UUID.randomUUID());

		groupDTOtoBeSaved.setContactsIdList(contactsIds);

		// Group Entity
		Group group = new Group();
		group.setId(UUID.randomUUID());
		group.setContactsIdList(groupDTOtoBeSaved.getContactsIdList());

		// Test whether group id list and group dto id list are exactly the same or not
		when(groupRepository.save(groupMapper.toGroupEntity(groupDTOtoBeSaved))).thenReturn(group);
		GroupDTO returnGroupDTO = groupService.addGroup(groupDTOtoBeSaved);
		Assert.assertEquals(true, returnGroupDTO.getContactsIdList().equals(groupDTOtoBeSaved.getContactsIdList()));
	}

	@Test
	public void getGroupsTest() {
		when(groupRepository.findAll()).thenReturn(getListofGroups());
		when(contactRepository.findById(getContactId(1))).thenReturn(getContact1());
		when(contactRepository.findById(getContactId(2))).thenReturn(getContact2());
		when(contactRepository.findById(getContactId(3))).thenReturn(getContact3());
		List<List<ContactDTO>> returnedList = groupService.getGroups();
		Assert.assertEquals(returnedList, getAnswerOfGroupRetrieval());
	}

	@Test
	public void updateGroupTest() {
		// GroupDTO which will update entity
		GroupDTO groupDTOtoUpdate = new GroupDTO();
		groupDTOtoUpdate.setId(UUID.randomUUID());
		List<UUID> contactIds = new ArrayList<>();
		contactIds.add(getContactId(1));
		contactIds.add(getContactId(2));
		contactIds.add(getContactId(3));
		groupDTOtoUpdate.setContactsIdList(contactIds);

		// Group entity which will be updated by dto (Contact 2 will be added to group)
		Group oldGroup = new Group();
		oldGroup.setId(groupDTOtoUpdate.getId()); // ids should be same
		List<UUID> oldContactIds = new ArrayList<>();
		oldContactIds.add(getContactId(1));
		oldContactIds.add(getContactId(3));
		oldGroup.setContactsIdList(oldContactIds);

		// Test to update outdated entity
		when(groupRepository.findById(groupDTOtoUpdate.getId())).thenReturn(java.util.Optional.of(oldGroup));
		groupMapper.updateEntity(groupDTOtoUpdate, oldGroup); // if update is successful, the remainder work is repository work
		Assert.assertEquals(groupDTOtoUpdate.getContactsIdList(), oldGroup.getContactsIdList());
	}

	@Test
	public void deleteGroupTest() {
		// id of the group
		UUID id = UUID.randomUUID();

		// Group to be deleted
		Group group = new Group();
		group.setId(id);
		group.setContactsIdList(null);

		when(groupRepository.existsById(id)).thenReturn(true);
		groupService.deleteGroup(id);
		verify(groupRepository, times(1)).deleteById(id);
	}

	public List<List<ContactDTO>> getAnswerOfGroupRetrieval() {
		// Group 1
		List<ContactDTO> group1 = new ArrayList<>();
		group1.add(contactMapper.toContactDTO(getContact1().get()));
		group1.add(contactMapper.toContactDTO(getContact2().get()));
		group1.add(contactMapper.toContactDTO(getContact3().get()));

		// Group 2
		List<ContactDTO> group2 = new ArrayList<>();
		group2.add(contactMapper.toContactDTO(getContact1().get()));
		group2.add(contactMapper.toContactDTO(getContact3().get()));

		// Return list
		List<List<ContactDTO>> answer = new ArrayList<>();
		answer.add(group1);
		answer.add(group2);

		return answer;
	}

	public List<Group> getListofGroups() {
		// Group 1 with contacts 1,2 and 3
		Group group1 = new Group();
		List<UUID> group1ContactIds = new ArrayList<>();
		group1ContactIds.add(getContactId(1));
		group1ContactIds.add(getContactId(2));
		group1ContactIds.add(getContactId(3));
		group1.setContactsIdList(group1ContactIds);
		group1.setId(UUID.fromString("d50e845b-385f-4a8d-ac4a-c183a01bf4c9"));

		// Group 2 with contacts 1 and 3
		Group group2 = new Group();
		List<UUID> group2ContactIds = new ArrayList<>();
		group2ContactIds.add(getContactId(1));
		group2ContactIds.add(getContactId(3));
		group2.setContactsIdList(group2ContactIds);
		group2.setId(UUID.fromString("31d5b4cc-9f37-43bf-8434-10eae92c8b03"));

		List<Group> groupList = new ArrayList<>();
		groupList.add(group1);
		groupList.add(group2);
		return groupList;
	}


}
