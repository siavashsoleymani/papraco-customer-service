package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.Company;
import com.papraco.customerservice.repository.CompanyRepository;
import com.papraco.customerservice.service.CompanyService;
import com.papraco.customerservice.service.dto.CompanyDTO;
import com.papraco.customerservice.service.mapper.CompanyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Override
    public Optional<CompanyDTO> partialUpdate(CompanyDTO companyDTO) {
        log.debug("Request to partially update Company : {}", companyDTO);

        return companyRepository
            .findById(companyDTO.getId())
            .map(
                existingCompany -> {
                    companyMapper.partialUpdate(existingCompany, companyDTO);
                    return existingCompany;
                }
            )
            .map(companyRepository::save)
            .map(companyMapper::toDto);
    }

    @Override
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable).map(companyMapper::toDto);
    }

    public Page<CompanyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return companyRepository.findAllWithEagerRelationships(pageable).map(companyMapper::toDto);
    }

    @Override
    public Optional<CompanyDTO> findOne(String id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findOneWithEagerRelationships(id).map(companyMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}
