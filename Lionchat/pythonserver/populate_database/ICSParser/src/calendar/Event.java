package calendar;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Event {
    private ArrayList<String> categories;  // arraylist of event categories
    private String description; //string for the description
    private String location; //string for the event location
    private String summary; //string for the event summary / title
    private String status; // string for status of event
    private String URL; //string for the URL to the event information
    private String host; // string for event host

    private Calendar startTime; // Calendar object for the startTime
    private Calendar endTime; // Calendar object for the endTime

    public Event()
    {
        categories = new ArrayList<String>();
        description = "";
        startTime = new GregorianCalendar();
        endTime = new GregorianCalendar();
        location = "";
        summary = "";
        URL = "";
        host = "";
    }

    public void addCategories(String c)
    {
        this.categories.add(c);
    }

    public ArrayList<String> getCategories()
    {
        return this.categories;
    }

    public void setDescription(String d)
    {
        this.description = d;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setLocation(String l)
    {
        this.location = l;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setSummary(String s)
    {
        this.summary = s;
    }

    public String getSummary()
    {
        return this.summary;
    }

    public void setStatus(String s)
    {
        this.status = s;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setURL(String u)
    {
        this.URL = u;
    }

    public String getURL()
    {
        return this.URL;
    }

    public void setHost(String h)
    {
        this.host = h;
    }

    public String getHost()
    {
        return this.host;
    }

    public void setStartTime(Calendar s) {
        this.startTime = s;
    }

    public Calendar getStartTime()
    {
        return this.startTime;
    }

    public void printStartFormatted()
    {
        System.out.println("Start Time: " + getFormattedTime(this.startTime));
    }

    public String getStartFormatted()
    {
        return getFormattedTime(this.startTime);
    }

    public void setEndTime(Calendar e) {
        this.startTime = e;
    }

    public Calendar getEndTime()
    {
        return this.endTime;
    }

    public void printEndFormatted()
    {
        System.out.println("End Time: " + getFormattedTime(this.endTime));
    }

    public String getEndFormatted()
    {
        return getFormattedTime(this.endTime);
    }

    private String getFormattedTime(Calendar c)
    {
        String timeString = String.format("%02d", c.get(Calendar.MONTH) + 1)  + "-" +
                String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "-" +
                String.format("%02d", c.get(Calendar.YEAR)) + " " +
                String.format("%02d", c.get(Calendar.HOUR)) + ":" +
                String.format("%02d", c.get(Calendar.MINUTE)) + ":" +
                String.format("%02d", c.get(Calendar.SECOND)) + " ";

                if(c.get(Calendar.AM_PM) == 1)
                {
                    timeString += "PM";
                }
                else
                {
                    timeString += "AM";
                }

        return timeString;
    }


    @Override
    public String toString()
    {
        String str = "";

        str = "\"" + this.summary + "\",";
        str += "\"" + this.host + "\",\"";

        for(int i = 0; i < this.categories.size(); i++)
        {
            str += this.categories.get(i);
            if(i < this.categories.size() - 1)
            {
                str += ", ";
            }
        }

        str += "\",";
        str += (new Timestamp(this.getStartTime().getTimeInMillis())).toString() + ",";
        str += (new Timestamp(this.getEndTime().getTimeInMillis())).toString() + ",";
        //str += this.getStartFormatted() + ",";
        //str += this.getEndFormatted() + ",";
        str += "\"" + this.location + "\",\"";
        str += this.description;
        str += "\",";
        str += this.status + ",";
        str += this.URL;
        str += "\n";

        //System.out.println(str);

        return str;
    }


}
