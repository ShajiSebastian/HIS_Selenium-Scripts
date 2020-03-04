//package his_NEW;
package his_NEW;

import org.apache.commons.io.FileUtils;
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
import org.openqa.selenium.interactions.Actions;
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

public class TC_SA_ApplyOverseasFeeRule extends DriverSetup {
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
	         
		System.out.println("\ninside setup() fn (TC_SA_ApplyOverseasFeeRule.java)");
		System.out.println("TC_SA_ApplyOverseasFeeRule.java execution starts now");
		System.out.println("\nGetting current test case name (TC_SA_ApplyOverseasFeeRule.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (TC_SA_ApplyOverseasFeeRule.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);
		Loginpage = new LoginPage(driver, excelreadwrite, xls_Read);
		System.out.println ("pppppppp setup() fn (TC_SA_ApplyOverseasFeeRule.java)");
		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		System.out.println ("QQQQQQQQQ setup() fn (TC_SA_ApplyOverseasFeeRule.java)");

	}
	
		
	@DataProvider(name = "TC_SA_ApplyOverseasFeeRule")
	public Object[][] createData2() throws Exception {
	         
		System.out.println("inside TC_SA_ApplyOverseasFeeRule.java");
		System.out.println("\nTrying to to read input test data from input datasheet (TC_SA_ApplyOverseasFeeRule.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_ApplyOverseasFeeRule");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_ApplyOverseasFeeRule")
	public void ApplyHandlingFeeRule(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults,String NoOfChild,String NoOfInfant,String fromCity, String ExpAmount )
			throws InterruptedException, IOException {
			String url = getTestURL();
			System.out.println ("url is "+url);
		System.out.println ("\nGOING TO Loginpage common function");
		Loginpage.login(url);
		System.out.println ("\nBACK FROM Loginpage common function");
		// Store the current window handle
		
		
		//	String winHandleBefore = driver.getWindowHandle();

			// Clicking Airshopping page link
			
			MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='menu']/ul/li[1]/a"))); // waiting till Airshopping link is shown in page
			
			driver.findElement(By.xpath(".//*[@id='menu']/ul/li[1]/a")).click();
			System.out.println ("\nAirshopping page link clicked");
			Thread.sleep(4000);
			
			commonUtility.switchToThisWin_WithTitle(driver.getTitle(), driver);
		
			//driver.close();
			// Switch to new window opened
			/*
			for(String winHandle : driver.getWindowHandles())
			{
			    driver.switchTo().window(winHandle);
			    System.out.println("WINDOW IS "+driver.getTitle());
			    Thread.sleep(3000);
			}
			
			*/
			 System.out.println("CURRENT WINDOW IS "+driver.getTitle());
			MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='originSId']"))); // waiting till Search page is shown in page
			
			// Checking whether airshopping page displayed correctly or not
			
					String actualTitleairshopping = driver.getTitle(); 
			        System.out.println ("Title of launched page is : "+ actualTitleairshopping);
			        
			        String expectedTitleairshopping = "H.I.S. Online Flight Reservation Site";
			        
			        // compare the actual title of the page with the expected one and print the result as "Passed" or "Failed"
			     
			        /*
			        if (actualTitleairshopping.contentEquals(expectedTitleairshopping))
			        {
			            System.out.println("Airshopping page launched successfully");
			        } 
			        else 
			        {
			            System.out.println("Airshopping page failed to launch");
			        }
			        
			        */
				
			   	// trying to take screen shot of airshoppingPage
		 			try{
		 	            //take screenshot and save it in a file
		 	            File screenshotAirShoppingPage = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		 	            //copy the file to the required path
		 	            FileUtils.copyFile(screenshotAirShoppingPage,new File("D:\\Shaji\\Selenium\\ScreenShots\\AirShoppingPage.jpeg"));

		 	        }
		 			catch(Exception screenShotError)
		 			{
		 	            //if it fails to take screenshot then this block will execute
		 	            System.out.println("Failure to take screenshot "+screenShotError);
		 	        }
				
			       //inputStream.close();	
			      //searchProducts(vscWorkbook); 
	
		 			//driver.navigate().to(getTestURL());
		 			/*
		 			Thread.sleep(5000);
		 			driver.manage().deleteAllCookies();
		 			driver.navigate().refresh();
		 			Thread.sleep(5000);
	
	*//*
		 			Thread.sleep(3000);
		 			driver.manage().deleteAllCookies();
		 			Thread.sleep(3000);
		 			*/
		//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='search_store_box']/p/label[1]"))); // waiting till Search page is shown in page
		//driver.findElement(By.cssSelector("input[value='RT']")).clear();
		 			System.out.println ("Going to click RT option");
		 			
