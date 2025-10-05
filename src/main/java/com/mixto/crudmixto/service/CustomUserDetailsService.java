package com.mixto.crudmixto.service; // O el paquete donde tengas tus servicios

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mixto.crudmixto.entity.User;
import com.mixto.crudmixto.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Inyección de dependencias
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usa el método findByUsername del repositorio que creaste
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));
        
        // Convierte tu entidad User a un objeto UserDetails de Spring Security
        // Nota: Si solo usas el campo 'role' simple, necesitarás un paso intermedio. 
        // A continuación, se muestra un ejemplo simple asumiendo roles en formato "ROLE_XYZ".
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole()))
        );
    }
}