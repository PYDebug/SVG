package edu.tongji.webgis.config;

import edu.tongji.webgis.security.AccountUserDetailsService;
import edu.tongji.webgis.security.TokenAuthenticationFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
@PropertySource(value = { "/WEB-INF/spring-security.properties" })
@ComponentScan("edu.tongji.webgis")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = Logger
            .getLogger(SecurityConfig.class);

    @Autowired
    Environment               env;
    @Autowired
    AccountUserDetailsService asds;
    @Autowired
    TokenAuthenticationFilter tokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        tokenFilter.setSignInUrl(env.getProperty("url.signin"));
        tokenFilter.setSignOutUrl(env.getProperty("url.signout"));
        tokenFilter.setResourceUrl(env.getProperty("url.resource"));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .antMatcher("/api/**")
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class).csrf()
                .disable().authorizeRequests().antMatchers(env.getProperty("url.signin")).permitAll()
                .antMatchers("/api/resource/**").permitAll()
                .antMatchers("/api/**")
                .hasAnyRole("ADMIN", "NORMAL_USER", "SPECIAL_USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(asds);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
