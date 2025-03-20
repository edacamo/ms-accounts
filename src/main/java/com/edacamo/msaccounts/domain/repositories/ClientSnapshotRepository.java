package com.edacamo.msaccounts.domain.repositories;

import com.edacamo.msaccounts.domain.entities.ClientSnapshot;
import org.springframework.data.repository.CrudRepository;

public interface ClientSnapshotRepository extends CrudRepository<ClientSnapshot, Long> {
}
