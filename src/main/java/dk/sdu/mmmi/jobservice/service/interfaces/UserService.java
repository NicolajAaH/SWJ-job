package dk.sdu.mmmi.jobservice.service.interfaces;


import dk.sdu.mmmi.jobservice.service.model.User;

public interface UserService {
    User createUser(User user);

    User getUser(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);
}
