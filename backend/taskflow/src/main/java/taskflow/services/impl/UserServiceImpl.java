package taskflow.services.impl;

import taskflow.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import taskflow.repository.UserRepository;
import taskflow.services.interfaces.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

     @Override
    public User save(User user){
         return userRepository.save(user);
     }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
