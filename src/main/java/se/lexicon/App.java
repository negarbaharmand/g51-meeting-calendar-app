package se.lexicon;

import se.lexicon.dao.UserDAO;
import se.lexicon.dao.db.CalendarDBConnection;
import se.lexicon.dao.impl.UserDAOImpl;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.model.User;
import se.lexicon.util.ConsoleColors;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        UserDAO userDAO = new UserDAOImpl(CalendarDBConnection.getConnection());
//        User user = userDAO.createUser("admin 1");
//        System.out.println("user.userInfo() = " + user.userInfo());

//        Optional<User> userOptional = userDAO.finByUsername("admin");
//        if(userOptional.isPresent()){
//            System.out.println(userOptional.get().userInfo());
//        }
        try{
            userDAO.authenticate(new User("admin 1", "0SbXryPNuG"));
            System.out.println(ConsoleColors.GREEN + "You are logged in ..." + ConsoleColors.RESET);
        }catch (Exception e) {
            CalendarExceptionHandler.handleException(e);
        }

    }
}