		 			/*driver.manage().deleteAllCookies();
		 			
		 			Thread.sleep(5000);
		 			driver.navigate().refresh();
		 			*/
		 			System.out.println ("page refreshed");
		//driver.findElement(By.xpath("input[value='RT']")).click();
		System.out.println ("RT search option selected by default");
	/*
	else if (typeOfSearch.equals("OW")) 
	{	
		
		MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='search_store_box']/p/label[2]"))); // waiting till Search page is shown in page
		
		//driver.findElement(By.cssSelector("input[value='OW']")).clear();	
		driver.findElement(By.cssSelector("input[value='OW']")).click();
		System.out.println ("OW search option selected");
	}
*/
	
	// Entering Origin, Destination, Departure Date, Arrival Date and submitting
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
			driver.findElement(By.xpath(".//*[@id='prfrdRetrnTimeId']")).click();	
	
		Thread.sleep(1000);	
	//Selecting passenger counts (Dropdown list)
	Select adultCount = new Select (driver.findElement(By.name("fareSearchVO.noOfAdults")));
	adultCount.selectByVisibleText(NoOfAdults);
	Thread.sleep(1000);	
	Select childCount = new Select (driver.findElement(By.name("fareSearchVO.noOfChild")));
	childCount.selectByVisibleText(NoOfChild);
	Thread.sleep(1000);	
	//waittimeShort() ;
	Select infantCount = new Select (driver.findElement(By.name("fareSearchVO.noOfInfants")));
	infantCount.selectByVisibleText(NoOfInfant);
	Thread.sleep(1000);	
	//waittimeShort() ;
	
	/*
	//Selecting Cabin Class (Dropdown list)
	Select cabinClass = new Select (driver.findElement(By.name("fareSearchVO.cabinClass")));
	cabinClass.selectByVisibleText("Economy");
	//waittimeShort() ;
	
	*/
	
	//Selecting Connection types (Radio button)
	//driver.findElement(By.cssSelector("input[value='DO']")).click();
	
	//Un checking Seat availability option (Check option)
	//WebElement seatAvailability =driver.findElement(By.id("seatAvlId"));
	//seatAvailability.click();
	/*
	if(seatAvailability instanceof WebElement) {
	System.out.println("Seat availability option visible");
	} else {
	System.out.println("Seat availability option Not visible");
	}
	*/
	
	System.out.println ("Search criteria entered");
	//waittimeMedium();
	Thread.sleep(2000);
	// Clicking on Search button
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	
	
	
	try
	{
	MyWaitVar5Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='fareProductSearchForm.errors']")));
	String errorMessage = driver.findElement(By.xpath(".//*[@id='fareProductSearchForm.errors']")).getText();
		System.out.println ("Error Message: "+errorMessage);	
		
		excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Apply FB module",
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
				commonUtility.getcurrentDateTime(), "Apply FB module",
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
		commonUtility.getcurrentDateTime(), "Apply FB Rule module",
			"Checking whether products are listed",
			"", true,
			"", "Products listed in shopping page",
			"Products should be listed in shopping page");

Thread.sleep(2000);	  


