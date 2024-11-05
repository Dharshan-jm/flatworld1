package org.tyss.flatworld.testverify;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
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
import org.tyss.flatworld.objectrepository.CurriculumPage;
import org.tyss.flatworld.objectrepository.DashboardPage;
import org.tyss.flatworld.objectrepository.IncentiveAdminHomePage;
import org.tyss.flatworld.objectrepository.KohlerNewPage;
import org.tyss.flatworld.objectrepository.NavigationTitleQuizPage;
import org.tyss.flatworld.objectrepository.PointsCreditManagerPage;
import org.tyss.flatworld.objectrepository.PublicUserPage;
import org.tyss.flatworld.objectrepository.RedeemPage;
import org.tyss.flatworld.objectrepository.TrainingCoursePage;
import org.tyss.flatworld.objectrepository.TrainingPage;
import org.tyss.flatworld.objectrepository.TransactionPage;
import org.tyss.flatworld.workflowutility.WorkflowUtility;
@Listeners(org.tyss.flatworld.genericutility.ListenerImplementationClass.class)
public class TC22Test extends BaseClass{
	
	@Test
	public void testRun() throws InterruptedException, EncryptedDocumentException, IOException, AWTException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		Reporter.log("----LOGIN SUCCESSFULL----", true);

		DashboardPage dashboardpage = new DashboardPage(driver);
		IncentiveAdminHomePage incentivepage = new IncentiveAdminHomePage(driver);
		PublicUserPage publicuserpage = new PublicUserPage(driver);
		KohlerNewPage kohlernewpage = new KohlerNewPage(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		PointsCreditManagerPage pointsCreditManagerPage = new PointsCreditManagerPage(driver);
		TransactionPage transactionpage = new TransactionPage(driver);
		RedeemPage redeempage=new RedeemPage(driver);

		String userid ="4042079";
		String invoicenumber = "123" + javaUtility.getRandomNumber(10000);
		String numberValue="4";

		dashboardpage.getMenuIcon().click();
		Reporter.log("----MENU ICON HAMBURGER ICON CLICK SUCCESSFULL----", true);

		dashboardpage.getInsentiveAdminPanelLink().click();
		Reporter.log("----INCENTIVE ADMIN HOME PAGE CLICK SUCCESSFULL----", true);
		webDriverUtility.waitForSeconds(2);

		Reporter.log("----NAVIGATE TO POINTS CREDIT MANAGER----", true);
		dashboardpage.getincentivemenu().click();
		incentivepage.getPointsCreditManager().click();

		Reporter.log("----CLICK ON ADD CREDIT INVOICE BUTTON----", true);
		pointsCreditManagerPage.getAddCreditInvoiceButton().click();
		pointsCreditManagerPage.getInvoiceDate().sendKeys(javaUtility.getDateAndTimeInSpecifiedFormat("MM-dd-yyyy"));
		pointsCreditManagerPage.getInvoiceNumber().sendKeys(invoicenumber);
		webDriverUtility.waitForSeconds(7);
	    
	    js.executeScript("arguments[0].click();", pointsCreditManagerPage.getSkudropdown());
	    
	    pointsCreditManagerPage.getSkucheckbox().click();
       
	    webDriverUtility.escape();
	 
        webDriverUtility.waitForSeconds(2);
        String skuttotalprice = pointsCreditManagerPage.getSkuTotalPrice().getText();
		String skuttotalpoints = pointsCreditManagerPage.getSkuTotalPoints().getText();
		Reporter.log("----TOTAL PRICE " + skuttotalprice, true);
		Reporter.log("----TOTAL POINTS " + skuttotalpoints, true);
		pointsCreditManagerPage.getUserid().sendKeys(userid); 
       
        pointsCreditManagerPage.getAddCreditPageSaveButton().click();

		Reporter.log("----ADD CREDIT PAGE SAVE SUCCESSFULL----", true);

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
		webDriverUtility.waitForSeconds(10);

		boolean actualresult = transactionpage.getPointsOnHold().isDisplayed();
		Assert.assertEquals(actualresult, true, "POINTS DISPLAYED");
		Reporter.log("---POINTS ON HOLD---:"+transactionpage.getPointsOnHold().getText(),true);
		webDriverUtility.waitForSeconds(5);
	    
		kohlernewpage.getMenuButton().click();
		Reporter.log("----NAVIGATE TO REDEEM PAGE----", true);
		redeempage.getGetRedeem().click();
		webDriverUtility.waitForSeconds(2);
		Reporter.log("----NAVIGATE TO KOHLER REWARDS PAGE----,TRUE");
		redeempage.getKohlerRewardsCard().click();
		
		Reporter.log("----ENTER POINTS----", true);
		redeempage.getLoadMyCardNumber().clear();
		redeempage.getLoadMyCardNumber().sendKeys(numberValue);
		redeempage.getRedeemNowButton().click();
		webDriverUtility.waitForSeconds(10);
		boolean errormessage=driver.findElement(By.partialLinkText("Something went wrong. Please try again, or contact support at")).isDisplayed();
		System.out.println(errormessage);
		Assert.assertEquals(errormessage, true, "---ERROR MESSAGE DISPLAYED");
		
	}

}
