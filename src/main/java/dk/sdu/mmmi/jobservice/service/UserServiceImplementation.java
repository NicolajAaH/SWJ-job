package dk.sdu.mmmi.jobservice.service;

import dk.sdu.mmmi.jobservice.service.interfaces.UserService;
import dk.sdu.mmmi.jobservice.service.interfaces.DatabaseService;
import dk.sdu.mmmi.jobservice.service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService {

    private final DatabaseService databaseService;
    @Override
    public User createUser(User user) {
        log.info("--> createUser: {}", user);
        return databaseService.createUser(user);
    }

    @Override
    public User getUser(long id) {
        log.info("--> getUser: {}", id);
        return databaseService.getUser(id);
    }

    @Override
    public User updateUser(long id, User user) {
        log.info("--> updateUser: {}", user);
        return databaseService.updateUser(id, user);
    }

    @Override
    public void deleteUser(long id) {
        log.info("--> deleteUser: {}", id);
        databaseService.deleteUser(id);
    }
}
