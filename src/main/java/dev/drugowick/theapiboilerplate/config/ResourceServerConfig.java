package dev.drugowick.theapiboilerplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // TODO make the access to the collection resource be dynamic as well
                .antMatchers("/examples").hasRole("USER")
                .antMatchers("/examples/{exampleId}").access("hasAuthority('READ_' + #exampleId)")
                .anyRequest().permitAll();
    }
}

