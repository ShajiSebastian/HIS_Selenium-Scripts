//package his_NEW;
package his_NEW;

import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait
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

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class VerifyProductInformationBasicListingPage extends DriverSetup {
	SearchPage SearchPage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	int productCount;
	
	
	
	@BeforeClass
	public void setup() {
				
	         
		System.out.println("\ninside setup() fn (VerifyProductInformationBasicListingPage.java)");
		System.out.println("VerifyProductInformationBasicListingPage.java execution starts now");
		System.out.println("\nGetting current test case name (VerifyProductInformationBasicListingPage.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (VerifyProductInformationBasicListingPage.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "VerifyProductInformationBasicListingPage")
	public Object[][] createData2() throws Exception {

	         
		System.out.println("inside VerifySortByPrice.java");
		System.out.println("\nTrying to to read input test data from input datasheet (VerifyProductInformationBasicListingPage.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"VerifyProductInformationBasicListingPage");
		return (retObjArr);
	}

	@Test(dataProvider = "VerifyProductInformationBasicListingPage")
	public void Verifychoosedeparture(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults, String NoOfChild,
			String NoOfInfant, String fromCity)
			throws InterruptedException {
		
		try{
		System.out.println("\nTrying to invoke HIS page to Execute 'VerifyProductInformationBasicListingPage' Test Case  (VerifySortByPrice.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		/*driver.get(getTestURL());*/
		driver.navigate().to(getTestURL());
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		Thread.sleep(5000);
		if (!driver.findElement(By.xpath("html/body/div[1]/div[4]/div/div/div[1]/div[1]/form/div/div/div[2]/div[2]/div/label[1]/input")).isSelected()) {
			
			System.out.println("Trying to select RT option");
			driver.navigate().refresh();
			driver.findElement(By.xpath("html/body/div[1]/div[4]/div/div/div[1]/div[1]/form/div/div/div[2]/div[2]/div/label[1]/input")).click();
		}

		// Getting current url
	         String currentUrl= driver.getCurrentUrl();
	         System.out.println("Current url is: "+ currentUrl);
	         
		
		System.out.println("\nTrying to enter search criteria");
		System.out.println("-----------------------------");
		driver.findElement(By.xpath(".//*[@id='fromCombo']")).click();
		List<WebElement> fromCityOptions = driver.findElements(By
				.xpath(".//*[@id='fromCombo']//option"));
		
		for (WebElement menu : fromCityOptions) {
			if (menu.getText().trim().equalsIgnoreCase(fromCity.trim())) {
			
				System.out.println("Input From City/Airport: "+fromCity.trim());
				System.out.println("Selected From City/Airport: "+menu.getText().trim());
				
				Thread.sleep(1000);

				Actions action = new Actions(driver);

				action.sendKeys(menu, Keys.ENTER).build().perform();

				Thread.sleep(1000);

				break;
			}
		}
		
		System.out.println("Completed checking From City options");
	
		driver.findElement(By.id("destinationSId")).click();
		driver.findElement(By.id("destinationSId")).clear();
		driver.findElement(By.id("destinationSId")).sendKeys(ToCity1);
		Thread.sleep(1000);
		driver.findElement(By.id("destinationSId")).click();
		
		driver.findElement(By.id("destinationSId")).sendKeys(Keys.TAB);
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).sendKeys(Depart1);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='alternateSto']")).sendKeys(Depart2);
		Thread.sleep(1000);
		SearchPage.NoOfAdults.selectOptionByValue(NoOfAdults);
		Thread.sleep(1000);
		SearchPage.NoOfChild.selectOptionByValue(NoOfChild);
		Thread.sleep(1000);
		SearchPage.NoOfInfant.selectOptionByValue(NoOfInfant);
		Thread.sleep(1000);

	//	driver.findElement(By.xpath(".//*[@id='moreSearchId']")).click();
	//	Thread.sleep(5000);

		//driver.findElement(By.xpath(".//*[@id='airlineId']")).sendKeys(
		//		PreferredAirline.trim());
		driver.findElement(By.xpath(".//*[@id='modSearchButton']")).click();
		System.out.println("\nClicked on Search button");
		System.out.println("------------------------");
		Thread.sleep(15000);
		String pageTitle = driver.getTitle().trim();
		System.out.println("Page title is: "+pageTitle);
		if (pageTitle.toLowerCase().trim().contains("H.I.S.".toLowerCase().trim())) 
		{
			System.out.println("\nSearch result page came");
			System.out.println("----------------------------------");
			System.out.println("\nUpdating Report VerifyProductInformationBasicListingPage.xls in reports/xls");
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking for result page",
					"", true, "", "Search result page shown",
									 "Search result page should be shown");
		} 
		else 
		{
			System.out.println("\nSearch result page didnt came");
			System.out.println("----------------");
			System.out.println("\nUpdating Report VerifyProductInformationBasicListingPage.xls in reports/xls");
			String errorText = driver.findElement(By.xpath(".//*[@id='errorID']")).getText();
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking for result page",
					"", true, "", "Search page result NOT shown."
							+ errorText, "Search page result should be shown");
			Assert.assertFalse(true,"H.I.S. Flight Search Results is not displayed");
		}
		
		System.out.println("\nback to VerifyProductInformationBasicListingPage.java ");

		// Verification 2 - After search, In Fare matrix display â€“ Check the
		// text â€œDL â€“Delta Airlinesâ€�? is properly displayed.
		
		System.out.println ("\nBasic Listing page shown");
 		// code to check number of products returned
 		if(driver.findElements(By.id("product_content_sect")).isEmpty())
		{ 
 			System.out.println ("No products returned");
 			return;
 		}
						 
 		else 
		{
 			productCount = Integer.parseInt(driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/div[3]/div/div/p[1]/strong")).getText());
 			System.out.println ("Total Number of Products returned in shopping page: "+productCount);	
		}
 		
 		List<WebElement> totalProductsinFirstPage =  driver.findElements(By.id("totPrdtSizeDiv"));
		
		System.out.println ("Number of products in 1st page is : "+ totalProductsinFirstPage.size() );	
		
		System.out.println ("\nVerifying the Price of each product'");
		System.out.println ("*************************************\n");
				
		int priceCount;
				for(priceCount=1; priceCount<= totalProductsinFirstPage.size(); ++priceCount) 
			 	{
				WebElement priceDisplayed = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+priceCount+"]/div/div[2]/div[2]/div[1]/span"));
				String priceWithoutComma= priceDisplayed.getText().replace(",","");
				System.out.println("Price of "+priceCount+"th product is: " +priceWithoutComma);	
				if (priceWithoutComma.isEmpty())
					{
					System.out.println("Price is not present for "+priceCount+"th product");	
					excelreadwrite.insertFailedData(currentTestName,
							commonUtility.getcurrentDateTime(), "Basic Listing Page",
							"Checking for result page",
							"", true, "", "Price is not present for "+priceCount+"th product"
									+ "error", "Price should present for all products");
					Assert.assertFalse(true,"Price should present for all products");
					break;
					 }
					
			 	  }
				System.out.println("priceCount"+ priceCount);	
				System.out.println("totalProductsinFirstPage.size()"+ totalProductsinFirstPage.size());	
				
		if (priceCount > totalProductsinFirstPage.size())
		{
			System.out.println("Price is present for all products");
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Basic Listing Page",
					"Checking for Price field of product",
					"", true, "", "Price field shown",
									 "Price field should be shown");
		}
		
		System.out.println ("\nVerifying the Airline of each product'");
		System.out.println ("*************************************\n");
				
		int airlineCount;
		
				for(airlineCount=1; airlineCount<= totalProductsinFirstPage.size(); ++airlineCount) 
			 	{
					System.out.println("\nAirlines of "+airlineCount+"th Product are:");
					WebElement airlineDisplayedOnward = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+airlineCount+"]/div/div[2]/div[1]/div[1]/div[1]"));
					WebElement airlineDisplayedReturn = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+airlineCount+"]/div/div[2]/div[1]/div[2]/div[1]"));
					
					System.out.println("Onward Segment: " +airlineDisplayedOnward.getText());	
					System.out.println("Return Segment: " +airlineDisplayedReturn.getText());	
					  
					  if (airlineDisplayedOnward.getText().isEmpty() ||airlineDisplayedReturn.getText().isEmpty() )
						{
						System.out.println("Airline is not present for "+airlineCount+"th product");	
						excelreadwrite.insertFailedData(currentTestName,
								commonUtility.getcurrentDateTime(), "Basic Listing Page",
								"Checking for Airline Field",
								"", true, "", "Airline is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
										+ "error", "Airline should present for all products except Opaque");
						Assert.assertFalse(true,"Airline should present for all products except Opaque");
						
						 }
			 	}
					
		if (airlineCount > totalProductsinFirstPage.size())
		{
			System.out.println("\nAirline field check done for all products");
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Basic Listing Page",
					"Checking for Airline field of each product",
					"", true, "", "Airline field check done for all products",
									 "Airline field should be shown except Opaque");
		}
		
		System.out.println ("\nVerifying the Flight Number field of each product'");
		System.out.println ("*********************************************\n");
				
		int flightNoCount;
		
				for(flightNoCount=1; flightNoCount<= totalProductsinFirstPage.size(); ++flightNoCount) 
			 	{
					System.out.println("flight Numbers of "+flightNoCount+"th Product are:");
					
					Thread.sleep(2000); 
					if(flightNoCount>10)
						
					{
						JavascriptExecutor jse = (JavascriptExecutor)driver;
						jse.executeScript("window.scrollBy(0,500)", "");
					
					}
					driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+flightNoCount+"]/div/div[2]/div[2]/div[6]/a")).click();	
					Thread.sleep(4000); 		 
					
					WebElement flightNumberDisplayedOnward = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+flightNoCount+"]/div/div[3]/div[1]/div/div[2]/div[2]/div[1]"));
					
					WebElement flightNumberDisplayedReturn;
					if(driver.findElements(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+flightNoCount+"]/div/div[3]/div[2]/div/div[2]/div[2]/div[1]")).isEmpty())
					{
					flightNumberDisplayedReturn = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+flightNoCount+"]/div/div[3]/div[2]/div/div[2]/div[2]/div[1]"));
					}
					else
					
					{
				 flightNumberDisplayedReturn = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+flightNoCount+"]/div/div[3]/div[2]/div/div[2]/div[2]/div[1]"));
					}
					
					System.out.println("Onward Segment: " +flightNumberDisplayedOnward.getText().trim());
				
					System.out.println("Return Segment: " +flightNumberDisplayedReturn.getText().trim());	
					
					if (flightNumberDisplayedOnward.getText().isEmpty() || flightNumberDisplayedReturn.getText().isEmpty() )
						{
						System.out.println("Flight number is not present for "+flightNoCount+"th product");	
						excelreadwrite.insertFailedData(currentTestName,
								commonUtility.getcurrentDateTime(), "Basic Listing Page",
								"Checking for Flight Number Field",
								"", true, "", "Flight Numbe is not present for "+flightNoCount+"th product. Check whether it is an Opaque or NOT"
										+ "error", "Flight Numbe should present for all products except Opaque");
						Assert.assertFalse(true,"Flight Numbe should present for all products except Opaque");
						
						 }
					//Close the details window
					driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+flightNoCount+"]/div/div[2]/div[2]/div[6]/a")).click();	
			 	}
					
		if (flightNoCount > totalProductsinFirstPage.size())
		{
			System.out.println("Flight number check done for all products");
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Basic Listing Page",
					"Checking for Flight Number field of each product",
					"", true, "", "Flight number field check done for all products",
									 "Flight number field should be shown except Opaque");
		}
		
		
		
		
		
		
		
		System.out.println ("\nVerifying the Departure Airport field of each product'");
		System.out.println ("************************************************\n");
		
		int departureAirportCount;
		
		for(departureAirportCount=1; departureAirportCount<= totalProductsinFirstPage.size(); ++departureAirportCount) 
	 	{
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,-600)", "");
			System.out.println("\nDeparture Airport of "+departureAirportCount+"th Product are:");
			
			Thread.sleep(1000); 
			driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+departureAirportCount+"]/div/div[2]/div[2]/div[6]/a")).click();
			
			WebElement departureAirportOnward = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+departureAirportCount+"]/div/div[3]/div[1]/div/div[1]/div[1]"));
			WebElement departureAirportReturn = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+departureAirportCount+"]/div/div[3]/div[2]/div/div[1]/div[1]"));
			
			System.out.println("We are here");	
			
			System.out.println("Onward Segment: " +departureAirportOnward.getText());	
			System.out.println("Return Segment: " +departureAirportReturn.getText());	
			  if (departureAirportOnward.getText().isEmpty() ||departureAirportReturn.getText().isEmpty() )
				{
				System.out.println("Departure Airport code is not present for "+departureAirportCount+"th product");	
				excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "Basic Listing Page",
						"Checking for Deparuture Aiport  Field of bot Onward and Return",
						"", true, "", "Departure airport  is not present for "+departureAirportCount+"th product. Check whether it is an Opaque or NOT"
								+ "error", "Departure Airport field should be present for all products except Opaque");
				Assert.assertFalse(true,"Departure Airport field should be present for all products except Opaque");
				
				 }
	 	}
			
