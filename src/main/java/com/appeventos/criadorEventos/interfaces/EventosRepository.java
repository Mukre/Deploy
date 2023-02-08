package com.appeventos.criadorEventos.interfaces;

import com.appeventos.criadorEventos.models.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepository extends CrudRepository<Evento, String> {
    Evento findByCodigo(long codigo);
}
