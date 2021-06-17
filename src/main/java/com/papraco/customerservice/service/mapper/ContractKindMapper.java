package com.papraco.customerservice.service.mapper;

import com.papraco.customerservice.domain.*;
import com.papraco.customerservice.service.dto.ContractKindDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContractKind} and its DTO {@link ContractKindDTO}.
 */
@Mapper(componentModel = "spring", uses = { ContractMapper.class })
public interface ContractKindMapper extends EntityMapper<ContractKindDTO, ContractKind> {
    @Mapping(target = "contract", source = "contract", qualifiedByName = "id")
    ContractKindDTO toDto(ContractKind s);
}
