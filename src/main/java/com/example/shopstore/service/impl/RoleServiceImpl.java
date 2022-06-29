package com.example.shopstore.service.impl;

import com.example.shopstore.dto.RoleDTO;
import com.example.shopstore.entity.Role;
import com.example.shopstore.repository.RoleRepository;
import com.example.shopstore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO addRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        roleRepository.save(role);
        if(role.getId() != 0) {
            roleDTO.setId(role.getId());
            return roleDTO;
        }
        return null;
    }

    @Override
    public List<RoleDTO> getAllRole() {
        List<RoleDTO> roleDTOS = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName());
            roleDTOS.add(roleDTO);
        }
        return roleDTOS;
    }
}
