package com.example.shopstore.config;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shopstore.common.CloudinaryCommon;
import com.example.shopstore.config.ajax.AjaxAwareAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
@Transactional
@ComponentScan(basePackages = {
        "com.example.shopstore.repository",
        "com.example.shopstore.service"
})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("loginSuccessHandler")
    private AuthenticationSuccessHandler loginSuccessHandler;

    @Autowired
    @Qualifier("logoutHandler")
    private LogoutSuccessHandler logoutHandler;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CloudinaryCommon.cloudName,
                "api_key", CloudinaryCommon.apiKey,
                "api_secret", CloudinaryCommon.apiSecret,
                "secure", CloudinaryCommon.secure
        ));
        return c;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**/admin/**").access("hasRole('ROLE_ADMIN')");

        http.authorizeRequests().antMatchers("/**/cart/**").authenticated();
        http.authorizeRequests().antMatchers("/**/checkout/**").authenticated();
        http.authorizeRequests().antMatchers("/**/user/**").authenticated();

        http.authorizeRequests().antMatchers("/").permitAll();

        http.authorizeRequests().and()
                .exceptionHandling().accessDeniedPage("/403")
                .and().exceptionHandling().authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login"));

        http.formLogin().loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(this.loginSuccessHandler).failureUrl("/login?error");

        http.logout().logoutSuccessHandler(this.logoutHandler);

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/**");
    }

}
