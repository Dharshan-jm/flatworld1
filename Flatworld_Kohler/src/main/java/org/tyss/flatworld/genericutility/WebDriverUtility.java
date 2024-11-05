package org.tyss.flatworld.genericutility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This utility class provides common WebDriver actions for interacting with web elements.
 * Each method is designed to be reusable across different test cases, and significant actions 
 * are logged to the Extent Report for better traceability.
 */
public class WebDriverUtility {

	/**
	 * Launches the specified URL in the provided WebDriver.
	 *
	 * @param driver - The WebDriver instance to use.
	 * @param url - The URL to open.
	 */
	public void navigateToUrl(WebDriver driver, String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Maximizes the browser window.
	 *
	 * @param driver - The WebDriver instance to use.
	 */
	public void maximizeWindow(WebDriver driver) {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Sets an implicit wait for the provided WebDriver.
	 *
	 * @param driver - The WebDriver instance to use.
	 * @param timeInSeconds - The timeout duration in seconds.
	 */
	public void setImplicitWait(WebDriver driver, int timeInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Waits for an element to be visible on the page.
	 *
	 * @param driver - The WebDriver instance to use.
	 * @param element - The WebElement to wait for.
	 * @param timeout - Maximum wait time in seconds.
	 */
	public void waitForElementVisibility(WebDriver driver, WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			throw e;
		}
	}

	/**
	 * Waits for an element to be clickable on the page.
	 *
	 * @param driver - The WebDriver instance to use.
	 * @param element - The WebElement to wait for.
	 * @param timeout - Maximum wait time in seconds.
	 */
	public void waitForElementToBeClickable(WebDriver driver, WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e) {
			throw e;
		}
	}

	/**
	 * Waits for a specified number of seconds (hard wait).
	 *
	 * @param seconds - Number of seconds to wait.
	 */
	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Takes a screenshot of the current browser window.
	 *
	 * @param driver - The WebDriver instance to use.
	 * @param screenshotName - The name to save the screenshot file.
	 * @return The file path of the saved screenshot.
	 */
	public String takeScreenshot(WebDriver driver, String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
			Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots/"));
			Files.copy(source.toPath(), Paths.get(destination));
			return destination;
		} catch (IOException e) {
			throw new RuntimeException("Failed to take screenshot: " + screenshotName, e);
		}
	}

	/**
	 * Clicks on a WebElement.
	 *
	 * @param element - The WebElement to click.
	 */
	public void clickElement(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Sends keys to a WebElement.
	 *
	 * @param element - The WebElement to send keys to.
	 * @param text - The text to enter into the element.
	 */
	public void enterText(WebElement element, String text) {
		try {
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			throw e;
		}
	}
	
	 /**
     * Opens a browser window based on the specified browser name and returns the WebDriver instance.
     *
     * @param browserName The name of the browser to open (e.g., "chrome", "firefox", "edge").
     * @return WebDriver instance for the specified browser.
     */
    public WebDriver openBrowserWindow(String browserName) {
        WebDriver driver = null;

        try {
            // Initialize WebDriver based on the specified browser
            switch (browserName.toLowerCase()) {
                case "chrome":
                    // Set up ChromeDriver using WebDriverManager
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;

                case "firefox":
                    // Set up FirefoxDriver using WebDriverManager
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                case "edge":
                    // Set up EdgeDriver using WebDriverManager
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;

                default:
                    // Log an error if the browser name is invalid
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }

            // Optional: Maximize the browser window
            driver.manage().window().maximize();

        } catch (Exception e) {
            // Log the exception in Extent Report
            throw e;  // Rethrow the exception for further handling
        }

        // Return the initialized WebDriver instance
        return driver;
    }
    
    
    // Method to verify if the current URL contains the expected URL
    public static void verifyUrlContains(String expectedUrl) {
        try {
            // Get the current URL of the page
            String currentUrl = UtilityObjectClass.getDriver().getCurrentUrl();
            
            // Check if the current URL contains the expected URL
            if (currentUrl.contains(expectedUrl)) {
                // Log a success message if the current URL contains the expected URL
            } else {
                // Log a failure message if the current URL does not contain the expected URL
            	Assert.fail();
            }
            
        } catch (Exception e) {
            // Log any exceptions that occur during the verification
        }
    }
    
    /**
     * @param switch to window
     */
      public void switchTowindow(WebDriver driver) {
    	  String parentWindow = driver.getWindowHandle();

  		Set<String> windows = driver.getWindowHandles();
  		Iterator<String> it = windows.iterator();
  		while (it.hasNext()) {
  			String childWindow = it.next();
  			if (!childWindow.equals(parentWindow)) {
  				driver.switchTo().window(childWindow);
  			}		
  		}
      }
      
      public void escape() throws AWTException {
    	  Robot robot = new Robot();

          // Simulate pressing the Escape key
          robot.keyPress(KeyEvent.VK_ESCAPE);
          robot.keyRelease(KeyEvent.VK_ESCAPE);
      }
    
    
}
