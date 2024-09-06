package se.lexicon.dao;

import se.lexicon.model.Meeting;

import java.util.List;
import java.util.Optional;

public interface MeetingDAO {
    Meeting createMeeting(Meeting meeting);

    Optional<Meeting> findById(int meetingId);

    //select * from meeting
    List<Meeting> findAllMeetingsByCalendarId(int calendarId);

    boolean deleteMeeting(int meetingId);

    //todo Add methods for updating calendars as needed
}
