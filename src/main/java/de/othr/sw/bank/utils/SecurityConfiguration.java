package de.othr.sw.bank.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("labresources")
    private UserDetailsService userSecurityService;
    @Autowired
    private EncryptionUtils encryptionUtils;

    private BCryptPasswordEncoder passwordEncoder() {
        return encryptionUtils.passwordEncoder();
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
            "/css/**", "/images/**", "/fonts/**", "/", "/login", "/forgotPassword", "/register"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                    .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
                    .permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                            redirectStrategy.sendRedirect(request, response, "/dashboard");
                        }
                    })
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                    .csrf()
                    .disable();


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
        /*
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user").password(passwordEncoder().encode("asdf")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("asdf")).roles("USER", "ADMIN");
        */
    }
}