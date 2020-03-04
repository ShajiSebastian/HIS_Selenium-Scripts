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

import java.util.ArrayList;
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

public class VerifySortByDuration_OnwardShorterToLonger extends DriverSetup {
	SearchPage SearchPage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	int productCount;
	private String title;
	
	@BeforeClass
	public void setup() {
				
	         
		System.out.println("\ninside setup() fn (VerifySortByDuration_OnwardShorterToLonger.java)");
		System.out.println("VerifySortByDuration_OnwardShorterToLonger.java execution starts now");
		System.out.println("\nGetting current test case name (VerifySortByDuration_OnwardShorterToLonger.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (VerifySortByDuration_OnwardShorterToLonger.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "VerifySortByDuration_OnwardShorterToLonger")
	public Object[][] createData2() throws Exception {

	         
		System.out.println("inside VerifySortByDuration_OnwardShorterToLonger.java");
		System.out.println("\nTrying to to read input test data from input datasheet (VerifySortByDuration_OnwardShorterToLonger.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"VerifySortByDuration_OnwardShorterToLonger");
		return (retObjArr);
	}

	@Test(dataProvider = "VerifySortByDuration_OnwardShorterToLonger")
	public void Verifychoosedeparture(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults, String NoOfChild,
			String NoOfInfant, String fromCity)
			throws InterruptedException {
		System.out.println("\nTrying to invoke HIS page to Execute 'VerifySortByDuration_OnwardShorterToLonger' Test Case  (VerifySortByDuration_OnwardShorterToLonger.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		driver.navigate().to(getTestURL());
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		Thread.sleep(5000);
		/*
		 // to be used  we want to run specifically for RT
		if (!driver.findElement(By.xpath(".//input[@value='RT']")).isSelected()) {
		System.out.println("Trying to select RT option");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//input[@value='RT']")).click();
		System.out.println("RT option selected");
		}
		*/

		// Getting current url
	    String currentUrl= driver.getCurrentUrl();
	    System.out.println("Current url is: "+ currentUrl);
	         
		
		System.out.println("\nTrying to enter search criteria");
		System.out.println("-----------------------------");
		WebElement dep=driver.findElement(By.xpath(".//*[@id='fromCombo']"));
		
	//	drop.selectOptionByVisibleText(fromCity);
		driver.findElement(By.xpath(".//*[@id='fromCombo']")).click();
		
		List<WebElement> fromCityOptions = driver.findElements(By.xpath(".//*[@id='fromCombo']//option"));
		
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
		Thread.sleep(10000);
		String pageTitle = driver.getTitle().trim();
		System.out.println("Page title is: "+pageTitle);
		if (pageTitle.toLowerCase().trim().contains("H.I.S. Flight Search Results".toLowerCase().trim())) 
		{
			System.out.println("\nSearch result page came");
			System.out.println("----------------------------------");
			System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking for result page",
					"", true, "", "Search result page shown",
									 "Search result page should be shown");
		} 
		else 
		{
			System.out.println("\nSearch result page didnt come");
			System.out.println("----------------");
			System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
			String errorText = driver.findElement(By.xpath(".//*[@id='errorID']")).getText();
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking for result page",
					"", true, "", "Search page result NOT shown."
							+ errorText, "Search page result should be shown");
			Assert.assertFalse(true,"H.I.S. Flight Search Results is not displayed");
		}
		
		System.out.println("\nback to VerifySortByDuration_OnwardShorterToLonger.java ");

		// Verification 2 - After search, In Fare matrix display â€“ Check the
		// text â€œDL â€“Delta Airlinesâ€�? is properly displayed.
		
		
		System.out.println ("\nBasic Listing page shown");
 		// code to check number of products returned
 		if(driver.findElements(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]")).isEmpty())
		{ 
 			System.out.println ("No products returned");
 			return;
 		 }
						 
 		else 
		{
 			productCount = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]/strong")).getText());
 			System.out.println ("Total Number of Products: "+productCount);	
		}
 		
 		 List<WebElement> totalProductsinFirstPage =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
			
			System.out.println ("Total products in 1st page is : "+ totalProductsinFirstPage.size() );	
		
		
		try
			{
		System.out.println ("\nVerifying the Functionality 'Sort based on Duration'");
		System.out.println ("*************************************************\n");
		
		//System.out.println("Number of products in first page before Sorting: "+totalProductsinFirstPage.size());	
		//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='priceLink']/span")));
		
		// identifying the total number of pages in the basic listing page	
		int TotalPages=1;
		
		if (productCount >20)
		{
		
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='paging']/a[7]")).click();
		//driver.findElement(By.id(title="Last Page")).click();
		Thread.sleep(2000); 
		String NumberOfPages = driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).getText();
		TotalPages =Integer.parseInt(NumberOfPages);
		System.out.println ("Total Number of Products: "+ productCount);	
		System.out.println("\nNumber of pages:" +TotalPages);
		
		//clicking on << to come back to first page	
		driver.findElement(By.xpath(".//*[@id='paging']/a[1]")).click();
		Thread.sleep(2000); 
		}
		
		if (productCount < 20)
		{
			System.out.println ("Total Number of Products: "+ productCount);	
			System.out.println("\nNumber of pages:" +TotalPages);
		}
		
		// storing Duration of all products in an array
		int count;
		int page;	
		Integer newHour;
		Integer newMin;
		int arrayIndexBeforeSortbyDuration=0;
		Integer[] hourBeforeSorting = new Integer[productCount];
		Integer[] timeBeforeSorting = new Integer[productCount];
		
		for(page=1; page<= TotalPages; ++page)
		{
			
		//clicking on next page
			if (productCount > 20)
			{
			int page_number;
			page_number = page + 2 ;
			if (page_number < 5)
			{
			driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number+"]")).click();
			}
			else
			{
			driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).click();
			}
			Thread.sleep(4000); 
			}
			List<WebElement> totalProductsInPageBeforeSorting =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
			
