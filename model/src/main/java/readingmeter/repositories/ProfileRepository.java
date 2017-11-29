package readingmeter.repositories;

import readingmeter.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ProfileRepository extends JpaRepository<Profile, Long>, PagingAndSortingRepository<Profile, Long> {
    Collection<Profile> findByAccountUsername(String username);
    Profile findByNameAndAccountUsername(String name,String username);
}