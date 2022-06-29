package com.example.shopstore.service;

import com.example.shopstore.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    public RoleDTO addRole(RoleDTO roleDTO);

    public List<RoleDTO> getAllRole();

}
