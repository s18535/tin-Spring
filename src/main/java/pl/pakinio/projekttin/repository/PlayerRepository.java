package pl.pakinio.projekttin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pakinio.projekttin.model.Player;


import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {

    List<Player> findAll();

    Player save(Player player);

    void deleteById(Integer id);

    Optional<Player> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByEmail(String email);

    public List<Player> findByFirstName(String FirstName);

    List<Player> findByUserUsername(String name);

}