if (departureAirportCount > totalProductsinFirstPage.size())
{
	System.out.println("Departure Aiport check done for all products");
	excelreadwrite.insertData(currentTestName,
			commonUtility.getcurrentDateTime(), "Basic Listing Page",
			"Checking for Departure Airport field of each product",
			"", true, "", "Departure Airport field check done for all products",
							 "Departure Airport field should be shown except Opaque");
}



System.out.println ("\nVerifying the Arrival Airport field of each product'");
System.out.println ("************************************************\n");

int ArrivalAirpportCount;

for(ArrivalAirpportCount=1; ArrivalAirpportCount<= totalProductsinFirstPage.size(); ++ArrivalAirpportCount) 
	{
	
	JavascriptExecutor jse = (JavascriptExecutor)driver;
	jse.executeScript("window.scrollBy(0,-600)", "");
	System.out.println("\nArrival Airport of "+ArrivalAirpportCount+"th Product are:");
	
	Thread.sleep(1000); 
	
	
	WebElement arrivalAirportrDisplayedOnward = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+ArrivalAirpportCount+"]/div/div[3]/div[1]/div/div[3]/div[1]"));
	WebElement arrivalAirportrDisplayedReturn = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+ArrivalAirpportCount+"]/div/div[3]/div[2]/div/div[3]/div[1]"));
	
	System.out.println("Onward Segment: " +arrivalAirportrDisplayedOnward.getText());	
	System.out.println("Return Segment: " +arrivalAirportrDisplayedReturn.getText());	
	  if (arrivalAirportrDisplayedOnward.getText().isEmpty() ||arrivalAirportrDisplayedReturn.getText().isEmpty() )
		{
		System.out.println("Arrival Airport code is not present for "+ArrivalAirpportCount+"th product");	
		excelreadwrite.insertFailedData(currentTestName,
				commonUtility.getcurrentDateTime(), "Basic Listing Page",
				"Checking for Arrival Aiport  Field of bot Onward and Return",
				"", true, "", "Arrival airport  is not present for "+ArrivalAirpportCount+"th product. Check whether it is an Opaque or NOT"
						+ "error", "Arrival Airport field should be present for all products except Opaque");
		Assert.assertFalse(true,"Arrival Airport field should be present for all products except Opaque");
		
		 }
	}
	
if (ArrivalAirpportCount > totalProductsinFirstPage.size())
{
System.out.println("Arrival Aiport check done for all products");
excelreadwrite.insertData(currentTestName,
	commonUtility.getcurrentDateTime(), "Basic Listing Page",
	"Checking for Arrival Airport field of each product",
	"", true, "", "Arrival Airport field check done for all products",
					 "Arrival Airport field should be shown except Opaque");
	 	
		}

		}
		catch(Exception e){
			e.printStackTrace();
		}
}
}
