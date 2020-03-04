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

public class TC_SA_Pex_product_count  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_SA_Pex_product_count")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Pex_product_count");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_Pex_product_count")
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
	
	
	/*List<WebElement> pexString2= driver.findElements(By.xpath("//*[contains(@id,'wishListActive')]"));
	for (int i=1; i<=4; i++)
	{
		pexString2.get(i).click();
		Thread.sleep(1000);
		
	}
	

	driver.findElement(By.xpath(".//*[@class='compareLink overflow']")).click();
	Thread.sleep(8000);
	
	Set <String> set1=driver.getWindowHandles();
	Iterator <String> win1=set1.iterator();
	String parent=win1.next();
	String child=win1.next();
	driver.switchTo().window(child);

	
	
	driver.findElement(By.xpath("html/body/div[6]/div[2]/div[1]/table[1]/tbody/tr/td[2]/img[2]")).click();
	Thread.sleep(8000);
	
	*/
	
	
	
	
	
	
	driver.findElement(By.xpath(".//*[@title='Last Page']")).click();
	Thread.sleep(800);
	String pageno= driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).getText();
	String pageno1=pageno.replace(",", "").trim();
   	int pageno2=Integer.parseInt(pageno1);
   	System.out.println("lastpag count:"+pageno2);
   	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "PEX Products in SA Shopping page ", "Checking total number of pages availble", "Navigating to listing page", true, "Same as Expected", "Total number of pages  "+pageno2, "All pages should be displayed");
   	int pexCount=0;
   	for( int i=1; i<=pageno2;i++)
   	{
   		
   	List<WebElement> pexString= driver.findElements(By.xpath(".//*[@class='pexClass']"));
	int eachpagCount= pexString.size();
	driver.findElement(By.xpath(".//*[@id='paging']/a[6]")).click();
	System.out.println("Each page count:"+eachpagCount);
	pexCount  = pexCount+pexString.size();
	System.out.println("Count:"+pexCount);
	    
	
 	}
   	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "PEX Products in SA Shopping page ", "Checking for PEX products", "Validating PEX Products", true, "Same as Expected", "PEX products are available ", "PEX products should be available");
   	System.out.println("Final Count:"+pexCount);
   	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "PEX Products in SA Shopping page ", "Checking PEX products in all pages", "Validating PEX Products in all pages", true, "Same as Expected", "Total number of PEX Products available: "+pexCount, "PEX products should be available");

	}
			catch(Exception e)
			{
				e.printStackTrace();

				 excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "PEX Products in SA Shopping page ", "Verify whether PEX Products is available ", "PEX Products is not available/Some error in finding PEX products", true, "Not Same as Expected", "PEX Products is not available", "PEX Products should be available");
				Assert.assertFalse(true, "PEX Product is not available");
				System.out.println("fail");
				
			}
	}
}





