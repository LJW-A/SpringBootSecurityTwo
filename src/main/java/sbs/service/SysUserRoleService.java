package sbs.service;

import sbs.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService {

    public List<SysUserRole> listByUserId(Integer userId);
}
