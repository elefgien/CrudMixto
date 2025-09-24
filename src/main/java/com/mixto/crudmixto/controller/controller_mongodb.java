package com.mixto.crudmixto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mixto.crudmixto.entity.Empleado;
import com.mixto.crudmixto.entity.Proyecto;
import com.mixto.crudmixto.service.EmpleadoService;
import com.mixto.crudmixto.service.ProyectoService;

@Controller
@RequestMapping("/proyectos")
public class controller_mongodb {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/listar")
    public String listarProyectos(Model model) {
        List<Proyecto> proyectos = proyectoService.obtenerTodos();
        model.addAttribute("proyectos", proyectos);
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "proyectos/proyecto-list"; // Corrected return statement
    }

    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "proyectos/proyecto-form"; // Corrected return statement
    }

    @PostMapping("/guardar")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto) {
        proyectoService.guardar(proyecto);
        return "redirect:/proyectos/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable String id, Model model) {
        Proyecto proyecto = proyectoService.obtenerPorId(id);
        model.addAttribute("proyecto", proyecto);
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "proyectos/proyecto-form"; // Corrected return statement
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable String id) {
        proyectoService.eliminar(id);
        return "redirect:/proyectos/listar";
    }
}