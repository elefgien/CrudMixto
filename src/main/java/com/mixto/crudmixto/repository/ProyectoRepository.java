package com.mixto.crudmixto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mixto.crudmixto.entity.Proyecto;

@Repository
public interface ProyectoRepository extends MongoRepository<Proyecto, String> {

    /**
     * Este método es una consulta personalizada que Spring Data MongoDB
     * implementa automáticamente para encontrar todos los proyectos
     * que tienen el mismo empleadoId.
     *
     * @param empleadoId El ID del empleado a buscar.
     * @return Una lista de proyectos asignados a ese empleado.
     */
    List<Proyecto> findByEmpleadoId(Long empleadoId);
}