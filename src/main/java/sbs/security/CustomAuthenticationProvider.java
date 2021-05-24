package sbs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    /*
     * (查看源码发现)
     * 这个CustomUserDetailsService 实现了UserDetailsService接口 这个接口里面含有这个UserDetails 而这个类最后返回的就是权限账号和密码
     * 而这个UserDetailsService接口里面的 UserDetails 有一个方法 就是 getAuthorities()
     * 和 AuthenticationProvider接口里面的 Authentication 有一个 也是   getAuthorities()
     * 所以说 这个  AuthenticationProvider 和  UserDetailsService 存在者依赖的关系
     *
     * */

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /*
    * (查看源码发现)
    * 因为 UsernamePasswordAuthenticationToken 这个类 继承了 AbstractAuthenticationToken
    * 而 AbstractAuthenticationToken 有实现了 Authentication 接口 所以当用户输入账号和密码的时候
    * 使用 Authentication 来接收账好和密码 等一些列
    * 使用 UsernamePasswordAuthenticationToken(Authentication) 来验证这些数据
    *
    * 然后生成的  Authentication  会被 AuthenticationManager()来进行管理
    * 而 AuthenticationManager 有管理一些   AuthenticationProvider
    * 而每一个Provider都会通UserDetailsService和UserDetail来返回一个
    *
    * 以UsernamePasswordAuthenticationToken 实现的带用户名和密码以及权限的Authentication
    *
    * */


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //一种非加密对称
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

        // 获取用户输入的用户名
        String inputName = authentication.getName();
        // 获取用户输入的和密码
        String inputPassword = authentication.getCredentials().toString();

        // userDetails为数据库中查询到的用户信息
        // 因为是CustomUserDetailsService 实现了 webUserDetailsService接口 里面有一个
        // loadUserByUsername  APi 就是通过用户名来获取用户的权限 直接来调用就行
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(inputName);

        System.out.println("密码"+userDetails.getPassword()+":输入的密码"+inputPassword);

        //比较查出的密码 和输入的密码 是不是一样的 采用的是
        // 如果是自定义AuthenticationProvider，需要手动密码校验 bCryptPasswordEncoder.matches
        if(bCryptPasswordEncoder.matches(inputPassword,userDetails.getPassword())){
            //通过  UsernamePasswordAuthenticationToken 将用户的所有信息 返回出去
            return new UsernamePasswordAuthenticationToken(userDetails, inputPassword, userDetails.getAuthorities());
        }
        //抛出BadCredentialsException 的异常
        throw new BadCredentialsException("密码错误");

    }



    @Override
    public boolean supports(Class<?> authentication) {
        // 这里不要忘记，和UsernamePasswordAuthenticationToken比较
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        //return true;
    }


}
