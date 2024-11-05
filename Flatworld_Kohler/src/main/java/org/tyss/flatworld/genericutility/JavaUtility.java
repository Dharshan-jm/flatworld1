package org.tyss.flatworld.genericutility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * This Utility class provides common Java-based utility methods,
 * such as generating random numbers, strings, and fetching system details.
 */
public class JavaUtility {
	
    /**
     * This method returns the current date and time in a specified format.
     * 
     * @param format - The desired date-time format (e.g., "yyyy-MM-dd HH:mm:ss").
     * @return The formatted date-time string.
     */
    public String getDateAndTimeInSpecifiedFormat(String format) {
        String dateTime = new SimpleDateFormat(format).format(new Date()).toString();
        return dateTime;
    }
    
    public String getRequiredDateAndTimeInSpecifiedFormat(String format,int days) {
		Date date = new Date();
		 SimpleDateFormat dateTime = new SimpleDateFormat(format);
		dateTime.format(date);
		Calendar cal = dateTime.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH,days);
		String result = dateTime.format(cal.getTime());
		return result;
	}

    /**
     * Generates a random number within a specified range.
     * 
     * @param range - The upper bound for the random number (exclusive).
     * @return A random integer from 0 (inclusive) to the specified range (exclusive).
     */
    public int getRandomNumber(int range) {
        int randomNumber = new Random().nextInt(range);
        return randomNumber;
    }

    /**
     * Retrieves the current project directory path.
     * 
     * @return The absolute path of the current project directory.
     */
    public String getCurrentProjectDirectory() {
        String projectDir = System.getProperty("user.dir");
        return projectDir;
    }

    /**
     * Generates a random alphanumeric string of a specified length.
     * 
     * @param numberOfCharacters - The desired length of the random string.
     * @return A randomly generated alphanumeric string of length n.
     */
    public String getRandomAlphaNumericString(int numberOfCharacters) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(numberOfCharacters);

        for (int i = 0; i < numberOfCharacters; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        String randomString = sb.toString();
        return randomString;
    }
}
