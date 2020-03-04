package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

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

public class TC_SA_Time_Bar_Arrival  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_SA_Time_Bar_Arrival")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Time_Bar_Arrival");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_Time_Bar_Arrival")
	public void ApplyHandlingFeeRule(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults,String NoOfChild,String NoOfInfant,String fromCity )
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
	
	
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	Thread.sleep(2000);
	
	Boolean isPresentTimebar= driver.findElements(By.xpath(".//*[@id='timeBarViewLink']/a")).size() > 0;
	if (isPresentTimebar== true)
	{
	driver.findElement(By.xpath(".//*[@id='timeBarViewLink']/a")).click();
	Thread.sleep(2000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Checking timebar link", "Clicking on Time bar link ", true, "Same as Expected", "Time bar link is clickable", "Time bar link should be clickable");
	
	driver.findElement(By.xpath(".//*[@id='inBoundRadio']")).click();
	Thread.sleep(10000);
	
	driver.findElement(By.xpath("//div[@class='timeline_store']//div[@class='left']/p/span")).click();
	Thread.sleep(15000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Checking for return segment", "Clicking on return segment ", true, "Same as Expected", "Return segment is selected", "Return segment should be selected");
	
	driver.findElement(By.xpath(".//*[@class='clear timline_separator']//p")).click();
	Thread.sleep(20000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Checking for onward segment", "Clicking on onward segment ", true, "Same as Expected", "Onward segment is selected", "Onward segment should be selected");
	
	
	driver.findElement(By.xpath(".//*[@class='button_continue_lmd']")).click();
	Thread.sleep(9000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Checking for back to listing button", "Clicking on back to listing button ", true, "Same as Expected", "Back to listing button is clicked", "Back to listing button should be selected");
	
	
	
	driver.findElement(By.xpath("//a[@onclick='deselectChooseDepOrArrival();']")).click();
	Thread.sleep(8000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Checking for Deselect button", "Clicking on Deselect button ", true, "Same as Expected", "Deselect button is clicked", "Deselect button should be selected");
	
	driver.findElement(By.xpath(".//*[@id='productListViewLink']/a")).click();
	Thread.sleep(9000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Checking for Product View option ", "Clicking on Product View option  ", true, "Same as Expected", "Product View option button is clicked", "Product View option  should be selected");
	
	
		
	}
	else
	{
		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Verify whether Timebar option is available ", "Timebar option is not available", true, "Not Same as Expected", "Timebar option is not available", "Timebar option should be available");
		Assert.assertFalse(true, "No Timebar option available");
		System.out.println("fail");
	}

			}
			catch(Exception e)
			{
				e.printStackTrace();

				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Time Bar Functionality for Arrival of RT product", "Verify whether Timebar option is available ", "Timebar option is not available", true, "Not Same as Expected", "Timebar option is not available", "Timebar option should be available");
				Assert.assertFalse(true, "Timebar option is not available");
				System.out.println("fail");
				
			}
	}
}





