package his_NEW;

import org.apache.commons.io.FileUtils;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;

import pages.his.Login_page;
import pages.his.WaitFunction;
import pages.his.Common_functions;

import pages.his.HISCommonFns;

import common.CommonUtility;
import common.DriverSetup;
//import common.Excel;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;


public class AutoIT extends DriverSetup {
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	int productCount;
	public WebDriverWait WaitVar30Sec; 
	public WebDriverWait WaitVar5Sec;
	static String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	//private static ExtentReports extent;
	//private static ExtentTest test;
	     
	 
	@BeforeClass
	public void setup() {				
	         
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
	    WaitVar30Sec = new WebDriverWait(driver,30);
		WaitVar5Sec = new WebDriverWait(driver,5);
		}
	
@DataProvider(name = "SearchPage")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"SearchPage");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}


@Test(dataProvider = "SearchPage")

	public void getTestSuite(String usrname,String pwd,String from,String to,String drop,String drop1,String drop2,String from_date,String to_date,String name,String fromaddr,String toaddr,String subject,String msg) 
{
	//	extent = new ExtentReports (System.getProperty("user.dir") +"/Rep/ExtentReport_"+timeStamp+".html", false);	
	//	test = extent.startTest("Wishlist");
	
	try{	
		System.out.println ("\nGoing to load Loing page");
	driver.get(baseURL);
		
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for base URL", "Navigating to base URL ", true, "Same as Expected", "Page redirected to HIS page " , "Page should be redirected to HIS page");
   //  test.log(LogStatus.PASS, "Redirected to base URL");
		
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_username")));
	driver.findElement(By.id("j_username")).sendKeys(usrname);
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for username validity", "username is valid ", true, "Same as Expected", "valid one " , "should be valid");
	//test.log(LogStatus.PASS, "Username is valid");
	   
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_password")));
	driver.findElement(By.id("j_password")).sendKeys(pwd);
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for password validity", "password is valid ", true, "Same as Expected", "valid one " , "should be valid");
	//test.log(LogStatus.PASS, "Password is valid");
		 
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.name("submit")));
	driver.findElement(By.name("submit")).click();
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for login validity ", "login is valid ", true, "Same as Expected", "valid one " , "should be valid");
	//test.log(LogStatus.PASS, "Loginbutton functionality is valid");
	
//Clicking the AirShopping icon
	 WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/fb/store/shop/airfaresearch.htm?ehn=TYO&ln=EN']")));
	 driver.findElement(By.xpath("//a[@href='/fb/store/shop/airfaresearch.htm?ehn=TYO&ln=EN']")).click();
	 excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for AirShopping validity ",  "Air Shopping is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "AirShopping Link is redirected properly");
	
//To switch search page(AirShopping page) 
	 String Parent_Window=driver.getWindowHandle();
	 for (String Child_Window : driver.getWindowHandles())
	   {
	       driver.switchTo().window(Child_Window); 
	             
	   }
	 
//this is used to move the key down and select the 1st value from suggestion text box....
	 WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("originSId")));
	 driver.findElement(By.id("originSId")).sendKeys(from);
	 Thread.sleep(1000);
	 driver.findElement(By.id("originSId")).sendKeys(Keys.DOWN,Keys.ENTER);
	 excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for OriginId validity ",  "OriginId is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "OriginId TextBox is working properly");
	  
	 WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("destinationSId")));
     driver.findElement(By.id("destinationSId")).sendKeys(to);
     Thread.sleep(1000);
     driver.findElement(By.id("destinationSId")).sendKeys(Keys.DOWN,Keys.ENTER);
     excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for DestinationId validity ",  "DestinationId is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "DestinationId TextBox is working properly");
//Selecting values from dropdown...	
     WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("adults")));	
     Select oselect=new Select(driver.findElement(By.id("adults")));
	 Thread.sleep(1000);
	 driver.findElement(By.id("adults")).sendKeys(drop);
	 Thread.sleep(1000);
	 excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for AdultDropdown functionality ",  "AdultDropdown is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Adult Dropdown value is selecting properly");

	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("child")));	
	Select oselect1=new Select(driver.findElement(By.id("child")));
	Thread.sleep(1000);
	driver.findElement(By.id("child")).sendKeys(drop1);
	Thread.sleep(1000);
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for ChildDropdown functionality ",  "ChildDropdown is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Child Dropdown value is selecting properly ");
	 
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("infantNo")));	
	Select oselect2=new Select(driver.findElement(By.id("infantNo")));
	Thread.sleep(1000);
	driver.findElement(By.id("infantNo")).sendKeys(drop2);
	Thread.sleep(1000);
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for InfantDropdown functionality ",  "InfantDropdown is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Infant Dropdown value is selecting properly");
	 
