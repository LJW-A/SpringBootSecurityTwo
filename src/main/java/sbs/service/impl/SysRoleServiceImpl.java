package sbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbs.entity.SysRole;
import sbs.entity.SysUserRole;
import sbs.mapper.SysRoleMapper;
import sbs.mapper.SysUserRoleMapper;
import sbs.service.SysRoleService;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleMapper roleMapper;


    @Override
    public SysRole selectById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public SysRole selectByName(String name) {
        return roleMapper.selectByName(name);
    }
}
