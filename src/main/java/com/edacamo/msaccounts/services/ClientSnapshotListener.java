package com.edacamo.msaccounts.services;

import com.edacamo.msaccounts.application.events.ClientEvent;
import com.edacamo.msaccounts.domain.entities.ClientSnapshot;
import com.edacamo.msaccounts.domain.repositories.ClientSnapshotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientSnapshotListener {

    @Autowired
    private ClientSnapshotRepository repository;

    @KafkaListener(topics = "client-events", groupId = "ms-accounts-client-snapshot")
    public void consume(ClientEvent event) {
        // Log para ver el contenido del evento recibido
        log.info("Received ClientEvent: clienteId={}, nombre={}, edad={}, identificacion={}, direccion:{}, telefono: {}, estado={}",
                event.getClienteId(), event.getNombre(), event.getEdad(), event.getIdentificacion(),
                event.getDireccion(), event.getTelefono(), event.getEstado());

        // Creación de ClientSnapshot
        ClientSnapshot snapshot = new ClientSnapshot();
        snapshot.setClienteId(event.getClienteId());
        snapshot.setNombre(event.getNombre());
        snapshot.setGenero(event.getGenero());
        snapshot.setEdad(event.getEdad());
        snapshot.setIdentificacion(event.getIdentificacion());
        snapshot.setDireccion(event.getDireccion());
        snapshot.setTelefono(event.getTelefono());
        snapshot.setEstado(event.getEstado());

        // Guardar el snapshot
        repository.save(snapshot);

        // Log para confirmar que el snapshot fue guardado
        log.info("ClientSnapshot for clienteId={} saved successfully", event.getClienteId());
    }
}
