//package his_NEW;
package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.jetty.html.Break;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait

//verify Preferred Airline And Fares
/**
 * To verify preferred airline given in search criteria with the basic listing
 *
 * Search Criteria: 
 *
 * ToCity1	FromDate	ToDate   	NoOfAdults	NoOfChild	NoOfInfant	PreferredAirline	fromCity
 * BKK	    2015/10/01	2015/10/05	2	        2	        1	        KMÂ -Â ãƒžãƒ«ã‚¿èˆªç©º	           æ�?±äº¬
 *
 * verification points
 * 1) After search, In Fare matrix display â€“ Check the text Preferred airline: â€œDL â€“Delta Airlinesâ€�? is properly displayed.
 * 2) In basic listing: for First result - Departure - Check the text â€œDL â€“Delta Airlinesâ€�? is properly displayed.
 * 3) In basic listing: for First result - Return - Check the text â€œDL â€“Delta Airlinesâ€�? is properly displayed.
 * 4) In basic listing: for First result - Departure - Check the text â€œDL â€“Delta Airlinesâ€�? is properly displayed in show details.
 * 5) In basic listing: for First result - Return - Check the text â€œDL â€“Delta Airlinesâ€�? is properly displayed in show details.
 * 6) In basic listing: Check the text "Basic Fare" in Fare breakup is properly displayed.
 * 7) In basic listing: Check the text "Taxes" in Fare breakup is properly displayed.
 * 8) Validating Total Amount in basic listing with Fare breakup added amount
 * 9) Validating Total PAX in basic listing with Search criteria PAX
 * 10) verify japanese only button is enabled 
 */
 
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.Action;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class TC_SA_VerifySearchResultsWithDifferentPAX extends DriverSetup {
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
	public Action act;
	//int j;
	@BeforeClass
	public void setup() {				
	         
		currentTestName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();		
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);
		Loginpage = new LoginPage(driver, excelreadwrite, xls_Read);
		System.out.println ("pppppppp setup() fn (TC_SA_VerifySearchResultsWithDifferentPAX.java)");
		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		 	}
		
	@DataProvider(name = "TC_SA_VerifySearchResultsWithDifferentPAX")
	public Object[][] createData2() throws Exception {

		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_VerifySearchResultsWithDifferentPAX");
		
		return (retObjArr);
		
	}
		@Test(dataProvider = "TC_SA_VerifySearchResultsWithDifferentPAX")
	public void CheckNFEProduct(String Origin, String Destination,
			String Depart, String Return,String NoofAdult,String NoofChild,String NoofInfant)
			throws InterruptedException, IOException {
			try{
				int adult=Integer.valueOf(NoofAdult);
				int child=Integer.valueOf(NoofChild);
				int Infant=Integer.valueOf(NoofInfant);
				int sum=adult+child+Infant;
				try{
				if(sum>9 || adult>Infant){
				System.out.println ("Search criteria entered");
				
				}
				}
				catch(Exception e)
				{
					excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "NFE",
						"Trying to Enter PAX type",
						"", true,
						"", "Total pax count should not be > 9",

						"Total Pax count should be <=9");
				Assert.assertFalse(true,"Some issues happend during verify NFE product");
				}
			String url = getTestURL();
			System.out.println ("url is "+url);
		System.out.println ("\nGOING TO Loginpage common function");
		Loginpage.login(url);
		System.out.println ("\nBACK FROM Loginpage common function");
					MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='menu']/ul/li[1]/a"))); // waiting till Airshopping link is shown in page
			
			driver.findElement(By.xpath(".//*[@id='menu']/ul/li[1]/a")).click();
			System.out.println ("\nAirshopping page link clicked");
			Thread.sleep(4000);
			
			commonUtility.switchToThisWin_WithTitle(driver.getTitle(), driver);
		
			 System.out.println("CURRENT WINDOW IS "+driver.getTitle());
			MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='originSId']"))); // waiting till Search page is shown in page
			
			// Checking whether airshopping page displayed correctly or not
			
				
		driver.findElement(By.xpath(".//*[@id='originSId']")).clear();	  
		WebElement a= driver.findElement(By.xpath(".//*[@id='originSId']"));
		a.sendKeys(Origin);
		//driver.findElement(By.xpath(".//*[@id='originSId']")).click();	
		Actions action = new Actions(driver);
		action.sendKeys(a, Keys.ARROW_DOWN).build().perform();
		driver.findElement(By.xpath(".//*[@id='originSId']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.id("destinationSId")).click();
		driver.findElement(By.id("destinationSId")).clear();
		driver.findElement(By.id("destinationSId")).sendKeys(Destination);
		action.sendKeys(a, Keys.ARROW_DOWN).build().perform();
		Thread.sleep(1000);
		driver.findElement(By.id("destinationSId")).click();
		
		//driver.findElement(By.id("destinationSId")).sendKeys(Keys.TAB);
		
		
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).clear();
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).sendKeys(Depart);
		//driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).click();	
		Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).clear();
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).sendKeys(Return);
			//driver.findElement(By.xpath(".//*[@id='prfrdRetrnTimeId']")).click();		
	
		Thread.sleep(1000);	
	//Selecting passenger counts (Dropdown list)
	Select adultCount = new Select (driver.findElement(By.xpath(".//*[@id='adults']")));
	adultCount.selectByVisibleText(NoofAdult);
	Thread.sleep(1000);	
	Select childCount = new Select (driver.findElement(By.xpath(".//*[@id='child']")));
	childCount.selectByVisibleText(NoofChild);
	Thread.sleep(1000);	
	//waittimeShort() ;
	Select infantCount = new Select (driver.findElement(By.xpath(".//*[@id='infantNo']")));
	infantCount.selectByVisibleText(NoofInfant);
	Thread.sleep(1000);	
	
	//waittimeMedium();
	Thread.sleep(2000);
	// Clicking on Search button
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	
	Thread.sleep(4000);
	
	try
	{
	MyWaitVar5Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='fareProductSearchForm.errors']")));
	String errorMessage = driver.findElement(By.xpath(".//*[@id='fareProductSearchForm.errors']")).getText();
		System.out.println ("Error Message: "+errorMessage);	
		
		excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Verify NFE",
				"Checking whether search is succssfull",
				"", true,
		"", errorMessage,
			"Products should be listed in Shopping page");
			Assert.assertFalse(true,
					"In Fare matrix display actual text is not valid");
		//continue;
	}
	catch(Exception e01)
	{
		try
		{
		//MyWaitVar5Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/center/h1/span[2]")));
		String errorMessage2 = driver.findElement(By.xpath("html/body/center/h1/span[2]")).getText();
		System.out.println ("Error Message: "+errorMessage2);	
		
		excelreadwrite.insertFailedData(currentTestName,
				commonUtility.getcurrentDateTime(), "Verify NFE",
			"Checking whether search is succssfull",
			"", true,
	"", errorMessage2,
		"Products should be listed in Shopping page");
		Assert.assertFalse(true,
				"In Fare matrix display actual text is not valid");
		
		}
		catch(Exception e02)
		{
			System.out.println ("Search completed");	
			
		}
	}


