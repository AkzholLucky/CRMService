package com.backend.PhoneVerify.services;


import com.backend.PhoneVerify.models.User;
import com.backend.PhoneVerify.repositories.UsersRepository;
import com.backend.PhoneVerify.security.UsersDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByName(username);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("Incorrect username or password!");
        }

        return new UsersDetails(user.get());
    }
}
