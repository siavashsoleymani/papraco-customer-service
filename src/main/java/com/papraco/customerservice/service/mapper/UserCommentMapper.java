package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.UserCommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserComment} and its DTO {@link UserCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface UserCommentMapper extends EntityMapper<UserCommentDTO, UserComment> {
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    UserCommentDTO toDto(UserComment s);
}
