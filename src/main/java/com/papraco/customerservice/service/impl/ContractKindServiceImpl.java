package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.ContractKind;
import com.papraco.customerservice.repository.ContractKindRepository;
import com.papraco.customerservice.service.ContractKindService;
import com.papraco.customerservice.service.dto.ContractKindDTO;
import com.papraco.customerservice.service.mapper.ContractKindMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ContractKind}.
 */
@Service
public class ContractKindServiceImpl implements ContractKindService {

    private final Logger log = LoggerFactory.getLogger(ContractKindServiceImpl.class);

    private final ContractKindRepository contractKindRepository;

    private final ContractKindMapper contractKindMapper;

    public ContractKindServiceImpl(ContractKindRepository contractKindRepository, ContractKindMapper contractKindMapper) {
        this.contractKindRepository = contractKindRepository;
        this.contractKindMapper = contractKindMapper;
    }

    @Override
    public ContractKindDTO save(ContractKindDTO contractKindDTO) {
        log.debug("Request to save ContractKind : {}", contractKindDTO);
        ContractKind contractKind = contractKindMapper.toEntity(contractKindDTO);
        contractKind = contractKindRepository.save(contractKind);
        return contractKindMapper.toDto(contractKind);
    }

    @Override
    public Optional<ContractKindDTO> partialUpdate(ContractKindDTO contractKindDTO) {
        log.debug("Request to partially update ContractKind : {}", contractKindDTO);

        return contractKindRepository
            .findById(contractKindDTO.getId())
            .map(
                existingContractKind -> {
                    contractKindMapper.partialUpdate(existingContractKind, contractKindDTO);
                    return existingContractKind;
                }
            )
            .map(contractKindRepository::save)
            .map(contractKindMapper::toDto);
    }

    @Override
    public Page<ContractKindDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContractKinds");
        return contractKindRepository.findAll(pageable).map(contractKindMapper::toDto);
    }

    @Override
    public Optional<ContractKindDTO> findOne(String id) {
        log.debug("Request to get ContractKind : {}", id);
        return contractKindRepository.findById(id).map(contractKindMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete ContractKind : {}", id);
        contractKindRepository.deleteById(id);
    }
}
