package pl.pakinio.projekttin.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pakinio.projekttin.model.Role;
import pl.pakinio.projekttin.model.User;
import pl.pakinio.projekttin.repository.RoleRepository;
import pl.pakinio.projekttin.repository.UserRepository;

import java.util.Optional;

@Service
public class SignUpService implements SignUp{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public SignUpService(UserRepository userRepository, RoleRepository roleReposiotry, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleReposiotry;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signUpUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if (roleOptional.isPresent()){
            user.getRoles().add(roleOptional.get());
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
