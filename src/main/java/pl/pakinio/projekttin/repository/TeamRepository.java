package pl.pakinio.projekttin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pakinio.projekttin.model.Team;


import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {

    List<Team> findAll();

    Team save(Team team);

    void deleteById(Integer id);

    Optional<Team> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByTeamName(String teamName);

    List<Team> findByUserUsername(String name);

}
