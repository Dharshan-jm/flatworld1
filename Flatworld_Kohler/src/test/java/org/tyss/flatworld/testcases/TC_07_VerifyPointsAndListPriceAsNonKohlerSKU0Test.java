package org.tyss.flatworld.testcases;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.tyss.flatworld.genericutility.BaseClass;
import org.tyss.flatworld.genericutility.IConstants;
import org.tyss.flatworld.objectrepository.DashboardPage;
import org.tyss.flatworld.objectrepository.IncentiveAdminHomePage;
import org.tyss.flatworld.objectrepository.KohlerNewPage;
import org.tyss.flatworld.objectrepository.PointsCreditManagerPage;
import org.tyss.flatworld.objectrepository.PublicUserPage;
import org.tyss.flatworld.objectrepository.TransactionPage;

@Listeners(org.tyss.flatworld.genericutility.ListenerImplementationClass.class)
public class TC_07_VerifyPointsAndListPriceAsNonKohlerSKU0Test extends BaseClass {

	@Test
	public void runTest() throws InterruptedException, AWTException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		Reporter.log("----LOGIN SUCCESSFULL----", true);

		DashboardPage dashboardpage = new DashboardPage(driver);
		IncentiveAdminHomePage incentivepage = new IncentiveAdminHomePage(driver);
		PublicUserPage publicuserpage = new PublicUserPage(driver);
		KohlerNewPage kohlernewpage = new KohlerNewPage(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		PointsCreditManagerPage pointsCreditManagerPage = new PointsCreditManagerPage(driver);
		TransactionPage transactionpage = new TransactionPage(driver);

		int number = javaUtility.getRandomNumber(4000);
		String userid = "thor1";
		String firstname = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "firstname");
		String lastname = fileUtility.getDataFromPropertyFile(IConstants.PROPERTY_FILE_PATH, "lastname");
		String organization = javaUtility.getRandomAlphaNumericString(5);
		String email = javaUtility.getRandomAlphaNumericString(5) + "@gmail.com";
		String invoicenumber = "123" + javaUtility.getRandomNumber(10000);
		String skuName = "skuName" + javaUtility.getRandomNumber(1009);

		dashboardpage.getMenuIcon().click();
		Reporter.log("----MENU ICON HAMBURGER ICON CLICK SUCCESSFULL----", true);

		dashboardpage.getInsentiveAdminPanelLink().click();
		Reporter.log("----INCENTIVE ADMIN HOME PAGE CLICK SUCCESSFULL----", true);
		
		Reporter.log("----NAVIGATE TO POINTS CREDIT MANAGER----", true);
		dashboardpage.getincentivemenu().click();
		incentivepage.getPointsCreditManager().click();

		Reporter.log("----CLICK ON ADD CREDIT INVOICE BUTTON----", true);
		pointsCreditManagerPage.getAddCreditInvoiceButton().click();
		pointsCreditManagerPage.getInvoiceDate().sendKeys(javaUtility.getDateAndTimeInSpecifiedFormat("MM-dd-yyyy"));
		pointsCreditManagerPage.getInvoiceNumber().sendKeys(invoicenumber);
		webDriverUtility.waitForSeconds(7);
		pointsCreditManagerPage.getIneligibleSkuButton().click();
		pointsCreditManagerPage.getAddskuNewName().sendKeys(skuName);
		pointsCreditManagerPage.getAddSkuNewSaveButton().click();
		pointsCreditManagerPage.getUserid().sendKeys(userid);
		pointsCreditManagerPage.getAddCreditPageSaveButton().click();

		Reporter.log("----ADD CREDIT PAGE SAVE SUCCESSFULL----", true);
		
		Reporter.log("----VERIFY IN LIST----", true);
		String invoicenum=driver.findElement(By.xpath("(//span[contains(@class,'text-ellipsis')])[1]")).getText();
		String totalpoints=driver.findElement(By.xpath("(//td[contains(@role,'cell')])[10]")).getText();
		Reporter.log("INVOICE NUMBER :"+invoicenum, true);
		Reporter.log("TOTAL POINTS :"+totalpoints, true);
		

		webDriverUtility.waitForSeconds(10);
		dashboardpage.getincentivemenu().click();
		incentivepage.getUserAdministrationMenu().click();
		incentivepage.getPublicUserOption().click();

		Reporter.log("----NAVIGATE TO PUBLIC USER----", true);

		publicuserpage.getPublicUserSearchButton().sendKeys(userid, Keys.ENTER);
		publicuserpage.getPublicUserApplyButton().click();
		webDriverUtility.waitForSeconds(2);
		publicuserpage.getPublicUserActionButton().click();

		Reporter.log("----NAVIGATE TO IMPERSONATE USER----", true);

		js.executeScript("arguments[0].click()", publicuserpage.getPublicUserImpersonatebutton());

		Reporter.log("----SWITCH TO NEW WINDOW----", true);
		webDriverUtility.switchTowindow(driver);
		webDriverUtility.waitForSeconds(5);
		kohlernewpage.getMenuButton().click();
		webDriverUtility.waitForSeconds(2);

		Reporter.log("----NAVIGATE TO TRANSACTION LINK----", true);
		kohlernewpage.getTransactionLink().click();
		webDriverUtility.waitForSeconds(2);

		boolean actualresult = transactionpage.getPoints().isDisplayed();
		Assert.assertEquals(actualresult, true, "POINTS DISPLAYED");
        
		
		String actualinvoice=driver.findElement(By.xpath("(//span[contains(@class,'text-ellipsis')])[1]")).getText();
		Reporter.log("INVOICE IN TRANSACTION PAGE :"+actualinvoice, true);
		
		String actualpoints= transactionpage.getPoints().getText();
		Reporter.log("POINTS IN TRANSACTION PAGE :"+actualpoints, true);

		String pointsOnHold = transactionpage.getPointsOnHold().getText();
		Reporter.log("POINTS ON HOLD :"+pointsOnHold, true);
		
		if (actualinvoice.contains(invoicenum)) {
			Reporter.log("INVOICE IN BOTH LIST AND TRANSACTION PAGE IS MATCHING", true);
		}else {
			Reporter.log("INVOICE IN BOTH LIST AND TRANSACTION PAGE IS NOT MATCHING", true);
		}
		
		if (actualpoints.contains(totalpoints)) {
			Reporter.log("POINTS IN BOTH LIST AND TRANSACTION PAGE IS MATCHING", true);
		}else {
			Reporter.log("POINTS IN BOTH LIST AND TRANSACTION PAGE IS NOT MATCHING", true);
		}

	}

}
