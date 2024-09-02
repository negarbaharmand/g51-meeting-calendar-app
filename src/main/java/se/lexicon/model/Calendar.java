package se.lexicon.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private int id;
    private String title;
    private List<Meeting> meetings;
    private String username;


    public Calendar(String title, String username) {
        this.title = title;
        this.username = username;
    }

    public Calendar(int id, String title, String username) {
        this(title, username);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Meeting> getMeetings() {
        if (meetings == null) meetings = new ArrayList<>();
        return meetings;
    }

    public String getUsername() {
        return username;
    }

    public void addMeeting(Meeting meeting) {
        if (meetings == null) meetings = new ArrayList<>();
        //todo check if meeting exist in meetings list
        meetings.add(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        if (meetings == null) throw new IllegalArgumentException("Meeting list is null.");
        if (meeting == null) throw new IllegalArgumentException("Meeting data is null");
        meetings.remove(meeting);
    }

    public String calendarInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calendar info: ").append("\n");
        stringBuilder.append("Id ").append(id).append("\n");
        stringBuilder.append("Title ").append(title).append("\n");
        stringBuilder.append("Username ").append(username).append("\n");
        return stringBuilder.toString();
    }

}
