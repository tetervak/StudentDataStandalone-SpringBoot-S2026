package ca.tetervak.studentdata.data.repositories;


import ca.tetervak.studentdata.data.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserDataRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String userName);

    void deleteByUsername(String useName);

    List<AppUser> findAllByOrderByUsername();
}
