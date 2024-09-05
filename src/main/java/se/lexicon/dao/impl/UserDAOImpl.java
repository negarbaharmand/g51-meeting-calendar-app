package se.lexicon.dao.impl;

import se.lexicon.dao.UserDAO;
import se.lexicon.exception.AuthenticationFailedException;
import se.lexicon.exception.MySQLException;
import se.lexicon.exception.UserExpiredException;
import se.lexicon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User createUser(String username) {
        String query = "insert into users(username, _password) values(?,?)";
        try (
                //Connection connection = CalendarDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            User user = new User(username);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getHashedPassword());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new MySQLException("Creating user failed, no rows affected.");
            }
            return user;
        } catch (SQLException e) {
            throw new MySQLException("Error occurred while creating user: " + username, e);
        }
    }

    @Override
    public Optional<User> finByUsername(String username) {
        String query = "select * from users where username = ?";

        try (
                //Connection connection = OracleDBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);

        ) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String foundPassword = resultSet.getString("_password");
                boolean foundExpired = resultSet.getBoolean("expired");
                User user = new User(foundUsername, foundPassword, foundExpired);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MySQLException("Error occurred while finding user by username: " + username, e);
        }
    }

    @Override
    public boolean authenticate(User user) throws AuthenticationFailedException, UserExpiredException {
        //step 1: define select query
        String query = "select * from users where username = ?";
        //step 2: create prepared statement
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            //step 3: set parameters to the statement
            preparedStatement.setString(1, user.getUsername());
            //step 4: execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            //step 5: check the result set
            //step 6: if the result set exists:
            if (resultSet.next()) {
                //step 7: check if the user is expired -> throw exception
                boolean isExpired = resultSet.getBoolean("expired");
                if (isExpired) {
                    throw new UserExpiredException("User is expired. username: " + user.getUsername());
                }
                String hashedPassword = resultSet.getString("_password");
                user.checkHash(hashedPassword);

            } else { //step 8: else if the result set was null throw exception
                throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
            }
            //step 9: return true
            return true;
        } catch (SQLException e) {
            throw new MySQLException("Error occurred while authenticating user by username: " + user.getUsername());
        }

    }
}
