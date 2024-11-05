package org.tyss.flatworld.workflowutility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.tyss.flatworld.genericutility.FileUtility;
import org.tyss.flatworld.genericutility.JavaUtility;
import org.tyss.flatworld.genericutility.UtilityObjectClass;
import org.tyss.flatworld.genericutility.WebDriverUtility;
import org.tyss.flatworld.objectrepository.SignInPage;

public class WorkflowUtility {
	FileUtility fileUtility = new FileUtility();
	JavaUtility javaUtility = new JavaUtility();
	WebDriverUtility webDriverUtility = new WebDriverUtility();

	public void signInToApplication(String email, String password) {
		SignInPage signInPage = new SignInPage(UtilityObjectClass.getDriver());
		webDriverUtility.enterText(signInPage.getUserIdTextfield(), email);
		webDriverUtility.enterText(signInPage.getPasswordTextfield(), password);
		webDriverUtility.clickElement(signInPage.getSignInButton());
	}
	
	public void screenSizeReduce() throws InterruptedException {
		Robot robot;
		try {
			robot = new Robot();

			// Give some time to switch to the correct window (optional)
			Thread.sleep(2000);

			// Press and hold the Ctrl key
			robot.keyPress(KeyEvent.VK_CONTROL);

			// Press the '-' key (minus)
			robot.keyPress(KeyEvent.VK_MINUS);
			robot.keyRelease(KeyEvent.VK_MINUS); // Release the '-' key

			// Release the Ctrl key
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

