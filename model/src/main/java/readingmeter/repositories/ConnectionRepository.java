package readingmeter.repositories;

import readingmeter.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
      Collection<Connection> findByProfileAccountUsername(String username);
}
