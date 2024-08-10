package com.example.campingontop.domain.mysql.user.service;

import com.example.campingontop.domain.mysql.user.repository.queryDsl.UserRepository;
import com.example.campingontop.domain.mysql.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        }
        return user;
    }
}