List<WebElement> bookButtonClick =  driver.findElements(By.xpath("//*[starts-with(@id,'bookButtonId')]/button"));
	System.out.println ("Number of products with Book button:"+bookButtonClick.size());
	//int k=-1;
	int m=1; 
				
	
	//for(int j = 0;j < bookButtonClick.size(); ++j) 
	for(int j = 0;j < 1; ++j) 
	{
		
		Thread.sleep(1000);
		 bookButtonClick =  driver.findElements(By.xpath("//*[starts-with(@id,'bookButtonId')]/button"));
		// k=k+1;
		 m = j+1;
		 System.out.println ("\n\nProduct Number: " +m);	
		 WebElement checkbox = bookButtonClick.get(j); 
		 checkbox.click();
		 System.out.println ("Book button clicked");
		 MyWaitVar30Sec.until(ExpectedConditions.elementToBeClickable((By.xpath(".//*[@onclick='goBack()']"))));
 		
		 excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Apply FB Rule module",
						"Checking whether page3 is accessible for "+ m+"th product",
						"", true,
						"", "Page3 is accessible for "+ m+"th product",
						"Page3 should be accessible ");
		 
		 try
		 {
		 
		// String visibleText = driver.findElement(By.xpath(".//*[@id='product_develop']/div[3]/div[2]/div[2]/div")).getText();
			 String visibleText = driver.findElement(By.xpath(".//*[@id='product_detail']")).getText();
		 				   					
		 System.out.println ("Text shown is: "+visibleText ); 													
		 
		 //if visibleText.compareTo((Handling fee))
		//if ( visibleText.equals("(Overseas Ticketing fee)"))
			if ( visibleText.contains("(Overseas Ticketing fee)"))
		{
		 System.out.println ("Overseas Ticketing fee appiled ");
		 
		 excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Apply FB Rule module",
						"Checking whether Overseas Fee shown in page3 for "+ m+"th product",
						"", true,
						"", "Overseas Ticketing fee is shown in page3 for "+ m+"th product",
						"Overseas Ticketing fee should be shown in page3");
		 
		}
		
		else
		{
			 System.out.println ("Overseas Fee NOT appiled Successfully ");
			 
			 excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "Apply FB module",
					"Checking whether Overseas Fee shown in page3 for "+ m+"th product",
					"", true,
			"", "Overseas Fee is NOT shown in page3.  Make sure matching rule is present in DB ",
				"Overseas Fee should be shown in page3 if Matching rule is present");
				Assert.assertFalse(true,
						"In Fare matrix display actual text is not valid");

			}
		 }
		 
		 catch(Exception e02)
			{
				System.out.println ("No Overseas Fee appiled  ");
				 
				 excelreadwrite.insertFailedData(currentTestName,
							commonUtility.getcurrentDateTime(), "Apply FB module",
						"Checking whether Overseas Fee shown in page3 for "+ m+"th product",
						"", true,
				"", "No Overseas Fee amount shown in page3. Make sure matching rule is present in DB ",
					"Overseas Fee should be shown in page3, if matching rule is present");
					Assert.assertFalse(true,
							"In Fare matrix display actual text is not valid");

				
			}
		 
		
		
		
		/*
		 
		 // checking whether the handling fee amount shown in as expected or not
		 String overseasFeeAmount = driver.findElement(By.xpath(".//*[@id='product_develop']/div[4]/div[2]/div[2]/div/dl[4]/dt[2]")).getText();
		// System.out.println ("handlingFeeAmount:"+handlingFeeAmount);
		 String removedCurrencySybmolOverseasFeeAmount = overseasFeeAmount.substring(1);
		// System.out.println ("removedCurrencySybmolHandFeeAmount:"+removedCurrencySybmolHandFeeAmount);
		 String removedCommaOverseasFeeAmount = removedCurrencySybmolOverseasFeeAmount.replace(",","" );
		 //System.out.println ("removedCommaHandFeeAmount:"+removedCommaHandFeeAmount);
		 String removedDecimalPointsOverseasFeeAmount0 = removedCommaOverseasFeeAmount.substring(0, 4);
		 //System.out.println ("removedDecimalPointsHandFeeAmount:"+removedDecimalPointsHandFeeAmount);
		 
		 String removedDecimalPointsOverseasFeeAmount = removedDecimalPointsOverseasFeeAmount0.trim();
		 
		 System.out.println ("Expected amount: "+ExpAmount );
		 System.out.println ("Actual amount: "+removedDecimalPointsOverseasFeeAmount );
		 
		 System.out.println ("Overseas Ticketing fee appiled ");
		 
		 if (removedDecimalPointsOverseasFeeAmount.equals(ExpAmount)) //ExpAmount)
		 {
			 System.out.println ("Expected amount:"+ExpAmount);
			 System.out.println ("Actual amount:"+removedDecimalPointsOverseasFeeAmount);
			 System.out.println ("Overseas Fee Amount shown is correct ");
			 
			 excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Apply FB Rule module",
							"Checking whether amount shown in page is is correct or not",
							"", true,
							"", "Overseas Amount shown is correct. Amount shown is "+removedDecimalPointsOverseasFeeAmount,
							"Expected amount is "+ExpAmount);
		 }
		 else
		 {
			 System.out.println ("Expected amount:"+ExpAmount);
			 System.out.println ("Actual amount:"+removedDecimalPointsOverseasFeeAmount);
			 System.out.println ("Overseas Fee Amount shown is NOT correct ");
			 
			 excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "Apply FB module",
					"Checking whether Amount shown is correct or not",
					"", true,
			"", "Amount shown is not same as expected amount. Amount shown is "+removedDecimalPointsOverseasFeeAmount,
				"AExpected amount is "+ExpAmount);
				Assert.assertFalse(true,
						"In Fare matrix display actual text is not valid");
		 }
		}
		
		*/
		
		
		 
		 try
		     {
			 Thread.sleep(1000);
	    		MyWaitVar30Sec.until(ExpectedConditions.elementToBeClickable((By.xpath(".//*[@onclick='goBack()']"))));
	    		driver.findElement(By.xpath(".//*[@onclick='goBack()']")).click();
	    		System.out.println ("Back to listing page"); 
	    		//waittimeShort();
	    		MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[starts-with(@id,'bookButtonId')]/button"))); 
	    		
		     }
	    	 
	    	catch (Exception BackToListing)
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

	
	
}





