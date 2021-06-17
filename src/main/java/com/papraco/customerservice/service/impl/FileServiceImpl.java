package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.File;
import com.papraco.customerservice.repository.FileRepository;
import com.papraco.customerservice.service.FileService;
import com.papraco.customerservice.service.dto.FileDTO;
import com.papraco.customerservice.service.mapper.FileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link File}.
 */
@Service
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public FileDTO save(FileDTO fileDTO) {
        log.debug("Request to save File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        return fileMapper.toDto(file);
    }

    @Override
    public Optional<FileDTO> partialUpdate(FileDTO fileDTO) {
        log.debug("Request to partially update File : {}", fileDTO);

        return fileRepository
            .findById(fileDTO.getId())
            .map(
                existingFile -> {
                    fileMapper.partialUpdate(existingFile, fileDTO);
                    return existingFile;
                }
            )
            .map(fileRepository::save)
            .map(fileMapper::toDto);
    }

    @Override
    public Page<FileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return fileRepository.findAll(pageable).map(fileMapper::toDto);
    }

    @Override
    public Optional<FileDTO> findOne(String id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id).map(fileMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
