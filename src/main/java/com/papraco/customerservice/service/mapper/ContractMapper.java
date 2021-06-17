package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.ContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contract} and its DTO {@link ContractDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface ContractMapper extends EntityMapper<ContractDTO, Contract> {
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    ContractDTO toDto(Contract s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContractDTO toDtoId(Contract contract);
}
