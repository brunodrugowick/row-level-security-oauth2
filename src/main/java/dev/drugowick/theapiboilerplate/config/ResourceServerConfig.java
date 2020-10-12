package dev.drugowick.theapiboilerplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers(HttpMethod.POST,"/examples").access(
                        String.format("hasAuthority('%s')", EntitySecurityInfo.EXAMPLE.getEntityCreationRole()))
                .antMatchers("/examples/{exampleId}").access(
                        String.format("hasAuthority('%s' + #exampleId)", EntitySecurityInfo.EXAMPLE.getSingleEntityAccessRolePrefix()))

                // Other API resources that you want to protect here

                .anyRequest().permitAll();
    }
}

