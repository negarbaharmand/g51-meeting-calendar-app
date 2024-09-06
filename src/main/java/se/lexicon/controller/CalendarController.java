package se.lexicon.controller;

import se.lexicon.dao.CalendarDAO;
import se.lexicon.dao.MeetingDAO;
import se.lexicon.dao.UserDAO;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.model.Calendar;
import se.lexicon.model.Meeting;
import se.lexicon.model.User;
import se.lexicon.view.CalendarView;

import java.util.*;

public class CalendarController {

    //dependencies:
    private CalendarView view;
    private UserDAO userDAO;
    private CalendarDAO calendarDAO;
    private MeetingDAO meetingDAO;

    //fields:
    private boolean isLoggedIn;
    private String username;

    public CalendarController(CalendarView view, UserDAO userDAO, CalendarDAO calendarDAO, MeetingDAO meetingDAO) {
        this.view = view;
        this.userDAO = userDAO;
        this.calendarDAO = calendarDAO;
        this.meetingDAO = meetingDAO;
    }

    public void run() {
        while (true) {
            view.displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 0:
                    register();
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    createCalendar();
                    break;
                case 3:
                    createNewMeeting();
                    break;
                case 4:
                    deleteSelectedCalendar();
                    break;
                case 5:
                    displayCalendar();
                    break;
                case 6:
                    isLoggedIn = false;
                    view.displayMessage("You are logged out.");
                    break;
                case 7:
                    //todo call exit
                    System.exit(0);
                    break;
                default:
                    view.displayWarningMessage("Invalid choice. Please select a valid option.");
            }
        }
    }

    private int getUserChoice() {
        String operationType = view.promoteString();
        int choice = -1;
        try {
            choice = Integer.parseInt(operationType);
        } catch (NumberFormatException e) {
            view.displayErrorMessage("Invalid input, please enter a number.");
        }
        return choice;
    }

    private void register() {
        view.displayMessage("Enter your username");
        String username = view.promoteString();
        User registeredUser = userDAO.createUser(username);
        view.displayUser(registeredUser);
    }

    private void login() {
        User user = view.promoteUserForm();
        try {
            isLoggedIn = userDAO.authenticate(user);
            username = user.getUsername();
            view.displaySuccessMessage("Login successful. Welcome " + username);
        } catch (Exception e) {
            CalendarExceptionHandler.handleException(e);
        }
    }

    private void createCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to login first.");
            return;
        }
        String calendarTitle = view.promoteCalendarForm();
        Calendar createdCalendar = calendarDAO.createCalendar(calendarTitle, username);
        view.displaySuccessMessage("Calendar created successfully.");
        view.displayCalendar(createdCalendar);

    }

    public void createNewMeeting() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to login first.");
            return;
        }
        System.out.println("Available calendars: ");
        Collection<Calendar> calendars = calendarDAO.findCalendarByUsername(username);
        for (Calendar calendar :
                calendars) {
            System.out.println("Calendar title: " + calendar.getTitle());
        }
        String calendarTitle = view.promoteCalendarForm();
        Optional<Calendar> meetingCalendarOptional = calendarDAO.findByTitleAndUsername(calendarTitle, username);
        if (!meetingCalendarOptional.isPresent()) {
            view.displayErrorMessage("Meeting calendar doesn't exist.");
            return;
        }

        Meeting newMeeting = view.promoteMeetingForm();
        newMeeting.setCalendar(meetingCalendarOptional.get());

        // Validate the meeting times before sending it to the DAO
        try {
            newMeeting.timeValidation();
        } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }

        Meeting createdMeeting = meetingDAO.createMeeting(newMeeting);
        view.displaySuccessMessage("Meeting created successfully.");
        view.displayMeetings(Collections.singletonList(createdMeeting));

    }

    public void deleteSelectedCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to log in first.");
            return;
        }

        System.out.println("Choose title from available calendars: ");
        Collection<Calendar> calendars = calendarDAO.findCalendarByUsername(username);
        for (Calendar calendar : calendars) {
            System.out.println("Title: " + calendar.getTitle());
        }

        String calendarTitle = view.promoteString();
        Optional<Calendar> meetingCalendarOptional = calendarDAO.findByTitleAndUsername(calendarTitle, username);

        if (!meetingCalendarOptional.isPresent()) {
            view.displayErrorMessage("Meeting calendar doesn't exist.");
            return;
        }

        Calendar meetingCalendar = meetingCalendarOptional.get();

        // Delete associated meetings first
        for (Meeting meeting : meetingDAO.findAllMeetingsByCalendarId(meetingCalendar.getId())) {
            meetingDAO.deleteMeeting(meeting.getId());
        }

        boolean isDeleted = calendarDAO.deleteCalendar(meetingCalendar.getId());

        if (isDeleted) {
            view.displaySuccessMessage("Calendar deleted successfully.");
        } else {
            view.displayWarningMessage("Failed to delete the calendar.");
        }
    }

    public void displayCalendar() {
        if (!isLoggedIn) {
            view.displayWarningMessage("You need to log in first.");
            return;
        }

        System.out.println("Choose a calendar to display: ");
        Collection<Calendar> calendars = calendarDAO.findCalendarByUsername(username);
        for (Calendar calendar : calendars) {
            System.out.println("Title: " + calendar.getTitle());
        }

        String calendarTitle = view.promoteString();
        Optional<Calendar> meetingCalendarOptional = calendarDAO.findByTitleAndUsername(calendarTitle, username);

        if (!meetingCalendarOptional.isPresent()) {
            view.displayErrorMessage("Meeting calendar doesn't exist.");
            return;
        }

        Calendar selectedCalendar = meetingCalendarOptional.get();

        view.displayCalendar(selectedCalendar);

        List<Meeting> meetings = meetingDAO.findAllMeetingsByCalendarId(selectedCalendar.getId());
        view.displayMeetings(meetings);
    }
}
