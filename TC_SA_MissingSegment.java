//package his_NEW;
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

public class TC_SA_MissingSegment  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_SA_MissingSegment")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_MissingSegment");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_MissingSegment")
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
			//
			
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
	
	/*driver.findElement(By.xpath(".//*[@id='timeBarViewLink']/a")).click();
	Thread.sleep(2000);
	
	driver.findElement(By.xpath(".//*[@id='inBoundRadio']")).click();
	Thread.sleep(4000);
	
	driver.findElement(By.xpath("//div[@class='timeline_store']//div[@class='left']/p/span")).click();
	Thread.sleep(12000);
	
	//driver.findElement(By.xpath("html/body/div[1]/div[5]/div[3]/div[1]/div/div/form/div[3]/div/div[7]/div[2]/div[1]/div[1]/p")).click();
	
	
	driver.findElement(By.xpath(".//*[@class='clear timline_separator']//p")).click();
	Thread.sleep(20000);
	
	
	driver.findElement(By.xpath(".//*[@class='button_continue_lmd']")).click();
	Thread.sleep(9000);
	
	
	
	driver.findElement(By.xpath("//a[@onclick='deselectChooseDepOrArrival();']")).click();
	Thread.sleep(8000);
	
	driver.findElement(By.xpath(".//*[@id='productListViewLink']/a")).click();
	Thread.sleep(9000);*/
	
	Boolean isPresentMissing= driver.findElements(By.xpath(".//*[@class='button_book']")).size() > 0;
	if (isPresentMissing==true)
	{
	driver.findElement(By.xpath(".//*[@class='button_book']")).click();
	Thread.sleep(20000);
	Boolean isPresentMissingExpanded= driver.findElements(By.xpath("//*[starts-with(@id,'pdct_group_hang')]")).size() > 0;
	if (isPresentMissingExpanded==true)
	{
		
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Missing Segment in Japan SA", "Checking missing segment products", "Validating Missing segment product ", true, "Same as Expected", "Missing segment product found", "Missing segment product should be available");
	List <WebElement> expandedProds= driver.findElements(By.xpath("//*[starts-with(@id,'pdct_group_hang')]"));
	int expandedProdsCount= expandedProds.size();
	int actualexpandedProdsCount= expandedProdsCount+1;
	System.out.println("Count:"+actualexpandedProdsCount);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Missing Segment in Japan SA", "Checking missing segment products", "Clicking on Check seat availability button ", true, "Same as Expected", "No: of Products got fetched on clicking Check seat availability button:"+actualexpandedProdsCount, "GDS Check should be completed successfully");
	
	}
	
	else
	{
		System.out.println("No products on expanding");
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Missing Segment in Japan SA", "Checking missing segment products", "Clicking on Check seat availability button ", true, "Same as Expected", "No seats available product got displayed after GDS Check", "GDS Check should be completed successfully");
	}
	
	}

	if (isPresentMissing==false)
	{
		System.out.println("No missing segment products found");
		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Missing Segmen in Japan SA", "Verify whether Missing Segment products are available in basic listing page", "Missing Segment products not found", true, "Not Same as Expected", "Missing segment products not available", "Missing segment products should be available");
		Assert.assertFalse(true, "Missing segment products not available");
		System.out.println("fail");
	}
	
	
		


			}
			catch(Exception e)
			{
				e.printStackTrace();
				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Missing Segmen in Japan SA", "Verify whether Missing Segment products are available in basic listing page", "Missing Segment products not found", true, "Not Same as Expected", "Missing segment products not available", "Missing segment products should be available");
				Assert.assertFalse(true, "Missing segment products not available");
				System.out.println("fail");
				
			}
	}
}




