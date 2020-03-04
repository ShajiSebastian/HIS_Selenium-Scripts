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

public class TC_SA_Choose_Dept  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_Time_Bar_Departure")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Choose_Dept");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_Time_Bar_Departure")
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
	

	String productcount=driver.findElement(By.xpath(".//*[@id='productSpanId']")).getText();
	String initialcount1=productcount.replace(",", "").trim();
	int initialcount=Integer.parseInt(initialcount1);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Checking for number of products in basic listing page", "Printing product count", true, "Same as Expected", "No: of products displayed in basic listing page:"+initialcount, "Products should be displayed in basic listing page");
	
	List<WebElement> prodcount= driver.findElements(By.xpath(".//*[@id='checkTable']"));
	int size= prodcount.size();
	
	List<WebElement> deptcount= driver.findElements(By.xpath(".//*[@class='chooseDept_active']"));
	int sizeDept= deptcount.size();
	int diffCount= size- sizeDept;
	 
	if (size==sizeDept)
	{
		System.out.println("All product is having Choose departure link");
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Checking for Choose departure link", "Validating Choose Departure link", true, "Same as Expected", "All products is having Choose Departure link", "All products should have Choose Departure link");
	}
	else
	{
		System.out.println("No: of products having no Choose departure link:"+diffCount);
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Checking for Choose departure link", "Validating Choose Departure link", true, "Same as Expected", "No: of products having no Choose departure link:"+diffCount, "No Choose departure option is available for missing segment product");
	}
	driver.findElement(By.xpath(".//*[@class='chooseDept_active']")).click();
	Thread.sleep(16000);
	
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Checking for return segments", "Validating return segments", true, "Same as Expected", "Return segment is found", "Return segment should be available");
	
	driver.findElement(By.xpath(".//*[@class='button_book_agent']")).click();
	Thread.sleep(16000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Checking for select button", "Navigating to page 3", true, "Same as Expected", "Navigated to page 3 ", "Page 3 should be available");
	
	driver.findElement(By.xpath(".//*[@class='button_continue_lmd']")).click();
	Thread.sleep(9000);
	
	driver.findElement(By.xpath("//a[@onclick='deselectChooseDepOrArrival();']")).click();
	Thread.sleep(8000);
	
	String productcount2=driver.findElement(By.xpath(".//*[@id='productSpanId']")).getText();
	String finalcount1=productcount2.replace(",", "").trim();
	int finalcount=Integer.parseInt(finalcount1);
	
	if (initialcount==finalcount)
	{
		System.out.println("Product count is correct");
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Clicking on back to listing  button", "Navigating to listing page", true, "Same as Expected", "Product count is correct and is  "+finalcount, "Product count should be :"+initialcount);
	}
	else
	{
		System.out.println("Product count is wrong");
		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Verify whether product count is same after navigating back from page3 ", "Product count is wrong", true, "Not Same as Expected", "Product count is wrong", "Product count should be correct");
		Assert.assertFalse(true, "Product count is wrong");
	}
	
	

			}
			catch(Exception e)
			{
				e.printStackTrace();
				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Choose Departure Functionality ", "Verify whether Choose Departure Functionality is available ", "Choose Departure Functionality is not available", true, "Not Same as Expected", "Choose Departure Functionality is not available", "Choose Departure Functionality should be available");
				Assert.assertFalse(true, "Choose Departure Functionality is not available");
				System.out.println("fail");
				
			}
	}
}





