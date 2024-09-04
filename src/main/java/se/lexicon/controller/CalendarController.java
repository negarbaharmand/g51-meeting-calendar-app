package se.lexicon.controller;

import se.lexicon.dao.CalendarDAO;
import se.lexicon.dao.UserDAO;
import se.lexicon.exception.CalendarExceptionHandler;
import se.lexicon.model.Calendar;
import se.lexicon.model.User;
import se.lexicon.view.CalendarView;

public class CalendarController {

    //dependencies:
    private CalendarView view;
    private UserDAO userDAO;
    private CalendarDAO calendarDAO;

    //fields:
    private boolean isLoggedIn;
    private String username;

    public CalendarController(CalendarView view, UserDAO userDAO, CalendarDAO calendarDAO) {
        this.view = view;
        this.userDAO = userDAO;
        this.calendarDAO = calendarDAO;
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
                    //todo call create meeting method
                    break;
                case 4:
                    //todo call delete calendar method
                    break;
                case 5:
                    //todo call display calendar method
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

}
