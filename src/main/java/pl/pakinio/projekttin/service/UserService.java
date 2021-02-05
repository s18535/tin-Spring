package pl.pakinio.projekttin.service;

import org.springframework.stereotype.Service;
import pl.pakinio.projekttin.exception.NotFoundIdException;
import pl.pakinio.projekttin.model.User;
import pl.pakinio.projekttin.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(String name) throws NotFoundIdException {
        return userRepository.findByUsername(name).
                orElseThrow(() -> new NotFoundIdException("not found"));
    }
}
