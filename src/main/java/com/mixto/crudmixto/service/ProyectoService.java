package com.mixto.crudmixto.service;

import com.mixto.crudmixto.entity.Proyecto;
import com.mixto.crudmixto.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Guarda o actualiza un proyecto
    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    // Obtiene un proyecto por su ID
    public Proyecto obtenerPorId(String id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        return proyecto.orElse(null);
    }

    // Obtiene todos los proyectos
    public List<Proyecto> obtenerTodos() {
        return proyectoRepository.findAll();
    }

    // Elimina un proyecto por su ID
    public void eliminar(String id) {
        proyectoRepository.deleteById(id);
    }

    // Método para el reto: obtener todos los proyectos de un empleado
    public List<Proyecto> obtenerProyectosPorEmpleadoId(Long empleadoId) {
        return proyectoRepository.findByEmpleadoId(empleadoId);
    }
}