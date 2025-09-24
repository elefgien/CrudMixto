
package com.mixto.crudmixto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixto.crudmixto.entity.Empleado;
import com.mixto.crudmixto.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado guardar(Empleado empleado) {
        // Check if email already exists
        Empleado existing = empleadoRepository.findByEmail(empleado.getEmail());
        if (existing != null && !existing.getId().equals(empleado.getId())) {
            throw new IllegalArgumentException("Este correo ya esta registrado intenta con otro");
        }
        return empleadoRepository.save(empleado);
    }
    public Empleado obtenerPorId(Long id) { Optional<Empleado> empleado = empleadoRepository.findById(id); return empleado.orElse(null); }
    public List<Empleado> obtenerTodos() { return empleadoRepository.findAll(); }
    public void eliminar(Long id) { empleadoRepository.deleteById(id); }
}