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

import java.util.Collections;
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
import java.util.Arrays;
//import controls.*;
public class VerifySortByArrival_ReturnEarlierToLater extends DriverSetup {
	SearchPage SearchPage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	//public DropDown drop;
	String currentTestName;
	int productCount;
	Xls_Read xls_Read;

    String del;
	
	@BeforeClass
	public void setup() 
	{
				
	         
		System.out.println("\ninside setup() fn (VerifySortByArrival_ReturnEarlierToLater.java)");
		System.out.println("VerifySortByArrival_ReturnEarlierToLater.java execution starts now");
		System.out.println("\nGetting current test case name (VerifySortByArrival_ReturnEarlierToLater.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
	//	drop = new DropDown(driver,del);
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (VerifySortByArrival_ReturnEarlierToLater.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "VerifySortByArrival_ReturnEarlierToLater")
	public Object[][] createData2() throws Exception 
	{
	         
		System.out.println("inside VerifySortByArrival_ReturnEarlierToLater.java");
		System.out.println("\nTrying to to read input test data from input datasheet (VerifySortByArrival_ReturnEarlierToLater.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		System.out.println("The identification text of test data to be given below function. If not mentioned correctly TC will skip");
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"VerifySortByArrival_ReturnEarlierToLater");
	
		return (retObjArr);
	}

	@Test(dataProvider = "VerifySortByArrival_ReturnEarlierToLater")
	public void Verifychoosedeparture(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults, String NoOfChild,
			String NoOfInfant, String fromCity)
			throws InterruptedException 
			{
		System.out.println("\nTrying to invoke HIS page to Execute 'VerifySortByArrival_ReturnEarlierToLater' Test Case  (VerifySortByArrival_ReturnEarlierToLater.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		/*driver.get(getTestURL());
		Thread.sleep(1000);*/
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
		
		System.out.println("\nback to VerifySortByArrival_ReturnEarlierToLater.java ");

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
 			productCount = Integer.parseInt(driver.findElement(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]/strong")).getText());
 			System.out.println ("Total Number of Products returned in shopping page: "+productCount);	
		}
 		
 		 List<WebElement> totalProductsinFirstPage =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
			
			System.out.println ("Total products in 1st page is : "+ totalProductsinFirstPage.size() );	
		
			
		Thread.sleep(2000); 
		
		try
		{
	System.out.println ("\nVerifying the Functionality 'Sort by Arrival - Return Earlier to Later'");
	System.out.println ("*************************************************\n");
	
	Thread.sleep(3000);	
	// identifying the total number of pages in the basic listing page				
	driver.findElement(By.xpath(".//*[@id='paging']/a[7]")).click();
	Thread.sleep(2000); 
	String NumberOfPages = driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).getText();
	int TotalPages =Integer.parseInt(NumberOfPages);
	System.out.println("\nNumber of pages:" +TotalPages);
	
	//clicking on << to come back to first page	
	driver.findElement(By.xpath(".//*[@id='paging']/a[1]")).click();
	Thread.sleep(2000); 
		
		
		// storing Arrival time of Return slice of all products in an array
		int count;
		int page;	
		int newArrivalTime;
		int arrayIndex=0;
		Integer[] timeBeforeSorting = new Integer[productCount];
		int page_number1=0;

		List<WebElement> totalProductsInPageBeforeSorting =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
		
		System.out.println("Number of products in first page is: "+ totalProductsInPageBeforeSorting.size()+"\n");
		
		/*
		
		//for(page=1; page<= TotalPages; ++page) for all pages
		for(page=1; page<= 1; ++page) //for only first page
		{
			
		//clicking on next page
//clicking on next page
			
			if (page <=3)
			{
			page_number1 = page + 2 ;

			driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number1+"]")).click();
			}
			else
			{
				driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).click();
				
			}
			
			System.out.println("\npage number is: " +page);
			
			/*
			int page_number;
			page_number = page + 2 ;
			driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number+"]")).click();
			Thread.sleep(2000); 
			*/
		/*
			Thread.sleep(2000); 
			List<WebElement> totalProductsInPageBeforeSorting =  driver.findElements(By.xpath(".//*[@id='example']//*[@class='prdouct_develop_grp']"));
			
			System.out.println("Number of products in "+page+ "th page is: "+ totalProductsInPageBeforeSorting.size()+"\n");
			
			for(count=1; count<= totalProductsInPageBeforeSorting.size(); ++count) 
			{	
					WebElement arrivalTime= driver.findElement(By.xpath("(((.//*[@id='example']//*[@class='prdouct_develop_grp'])["+count+"])//*[starts-with(@class,'arrvalSlice')])[2]"));
			 		System.out.println("Arrival Date and time of "+count+"th product in "+page+"th page is: " + arrivalTime.getText());	
			 		String timeWithoutDate = arrivalTime.getText().substring(15);
			 		System.out.println("timeWithoutDate is: " + timeWithoutDate);	
			 		String timeWithoutColumn= timeWithoutDate.replace(":", "");
			 		System.out.println("timeWithoutColumn is: " + timeWithoutColumn);	
	 				
	 				newArrivalTime= Integer.parseInt(timeWithoutColumn.trim()); 
	 				System.out.println("newArrivalTime (after trimming) is: " + newArrivalTime);
					timeBeforeSorting[arrayIndex]=newArrivalTime;
					arrayIndex = arrayIndex + 1;
					Thread.sleep(3000); 
			}


			}
			
		arrayIndex = arrayIndex - 1;
		
		//trying to print each values from the array
		System.out.print("\nBefore Sorting Functionality: The arrival time stored in array is :");
		for (int x: timeBeforeSorting)
		    System.out.print(x + ",");
		System.out.print("\nPrinting same data again");
		for (int arraycount=0;arraycount<=arrayIndex-1;++arraycount)
		{
			System.out.print(timeBeforeSorting[arraycount]+",");
		}
		
		//trying to sort the values in the above array 	
		Arrays.sort(timeBeforeSorting);
			
			
		 //trying to print each values in an array after sorting Higher to Lower
			System.out.print("\nBefore Sorting Functionality: The Arrival time List stored in array after sorting array values is :");
			for (int x: timeBeforeSorting )
			    System.out.print(x + ",");
		
			*/
				try
				{
					Thread.sleep(4000); 
				driver.findElement(By.xpath(".//*[@id='btn']")).click();
				
				
				System.out.println ("\nSort by Arrival option availalbe");
					System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
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
	 				
	 				System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
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
				Thread.sleep(2000); 
				
				try
				{
				Thread.sleep(2000); 
				//driver.findElement(By.xpath(".//*[@id='sortByMenu4']/p[3]/a")).click();
				driver.findElement(By.xpath(".//*[@id='arrOutSortDivEtoL']")).click();
				System.out.println ("\n'Return- earlier to later' option availalbe");
				
				System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
				excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking the presence of Return- earlier to later option",
						"", true,
						"", "Return- earlier to later option available",
						"Return- earlier to later option Should be available");
				}
				catch (Exception E2)
				{
					System.out.println("'Return- earlier to laterr' option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of 'Return- earlier to later option",
							"", true,
					"", "'Return- earlier to later' option NOT availalbe",
						"'Return- earlier to later' option Should be available");
	 				Assert.assertFalse(true,"'Higher to Lower' option NOT availalbe");	
				}
				Thread.sleep(3000);
				 //List<WebElement> totalProductsinFirstPageafterSortingbyDepartreEarlierToLater =  driver.findElements(By.xpath(".//*[@id='example']//*[@class='prdouct_develop_grp']"));
				
				//System.out.println ("Total products in 1st page after Sorting by Price is : "+ totalProductsinFirstPageafterSortingbyDepartreEarlierToLater.size() );
				
				//int prodSize= totalProductsinFirstPage.size();
				
				Thread.sleep(2000); 
				List<WebElement> totalProductsInPageAfterSorting =  driver.findElements(By.xpath(".//*[@id='totPrdtSizeDiv']"));
				System.out.println("Number of products in 1st page is: "+ totalProductsInPageAfterSorting.size()+"\n");
				
				System.out.println("Clicked on Sort by Arrival-Earlier to Later option");
				// storing time of all products in an array after 'sort by Arrival' action
				int countAfterSort;
				int newTimeAfterSorting;
				int arrayIndexAfterSorting=0;
				//Integer[] timeAfterSorting = new Integer[totalProductsInPageBeforeSorting.size()];
				Integer[] timeAfterSorting = new Integer[3];
				int page_number2=0;
				//for(page=1; page<= TotalPages; ++page) for all pages
				for(page=1; page<= 1; ++page) //for only first page
				{
					
					//clicking on next page
					
										
					//clicking on next page
						
						if (page <=3)
						{
						page_number2 = page + 2 ;

						driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number2+"]")).click();
						}
						else
						{
							driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).click();
							
						}
						
						System.out.println("\npagenumber is: " +page);
					
					/*
					int page_number;
					page_number = page + 2 ;
					driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number+"]")).click();
					*/
					
				
					
					//for(count=1; count<= totalProductsInPageAfterSorting.size(); ++count) //to run in entire page
						for(count=1; count<= 3; ++count) //checking only for first 3 products in first page
					{	
					//WebElement arrivalTime= driver.findElement(By.xpath("(((.//*[@id='example']//*[@class='prdouct_develop_grp'])["+count+"])//*[starts-with(@class,'arrvalSlice')])[2]"));
					WebElement arrivalTime= driver.findElement(By.xpath("((.//*[@id='listView1'])["+count+"]//div[@class='travelTime'])[2]"));
					System.out.println("Arrival Date and time of "+count+"th product in "+page+" th page is: " + arrivalTime.getText());	
			 	 //--------------------------------------------need fix
					String timeWithoutDate = arrivalTime.getText().substring(0,5);
			 		System.out.println("timeWithoutDate of "+count+"th product is: " + timeWithoutDate);	
			 		String timeWithoutColumn= timeWithoutDate.replace(":", "");
			 		System.out.println("timeWithoutColumn of "+count+"th product is: " + timeWithoutColumn);	
	 				
	 				newArrivalTime= Integer.parseInt(timeWithoutColumn.trim()); 
					timeAfterSorting[arrayIndexAfterSorting]=newArrivalTime;
					arrayIndexAfterSorting = arrayIndexAfterSorting + 1; 
					Thread.sleep(2000);
					}
					
				}
				arrayIndexAfterSorting = arrayIndexAfterSorting - 1;
				
				//trying to print each values in an array
				
				System.out.print("\nPrinting the time before Java Sorting)");
				// for (int arraycount=0;arraycount<=totalProductsInPageBeforeSorting.size()-1;++arraycount)
				 for (int arraycount=0;arraycount<=2;++arraycount)
				{
					System.out.print(timeAfterSorting[arraycount]+",");
				}
				 System.out.print("\n size of array before java sorting: "+ timeAfterSorting.length);
				/*
				System.out.print("\n The time List stored in array is (Printing again):");
				
				for (int x: timeAfterSorting )
				    System.out.print(x + ",");
				    */
				
				//trying to sort higher to Lower in the above array 	
				 System.out.print("\n Creating a copy of the array");
				 // Integer[] integerArray = new Integer[totalProductsInPageBeforeSorting.size()];
				 Integer[] integerArray = new Integer[3];
				// for (int count4=0; count4<=totalProductsInPageBeforeSorting.size()-1; ++count4)
				for (int count4=0; count4<=2; ++count4)
				 {
					 integerArray[count4]=  new Integer(timeAfterSorting[count4]);
				}
				 
				 System.out.print("\n size of array in new copy: "+ integerArray.length);
				 /*
				 Arrays.sort(integerArray, Collections.reverseOrder());
				 System.out.print("\n After Java sorting in Descending order :");
					
					for (int x: integerArray )
					    System.out.print(x + ",");
					    */
				 System.out.print("\n Doing ascending sorting using javan fn");	
				 Arrays.sort(integerArray);
				 
				 System.out.println("After Java sorting in Descending order :");
				 for (int abcd : integerArray) {
				 	System.out.println(abcd);
				 }
				
				 System.out.print("\n size of array in new copy after sorting by Java fn: "+ integerArray.length);
				 
				
				//System.out.print("\nComparing the array before and after sorting functionality");
				
				// Comparing the two arrays
				 
				 System.out.print("\n Printing both arrays again");
				 
				 
				 System.out.print("\nBefore Java Sort:");
				//	for (int arraycount=0;arraycount<=totalProductsInPageBeforeSorting.size()-1;++arraycount)
					for (int arraycount=0;arraycount<=2;++arraycount)
					{
						System.out.print(timeAfterSorting[arraycount]+",");
					}
					
					
					System.out.print("\nAfter  Java Sort:");
				
				// for (int arraycount2=0;arraycount2<=totalProductsInPageBeforeSorting.size()-1;++arraycount2)
				 for (int arraycount2=0;arraycount2<=2;++arraycount2)
				{
					System.out.print(integerArray[arraycount2]+",");
				}
				/*
					 for (int abcd : integerArray) {
					 	System.out.println(abcd);
					 }
					 */
							
		        if (Arrays.equals(timeAfterSorting, integerArray))
		        {
		        	System.out.println ("\nThe flights are sorted based on Arrival time- Higher to Lower");
					System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLaterin reports/xls");
					excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking whether products sorted based on time",
							"", true,
							"", "Products sorted based on time",
							"Products should be sorted based on time");
		        }
		        else
		        {
		        	System.out.println("\ncalling insertFailedData() fn from ExcelReadWrite");
		        	System.out.println ("\nThe flights are NOT sorted based on time- Lower to Higher");
	 				System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking whether products sorted based on Arrival time",
							"", true,
					"", "Products NOT sorted based on Time",
						"Products should be sorted based on Time");
	 				Assert.assertFalse(true,"Products should be sorted based on Time");
	 				
		        }
				
		}
										
		
			catch (Exception SortbyArrivalError)
			{
				System.out.println ("\nSome issues found while trying to sort by Arrival: " +SortbyArrivalError);
			
 				
 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
 				
 				System.out.println("\nUpdating Report VerifySortByArrival_ReturnEarlierToLater.xls in reports/xls");
 				excelreadwrite.insertFailedData(currentTestName,
 						commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking whether products sorted based on Return- earlier to later",
						"", true,
				"", "Some issues found while trying to sort by Arrival",
					"Products should be sorted based on Return- earlier to later");
 				Assert.assertFalse(true,"Products should be sorted based on Return- earlier to later");
 				
			}
		}
		//----------------
	/*
		catch (Exception SortbyArrivalError2)
		{
			System.out.println ("\nSome issues found while trying to sort by Arrival: " +SortbyArrivalError2);
		}
			*/
}



		
		
			
		





