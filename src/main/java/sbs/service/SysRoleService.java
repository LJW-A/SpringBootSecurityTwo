package sbs.service;

import sbs.entity.SysRole;

public interface SysRoleService {

    public SysRole selectById(Integer id);

    SysRole selectByName(String name);
}
