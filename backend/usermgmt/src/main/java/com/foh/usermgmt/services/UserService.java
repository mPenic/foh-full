package com.foh.usermgmt.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.foh.usermgmt.dto.UserSummaryDto;

import jakarta.persistence.EntityNotFoundException;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.entities.usermgmt.RoleDictionaryEntity;
import com.foh.data.entities.usermgmt.UserEntity;
import com.foh.data.repository.usermgmt.CompanyDictionaryRepository;
import com.foh.data.repository.usermgmt.RoleDictionaryRepository;
import com.foh.data.repository.usermgmt.UserRepository;
import com.foh.security.CurrUserDetails;
import com.foh.data.vo.UserVO;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleDictionaryRepository roleDictionaryRepository;
    private final CompanyDictionaryRepository companyDictionaryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleDictionaryRepository roleDictionaryRepository,
                       CompanyDictionaryRepository companyDictionaryRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleDictionaryRepository = roleDictionaryRepository;
        this.companyDictionaryRepository = companyDictionaryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a new user (the current user’s company is used)
    public UserSummaryDto createUser(UserVO userVO, Long currentCompanyId) {
        // Check if username already exists
        Optional<UserEntity> existingUser = userRepository.findByUsername(userVO.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }

        // Construct new user
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userVO.getUsername());
        // Hash the provided password
        userEntity.setPasswordHash(passwordEncoder.encode(userVO.getPassword()));

        // Set role
        RoleDictionaryEntity role = roleDictionaryRepository.findById(userVO.getRoleId())
        .orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));

        CompanyDictionaryEntity company = companyDictionaryRepository.findById(currentCompanyId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid company ID"));

        userEntity.setRole(role);
        userEntity.setCompany(company);

        // Save
        UserEntity savedUser = userRepository.save(userEntity);
        
        return toDto(savedUser);
    }

    public UserSummaryDto getUserSummaryById(Long userId) {
        return toDto(userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    public List<UserSummaryDto> getUsersByCompanyId(Long companyId) {
    return userRepository.findByCompanyCompanyId(companyId)
        .stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }

    // Get all users
    public List<UserSummaryDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long userId, CurrUserDetails currentUser) {
      UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

      Long callerCompany = currentUser.getCompanyId();
      if (!user.getCompany().getCompanyId().equals(callerCompany)) {
        throw new IllegalArgumentException(
          "Cannot delete a user in a different company"
        );
      }

      userRepository.delete(user);
    }

    private UserSummaryDto toDto(UserEntity u) {
    return new UserSummaryDto(
      u.getUserId(),
      u.getUsername(),
      u.getCompany().getCompanyId(),
      u.getCompany().getCompanyName(),
      u.getRole().getRoleId(),
      u.getRole().getRoleName()
    );
  }
}
