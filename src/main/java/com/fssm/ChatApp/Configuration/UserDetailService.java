package com.fssm.ChatApp.Configuration;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            var userObj = user.get();
            Map<String, String> userDetailsMap = new HashMap<>();
            userDetailsMap.put("userId", String.valueOf(userObj.getUserId()));
            userDetailsMap.put("username", userObj.getUserName());
            userDetailsMap.put("password", userObj.getPassword());
            return new CustomUser(userDetailsMap);
        } else {
            throw new UsernameNotFoundException(userName);
        }
    }
}
