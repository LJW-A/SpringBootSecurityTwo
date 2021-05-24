package sbs.service;

import sbs.entity.SysUser;

public interface SysUserService {

    public SysUser selectById(Integer id);

    public SysUser selectByName(String name);



}
