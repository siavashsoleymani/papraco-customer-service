package com.papraco.customerservice.service.impl;

import com.papraco.customerservice.domain.UserComment;
import com.papraco.customerservice.repository.UserCommentRepository;
import com.papraco.customerservice.service.UserCommentService;
import com.papraco.customerservice.service.dto.UserCommentDTO;
import com.papraco.customerservice.service.mapper.UserCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link UserComment}.
 */
@Service
public class UserCommentServiceImpl implements UserCommentService {

    private final Logger log = LoggerFactory.getLogger(UserCommentServiceImpl.class);

    private final UserCommentRepository userCommentRepository;

    private final UserCommentMapper userCommentMapper;

    public UserCommentServiceImpl(UserCommentRepository userCommentRepository, UserCommentMapper userCommentMapper) {
        this.userCommentRepository = userCommentRepository;
        this.userCommentMapper = userCommentMapper;
    }

    @Override
    public UserCommentDTO save(UserCommentDTO userCommentDTO) {
        log.debug("Request to save UserComment : {}", userCommentDTO);
        UserComment userComment = userCommentMapper.toEntity(userCommentDTO);
        userComment = userCommentRepository.save(userComment);
        return userCommentMapper.toDto(userComment);
    }

    @Override
    public Optional<UserCommentDTO> partialUpdate(UserCommentDTO userCommentDTO) {
        log.debug("Request to partially update UserComment : {}", userCommentDTO);

        return userCommentRepository
            .findById(userCommentDTO.getId())
            .map(
                existingUserComment -> {
                    userCommentMapper.partialUpdate(existingUserComment, userCommentDTO);
                    return existingUserComment;
                }
            )
            .map(userCommentRepository::save)
            .map(userCommentMapper::toDto);
    }

    @Override
    public Page<UserCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserComments");
        return userCommentRepository.findAll(pageable).map(userCommentMapper::toDto);
    }

    @Override
    public Optional<UserCommentDTO> findOne(String id) {
        log.debug("Request to get UserComment : {}", id);
        return userCommentRepository.findById(id).map(userCommentMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete UserComment : {}", id);
        userCommentRepository.deleteById(id);
    }
}
