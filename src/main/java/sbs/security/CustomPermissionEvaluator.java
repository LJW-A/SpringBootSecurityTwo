package sbs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import sbs.entity.SysPermission;
import sbs.service.SysPermissionService;
import sbs.service.SysRoleService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component


public class CustomPermissionEvaluator implements PermissionEvaluator {


    /*
    * 这个 PermissionEvaluator 的接口的目的就是为了更好的鉴别权限
    * 它主要就是和controller里面的一个注解是相互对应的关系
    *  比如这个:  @PreAuthorize("hasPermission('/admin','r')")
    * 如果用户走到这块 会拿到这个 "r" 权限  通过这样的注解 找到实现这个接口的类 也就是  CustomPermissionEvaluator
    *
    * 如果符合 有这个权限的话  返回的是true  没有的话返回的是false
    *
    * */

    @Autowired
    private SysPermissionService permissionService;


    @Autowired
    private SysRoleService roleService;


    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {

        // 获得loadUserByUsername()方法的结果
        User user = (User)authentication.getPrincipal();

        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        // 遍历用户所有角色
        for(GrantedAuthority authority : authorities) {
            //获取到权限名称
            String roleName = authority.getAuthority();
            //通过这个名称的id   获取到role的id
            Integer roleId = roleService.selectByName(roleName).getId();
            // 通过id 得到角色所有的权限
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);

            // 遍历permissionList
            for(SysPermission sysPermission : permissionList) {
                // 获取权限集
                List permissions = sysPermission.getPermissions();
                //  如果访问的Url和权限用户符合的话，返回true
                if(targetUrl.equals(sysPermission.getUrl())
                        && permissions.contains(targetPermission)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }


}
