package pl.pakinio.projekttin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.pakinio.projekttin.exception.NotFoundIdException;
import pl.pakinio.projekttin.exception.PlayerAlreadyExistException;
import pl.pakinio.projekttin.model.Player;
import pl.pakinio.projekttin.repository.PlayerRepository;
import pl.pakinio.projekttin.service.PlayerService;
import pl.pakinio.projekttin.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@CrossOrigin
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final UserService userService;

    public PlayerController(PlayerService playerService, PlayerRepository playerRepository, UserService userService) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.userService = userService;
    }

    @GetMapping("/players")
    Collection<Player> ShowAllPlayers() {
        return playerService.readAll();
    }
    @GetMapping("/player/{id}")
    ResponseEntity<?> getPlayer(@PathVariable int id) {
        Optional<Player> player = playerRepository.findById(id);
        return player.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/newplayer")
    ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) throws URISyntaxException, PlayerAlreadyExistException {
        //Player result = playerService.save(player);
        Player result = playerService.createPLayer(player);
        return ResponseEntity.created(new URI("player/" + result.getIdPlayer()))
                .body(result);
    }
    @PutMapping("/player/{id}")
    ResponseEntity<Player> updateGroup(@PathVariable int id, @Valid @RequestBody Player player){
        Player result = playerService.findPlayerUpdate(id, player);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/player/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable int id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok().build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors=new LinkedHashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            if (errors.containsKey(fieldName)){
                errors.get(fieldName).add(errorMessage);
            }else {
                errors.put(fieldName,new ArrayList<>());
                errors.get(fieldName).add(errorMessage);
            }

        });
        return errors;
    }
}
