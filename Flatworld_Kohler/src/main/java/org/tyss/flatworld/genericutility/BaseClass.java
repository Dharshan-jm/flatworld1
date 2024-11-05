package org.tyss.flatworld.genericutility;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.tyss.flatworld.workflowutility.WorkflowUtility;

/**
 * Base class that sets up common configurations for test execution,
 * such as initializing utilities, setting up the browser.
 */
public class BaseClass {

	// Utilities for various operations like Excel, file handling, etc.
	public ExcelUtility excelUtility = new ExcelUtility();
	public FileUtility fileUtility = new FileUtility();
	public JavaUtility javaUtility = new JavaUtility();
	public AssertionUtility assertionUtility = new AssertionUtility();
	public WebDriverUtility webDriverUtility = new WebDriverUtility();
	public WorkflowUtility workflowUtility = new WorkflowUtility();

	// WebDriver instance for browser interaction
	public WebDriver driver;
	String browserName;

	/**
	 * Configures the test class before executing any tests.
	 * Opens the browser, maximizes the window, navigates to the URL,
	 * and sets implicit wait time.
	 */
	@BeforeClass
	public void configBeforeClass() {
		System.out.println("*********Open Browser*********");
		String url = "http://172.191.225.236:8080/Admin/sign-in";
		browserName = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "BROWSER_NAME");
		UtilityObjectClass.setBrowserName(browserName);

		// Open the browser
		driver = webDriverUtility.openBrowserWindow(browserName);
		UtilityObjectClass.setDriver(driver);
		webDriverUtility.maximizeWindow(driver);
		webDriverUtility.navigateToUrl(driver, url);
		webDriverUtility.setImplicitWait(driver, IConstants.IMPLICIT_WAIT_TIME);
	}
	
	/**
	 * Configures the test method before executing each test case.
	 * Checks if the test case exists in Jira and logs the execution.
	 */
	@BeforeMethod
	public void configBeforeMethod(ITestResult result) {
		// Sign in to the application
		String userId = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "APP_USERID");
		String password = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "APP_PASSWORD");
		workflowUtility.signInToApplication(userId, password);
	}
	
	/**
	 * Configures actions after each test method is executed.
	 * Updates the test case status in Jira based on the result.
	 */
//	@AfterMethod
//	public void configAfterMethod(ITestResult result) {
//		// Sign out from the application
//	}
//
//	/**
//	 * Closes the browser after all tests in the class are executed.
//	 */
//	@AfterClass
//	public void configAfterClass() {
//		System.out.println("*********Close Browser*********");
//		try {
//			if (driver != null) {
//				driver.quit();
//			}
//		} catch (Exception e) {
//		}
//	}
}
