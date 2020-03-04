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

public class TC_SA_Print_Functionality  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_SA_Print_Functionality")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Print_Functionality");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_Print_Functionality")
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
	
	
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	Thread.sleep(2000);
	
	
	
	
	
	driver.findElement(By.xpath(".//*[@class='printImg']")).click();
	Thread.sleep(8000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Print Functionality in Japan SA ", "Checking for Print icon", "Clicking on Print icon", true, "Same as Expected", "Print icon is clickable", "Print icon should be clickable");
	Boolean isPresent2 = driver.findElements(By.xpath(".//*[@class='button_black']")).size()>0;
    if (isPresent2== true)
    {
   	 System.out.println("True");
   	 //Solving ElementNotVisibleException
   	 //taken the size of the element  
   	int ok_size1=driver.findElements(By.xpath(".//*[@class='button_black']")).size();
   	
    // fetch all windows before clicking on new window link.
    Set<String> windowHandles = driver.getWindowHandles();
       //took the first element from the list
 // Click on link to open new window
   	 driver.findElements(By.xpath(".//*[@class='button_black']")).get(ok_size1-1).click();
   	 
   	 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Print Functionality in Japan SA ", "Checking for Various languages available for Print option", "Selecting any language ", true, "Same as Expected", "Language is selected", "Language should be selected");
   	Thread.sleep(8000);
   	 System.out.println("Language option found");

     
   

     Set<String> updatedWindowHandles = driver.getWindowHandles();
     updatedWindowHandles.removeAll(windowHandles);
     for (String window: updatedWindowHandles) {
         driver.switchTo().window(window);
     	String pagesource= driver.getPageSource();
    	if (pagesource.contains("addCommentArea")==true)
    	{
    		System.out.println("2nd page");
    		String text2=	driver.findElement(By.id("total_label")).getText();
    		System.out.println("Sample text1:"+text2);
    	     Thread.sleep(800);
    	   
    	     driver.findElement(By.xpath(".//*[@class='button_black']")).click();
    	     Thread.sleep(8000);
    	     excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Print Functionality in Japan SA ", "Checking for Preview mode of details page", "Validating Preview mode of details page ", true, "Same as Expected", "Preview mode of details page is available", "Preview mode of details page should be  available");
    	     System.out.println("Moving to Print screen");
    	     Robot r = new Robot();
    	     r.keyPress(KeyEvent.VK_ENTER);
    	   //  r.keyRelease(KeyEvent.VK_ESCAPE);
    	     Thread.sleep(8000);
    	     excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Print Functionality in Japan SA ", "Checking for Print Screen", "Validating Print Screen ", true, "Same as Expected", "Print is successful", "Print should be successful");
    	     System.out.println("Print Sucessful");
    	    
    	}
    	else
    	{
    		System.out.println("1PAGE");
    	}
     }
   	 
   

	
   	          }
   	       
	
/*String text2=	driver.findElement(By.id("total_label")).getText();
	System.out.println("Sample text1:"+text2);
Thread.sleep(8000);	


String text=	driver.findElement(By.xpath(".//*[@class='clr_red']/strong")).getText();
System.out.println("Sample text:"+text);
driver.manage().window().maximize();
driver.findElement(By.xpath("//input[@class='button_black']")).click();
   //	JavascriptExecutor js = (JavascriptExecutor)driver; 
   	//js.executeScript("arguments[0].click();", element);    
   	Thread.sleep(8000);
   	 
   
    }*/
	
    else
    {
   	 System.out.println("Language option not found");
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	

	
			}
			catch(Exception e)
			{
				e.printStackTrace();

				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Print Functionality in Japan SA", "Checking whether Print is sent or not", "Print failed", true, "Not Same as Expected", "Print failed", "Print should be working");
				Assert.assertFalse(true, "Print failed");
				System.out.println("fail");
				
			}
	}
}






