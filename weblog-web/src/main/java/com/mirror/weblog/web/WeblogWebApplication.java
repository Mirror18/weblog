package com.mirror.weblog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author mirror
 */
@SpringBootApplication
//多模块项目中，必须手动指定扫描 包下面的所有类
@ComponentScan("com.mirror.weblog.*")
//@EnableAspectJAutoProxy
public class WeblogWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeblogWebApplication.class,args);
    }
}
