package sbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbs.entity.SysUserRole;
import sbs.mapper.SysUserRoleMapper;
import sbs.service.SysUserRoleService;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<SysUserRole> listByUserId(Integer userId) {
        return userRoleMapper.listByUserId(userId);
    }
}
