package org.tyss.flatworld.testverify;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.tyss.flatworld.genericutility.BaseClass;
import org.tyss.flatworld.genericutility.ExcelUtility;
import org.tyss.flatworld.genericutility.JavaUtility;
import org.tyss.flatworld.objectrepository.DashboardPage;
import org.tyss.flatworld.objectrepository.IncentiveAdminHomePage;

@Listeners(org.tyss.flatworld.genericutility.ListenerImplementationClass.class)
public class TC_0010Test extends BaseClass{

	@Test
	public void runTest() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		driver.findElement(By.xpath("//span[text()='Menu']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Incentive Admin Panel')]")).click();

		
		driver.findElement(By.xpath("//span[contains(text(),'Points Credit File Upload')]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Demo Template')]")).click();
		
		Thread.sleep(3000);
		
		 String fileDirectory = "";
		 File latestFile = getLatestFile(fileDirectory);
		 String name = latestFile.getName();
		 System.out.println(name);
		 String path=fileDirectory +"\\" + name;
		 System.out.println(path);
		 
		String userID = "Test69873";
		String skuNum = "1275879-BN";
		
		//setData(path, 1, 3, skuNum);
		
		//setData1(path,  "Demo Template", 1, 3, skuNum);
		
//		updateCellInCSV(path, 1, 3, skuNum);
//		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(path);
		
	}
	   private static File getLatestFile(String directoryPath) {
	    File directory = new File(directoryPath);
	    File[] files = directory.listFiles();
	    if (files == null || files.length == 0) {
	        return null; // No files found
	    }

	    File latestFile = files[0];

	    for (File file : files) {
	        if (file.isFile() && file.lastModified() > latestFile.lastModified()) {
	            latestFile = file;
	        }
	    }
	    return latestFile;
	}
		
}

