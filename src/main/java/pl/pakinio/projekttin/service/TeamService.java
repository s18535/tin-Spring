package pl.pakinio.projekttin.service;

import org.springframework.stereotype.Service;
import pl.pakinio.projekttin.dto.TeamInPlayerReadModel;
import pl.pakinio.projekttin.exception.NotFoundIdException;
import pl.pakinio.projekttin.exception.TeamAlreadyExistException;
import pl.pakinio.projekttin.model.Player;
import pl.pakinio.projekttin.model.Team;
import pl.pakinio.projekttin.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    TeamService(final TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> readAll() {
        return teamRepository.findAll();
    }

   /* public void createTeam(Team team) throws TeamAlreadyExistException {
        if (teamRepository.existsByTeamName(team.getTeamName())){
            throw new TeamAlreadyExistException("Team already exists for this teamName");
        }
        teamRepository.save(team);
    }*/
    public Team createTeam(Team team) throws TeamAlreadyExistException {
        if (teamRepository.existsByTeamName(team.getTeamName())){
            throw new TeamAlreadyExistException("Team already exists for this teamName");
        }
        teamRepository.save(team);
        return team;
    }

    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }

    public Team findTeam(int id) throws NotFoundIdException {
        return teamRepository.findById(id).
                orElseThrow(() -> new NotFoundIdException("not found"));
    }

   /* public void findTeamUpdate(int id, Team toUpdate) {
        teamRepository.findById(id).ifPresent(player -> {
            player.updateForm(toUpdate);
            teamRepository.save(player);
        });
    }*/
   public Team findTeamUpdate(int id, Team toUpdate){
       return teamRepository.findById(id)
               .map(team -> {
                   team.setTeamName(toUpdate.getTeamName());
                   team.setDescription(toUpdate.getDescription());
                   team.setMinAge(toUpdate.getMinAge());
                   team.setDateTo(toUpdate.getDateTo());
                   return teamRepository.save(team);
               })
               .orElseGet(() -> {
                   toUpdate.setIdTeam(id);
                   return teamRepository.save(toUpdate);
               });
   }

    public Team save(Team toSave) {
        return teamRepository.save(toSave);
    }

    public List<TeamInPlayerReadModel> showTeamInPlayer(int id) throws NotFoundIdException {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NotFoundIdException("not found"));
        List<TeamInPlayerReadModel> result = team.getPlayerTeams().stream().map(element -> {
            TeamInPlayerReadModel readModel = new TeamInPlayerReadModel();
            readModel.setDateFrom(element.getDateFrom());
            readModel.setDateTo(element.getDateTo());
            readModel.setFirstName(element.getPlayer().getFirstName());
            readModel.setLastName(element.getPlayer().getLastName());
            return readModel;
        }).collect(Collectors.toList());
        return result;
    }
    public List<Team> showPLayerForUsers(String name){
        return teamRepository.findByUserUsername(name);
    }

}
