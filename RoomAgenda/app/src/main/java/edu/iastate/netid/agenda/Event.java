package edu.iastate.netid.agenda;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents the Event object whose data will be stored in the database.
 */
@Entity(tableName = "event")
public class Event {

    //TODO - create event attributes for a uid(int) that autogenerates, title(string), location(string), startTime(string), endTime(string), and details(string)

    @PrimaryKey(autoGenerate = true)
    private Integer id;


    private String title;
    private String location;
    private String startTime;
    private String endTime;
    private String details;

    /**
     * Constructs an event given data from database
     */
    public Event(String title, String location, String startTime,
                 String endTime, String details) {
        //TODO - assign the passed in variables to the corresponding private variables in this class
        this.title=title;
        this.location=location;
        this.startTime=startTime;
        this.endTime=endTime;
        this.details=details;
    }

    /**
     * Constructs an empty Event, used by Room database query
     */
    public Event() {
        this.title="";
        this.location="";
        this.startTime="";
        this.endTime="";
        this.details="";
    }



    //TODO - create getters and setters for all the above mentioned private variables

    public String getDetails() {
        return details;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    //TODO - uncomment the below methods once you have created the above variables
    /**
     * Returns the end time in the form of a Date object
     */
    public Date getEndTimeAsDate() {
        try {
            return new SimpleDateFormat("MMMM d, yyyy, 'at' h:mm a", Locale.US).parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public String getReadableStartTime() {
        return startTime.toString();
    }

    @Override
    public String toString() {
        return title + " - " + details;
    }

}