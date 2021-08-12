package com.picus.email_campaign.mapper;

import com.picus.email_campaign.dto.GroupDTO;
import com.picus.email_campaign.entity.Group;
import org.mapstruct.MappingTarget;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface GroupMapper {
	GroupDTO toGroupDto(Group group);
	Group toGroupEntity(GroupDTO groupDTO);
	List<GroupDTO> toGroupDTOList(List<Group> entity);
	void updateEntity(GroupDTO dto, @MappingTarget Group entity);
}
