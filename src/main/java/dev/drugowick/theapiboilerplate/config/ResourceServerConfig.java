package dev.drugowick.theapiboilerplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // TODO For now only allowing someone with the ROLE_USER. Later must validade dynamically.
        http.authorizeRequests().antMatchers("/examples/**").hasRole("USER")
                .anyRequest().permitAll();
    }
}

