package com.codingapi.fileservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 侯存路
 * \* Date: 2017/12/23
 * \* Time: 21:14
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class SpringBootStartApplication  extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(FileserviceApplication.class);
    }


}