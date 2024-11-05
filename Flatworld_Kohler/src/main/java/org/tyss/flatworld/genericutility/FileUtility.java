package org.tyss.flatworld.genericutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This Utility is used to interact with property files
 */
public class FileUtility {

	/**
	 * This method is used to fetch the data from a Property File
	 * @param filePath - Path to the property file
	 * @param key - Key for which value needs to be fetched
	 * @return - Value associated with the given key
	 */
	public String getDataFromPropertyFile(String filePath, String key) {
		Properties properties = new Properties();

		// Try-with-resources to ensure FileInputStream is closed properly
		try (InputStream inputStream = new FileInputStream(filePath)) {
			properties.load(inputStream);
			String value = properties.getProperty(key).trim();

			// Log success message in Extent Report
			return value;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		// Return null if something went wrong
		return null;
	}
}
