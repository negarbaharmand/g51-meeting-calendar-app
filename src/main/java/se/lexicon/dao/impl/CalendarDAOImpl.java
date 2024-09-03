package se.lexicon.dao.impl;

import se.lexicon.dao.CalendarDAO;
import se.lexicon.model.Calendar;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//todo Implement methods
public class CalendarDAOImpl implements CalendarDAO {

    @Override
    public Calendar createCalendar(String title, String username) {
        return null;
    }

    @Override
    public Optional<Calendar> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Calendar> findCalendarByUsername(String username) {
        return List.of();
    }

    @Override
    public Optional<Calendar> findByTitleAndUsername(String title, String username) {
        return Optional.empty();
    }

    @Override
    public boolean deleteCalendar(int id) {
        return false;
    }
}
