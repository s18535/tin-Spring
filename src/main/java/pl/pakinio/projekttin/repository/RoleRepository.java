package pl.pakinio.projekttin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pakinio.projekttin.model.Role;


import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
