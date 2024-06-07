    package com.example.project.authentication;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.CrossOrigin;

    import javax.sql.DataSource;

    @Configuration
    @EnableWebSecurity
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    public class SecurityConfig {

        @Autowired
        private DataSource dataSource;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            .requestMatchers("/api/public/**").permitAll()
                            .anyRequest().authenticated())
                    .formLogin(formLogin -> formLogin
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/Home", true)
                            .permitAll())
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                            .permitAll());
            return http.build();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                    .authoritiesByUsernameQuery("select username, authority from authorities where username = ?");
        }

        // Required to provide UserDetailsService for "remember functionality"
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER");
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
