package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.Staff;
import com.papraco.customerservice.repository.StaffRepository;
import com.papraco.customerservice.service.StaffService;
import com.papraco.customerservice.service.dto.StaffDTO;
import com.papraco.customerservice.service.mapper.StaffMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Staff}.
 */
@Service
public class StaffServiceImpl implements StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    public StaffServiceImpl(StaffRepository staffRepository, StaffMapper staffMapper) {
        this.staffRepository = staffRepository;
        this.staffMapper = staffMapper;
    }

    @Override
    public StaffDTO save(StaffDTO staffDTO) {
        log.debug("Request to save Staff : {}", staffDTO);
        Staff staff = staffMapper.toEntity(staffDTO);
        staff = staffRepository.save(staff);
        return staffMapper.toDto(staff);
    }

    @Override
    public Optional<StaffDTO> partialUpdate(StaffDTO staffDTO) {
        log.debug("Request to partially update Staff : {}", staffDTO);

        return staffRepository
            .findById(staffDTO.getId())
            .map(
                existingStaff -> {
                    staffMapper.partialUpdate(existingStaff, staffDTO);
                    return existingStaff;
                }
            )
            .map(staffRepository::save)
            .map(staffMapper::toDto);
    }

    @Override
    public Page<StaffDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Staff");
        return staffRepository.findAll(pageable).map(staffMapper::toDto);
    }

    @Override
    public Optional<StaffDTO> findOne(String id) {
        log.debug("Request to get Staff : {}", id);
        return staffRepository.findById(id).map(staffMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteById(id);
    }
}