//waittimeShort() ;

		
 //MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='basicListingForm']/table/tbody/tr/td/span"))); // waiting till the given field is shown in page

String actualTitlebasiclistingpage = driver.getTitle(); 
System.out.println ("Webpage Title of newly lanched page is : "+ actualTitlebasiclistingpage);

excelreadwrite.insertData(currentTestName,
		commonUtility.getcurrentDateTime(), "Verify NFE ",
			"Checking whether products are listed",
			"", true,
			"", "Products listed in shopping page",
			"Products should be listed in shopping page");
driver.findElement(By.xpath("(//button[@class='button_book_agent'])[1]")).click();
Thread.sleep(15000);
String adultpax=driver.findElement(By.xpath("(//dl[@class='fare_listing'])[1]")).getText();
System.out.println(adultpax);

 String[] Adultpax=adultpax.split("\\s",3);
 //String adt=Adultpax[1];
 String adtt=Adultpax[2];
 String[]adpt=adtt.split("X");
 String dum=adpt[1];
 String[] r=dum.split("\\s");
 String abc= r[1];
int adultcount= Integer.valueOf(abc);
System.out.println(adultcount);


String childpax=driver.findElement(By.xpath("(//dl[@class='fare_listing'])[6]")).getText();
System.out.println(childpax);
 String[] Childpax=childpax.split("\\s",3);
 //String adt=Adultpax[1];
 String chdd=Childpax[2];
 String[]chdt=chdd.split("X");
 String dump=chdt[1];
 String[] g=dump.split("\\s");
 String cab= r[1];
int childcount= Integer.valueOf(cab);
System.out.println(childcount);
 
		String infantpax=driver.findElement(By.xpath("(//dl[@class='fare_listing'])[11]")).getText();
		System.out.println(infantpax);
		 String[] Infantpax=infantpax.split("\\s",3);
		 //String adt=Adultpax[1];
		 String inff=Infantpax[2];
		 String[]inft=inff.split("X");
		 String infa=inft[1];
		 String[] y=infa.split("\\s");
		 String baby= y[1];
		int infantcount= Integer.valueOf(baby);
		System.out.println(infantcount);
		if (infantcount == Infant && adultcount==adult && childcount==child){
			System.out.println("Successfully done the search with different pax count and different pax type");
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Verify Search results with different pax ",
						"Checking whether search with different pax count and different pax type",
						"", true,
						"", "Different pax type and pax count",
						"Successfully done the search with different pax count and different pax type");
		}
			}	  
			


	
			catch(Exception e)
			{
				excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "NFE",
						"Trying to verify NFE Product in shopping path",
						"", true,
						"", "Some issue occured during verify NFE product",
		
						"NFE Poduct should list if a search route contains NFE product");
				Assert.assertFalse(true,"Some issues happend during verify NFE product");}

		}
}
