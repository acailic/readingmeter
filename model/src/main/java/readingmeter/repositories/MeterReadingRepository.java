package readingmeter.repositories;

import readingmeter.Connection;
import readingmeter.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {
    Collection<MeterReading> findByConnection(Connection connection);
}

