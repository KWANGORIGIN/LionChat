/**
 * class ICSParser
 * package parsing
 * This object will parse a .ics file and generate an arrayList of calendar Events, as well as provide
 * functionality to convert it to a .csv file
 * @author William Hemminger
 * 30 November 2020
 */

package parsing;

import calendar.Event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


public class ICSParser {
    private ArrayList<Event> eventsList; // this will the events found in the .ics file;

    public ICSParser()
    {
        eventsList = new ArrayList<Event>();
    }


    /**
     * function parse parses an .ics file and stores events into an ArrayList
     * @param filename; the name of the ics file to be parsed
     * @return boolean; whether or not the file was successfully parsed
     */
    public boolean parse(String filename)
    {
        if(!filename.contains(".ics"))
        {
            System.err.println("Not a valid file type!");
            return false;
        }

        String line = ""; // line will iterate through each line of the ics file
        String summary = ""; // summary string
        String description = ""; // description string
        String host = ""; // host string
        boolean eventFlag = false; // checks if an event is currently being read

        TimeZone UTC = TimeZone.getTimeZone("UTC"); // time zone variables to be used with starting and ending time
        TimeZone localTime = TimeZone.getDefault();

        //set up SimpleDateFormat
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        //looks for dates in this format

        dateFormatter.setTimeZone(UTC); // set time zone for date to UTC

        //scan the file
        try(Scanner reader = new Scanner(new FileInputStream(filename)))
        {
            Event tempEvent = new Event(); // create a new event
            eventFlag = true;

            while(reader.hasNext())
            {
                summary = "";
                description = "";

                line = reader.nextLine();
                //System.out.println(line);
                if(line.contains("BEGIN:VEVENT") && !eventFlag) // if no event created, create new one
                {
                    eventFlag = true; //set flag to true
                    tempEvent = new Event();
                }

                if(line.contains("CATEGORIES:")) // add categories
                {
                    tempEvent.addCategories(line.substring(11));
                }

                if(line.contains("SUMMARY:")) // add summary
                {
                    while(true)
                    {
                        if(!(line.contains("UID:")))
                        {
                            summary += "\n" + line;
                            line = reader.nextLine();
                        }
                        else
                        {
                            break;
                        }
                    }

                    summary = cleanText(summary, 9);
                    /*
                    summary = summary.substring(9).replaceAll("\\n\\s", "");
                    summary = summary.replaceAll("\\\\", "");
                    summary = summary.replaceAll("\"", "\"\"");
                     */
                    tempEvent.setSummary(summary);
                }

                if(line.contains("DESCRIPTION:")) // add description
                {
                    while(true)
                    {
                        if(!(line.contains("DTEND:")))
                        {
                            description += "\n" + line;
                            line = reader.nextLine();
                        }
                        else
                        {
                            break;
                        }
                    }

                    // process description string
                    /*
                    description = description.substring(13).replaceAll("\\n\\s", "");
                    description = description.replaceAll("\\\\n" , " ");
                    description = description.replaceAll("\\\\" , "");
                    description = description.replaceAll("\u00A0"," ");
                    description = description.replaceAll("\"", "\"\"");
                     */
                    description = cleanText(description, 13);

                    tempEvent.setDescription(description);
                }

                if(line.contains("LOCATION:")) // add location
                {
                    tempEvent.setLocation(line.substring(9).replaceAll("\\\\", ""));
                }

                if(line.contains("URL:")) // add URL
                {
                    tempEvent.setURL(line.substring(4));
                }

                if(line.contains("DTSTART:")) // configure and add starting time
                {
                    Date tempDate = dateFormatter.parse(line.substring(8), new ParsePosition(0));

                    tempEvent.getStartTime().setTimeZone(localTime);
                    tempEvent.getStartTime().setTime(tempDate);
                }

                if(line.contains("DTEND:")) // configure and add ending time
                {
                    Date tempDate = dateFormatter.parse(line.substring(6), new ParsePosition(0));
                    tempEvent.getEndTime().setTimeZone(localTime);
                    tempEvent.getEndTime().setTime(tempDate);
                }

                if(line.contains("STATUS:")) // add status of event
                {
                    tempEvent.setStatus(line.substring(7));
                }

                if(line.contains("X-HOSTS:"))
                {
                    host = line.substring(8);
                    host = host.replaceAll("\"", "\"\"");
                    tempEvent.setHost(host);
                }

                if(line.contains("END:VEVENT")) // add event to list
                {
                    eventFlag = false; //set flag to false
                    eventsList.add(tempEvent);
                }
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error");
            return false;
        }

        return true;
    }

    public ArrayList<Event> getEvents()
    {
        return this.eventsList;
    }

    /**
     * function eventsToCSV takes ICSParser's eventsList and converts to .csv file
     * @return boolean; whether or not conversion was successful
     */
    public boolean eventsToCSV(String fileName)
    {
        try(Formatter write =  new Formatter(fileName.substring(0, fileName.length() - 4) + ".csv"))
        {
            write.format("summary,host,categories,startTime,endTime,location,description,status,URL\n");
            for(Event e : this.eventsList) // loop through events in list and write properties to file
            {
                write.format(e.toString());
            }

            return true;

        }
        catch(FileNotFoundException e)
        {
            System.err.println("An error has occurred when trying to create the file.");
            return false;
        }
    }

    private String cleanText(String text, int start)
    {
        //System.out.println(text);
        text = text.substring(start).replaceAll("\\n\\s", "").replaceAll("\\\\n" , " ")
                .replaceAll("\\\\" , "").replaceAll("\u00A0"," ")
                .replaceAll("\"", "\"\"").replaceAll("%", "%%");

        //System.out.println(text);
        return text;
    }


}


