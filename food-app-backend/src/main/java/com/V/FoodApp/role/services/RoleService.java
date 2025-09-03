package com.V.FoodApp.role.services;

import com.V.FoodApp.response.Response;
import com.V.FoodApp.role.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    Response<RoleDTO> createRole(RoleDTO roleDTO);

    Response<RoleDTO> updateRole(RoleDTO roleDTO);

    Response<List<RoleDTO>> getAllRoles();

    Response<?> deleteRole(Long id);

}
