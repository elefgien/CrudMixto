package com.mixto.crudmixto.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map; // <-- Importado
import java.util.stream.Collectors; // <-- Importado

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;
import com.mixto.crudmixto.entity.Empleado;
import com.mixto.crudmixto.entity.Proyecto;
import com.mixto.crudmixto.service.EmpleadoService;
import com.mixto.crudmixto.service.ProyectoService;
import com.mixto.crudmixto.util.ProyectoExcelExporter;
import com.mixto.crudmixto.util.ProyectoPDFExporter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/proyectos")
public class controller_mongodb {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private EmpleadoService empleadoService;

    // --------------------------------------------------------------------------
    // CRUD Y LISTADO (MODIFICADO PARA USAR MAPA)
    // --------------------------------------------------------------------------
     @GetMapping("/listar")
        public String listarProyectos(Model model) {
            List<Proyecto> proyectos = proyectoService.obtenerTodos();
            model.addAttribute("proyectos", proyectos);
            
            List<Empleado> empleadosList = empleadoService.obtenerTodos();
            
            // CORRECCIÓN CLAVE EN EL CONTROLADOR: 
            // Usamos .toString() para asegurar que la clave del mapa es un String consistente.
            Map<String, Empleado> empleadosMap = empleadosList.stream()
                .collect(Collectors.toMap(
                    // El ID se convierte a String. Si es nulo, usamos una cadena vacía como clave.
                    empleado -> empleado.getId() != null ? empleado.getId().toString() : "", 
                    empleado -> empleado
                ));
                
            model.addAttribute("empleadosMap", empleadosMap); // Pasa el mapa a la vista
            model.addAttribute("empleados", empleadosList); // También pasamos la lista para otros posibles usos (selects)
            
            return "proyectos/proyecto-list";
        }
    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "proyectos/proyecto-form";
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
        return "proyectos/proyecto-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable String id) {
        proyectoService.eliminar(id);
        return "redirect:/proyectos/listar";
    }

    // --------------------------------------------------------------------------
    // EXPORTACIÓN A PDF (TODOS)
    // --------------------------------------------------------------------------
    @GetMapping("/exportar/pdf")
    public void exportarProyectosPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proyectos_" + currentDateTime + ".pdf"; 
        response.setHeader(headerKey, headerValue);

        List<Proyecto> listProyectos = proyectoService.obtenerTodos();
        
        ProyectoPDFExporter exporter = new ProyectoPDFExporter(listProyectos);
        exporter.export(response);
    }

    // --------------------------------------------------------------------------
    // EXPORTACIÓN A PDF (INDIVIDUAL)
    // --------------------------------------------------------------------------
    @GetMapping("/exportar/pdf/{id}")
    public void exportarProyectoIndividualPDF(@PathVariable("id") String id, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proyecto_" + id + ".pdf";
        response.setHeader(headerKey, headerValue);

        Proyecto proyecto = proyectoService.obtenerPorId(id);
        List<Proyecto> listProyectos = Collections.singletonList(proyecto);
        
        ProyectoPDFExporter exporter = new ProyectoPDFExporter(listProyectos);
        exporter.export(response);
    }

    // --------------------------------------------------------------------------
    // EXPORTACIÓN A EXCEL (TODOS)
    // --------------------------------------------------------------------------
    @GetMapping("/exportar/excel")
    public void exportarProyectosExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proyectos_todos.xlsx";
        response.setHeader(headerKey, headerValue);

        List<Proyecto> listProyectos = proyectoService.obtenerTodos();
        
        ProyectoExcelExporter exporter = new ProyectoExcelExporter(listProyectos);
        exporter.export(response);
    }

    // --------------------------------------------------------------------------
    // EXPORTACIÓN A EXCEL (INDIVIDUAL)
    // --------------------------------------------------------------------------
    @GetMapping("/exportar/excel/{id}")
    public void exportarProyectoIndividualExcel(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=proyecto_" + id + ".xlsx";
        response.setHeader(headerKey, headerValue);

        Proyecto proyecto = proyectoService.obtenerPorId(id);
        List<Proyecto> listProyectos = Collections.singletonList(proyecto);

        ProyectoExcelExporter exporter = new ProyectoExcelExporter(listProyectos);
        exporter.export(response);
    }
}