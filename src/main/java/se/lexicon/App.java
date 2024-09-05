package se.lexicon;

import se.lexicon.controller.CalendarController;
import se.lexicon.dao.CalendarDAO;
import se.lexicon.dao.MeetingDAO;
import se.lexicon.dao.UserDAO;
import se.lexicon.dao.db.CalendarDBConnection;
import se.lexicon.dao.impl.CalendarDAOImpl;
import se.lexicon.dao.impl.MeetingDAOImpl;
import se.lexicon.dao.impl.UserDAOImpl;
import se.lexicon.view.CalendarView;
import se.lexicon.view.CalendarViewImpl;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        CalendarView calendarView = new CalendarViewImpl();
        UserDAO userDAO = new UserDAOImpl(CalendarDBConnection.getConnection());
        CalendarDAO calendarDAO = new CalendarDAOImpl(CalendarDBConnection.getConnection());
        MeetingDAO meetingDAO = new MeetingDAOImpl(CalendarDBConnection.getConnection());
        CalendarController calendarController = new CalendarController(calendarView, userDAO, calendarDAO, meetingDAO);
        calendarController.run();
    }
}
