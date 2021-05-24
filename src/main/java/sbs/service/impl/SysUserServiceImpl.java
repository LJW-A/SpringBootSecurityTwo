package sbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbs.entity.SysUser;
import sbs.mapper.SysUserMapper;
import sbs.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper userMapper;


    @Override
    public SysUser selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public SysUser selectByName(String name) {
        return userMapper.selectByName(name);
    }
}
