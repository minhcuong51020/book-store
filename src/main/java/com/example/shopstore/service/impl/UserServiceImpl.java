package com.example.shopstore.service.impl;

import com.example.shopstore.common.RoleCommon;
import com.example.shopstore.dto.RoleDTO;
import com.example.shopstore.dto.UserDTO;
import com.example.shopstore.entity.Role;
import com.example.shopstore.entity.User;
import com.example.shopstore.repository.RoleRepository;
import com.example.shopstore.repository.UserRepository;
import com.example.shopstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier("passwordEncoder")
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean addUser(UserDTO userDTO) {
        User user = new User();
        Role role = roleRepository.getRoleByName(RoleCommon.ROLE_USER);
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);
        Optional<User> userOptional = Optional.ofNullable(userRepository.save(user));
        return userOptional.isPresent();
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(
                    user.getId(), user.getUsername(), user.getPassword(),
                    user.getEmail(), user.getFullName(), user.getPhone(), user.getAddress(), user.getAvatar()
            );
            userDTO.setRole(user.getRole().getName());
            return userDTO;
        } else {
            return null;
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        int result = userRepository.updateInfoUser(userDTO.getFullName(), userDTO.getPhone(),
                userDTO.getEmail(), userDTO.getAddress(), userDTO.getId());
        System.out.println(result);
        if(result > 0) {
            Optional<User> optional = userRepository.findById(userDTO.getId());
            User user = optional.get();
            UserDTO userDTONew = new UserDTO();
            userDTONew.setId(user.getId());
            userDTONew.setFullName(user.getFullName());
            userDTONew.setPhone(user.getPhone());
            userDTONew.setAddress(user.getAddress());
            userDTONew.setEmail(user.getEmail());
            return userDTONew;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.getUserByUsername(username));
        if(!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username invalid");
        }
        User user = userOptional.get();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities
        );
    }

}
