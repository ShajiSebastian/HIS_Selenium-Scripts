package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class TC_SA_Check_Fare_Functionality  extends DriverSetup {
	SearchPage SearchPage;
	LoginPage Loginpage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	int productCount;
	public WebDriverWait MyWaitVar30Sec; 
	public WebDriverWait MyWaitVar5Sec;
	
	@BeforeClass
	public void setup() {				
	         
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);
		Loginpage = new LoginPage(driver, excelreadwrite, xls_Read);
		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		

	}
	
		
	@DataProvider(name = "TC_SA_Check_Fare_Functionality")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Check_Fare_Functionality");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_Check_Fare_Functionality")
	public void ApplyHandlingFeeRule(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults,String NoOfChild,String NoOfInfant,String fromCity)
			throws InterruptedException, IOException {
			String url = getTestURL();
			System.out.println ("url is "+url);
		System.out.println ("\nGOING TO Loginpage common function");
		Loginpage.login(url);
		System.out.println ("\nBACK FROM Loginpage common function");

			// Clicking Airshopping page link
			try
			{
			//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='menu']/ul/li[1]/a"))); // waiting till Airshopping link is shown in page
			
			driver.findElement(By.xpath(".//*[@id='menu']/ul/li[1]/a")).click();
			System.out.println ("\nAirshopping page link clicked");
			Thread.sleep(4000);
			
			commonUtility.switchToThisWin_WithTitle(driver.getTitle(), driver);
		    System.out.println("CURRENT WINDOW IS "+driver.getTitle());
			MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='originSId']"))); // waiting till Search page is shown in page
			

				
			   
		System.out.println ("Going to click RT option");
		 			System.out.println ("page refreshed");
		    System.out.println ("RT search option selected by default");
	
		driver.findElement(By.xpath(".//*[@id='originSId']")).clear();	  
		driver.findElement(By.id("originSId")).sendKeys(fromCity);
		driver.findElement(By.xpath(".//*[@id='originSId']")).click();	
		Thread.sleep(2000);
		
		driver.findElement(By.id("destinationSId")).click();
		driver.findElement(By.id("destinationSId")).clear();
		driver.findElement(By.id("destinationSId")).sendKeys(ToCity1);
		Thread.sleep(1000);
		driver.findElement(By.id("destinationSId")).click();
		
		driver.findElement(By.id("destinationSId")).sendKeys(Keys.TAB);
		
		
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).clear();
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).sendKeys(Depart1);
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).click();	
		Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).clear();
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).sendKeys(Depart2);
			//driver.findElement(By.xpath(".//*[@id='prfrdRetrnTimeId']")).click();	
	
		Thread.sleep(1000);	
	//Selecting passenger counts (Dropdown list)
	Select adultCount = new Select (driver.findElement(By.name("fareSearchVO.noOfAdults")));
	adultCount.selectByVisibleText(NoOfAdults);
	Thread.sleep(1000);	
	Select childCount = new Select (driver.findElement(By.name("fareSearchVO.noOfChild")));
	childCount.selectByVisibleText(NoOfChild);
	Thread.sleep(1000);	
	
	Select infantCount = new Select (driver.findElement(By.name("fareSearchVO.noOfInfants")));
	infantCount.selectByVisibleText(NoOfInfant);
	Thread.sleep(1000);	
	
	System.out.println ("Search criteria entered");
	Select select = new Select(driver.findElement(By.xpath(".//*[@id='fareRTType']")));
    select.selectByVisibleText("PEX (Published) Fares");
    Thread.sleep(800);
	
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	Thread.sleep(2000);
	
	
	
	driver.findElement(By.xpath(".//*[@class='button_book_agent']")).click();
	Thread.sleep(20000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Check fare Functionality in Japan SA ", "Checking for Select button", "Navigating to details page ", true, "Same as Expected", "Page 3 is available", "Page 3 should be available");
	
	driver.findElement(By.xpath(".//*[@value='Check Fare']")).click();
	Thread.sleep(8000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Check fare Functionality in Japan SA ", "Checking for Check Fare button", "Clicking on Check Fare button", true, "Same as Expected", "Check Fare button is clickable", "Check Fare button should be clickable");
	
	String Fare_msg= driver.findElement(By.xpath(".//*[@id='priceChangeId']")).getText();
	System.out.println("Message:"+Fare_msg);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Check fare Functionality in Japan SA ", "Checking Check Fare message", "Validating Check Fare message", true, "Same as Expected", "Check fare message displayed is:"+Fare_msg, "Check Fare message should be displayed");
	String change_price="   A change in the price of your selected products.";
			String no_change_price="   Fare Check Completed";
			String change_repriced = "   The price has changed again";
			if (Fare_msg.equals(change_price))
			{
				System.out.println("Fare changed");
				
			}
			else if(Fare_msg.equals(no_change_price))
			{
				System.out.println("No change in Price");
				
			}
			else if(Fare_msg.equals(change_repriced))
			{
				System.out.println("Fare changed again");
			}
			else 
			{
				System.out.println("Farecheck failed");
				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Check fare Functionality in Japan SA ", "Checking whether Check fare Functionality is working or not", "Check fare Functionality failed", true, "Not Same as Expected", "Check fare Functionality  failed", "Check fare Functionality should be working");
				Assert.assertFalse(true, "Check fare Functionality");
			}
			
			driver.findElement(By.xpath(".//*[@class='button_continue_lmd']")).click();
			Thread.sleep(8000);
			 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Check fare Functionality in Japan SA ", "Checking Back to Listing button", "Clicking on Back to Listing button", true, "Same as Expected", "Basic listing page is displayed", "Basic listing page should be displayed");
			}
			
			catch(Exception e)
			{
				e.printStackTrace();

				System.out.println("Farecheck failed");
				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Check fare Functionality in Japan SA ", "Checking whether Check fare Functionality is working or not", "Check fare Functionality failed", true, "Not Same as Expected", "Check fare Functionality  failed", "Check fare Functionality should be working");
				Assert.assertFalse(true, "Check fare Functionality");
				
			}
	}
}






