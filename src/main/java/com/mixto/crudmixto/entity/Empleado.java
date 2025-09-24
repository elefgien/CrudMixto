package com.mixto.crudmixto.entity;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "salario")
    private double salario;

    @Column(name = "email", unique = true)
    private String email;
    @ElementCollection
    private List<String> proyectoIds;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }
    public List<String> getProyectoIds() {
        return proyectoIds;
    }

    public void setProyectoIds(List<String> proyectoIds) {
        this.proyectoIds = proyectoIds;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}