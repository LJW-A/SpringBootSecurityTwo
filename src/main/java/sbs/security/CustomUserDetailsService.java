package sbs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sbs.entity.SysRole;
import sbs.entity.SysUser;
import sbs.entity.SysUserRole;
import sbs.service.SysRoleService;
import sbs.service.SysUserRoleService;
import sbs.service.SysUserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;


    //权限的数据  重写 UserDetailsService 里面的  UserDetails 里面的loadUserByUsername方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Collection  和  ArrayList的性质是一样的就是一个集合
        // 而里里面的泛型 是一个接口 里面有个获取权限的一个方法  String getAuthority();
        Collection<GrantedAuthority> authorities = new ArrayList<>();



        // 从数据库中取出用户信息
        SysUser user = userService.selectByName(username);

        // 判断用户是否存在
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.selectById(userRole.getRoleId());

            //将这个权限的名称存储在这个Collection 集合里面
            // 为什么要使用这个 SimpleGrantedAuthority
            //因为是实现了 GrantedAuthority接口
            //所以和 GrantedAuthority 是相似的  都具有一个方法  String getAuthority(); 获取权限
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        // 返回UserDetails实现类   将这些信息存储到User里面 这个User 是UserDetails里面的 也就是Security 里面的USer
        //因为这个User 是 实现了 UserDetails接口
        return new User(user.getName(), user.getPassword(), authorities);
    }

}
