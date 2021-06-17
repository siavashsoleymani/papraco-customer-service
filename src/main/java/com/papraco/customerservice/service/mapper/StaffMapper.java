package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.StaffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Staff} and its DTO {@link StaffDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface StaffMapper extends EntityMapper<StaffDTO, Staff> {
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    StaffDTO toDto(Staff s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StaffDTO toDtoId(Staff staff);
}
