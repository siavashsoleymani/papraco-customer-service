package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.CompanyDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = { TagMapper.class })
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "idSet")
    CompanyDTO toDto(Company s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoId(Company company);

    @Mapping(target = "removeTag", ignore = true)
    Company toEntity(CompanyDTO companyDTO);
}
