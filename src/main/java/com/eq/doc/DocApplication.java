package com.eq.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * create time 2025/8/3 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
@SpringBootApplication
@ComponentScan("com.eq.doc.*")
public class DocApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocApplication.class, args);
    }
}