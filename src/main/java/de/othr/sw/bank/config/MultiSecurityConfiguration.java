package de.othr.sw.bank.config;

import de.othr.sw.bank.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

@Configuration
@EnableWebSecurity
public class MultiSecurityConfiguration {

    @Order(1)
    @Configuration
    public static class SecurityConfigurationCustomer extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("customerUserDetailsService")
        private UserDetailsService customerService;
        @Autowired
        private EncryptionUtils encryptionUtils;

        private BCryptPasswordEncoder passwordEncoder() {
            return encryptionUtils.passwordEncoder();
        }

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
                "/css/**", "/js/**", "/images/**", "/fonts/**", "/login", "/forgotPassword", "/register","/customer/**","/"};

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
                    .permitAll().anyRequest().authenticated();
            http
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .successHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/dashboard"))
                    .failureUrl("/login?error=true")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                    .rememberMe()
                    .and()
                    .csrf()
                    .disable();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customerService).passwordEncoder(passwordEncoder());
        }
    }

    @Configuration
    @Order(2)
    public static class SecurityConfigurationEmployee extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("employeeUserDetailsService")
        private UserDetailsService employeeService;
        @Autowired
        private EncryptionUtils encryptionUtils;

        private BCryptPasswordEncoder passwordEncoder() {
            return encryptionUtils.passwordEncoder();
        }

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
                "/css/**", "/js/**", "/images/**", "/fonts/**", "/login", "/forgotPassword", "/register","/employee/**","/"};

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
                    .permitAll().anyRequest().authenticated();
            http
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .successHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/dashboard"))
                    .failureUrl("/login?error=true")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                    .rememberMe()
                    .and()
                    .csrf()
                    .disable();


        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(employeeService).passwordEncoder(passwordEncoder());
        }
    }
}
