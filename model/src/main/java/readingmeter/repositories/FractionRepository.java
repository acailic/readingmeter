package readingmeter.repositories;

import readingmeter.Fraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FractionRepository extends JpaRepository<Fraction, Long> {
    Collection<Fraction> findById(Long id);
}
