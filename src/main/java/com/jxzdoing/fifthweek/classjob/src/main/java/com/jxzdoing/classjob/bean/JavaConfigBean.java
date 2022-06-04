package com.jxzdoing.classjob.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lw
 */
@Configuration
public class JavaConfigBean {

    @Bean
    public JaveConfigClass javaCodeExample() {
        return new JaveConfigClass();
    }
}
