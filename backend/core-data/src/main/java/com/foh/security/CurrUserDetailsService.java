package com.foh.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.foh.data.entities.usermgmt.UserEntity;
import com.foh.data.repository.usermgmt.UserRepository;
//sesion menađer
@Service
public class CurrUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CurrUserDetailsService.class);

    private final UserRepository userRepository;

    public CurrUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CurrUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user by username: {}", username);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        log.debug("User found: {}", user);
        
        String roleName = user.getRole().getRoleName();  // e.g. "ADMIN"
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName));

        return new CurrUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getRole().getRoleId(),
                user.getCompany().getCompanyId(),
                authorities
        );
    }
}
