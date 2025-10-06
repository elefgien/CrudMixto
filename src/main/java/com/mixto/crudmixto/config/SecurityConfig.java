package com.mixto.crudmixto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Importación necesaria

import com.mixto.crudmixto.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Manejo de Autorización de Peticiones
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll() // Rutas públicas
                .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
            )
            // 2. Manejo de Login
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/menu", true)
                .permitAll()
            )
            // 3. MANEJO DE LOGOUT (¡MODIFICADO AQUÍ!)
            .logout(logout -> logout
                // Define la URL que activa el cierre de sesión (ej: el botón de tu HTML apunta a /logout)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
                // CLAVE: Redirige explícitamente a /login después de un cierre de sesión exitoso.
                .logoutSuccessUrl("/login?logout") 
                // Invalida la sesión HTTP actual
                .invalidateHttpSession(true) 
                // Elimina la cookie JSESSIONID para una limpieza total
                .deleteCookies("JSESSIONID") 
                .permitAll()
            );
        return http.build();
    }

    // ... (Métodos passwordEncoder y authenticationProvider se mantienen igual) ...
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}