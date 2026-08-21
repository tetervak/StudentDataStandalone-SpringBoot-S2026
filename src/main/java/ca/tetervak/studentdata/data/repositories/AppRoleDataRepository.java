package ca.tetervak.studentdata.data.repositories;

import ca.tetervak.studentdata.data.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleDataRepository extends JpaRepository<AppRole, Integer> {

    Optional<AppRole> findByRoleName(String roleName);
}
