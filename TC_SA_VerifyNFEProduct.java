//package his_NEW;
package his_NEW;

import org.apache.commons.io.FileUtils;
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

public class TC_SA_VerifyNFEProduct extends DriverSetup {
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
		System.out.println ("pppppppp setup() fn (TC_SA_VerifyNFEProduct.java)");
		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		 	}
		
	@DataProvider(name = "TC_SA_VerifyNFEProduct")
	public Object[][] createData2() throws Exception {

		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_VerifyNFEProduct");
		
		return (retObjArr);
		
	}
		@Test(dataProvider = "TC_SA_VerifyNFEProduct")
	public void CheckNFEProduct(String Origin, String Destination,
			String Depart, String Return,String NoofAdult,String NoOfChild,String NoOfInfant)
			throws InterruptedException, IOException {
			try{
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
	childCount.selectByVisibleText(NoOfChild);
	Thread.sleep(1000);	
	//waittimeShort() ;
	Select infantCount = new Select (driver.findElement(By.xpath(".//*[@id='infantNo']")));
	infantCount.selectByVisibleText(NoOfInfant);
	Thread.sleep(1000);	
	
	System.out.println ("Search criteria entered");
	//waittimeMedium();
	Thread.sleep(2000);
	// Clicking on Search button
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	
	Thread.sleep(3000);
	
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

Thread.sleep(2000);	  


List<WebElement> bookButtonClick =  driver.findElements(By.xpath("//*[starts-with(@id,'bookButtonId')]/button"));
	System.out.println ("Number of products with Book button:"+bookButtonClick.size());
	//int k=-1;
	//int m=1; 
				
	/*
	//for(int j = 0;j < bookButtonClick.size(); ++j) 
	for(int j = 0;j < 20; j++) 
	{
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//button[@class='button_book_agent'])["+j+"]")).click();
		 System.out.println ("Book button clicked");
		 MyWaitVar30Sec.until(ExpectedConditions.elementToBeClickable((By.xpath(".//*[@onclick='goBack()']"))));
 		
		 excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Apply FB Rule module",
						"Checking whether page3 is accessible for "+ m+"th product",
						"", true,
						"", "Page3 is accessible for "+ m+"th product",
						"Page3 should be accessible ");
		 
		/* try
		 {
		 */
		// String visibleText = driver.findElement(By.xpath(".//*[@id='product_detail']/div[8]")).getText();
		 
		 //System.out.println ("Text shown is: "+visibleText );
		
		 /*//if visibleText.compareTo((Handling fee))
		if ( visibleText.contains("Product ID :"))
		{
		 System.out.println ("It is a NFE Product ");
		 
		 excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Apply FB Rule module",
						"Checking whether Urgent Fee shown in page3 for "+ m+"th product",
						"", true,
						"", "Urgent Fee is shown in page3 for "+ m+"th product",
						"Urgent Fee should be shown in page3");
		break; 
		}
		
		
		else
		{
			 j++;
			driver.findElement(By.xpath("(//button[@class='button_book_agent'])["+j+"]")).click();

			}
		
		
		*/
		
		 // int j;
		for(int j=1;j < 20;j++)
		 {
			Thread.sleep(2000);
			 driver.findElement(By.xpath("(//button[@class='button_book_agent'])["+j+"]")).click();
			 Thread.sleep(4000);
			 String visibleText1 = driver.findElement(By.xpath(".//*[@id='product_detail']/div[8]")).getText();
			 if(visibleText1.contains("Product ID :")){System.out.println ("It is a NFE Product ");
			 Thread.sleep(2000);
			 excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Verify NFE ",
							"Checking whether NFE product is present in Shopping path",
							"", true,
							"", "NFE Product listed in shopping page",
							"NFE Poduct should list if a search route contains NFE product");
			 Thread.sleep(3000);
			 break;}
			 else{
				 driver.findElement(By.xpath("//button[@class='button_continue_lmd']")).click();
				 Thread.sleep(3000);
				 if(j==20)
				 {
					 Thread.sleep(4000);
					 excelreadwrite.insertData(currentTestName,
								commonUtility.getcurrentDateTime(), "Verify NFE ",
									"Checking whether NFE product is present in Shopping path",
									"", true,
									"", "NFE Product is not listed in shopping page",
									"NFE product might not be listed if search route has no NFE "); 
					 Thread.sleep(4000);
				 }
				 }
		  }
		 }
		/*catch(Exception e02)
			{
				System.out.println ("No Urgent Fee appiled  ");
				 
				 excelreadwrite.insertFailedData(currentTestName,
							commonUtility.getcurrentDateTime(), "Apply FB module",
						"Checking whether Overseas Fee shown in page3 for "+ m+"th product",
						"", true,
				"", "No Urgent Fee amount shown in page3. Make sure matching rule is present in DB ",
					"Urgent Fee should be shown in page3, if matching rule is present");
					Assert.assertFalse(true,
							"In Fare matrix display actual text is not valid");

				
			}
		*/
		
		 /*
		 try
		     {
			 Thread.sleep(1000);
	    		MyWaitVar30Sec.until(ExpectedConditions.elementToBeClickable((By.xpath(".//*[@onclick='goBack()']"))));
	    		driver.findElement(By.xpath(".//*[@onclick='goBack()']")).click();
	    		System.out.println ("Back to listing page"); 
	    		//waittimeShort();
	    		MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[starts-with(@id,'bookButtonId')]/button"))); 
	    		
		     }
	    	 */
	    /*	catch (Exception BackToListing)
    	 { 
    		 System.out.println ("Back to listing button not found. EXIT");
    		 excelreadwrite.insertFailedData(currentTestName,
    					commonUtility.getcurrentDateTime(), "Apply FB module",
    				"Checking whether 'Back to Listing page' button shows in page3",
    				"", true,
    		"", "'Back to Listing page' option NOT found. Execution Exiting",
    			"'Back to Listing page' button should be shown");
    			Assert.assertFalse(true,
    					"In Fare matrix display actual text is not valid");

    	 }
		}
		 //.//*[@id='product_develop']/div[4]/div[2]/div[2]/div/dl[3]/dt[3]
	
	 	
	
	
}


*/
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
