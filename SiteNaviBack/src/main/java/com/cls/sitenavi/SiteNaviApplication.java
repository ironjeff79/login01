package com.cls.sitenavi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


//@ServletComponentScan("com.cls.filter.MyFilter")
@Slf4j
@EnableScheduling
@ServletComponentScan(basePackages = {"com.cls.filter"}) //这一句就完成了配置
@SpringBootApplication
public class SiteNaviApplication {
    public static void main(String[] args) {
        SpringApplication.run(SiteNaviApplication.class, args);
        log.debug("rest server start success.");
    }
}

