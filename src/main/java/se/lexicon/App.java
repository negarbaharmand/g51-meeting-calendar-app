package se.lexicon;

import se.lexicon.controller.CalendarController;
import se.lexicon.dao.CalendarDAO;
import se.lexicon.dao.UserDAO;
import se.lexicon.dao.db.CalendarDBConnection;
import se.lexicon.dao.impl.CalendarDAOImpl;
import se.lexicon.dao.impl.UserDAOImpl;
import se.lexicon.view.CalendarView;
import se.lexicon.view.CalendarViewImpl;

import java.sql.Connection;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        CalendarView view = new CalendarViewImpl();
        Connection connection = CalendarDBConnection.getConnection();
        UserDAO userDAO = new UserDAOImpl(connection);
        CalendarDAO calendarDao = new CalendarDAOImpl(connection);
        CalendarController controller = new CalendarController(view, userDAO, calendarDao);
        controller.run();

    }
}
