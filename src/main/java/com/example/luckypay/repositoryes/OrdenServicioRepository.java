package com.example.luckypay.repositoryes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.luckypay.models.OrdenServicio;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {

    // Órdenes agendadas dentro de un rango (sirve para la agenda de un día)
    List<OrdenServicio> findByFechaAgendadaBetweenOrderByFechaAgendadaAsc(
            LocalDateTime inicio, LocalDateTime fin);

    // Historial con filtros opcionales: si un parámetro llega null, no filtra por él
    @Query("select o from OrdenServicio o "
            + "where (:estado is null or o.estado.nombre = :estado) "
            + "and (:desde is null or o.fechaAgendada >= :desde) "
            + "and (:hasta is null or o.fechaAgendada <= :hasta) "
            + "order by o.fechaAgendada desc")
    List<OrdenServicio> consultarHistorial(@Param("estado") String estado,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta);
}