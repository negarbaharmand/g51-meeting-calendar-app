package se.lexicon.dao;

import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.util.Optional;

public interface UserDAO {
    User createUser(String username);

    Optional<User> finByUsername(String username);

    boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException;

}
