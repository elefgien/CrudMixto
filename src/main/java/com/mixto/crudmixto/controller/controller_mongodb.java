package com.mixto.crudmixto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mixto.crudmixto.entity.Proyecto;
import com.mixto.crudmixto.service.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class controller_mongodb {
    @Autowired
    private ProyectoService proyectoService;

    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody Proyecto proyecto) {
        Proyecto nuevoProyecto = proyectoService.guardar(proyecto);
        return new ResponseEntity<>(nuevoProyecto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerProyectos() {
        List<Proyecto> proyectos = proyectoService.obtenerTodos();
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable String id) {
        Proyecto proyecto = proyectoService.obtenerPorId(id);
        if (proyecto != null) {
            return new ResponseEntity<>(proyecto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable String id, @RequestBody Proyecto proyecto) {
        proyecto.setId(id);
        Proyecto proyectoActualizado = proyectoService.guardar(proyecto); // Línea corregida
        return new ResponseEntity<>(proyectoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable String id) {
        proyectoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Proyecto>> obtenerProyectosPorEmpleadoId(@PathVariable Long empleadoId) {
        List<Proyecto> proyectos = proyectoService.obtenerProyectosPorEmpleadoId(empleadoId);
        return new ResponseEntity<>(proyectos, HttpStatus.OK);
    }
}