//Departure Date....
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("alternateSfrom")));
	Thread.sleep(1000);		
	driver.findElement(By.id("alternateSfrom")).sendKeys(from_date);
	Thread.sleep(2000);	
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for FromDateCalender functionality ",  "FromDateCalender is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Onward Calender field is working properly");
//Return date ....
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("alternateSto")));
	Thread.sleep(1000);		
	driver.findElement(By.id("alternateSto")).sendKeys(to_date);
	Thread.sleep(2000);	
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for ToDateCalender functionality ",  "ToDateCalender is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Towards Calender field is working properly");
	 
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("shoppingSearchButton")));
	Thread.sleep(1000);		
	driver.findElement(By.id("shoppingSearchButton")).click();
	Thread.sleep(8000);	
	excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for Search_Button functionality ",  "Search_Button is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Search Button is redirecting to wishList page");
	/*Boolean isPresent=driver.findElements(By.xpath("//*[starts-with(@id,'wishListActive__')]")).size()>0;
	System.out.println("Boolean:"+isPresent);*/
//Selecting itemsfrom wishlist
	List<WebElement> ele=driver.findElements(By.xpath("//*[starts-with(@id,'wishListActive__')]"));
	int size=ele.size();
								System.out.println("Size:************"+size);
								
	for(int c=0;c<3;c++)
     {
      ele.get(c).click();//Clicking multiple items from wish list..
     }		
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.id("moreheaderid")));
	Thread.sleep(2000);
	driver.findElement(By.id("moreheaderid")).click();
    Thread.sleep(1000);
    excelreadwrite.insertData(testName, commonUtility.getcurrentDateTime() ,"Search Page ", "Checking for Wish list functionality ",  "wish list is valid ", true, "Same as Expected", "valid one " , "should be valid");
	// test.log(LogStatus.PASS, "Values are added properly to WishList");
	
//handling the comapre window coz its a popup
  /*  for (String Child_Window1 : driver.getWindowHandles())
	   {
	       driver.switchTo().window(Child_Window1); 
	    }
    Thread.sleep(5000);
    WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/fb/images/shoppingpages/email.png']")));
	Thread.sleep(2000);
	driver.findElement(By.xpath("//img[@src='/fb/images/shoppingpages/email.png']")).click();
	Thread.sleep(2000);
	 test.log(LogStatus.PASS, "Comapre Link is functional");*/

	
	for (String Child_Window2 : driver.getWindowHandles())
	   {
	       driver.switchTo().window(Child_Window2); 
	    }
	Thread.sleep(5000);	
	//clicking the print button
	WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/fb/images/shoppingpages/print.png']")));
	Thread.sleep(2000);
	driver.findElement(By.xpath("//img[@src='/fb/images/shoppingpages/print.png']")).click();
	Thread.sleep(2000);
	// test.log(LogStatus.PASS, " Print button is functional");
	 //for clicking on submit
	 for (String Child_Window2 : driver.getWindowHandles())
	   {
	       driver.switchTo().window(Child_Window2); 
	    }
	 WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@value='Submit']")));
		Thread.sleep(2000);
		System.out.println("Selecting language and submitting");
		driver.findElement(By.xpath("//*[@value='Submit']")).click();
		Thread.sleep(5000);
		 for (String Child_Window2 : driver.getWindowHandles())
		   {
		       driver.switchTo().window(Child_Window2); 
		    }
			
		 System.out.println("Control moved to new window handle");
			Thread.sleep(3000);
			
			
			System.out.println("going to run Auto IT sript");
			
            Runtime.getRuntime().exec("D:\\Shaji\\HIS_StorePrint.exe");
            System.out.println("Auto IT script execution is in progress");
                       System.out.println("Going to click print button to get window popup");

            WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/fb/images/shoppingpages/print-button.png']")));

                    

                    driver.findElement(By.xpath("//img[@src='/fb/images/shoppingpages/print-button.png']")).click();
                    System.out.println("clicked and window popup available");

                  

	
	/*
//AutoIt Scripting							
			
			System.out.println("going to run Auto IT sript");
			Thread.sleep(5000);
			Runtime.getRuntime().exec("D:\\Shaji\\HIS_StorePrint.exe");
			System.out.println("Auto IT script execution is in progress");
			Thread.sleep(10000);
			System.out.println("Going to click print button to get window popup");
			WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/fb/images/shoppingpages/print-button.png']")));
			driver.findElement(By.xpath("//img[@src='/fb/images/shoppingpages/print-button.png']")).click();
			System.out.println("clicked and window popup available");
			Thread.sleep(10000);
			
			*/
			
			
						 
			Thread.sleep(20000);			
			 System.out.println("Printing comppleted");
			/* WaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/fb/images/shoppingpages/print-button.png']")));
				
				driver.findElement(By.xpath("//img[@src='/fb/images/shoppingpages/print-button.png']")).click();
				 test.log(LogStatus.PASS, " Print button is functional");
			 */
}								
	
catch(Exception e)
	{
		e.printStackTrace();
	//	test.log(LogStatus.FAIL, "Unexpected error occured");
		excelreadwrite.insertFailedData(testName, commonUtility.getcurrentDateTime(), "Search Page ", "Verify whether user is able to login ", "Not able to login", true, "Not Same as Expected", "Not able to login", "User should be able to login");
		
		Assert.assertFalse(true, "Not able to enter login");
	}
		String USERDIR=System.getProperty("user.dir");
		System.out.println("theoriginalpath======"+ USERDIR);
	
	}
	
	@AfterTest 
	public void tearDown()
	{  
		
	//	extent.endTest(test);
  //      extent.flush();
 //  extent.close();
    }
}


