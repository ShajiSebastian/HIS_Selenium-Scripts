package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

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

public class TC_SA_Email_Functionality  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_SA_Email_Functionality")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Email_Functionality");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_Email_Functionality")
	public void ApplyHandlingFeeRule(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults,String NoOfChild,String NoOfInfant,String fromCity,String name, String fromAddress,String ToAddress, String subject, String message )
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
	
	
	
	
	
	/*driver.findElement(By.xpath(".//*[@class='printImg']")).click();
	Thread.sleep(8000);
	Boolean isPresent2 = driver.findElements(By.xpath(".//*[@class='button_black']")).size()>0;
    if (isPresent2== true)
    {
   	 System.out.println("True");
   	 //Solving ElementNotVisibleException
   	 //taken the size of the element  
   	int ok_size1=driver.findElements(By.xpath(".//*[@class='button_black']")).size();
       //took the first element from the list
   	 driver.findElements(By.xpath(".//*[@class='button_black']")).get(ok_size1-1).click();
   	 
   	 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Wishlist-Email Functionality in Japan SA ", "Checking for Various languages available for Email option", "Selecting any language ", true, "Same as Expected", "Language is selected", "Language should be selected");
   	Thread.sleep(800);
   	 System.out.println("Language option found");
   	 
   	 
   	
   //	driver.findElement(By.xpath(".//*[@class='button_black']")).click();
   //	Thread.sleep(8000);
   	
   	
   	
   	Set <String> set1=driver.getWindowHandles();
	Iterator <String> win1=set1.iterator();
	String parent1=win1.next();
	String child1=win1.next();
	System.out.println("Child window"+child1);
	Thread.sleep(8000);
	driver.switchTo().window(child1);
	System.out.println("In child");
	String pagesource= driver.getPageSource();
	if (pagesource.contains("preview")==true)
	{
		System.out.println("2nd page");
	}
	else
	{
		System.out.println("1PAGE");
	}
	
	
Thread.sleep(8000);	
String text=	driver.findElement(By.xpath(".//*[@class='clr_red']/strong")).getText();
System.out.println("Sample text:"+text);
driver.manage().window().maximize();
driver.findElement(By.xpath("//input[@class='button_black']")).click();
   //	JavascriptExecutor js = (JavascriptExecutor)driver; 
   	//js.executeScript("arguments[0].click();", element);    
   	Thread.sleep(8000);
   	 
   	 Boolean isOkpresent= driver.findElements(By.xpath(".//*[@id='okButton']")).size()>0;
   	 if (isOkpresent==true)
   	 {
   		 System.out.println("Email send");
   		 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Wishlist-Email Functionality in Japan SA ", "Checking whether Eamil is sent or not", "Validating Email delivery feature ", true, "Same as Expected", "Email is sent", "Email should be sent");
   	 }
   	 else
   	 {
   		 System.out.println("Email failed");
   		 excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Wishlist-Email Functionality in Japan SA", "Checking whether Eamil is sent or not", "Email failed", true, "Not Same as Expected", "Email failed", "Email should be working");
			Assert.assertFalse(true, "Email failed");
   	 }
    }
    else
    {
   	 System.out.println("Language option not found");
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	*/
	

	driver.findElement(By.xpath(".//*[@class='sharelink']")).click();
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Email Functionality in Japan SA ", "Checking for Email icon", "Clicking on Email icon", true, "Same as Expected", "Email icon is clickable", "Email icon should be clickable");
	Thread.sleep(8000);
	Set <String> set3=driver.getWindowHandles();
	Iterator <String> win3=set3.iterator();
	String parent3=win3.next();
	String child3=win3.next();
	driver.switchTo().window(child3);
	driver.findElement(By.xpath(".//*[@ID='toName']")).sendKeys(name);
	driver.findElement(By.xpath(".//*[@ID='formAddress']")).sendKeys(fromAddress);
	driver.findElement(By.xpath(".//*[@ID='toAddress']")).sendKeys(ToAddress);
	driver.findElement(By.xpath(".//*[@ID='subject']")).sendKeys(subject);
	driver.findElement(By.xpath(".//*[@ID='message']")).sendKeys(message);
	driver.findElement(By.xpath(".//*[@ID='mailSendButton']")).click();
	Thread.sleep(800);
	
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Email Functionality in Japan SA ", "Checking for Email option", "Clicking on Email option", true, "Same as Expected", "All parameters needed for mail is fetched", "All parameters needed for mail should be fetched");
	Boolean isPresent = driver.findElements(By.xpath(".//*[@class='button_black']")).size()>0;
	             if (isPresent== true)
	             {
	            	 System.out.println("True");
	            	 //Solving ElementNotVisibleException
	            	 //taken the size of the element  
	            	int ok_size=driver.findElements(By.xpath(".//*[@class='button_black']")).size();
                    //took the first element from the list
	            	 driver.findElements(By.xpath(".//*[@class='button_black']")).get(ok_size-1).click();
	            	 
	            	 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Email Functionality in Japan SA ", "Checking for Various languages available for Email option", "Selecting any language ", true, "Same as Expected", "Language is selected", "Language should be selected");
	            	Thread.sleep(800);
	            	 System.out.println("Language option found");
	            	 
	            	  Thread.sleep(20000); 
	            String Email_msg= driver.findElement(By.xpath(".//*[@class='overflow label_red']")).getText();
	            	System.out.println("Text:"+Email_msg);
	            	
	            	if(Email_msg.contains("Email sending failed for"))
	            	{
	            		System.out.println("Email failed");
	            		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Email Functionality in Japan SA", "Checking whether Email is sent or not", "Email failed", true, "Not Same as Expected", "Email failed", "Email should be working");
	     				Assert.assertFalse(true, "Email failed");
	            	}
	            	
	            	else
	            	{
	            		System.out.println("Email sent");
	            		 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Email Functionality in Japan SA ", "Checking whether Email is sent or not", "Validating Email delivery feature ", true, "Same as Expected", "Email is sent", "Email should be sent");
	            	}
	             }
	             else
	             {
	            	 System.out.println("Language option not found");
	             }
	  //       }

	
			}
			catch(Exception e)
			{
				e.printStackTrace();

				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Email Functionality in Japan SA", "Checking whether Eamil is sent or not", "Email failed", true, "Not Same as Expected", "Email failed", "Email should be working");
				Assert.assertFalse(true, "Email failed");
				System.out.println("fail");
				
			}
	}
}






