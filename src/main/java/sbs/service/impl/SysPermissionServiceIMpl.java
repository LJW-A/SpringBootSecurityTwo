package sbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbs.entity.SysPermission;
import sbs.mapper.SysPermissionMapper;
import sbs.service.SysPermissionService;

import java.util.List;

@Service
public class SysPermissionServiceIMpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> listByRoleId(Integer roleId) {
        return sysPermissionMapper.listByRoleId(roleId);
    }
}
