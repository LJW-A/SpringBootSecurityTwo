package sbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//排除security 登陆时的需要的密码  的账号密码
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@SpringBootApplication
@MapperScan("sbs.mapper")
public class RunSBSAS {

    public static void main(String[] args) {
        SpringApplication.run(RunSBSAS.class);
    }
}
