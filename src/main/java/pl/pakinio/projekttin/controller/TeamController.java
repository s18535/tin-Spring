package pl.pakinio.projekttin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pakinio.projekttin.exception.NotFoundIdException;
import pl.pakinio.projekttin.exception.PlayerAlreadyExistException;
import pl.pakinio.projekttin.exception.TeamAlreadyExistException;
import pl.pakinio.projekttin.model.Team;
import pl.pakinio.projekttin.repository.TeamRepository;
import pl.pakinio.projekttin.service.TeamService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
public class TeamController {
    private final TeamService teamService;
    private final TeamRepository teamRepository;

    public TeamController(TeamService teamService, TeamRepository teamRepository) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/teams")
    Collection<Team> ShowAllTeams() {
        return teamService.readAll();
    }
    @GetMapping("/team/{id}")
    ResponseEntity<?> getTeam(@PathVariable int id){
        Optional<Team> team = teamRepository.findById(id);
        return team.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/newteam")
    ResponseEntity<Team> createPlayer(@Valid @RequestBody Team team) throws URISyntaxException, TeamAlreadyExistException {
        //Player result = playerService.save(player);
        Team result = teamService.createTeam(team);
        return ResponseEntity.created(new URI("team/" + result.getIdTeam()))
                .body(result);
    }
    @PutMapping("/team/{id}")
    ResponseEntity<Team> updateGroup(@PathVariable int id, @Valid @RequestBody Team team){
        Team result = teamService.findTeamUpdate(id, team);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/team/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable int id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
