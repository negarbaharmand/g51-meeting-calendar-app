package se.lexicon.dao.implementation;

import se.lexicon.dao.UserDAO;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public User createUser(String username) {
        return null;
    }

    @Override
    public Optional<User> finByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        return false;
    }
}
