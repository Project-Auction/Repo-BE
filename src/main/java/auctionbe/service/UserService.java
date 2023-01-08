package auctionbe.service;


import auctionbe.models.User;
import auctionbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByAccountId(String id) {
        User user = userRepository.findUserByAccountId(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
        }
        return user;
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
        }
        return user.get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
