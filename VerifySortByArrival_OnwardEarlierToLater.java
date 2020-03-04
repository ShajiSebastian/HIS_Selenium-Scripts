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

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class VerifySortByArrival_OnwardEarlierToLater extends DriverSetup {
	SearchPage SearchPage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	


	
	@BeforeClass
	public void setup() {
				
	         
		System.out.println("\ninside setup() fn (VerifySortByArrival_OnwardEarlierToLater.java)");
		System.out.println("VerifySortByArrival_OnwardEarlierToLater.java execution starts now");
		System.out.println("\nGetting current test case name (VerifySortByArrival_OnwardEarlierToLater.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (VerifySortByArrival_OnwardEarlierToLater.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "VerifySortByArrival_OnwardEarlierToLater")
	public Object[][] createData2() throws Exception {
	         
		System.out.println("inside VerifySortByArrival_OnwardEarlierToLater.java");
		System.out.println("\nTrying to to read input test data from input datasheet (VerifySortByArrival_OnwardEarlierToLater.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		System.out.println("The identification text of test data to be given below function. If not mentioned correctly TC will skip");
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"VerifySortByArrival_OnwardEarlierToLater");
	
		return (retObjArr);
	}
	
	@Test(dataProvider = "VerifySortByArrival_OnwardEarlierToLater")
	public void Verifychoosedeparture(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults, String NoOfChild,
			String NoOfInfant, String fromCity)
			throws InterruptedException {
		System.out.println("\nTrying to invoke HIS page to Execute 'VerifySortByArrival_OnwardEarlierToLater' Test Case  (VerifySortByArrivalEarlierToLater.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		/*driver.get(getTestURL());
		Thread.sleep(1000);*/
		driver.navigate().to(getTestURL());
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		Thread.sleep(5000);
		/*if (!driver.findElement(By.xpath(".//input[@value='RT']")).isSelected()) {
			System.out.println("Trying to select RT option");
			driver.findElement(By.xpath(".//input[@value='RT']")).click();
			System.out.println("RT option selected");
		}
*/
		// Getting current url
	         String currentUrl= driver.getCurrentUrl();
	         System.out.println("Current url is: "+ currentUrl);
	         
		
		System.out.println("\nTrying to enter search criteria");
		System.out.println("-----------------------------");
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
			System.out.println("\nUpdating Report VerifySortByArrival_OnwardEarlierToLater.xls in reports/xls");
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
			System.out.println("\nUpdating Report VerifySortByArrival_OnwardEarlierToLater.xls in reports/xls");
			String errorText = driver.findElement(By.xpath(".//*[@id='errorID']")).getText();
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking for result page",
					"", true, "", "Search page result NOT shown."
							+ errorText, "Search page result should be shown");
			Assert.assertFalse(true,"H.I.S. Flight Search Results is not displayed");
		}
		
		System.out.println("\nback to VerifySortByArrival_OnwardEarlierToLater.java ");

		// Verification 2 - After search, In Fare matrix display â€“ Check the
		// text â€œDL â€“Delta Airlinesâ€�? is properly displayed.
		
		
		System.out.println ("\nBasic Listing page shown");
 		// code to check number of products returned
 		//if(driver.findElements(By.xpath(".//*[@id='example']/span")).isEmpty())
		if(driver.findElements(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]")).isEmpty())
		{ 
 			System.out.println ("No products returned");
 			return;
 		 }
						 
 		else 
		{
 			int productCount = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]/strong")).getText());
 			System.out.println ("Total Number of Products returned in shopping page: "+productCount);	
		}
 		
 		List<WebElement> totalProductsinFirstPage =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
		
		System.out.println ("Total products in 1st page is : "+ totalProductsinFirstPage.size() );
		
		
		
		System.out.println ("\nVerifying the Functionality 'Sort By Arrival-OnwardEarlierToLater'");
		System.out.println ("*************************************************\n");
		
		//System.out.println("Number of products in first page before Sorting: "+totalProductsinFirstPage.size());	
		//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='priceLink']/span")));
		Thread.sleep(2000); 
		
				
		
		
				try
				{
				driver.findElement(By.xpath(".//*[@id='btn']")).click();
				
				System.out.println ("\nSort by Arrival option availalbe");
					System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
					excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of Sort by Arrival option",
							"", true,
							"", "Sort by Arrival option available",
							"Sort by Arrival Should be available");
				
				}
				catch (Exception e)
				{
					System.out.println("Sort by Arrival option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of Sort by Arrival option",
							"", true,
					"", "Sor by Arrival option NOT availalbe",
						"Sort by Arrival option Should be available");
	 				Assert.assertFalse(true,
	 						"In Fare matrix display actual text is not valid");
				}
				//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='sortByPriceMenu']/p[2]/a")));
				Thread.sleep(2000); ;
				
				try
				{
				driver.findElement(By.xpath(".//*[@id='arrOutSortDivLtoE']")).click();
				
				System.out.println ("\n'Outbound- earlier to later' option availalbe");
				System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
				 Thread.sleep(5000);
				
				excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking the presence of Outbound- earlier to later option",
						"", true,
						"", "Outbound- earlier to later option available",
						"Outbound- earlier to later option Should be available");
				}
				catch (Exception E2)
				{
					System.out.println("'Outbound- earlier to laterr' option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of 'Outbound- earlier to later option",
							"", true,
					"", "'Outbound- earlier to later' option NOT availalbe",
						"'Outbound- earlier to later' option Should be available");
	 				Assert.assertFalse(true,"'Higher to Lower' option NOT availalbe");	
				}
				Thread.sleep(2000);;
				 List<WebElement> totalProductsinFirstPageafterSortingbyDepartreEarlierToLater =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
				
				System.out.println ("Total products in 1st page after Sorting by Price is : "+ totalProductsinFirstPageafterSortingbyDepartreEarlierToLater.size() );
				
				//int prodSize= totalProductsinFirstPage.size();
				
				try
				{
				int depSortCount;
				int bigTime=2359;
				
				for(depSortCount=1; depSortCount<= totalProductsinFirstPageafterSortingbyDepartreEarlierToLater.size(); ++depSortCount) 
			 	{
			 			 			
					WebElement departureTime = driver.findElement(By.xpath("((.//*[@id='listView1'])["+depSortCount+"]//div[@class='travelTime'])[2]"));
			 		System.out.println("Departure Date and time of "+depSortCount+"th product is: " + departureTime.getText());	
			 		String timeWithoutDate = departureTime.getText().substring(0,5);
			 		System.out.println("timeWithoutDate of "+depSortCount+"th product is: " + timeWithoutDate);	
			 		String timeWithoutColumn= timeWithoutDate.replace(":", "");
			 		System.out.println("timeWithoutColumn of "+depSortCount+"th product is: " + timeWithoutColumn);	
	 				
	 				int newDepTime= Integer.parseInt(timeWithoutColumn.trim()); 
	 				
			 			if (newDepTime <=  bigTime)
			 			{
			 				bigTime =newDepTime;
			 				continue;
			 			}
			 			else
			 			{
			 				System.out.println("Products are not sorted on Outbound- earlier to later");
			 				
			 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
			 				
			 				System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
			 				excelreadwrite.insertFailedData(currentTestName,
			 						commonUtility.getcurrentDateTime(), "Sorting module",
									"Checking whether products sorted based on Outbound- earlier to later",
									"", true,
							"", "Products NOT sorted based on Outbound- earlier to later",
								"Products should be sorted based on Outbound- earlier to later");
			 				Assert.assertFalse(true,"Products should be sorted based on Outbound- earlier to later");
			 				break;
			 			}
			 			
			 				 		
			 	}
				
				System.out.println ("\ndepSortCount" +depSortCount);
				
				System.out.println ("\ntotalProductsinFirstPage.size()" +totalProductsinFirstPage.size());
	 			
 				System.out.println ("\nThe flights sorted based on Outbound, earlier to later");
 				System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
				excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking whether products are sorted based on Outbound-earlier to later",
						"", true,
						"", "Products sorted correctly",
						"Products should be sorted correctly");
 				
 					
				
				
				}
								
			
			catch (Exception SortPriceError)
			{
				System.out.println ("\nSome issues found while trying to sort by Price: " +SortPriceError);
			
 				
 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
 				
 				System.out.println("\nUpdating Report VerifySortByArrivalEarlierToLater.xls in reports/xls");
 				excelreadwrite.insertFailedData(currentTestName,
 						commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking whether products sorted based on Outbound- earlier to later",
						"", true,
				"", "Some issues found while trying to sort by Arrival",
					"Products should be sorted based on Outbound- earlier to later");
 				Assert.assertFalse(true,"Products should be sorted based on Outbound- earlier to later");
 				
			}
		
		
			
		
}

}


