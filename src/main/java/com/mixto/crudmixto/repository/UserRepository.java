package com.mixto.crudmixto.repository; // Asegúrate de que el paquete sea correcto

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mixto.crudmixto.entity.User; // Usa Optional para manejar el caso de no encontrar el usuario

// Asegúrate de usar la entidad correcta (User) y el tipo de dato de la clave primaria (Long)
public interface UserRepository extends JpaRepository<User, Long> {

    // Este método es crucial para Spring Security. 
    // Debe devolver exactamente una entidad o un Optional vacío.
    Optional<User> findByUsername(String username);
}