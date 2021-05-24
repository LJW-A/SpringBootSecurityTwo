package sbs.service;

import sbs.entity.SysPermission;

import java.util.List;

public interface SysPermissionService {

    List<SysPermission> listByRoleId(Integer roleId);


}
