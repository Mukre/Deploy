package com.appeventos.criadorEventos.interfaces;

import com.appeventos.criadorEventos.models.Convidado;
import com.appeventos.criadorEventos.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository <Convidado, String>{
    Iterable<Convidado> findByEvento(Evento evento);
    Convidado findByRg(String rg);
}
