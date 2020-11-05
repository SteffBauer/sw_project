package de.othr.sw.bank.repo;

import de.othr.sw.bank.entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session,Long> {
}
