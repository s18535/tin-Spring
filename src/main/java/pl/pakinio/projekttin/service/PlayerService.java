package pl.pakinio.projekttin.service;

import org.springframework.stereotype.Service;
import pl.pakinio.projekttin.dto.PlayerInTeamReadModel;
import pl.pakinio.projekttin.exception.NotFoundIdException;
import pl.pakinio.projekttin.exception.PlayerAlreadyExistException;
import pl.pakinio.projekttin.model.Player;
import pl.pakinio.projekttin.repository.PlayerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    PlayerService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> readAll() {
        return playerRepository.findAll();
    }

  /*  public void createPLayer(Player playerCreate) throws PlayerAlreadyExistException {
        if (playerRepository.existsByEmail(playerCreate.getEmail())){
            throw new PlayerAlreadyExistException("User already exists for this email");
        }
        playerRepository.save(playerCreate);
    }*/
  public Player createPLayer(Player playerCreate) throws PlayerAlreadyExistException {
      if (playerRepository.existsByEmail(playerCreate.getEmail())){
          throw new PlayerAlreadyExistException("User already exists for this email");
      }
      playerRepository.save(playerCreate);
      return playerCreate;
  }

    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
        //System.out.println(id+" playerservice");
    }

    public Player findPlayer(int id) throws NotFoundIdException {
        return playerRepository.findById(id).
                orElseThrow(() -> new NotFoundIdException("not found"));
    }
    /*public void findPlayerUpdate(int id,Player toUpdate){
        playerRepository.findById(id).ifPresent(player->{player.updateForm(toUpdate);
            playerRepository.save(player);});
    }*/
    public Player findPlayerUpdate(int id,Player toUpdate){
        return playerRepository.findById(id)
                .map(player -> {
                    player.setFirstName(toUpdate.getFirstName());
                    player.setLastName(toUpdate.getLastName());
                    player.setEmail(toUpdate.getEmail());
                    player.setPhoneNumber(toUpdate.getPhoneNumber());
                    player.setDateOfBirth(toUpdate.getDateOfBirth());
                    return playerRepository.save(player);
                })
                .orElseGet(() -> {
                    toUpdate.setIdPlayer(id);
                    return playerRepository.save(toUpdate);
                });
    }
    public Player save(Player toSave){
        return playerRepository.save(toSave);
    }

    public List<PlayerInTeamReadModel> showPlayerInTeam(int id) throws NotFoundIdException {
        Player player1 = playerRepository.findById(id).orElseThrow(() -> new NotFoundIdException("not found"));

        List<PlayerInTeamReadModel> result = player1.getPlayerTeams().stream().map(element -> {
            PlayerInTeamReadModel readModel = new PlayerInTeamReadModel();
            readModel.setDateFrom(element.getDateFrom());
            readModel.setDateTo(element.getDateTo());
            readModel.setNumberOfShirt(element.getNumOfShirt());
            readModel.setTeamName(element.getTeam().getTeamName());
            return readModel;
        }).collect(Collectors.toList());
        return result;
    }

    public List<Player> showPLayerForUsers(String name){
        return playerRepository.findByUserUsername(name);
    }
}
