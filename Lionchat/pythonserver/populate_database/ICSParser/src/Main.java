import calendar.Event;
import parsing.ICSParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class Main {
    public static void main(String [] args)
    {
        URL calendarURL;
        ICSParser parser = new ICSParser();
        int counter = 1;

        SimpleDateFormat df = new SimpleDateFormat("MM_dd_yyyy-HH_mm_ss");
        String fileName = "Calendar_" + df.format(currentTimeMillis()) + ".ics";
        System.out.println(fileName);

        try
        {
            // creating url
            calendarURL = new URL("https://behrend.campuslabs.com/engage/events.ics");

            //creating channel to read file from url
            ReadableByteChannel readChannel = Channels.newChannel(calendarURL.openStream());

            //create output stream for file
            FileOutputStream fos = new FileOutputStream(fileName);

            //create channel to send data to file
            FileChannel fileChannel = fos.getChannel();

            //transfer data from reading channel to file channel
            fileChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);

            System.out.println("Information retrieved.");
        }
        catch(IOException e)
        {
            System.err.println("Exception thrown! \n");
            e.printStackTrace();
            System.exit(1);
        }

        if(parser.parse(fileName))
        {
            ArrayList<Event> events = parser.getEvents();

            System.out.println();

            for(Event e : events)
            {
                System.out.println(counter);
                System.out.println(e.getSummary());
                System.out.println("Event Host: " + e.getHost());
                e.printStartFormatted();
                e.printEndFormatted();
                System.out.println("Location: " + e.getLocation());

                for(String c : e.getCategories())
                {
                    System.out.println("Category: " + c);
                }

                System.out.println("Description: " + e.getDescription());

                System.out.println("Status: " + e.getStatus());

                System.out.println("URL: " + e.getURL());

                System.out.println("-\n");
                counter++;
            }


            if(parser.eventsToCSV(fileName))
            {
                System.out.println("CSV CONVERSION SUCCESSFUL");
            }
            else
            {
                System.out.println("CSV CONVERSION FAILED");
            }
        }
    }

}
