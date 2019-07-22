package com.wannago.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.wannago.dust.dao", "com.wannago.dust.service" })
@Import({DBConfig.class})
public class ApplicationConfig {
}
