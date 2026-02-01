package com.rag.chat.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 * Developed by Syyed
 */

@Configuration
public class FilterConfig {

    private final ApiKeyFilter apiKeyFilter;

    public FilterConfig(ApiKeyFilter apiKeyFilter) {
        this.apiKeyFilter = apiKeyFilter;
    }

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration() {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);

        // Apply filter only to your API endpoints
        registrationBean.addUrlPatterns("/api/v1/*");

        registrationBean.setOrder(1); // make sure it runs first
        return registrationBean;
    }
}
