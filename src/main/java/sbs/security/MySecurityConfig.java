package sbs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity
// 开启基于方法的安全认证机制，也就是说在web层的 controller启用注解机制的安全确认，
// 也就是说 有这个注解 这个注解才会生效 @PreAuthorize("hasPermission('/admin','c')")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private  CustomAuthenticationProvider   customAuthenticationProvider;


    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
         /*
         * 这块的意思就是你是否还要继续进一步的验证 也是权限的主要部分 判断密码 以及当密码错误的时候给出的提示
         * 这块是因为 AuthenticationManager  来管理一些列的  AuthenticationProvider
         * 因为  CustomAuthenticationProvider 实现了 AuthenticationProvider 所以也是被管理的
         * 又因为 AuthenticationManagerBuilder 里面的一个 AuthenticationManager 方法 所以
         * 这个 customAuthenticationProvider 也是可以直接 被 AuthenticationManagerBuilder 管理
         * */
         auth.authenticationProvider(customAuthenticationProvider);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                // 设置登陆页
                .formLogin().loginPage("/login")
                // 设置登陆成功页
                .defaultSuccessUrl("/").permitAll()
                //出现错误的时候异常抛出的地方
                .failureUrl("/login/error")
                .and()
                .logout().permitAll();

        // 关闭CSRF跨域
        http.csrf().disable();
    }



    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }


}