			System.out.println("\n\nTotal products in this page is : " +totalProductsInPageBeforeSorting.size());
			
			for(count=1; count<= totalProductsInPageBeforeSorting.size(); ++count) 
			{	 
			//clicking on show details button
			Thread.sleep(1000); 
			//driver.findElement(By.xpath("//a[@class='onTogg']["+count+"]")).click(); 	
			Thread.sleep(0000); 
	 		WebElement durationDisplayed = driver.findElement(By.xpath("(.//*[@id='totPrdtSizeDiv'])["+count+"]//div[@class='row sliceOne']//div[@class='travelDuration']"));
	 		if (durationDisplayed.getText().contains("hr"))
	 		{
	 		String durationInHourandMin= durationDisplayed.getText().substring(0,10);
	 		System.out.println("\n\ndurationShown1: " +durationInHourandMin);
	 	
	 		String durationInHour= durationInHourandMin.substring(0, 2).trim();
	 		System.out.println("durationInHour: " +durationInHour);
	 		String durationInMin= durationInHourandMin.substring(5, 7).trim();
	 		System.out.println("durationInMin: " +durationInMin);
	 		
	 		newHour= Integer.parseInt(durationInHour);
	 		newMin= Integer.parseInt(durationInMin);
	 		
			System.out.println("Duration of "+count+"th product in " + page+ "th page is: " +newHour+" Hours "+newMin+" Min");	
			hourBeforeSorting[arrayIndexBeforeSortbyDuration]=newHour;
			timeBeforeSorting[arrayIndexBeforeSortbyDuration]=newMin;
			arrayIndexBeforeSortbyDuration = arrayIndexBeforeSortbyDuration + 1;
	 		}
	 		else
	 		{
	 				
	 			System.out.println("There is NO Duration for "+count+"th product in " + page+ "th page");	
				hourBeforeSorting[arrayIndexBeforeSortbyDuration]=1;
				timeBeforeSorting[arrayIndexBeforeSortbyDuration]=1;
				arrayIndexBeforeSortbyDuration = arrayIndexBeforeSortbyDuration + 1;
	 		}
			
			}
			
		}
		
		//trying to print each values in an array
		System.out.print("\nBefore Sorting Functionality: The default hour list is :");
		for (int x: hourBeforeSorting )
		    System.out.print(x + ",");
		System.out.print("\nBefore Sorting Functionality: The default time list is :");
		for (int x: timeBeforeSorting )
		    System.out.print(x + ",");
		
	
		
		//trying to sort the values in the above array 	
			Arrays.sort(hourBeforeSorting);
			Arrays.sort(timeBeforeSorting);
				
			//trying to print each values in an array
			System.out.print("\nBefore Sorting Functionality: The sorted hour list is :");
			for (int x: hourBeforeSorting )
			    System.out.print(x + ",");
			System.out.print("\nBefore Sorting Functionality: The sorted time list is :");
			for (int x: timeBeforeSorting )
			    System.out.print(x + ",");
			
		 
		
			//Checking whether Sort by Duration option available in application or not
		Thread.sleep(2000); 
				try
				{
				driver.findElement(By.xpath(".//*[@id='btn']")).click();
				System.out.println ("\n\nSort by Duration option availalbe");
					System.out.println("\nUpdating Report VerifySortByDuration_OnwardShorterToLonger.xls in reports/xls");
					excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of Sort by Duration option",
							"", true,
							"", "Sort by Duration option available",
							"Sort by Duration option Should be available");
				
				}
				catch (Exception e)
				{
					System.out.println("Sort by Duration option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySortByDuration_OnwardShorterToLonger.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of Sort by Duration option",
							"", true,
					"", "Sor by Duration option NOT availalbe",
						"Sort by Duration option Should be available");
	 				Assert.assertFalse(true,
	 						"In Fare matrix display actual text is not valid");
				}
				//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='sortByPriceMenu']/p[2]/a")));
				Thread.sleep(2000); ;
				
				//Checking whether 'Departure Shorter to Longer' option available in application or not
				try
				{
				driver.findElement(By.xpath(".//*[@id='durationOutSortDivStoL']")).click();
				
				System.out.println ("\n'Departure- Shorter to Longer' option availalbe");
				System.out.println ("\nClicked on Sorting Functionality");
				System.out.println("\nUpdating Report VerifySortByDuration_OnwardShorterToLonger.xls in reports/xls");
				excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking the presence of Departure- Shorter to Longer option",
						"", true,
						"", "Departure- Shorter to Longer option available",
						"Departure- Shorter to Longeroption Should be available");
				}
				catch (Exception E2)
				{
					System.out.println("'Departure- Shorter to Longer' option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySortByDuration_OnwardShorterToLonger.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of 'Departure- Shorter to Longer' option",
							"", true,
					"", "'Departure- Shorter to Longer' option NOT availalbe",
						"'Departure- Shorter to Longer' option Should be available");
	 				Assert.assertFalse(true,"'Departure- Shorter to Longer' option NOT availalbe");	
				}
				Thread.sleep(2000);
				
				// storing price of all products in an array after 'sort by price; action
				
				int count2;
			
				Integer newHour2;
				Integer newMin2;
				int arrayIndexAfterSortbyDuration=0;
				Integer[] hourAfterSorting = new Integer[productCount];
				Integer[] timeAfterSorting = new Integer[productCount];				
				
				for(page=1; page<= TotalPages; ++page) 
				{
					
				//clicking on next page
					
					if (productCount >20)
					{
					int page_number;
					page_number = page + 2 ;
					
					if (page_number < 5)
					{
					driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number+"]")).click();
					}
					else
					{
					driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).click();
					}
					
					Thread.sleep(4000); 
					}
					List<WebElement> totalProductsInPageBeforeSorting =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
					
					System.out.println("\n\nTotal products in this page is : " +totalProductsInPageBeforeSorting.size());
					
					for(count2=1; count2<= totalProductsInPageBeforeSorting.size(); ++count2) 
					{	 
					//clicking on show details button
					Thread.sleep(1000); 
					//driver.findElement(By.xpath("(//*[starts-with(@id,'exp_showDetails')])["+count2+"]")).click(); 	
					Thread.sleep(2000); 
			 		WebElement durationDisplayed = driver.findElement(By.xpath("(.//*[@id='totPrdtSizeDiv'])["+count2+"]//div[@class='row sliceOne']//div[@class='travelDuration']"));
			 		if (durationDisplayed.getText().contains("hr"))
			 		{
			 		String durationInHourandMin= durationDisplayed.getText().substring(26);
			 		System.out.println("\n\ndurationShown1: " +durationInHourandMin);
			 	
			 		String durationInHour= durationInHourandMin.substring(0, 2).trim();
			 		System.out.println("durationInHour: " +durationInHour);
			 		String durationInMin= durationInHourandMin.substring(6, 8).trim();
			 		System.out.println("durationInMin: " +durationInMin);
			 		
			 		newHour2= Integer.parseInt(durationInHour);
			 		newMin2= Integer.parseInt(durationInMin);
			 		
					System.out.println("Duration of "+count2+"th product in " + page+ "th page is: " +newHour2+" Hours "+newMin2+" Min");	
					hourAfterSorting[arrayIndexAfterSortbyDuration]=newHour2;
					timeAfterSorting[arrayIndexAfterSortbyDuration]=newMin2;
					arrayIndexAfterSortbyDuration = arrayIndexAfterSortbyDuration + 1;
			 		}
			 		else
			 		{			 				
			 			System.out.println("There is NO Duration for "+count2+"th product in " + page+ "th page");	
						hourAfterSorting[arrayIndexAfterSortbyDuration]=1;
						timeAfterSorting[arrayIndexAfterSortbyDuration]=1;
						arrayIndexAfterSortbyDuration = arrayIndexAfterSortbyDuration + 1;
			 		}
					
					}
					
				}
				
				//trying to print each values in an array after sorting Lower to Higher
				System.out.print("\nBefore Sorting Functionality:The Sorted Hour List is :");
				for (int x: hourBeforeSorting )
				    System.out.print(x + ",");
				
				System.out.print("\nAfter Sorting Functionality: The list is :");
				for (int x: hourAfterSorting )
				    System.out.print(x + ",");
				
				System.out.print("\n\nBefore Sorting Functionality: The Sorted time List is : :");
				for (int x: timeBeforeSorting )
				    System.out.print(x + ",");
				
				System.out.print("\nAfter Sorting Functionality: The time List is :");
				for (int x: timeAfterSorting )
				    System.out.print(x + ",");				
				
				System.out.print("\nComparing the array before and after sorting functionality");
				
				// Comparing the two arrays
				
		        if (Arrays.equals(hourBeforeSorting, hourAfterSorting))
		        {
		        	System.out.println ("\nThe flights are sorted based on Duration-Shorter to Longer");
					System.out.println("\nUpdating Report VerifySortByDuration_OnwardShorterToLonger.xls in reports/xls");
					excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking whether products sorted based on Duration-Shorter to Longer",
							"", true,
							"", "Products sorted based on Duration-Shorter to Longer",
							"Products should be sorted based on Duration-Shorter to Longer");
		        }
		        else
		        {
		        	System.out.println("calling insertFailedData() fn from ExcelReadWrite");
		        	System.out.println ("\nThe flights are NOT sorted based on Duration-Shorter to Longerr");
	 				System.out.println("\nUpdating Report VerifySortByDuration_OnwardShorterToLonger.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking whether products sorted based on Duration-Shorter to Longer",
							"", true,
					"", "Products NOT sorted based on Duration-Shorter to Longer",
						"Products should be sorted based on Duration-Shorter to Longer");
	 				Assert.assertFalse(true,"Products should be sorted based on Duration-Shorter to Longer");
	 				
		        }
		        

			
				
			
 			
				}		
 					

			
			catch (Exception SortPriceError)
			{
				System.out.println ("\nSome issues found while trying to sort by Duration: " +SortPriceError);
			}
		
		
			
		
}

}


