package com.mixto.crudmixto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mixto.crudmixto.entity.Empleado;
import com.mixto.crudmixto.service.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class controller_mysql { // Renombrado a controller_mysql

    @Autowired
    private EmpleadoService empleadoService;

    // Muestra todos los empleados en la vista 'empleados/list'
    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        model.addAttribute("empleados", empleadoService.obtenerTodos());
        return "empleados/list";
    }

    // Muestra el formulario para crear un nuevo empleado
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "empleados/form";
    }

    // Guarda un nuevo empleado o actualiza uno existente
    @PostMapping("/guardar")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model) {
        try {
            empleadoService.guardar(empleado);
            return "redirect:/empleados";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "empleados/form";
        }
    }

    // Muestra el formulario para editar un empleado
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Empleado empleado = empleadoService.obtenerPorId(id);
        model.addAttribute("empleado", empleado);
        return "empleados/form";
    }

    // Elimina un empleado por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable("id") Long id) {
        empleadoService.eliminar(id);
        return "redirect:/empleados";
    }
}