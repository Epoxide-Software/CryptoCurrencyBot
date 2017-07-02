package net.darkhax.xchange.util;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.io.*;

public class DataUtils {

    /**
     * A wrapper for {@link DataUtils#copyURLToFile(URL, File)}. Allows for quick download of
     * files based on input from users.
     *
     * @param site The site/url to download the file from.
     * @param fileName The location to save the file to.
     *
     * @return The file that was downloaded.
     */
    public static File downloadFile (String site, String fileName) {

        final File file = new File(fileName);

        try {

            FileUtils.copyURLToFile(new URL(site), file);
        }

        catch (final IOException e) {

            e.printStackTrace();
        }

        return file;
    }

    public static String humanReadableByteCount (long bytes, boolean si) {

        final int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        final int exp = (int) (Math.log(bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
    
    public static String readJson(String urlString) {
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))){
            
            StringBuffer buffer = new StringBuffer();
            
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        }
        
        catch (IOException e) {

        	//TODO logger
        }
        
        return "{}";
    }
    
    public static <T extends Object> T[] getSubArray(T[] array, int start) {
    	
    	return Arrays.copyOfRange(array, start, array.length);
    }
}
