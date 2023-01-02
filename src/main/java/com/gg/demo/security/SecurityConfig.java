package com.gg.demo.security;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${saml2.metadata-path}")
    private String metadataPath;
    @Value("${saml2.sp.protocol}")
    private String spProtocol;
    @Value("${saml2.sp.host}")
    private String spHost;
    @Value("${saml2.sp.path}")
    private String spBashPath;
    @Value("${saml2.sp.key-store.file}")
    private String keyStoreFile;
    @Value("${saml2.sp.key-store.password}")
    private String keyStorePassword;
    @Value("${saml2.sp.key-store.alias}")
    private String keyStoreAlias;
    @Autowired
    private SAMLUserDetailsService samlUserDetailsService;
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http
        .csrf().and()
        .authorizeRequests()
          .antMatchers("/saml/**").permitAll()
          .anyRequest().authenticated()
          .and()
        .apply(saml())
            .userDetailsService(samlUserDetailsService)
          .serviceProvider()
            .protocol(spProtocol) 
            .hostname(spHost)
            .basePath(spBashPath)
            .keyStore()
              .storeFilePath(keyStoreFile)
              .keyPassword(keyStorePassword)
              .keyname(keyStoreAlias)
            .and()
          .and()
          .identityProvider()
            .metadataFilePath(metadataPath)
          .and()
      .and();
    }
} 
