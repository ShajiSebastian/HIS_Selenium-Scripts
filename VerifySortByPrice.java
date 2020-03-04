//package his_NEW;
package his_NEW;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class VerifySortByPrice extends DriverSetup {
	SearchPage SearchPage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName,priceWithoutComma;
	Xls_Read xls_Read;
	int productCount;
	int setflag = 0;
	
	@BeforeClass
	public void setup() {				
	         
		System.out.println("\ninside setup() fn (VerifySortByPrice.java)");
		System.out.println("VerifySortByPrice.java execution starts now");
		System.out.println("\nGetting current test case name (VerifySortByPrice.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (VerifySortByPrice.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "VerifySortByPrice")
	public Object[][] createData2() throws Exception {

	         
		System.out.println("inside VerifySortByPrice.java");
		System.out.println("\nTrying to to read input test data from input datasheet (VerifySortByPrice.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"VerifySortByPrice");
		return (retObjArr);
	}

	@Test(dataProvider = "VerifySortByPrice")
	public void Verifychoosedeparture(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults, String NoOfChild,
			String NoOfInfant, String fromCity)
			throws InterruptedException {
		try{
		System.out.println("\nTrying to invoke HIS page to Execute 'VerifySortByPrice' Test Case  (VerifySortByPrice.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		/*driver.get(getTestURL());*/
		
		driver.navigate().to(getTestURL());
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		Thread.sleep(5000);
		
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

	
		driver.findElement(By.id("modSearchButton")).click();
		System.out.println("\nClicked on Search button");
		System.out.println("------------------------");
		Thread.sleep(10000);
		String pageTitle = driver.getTitle().trim();
		System.out.println("Page title is: "+pageTitle);
		if (pageTitle.toLowerCase().trim().contains("H.I.S. Flight Search Results".toLowerCase().trim())) 
		{
			System.out.println("\nSearch result page came");
			System.out.println("----------------------------------");
			System.out.println("\nUpdating Report VerifySoryByPrice.xls in reports/xls");
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
			System.out.println("\nUpdating Report VerifySoryByPrice.xls in reports/xls");
			String errorText = driver.findElement(By.xpath(".//*[@id='errorID']")).getText();
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking for result page",
					"", true, "", "Search page result NOT shown."
							+ errorText, "Search page result should be shown");
			Assert.assertFalse(true,"H.I.S. Flight Search Results is not displayed");
		}
		
		System.out.println("\nback to VerifySortByPrice.java ");

		
		System.out.println ("\nBasic Listing page shown");
 		// code to check number of products returned
 		if(driver.findElements(By.id("productPageDiv")).isEmpty())
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
			
			System.out.println ("Total products in 1st page is : "+ totalProductsinFirstPage.size() );	
		
		
		try
			{
		System.out.println ("\nVerifying the Functionality 'Sort based on Price'");
		System.out.println ("*************************************************\n");
				
		// identifying the total number of pages in the basic listing page	
		int TotalPages;
		if (productCount >20)
		{
		driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[2]/div/a[7]")).click();
		Thread.sleep(2000); 
		String NumberOfPages = driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).getText();
		 TotalPages =Integer.parseInt(NumberOfPages);
		System.out.println("\nNumber of pages:" +TotalPages);
		Thread.sleep(3000);
		//clicking on << to come back to first page	
				driver.findElement(By.xpath(".//*[@id='paging']/a[1]")).click();
				Thread.sleep(2000);
		}
		else
		{
			 TotalPages = 1;
			System.out.println("\nNumber of pages:" +TotalPages);
		}
		 
	
			//Checking whether Sort by Price option available in application or not
		Thread.sleep(2000); 
				try
				{
				driver.findElement(By.id("btn")).click();
				driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/div[3]/div/div/div/ul/li[3]/span"));
				System.out.println ("\nSort by option availalbe");
					System.out.println("\nUpdating Report VerifySoryByPrice.xls in reports/xls");
					excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of Sort by Price option",
							"", true,
							"", "Sort by option available",
							"Sort by option Should be available");
				
				}
				catch (Exception e)
				{
					System.out.println("Sor by option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySoryByPrice.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of Sort by option",
							"", true,
					"", "Sor by option NOT availalbe",
						"Sort by option Should be available");
	 				Assert.assertFalse(true,
	 						"In Fare matrix display actual text is not valid");
				}
				
				Thread.sleep(2000); ;
				
				//Checking whether 'Higher to Lower' option available in application or not
				try
				{
				
				driver.findElement(By.id("priceSortDivHtoC")).click();
				
				System.out.println ("\n'Higher to Lower' option availalbe");
				System.out.println("\nUpdating Report VerifySoryByPrice.xls in reports/xls");
				excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking the presence of Higher to Lower option",
						"", true,
						"", "Higher to Lower option available",
						"Higher to Lower option Should be available");
				}
				catch (Exception E2)
				{
					System.out.println("'Higher to Lower' option NOT availalbe");
	 				
	 				System.out.println("calling insertFailedData() fn from ExcelReadWrite");
	 				
	 				System.out.println("\nUpdating Report VerifySoryByPrice.xls in reports/xls");
	 				excelreadwrite.insertFailedData(currentTestName,
	 						commonUtility.getcurrentDateTime(), "Sorting module",
							"Checking the presence of 'Higher to Lower' option",
							"", true,
					"", "'Higher to Lower' option NOT availalbe",
						"'Higher to Lower' option Should be available");
	 				Assert.assertFalse(true,"'Higher to Lower' option NOT availalbe");	
				}
				Thread.sleep(2000);
				
				// storing price of all products in an array after 'sort by price; action
				int count;
				int page;	

				int newPriceAfterSorting;
				int arrayIndexAfterSorting=0;
				Integer[] priceAfterSorting = new Integer[productCount];
			
					int page_number2=0;
					
					for(page=1; page<= TotalPages; ++page) 
					{
						
					//clicking on next page
						
						if (page <=3)
						{
						page_number2 = page + 2 ;
						driver.findElement(By.xpath(".//*[@id='paging']/a["+page_number2+"]")).click();
						System.out.println(" page_number is: " +(page_number2-2));
						}
						else
						{
							driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).click();
							System.out.println(" page_number is: " +page);
						}
						
					
					
					Thread.sleep(3000); 
					List<WebElement> totalProductsInPageAfterSorting =  driver.findElements(By.className("amount"));
					
					for(count=1; count< totalProductsInPageAfterSorting.size(); ++count) 
					{	 
						try{	
			 				 try{
			 				if (driver.findElement(By.className("amount"))!=null){
			 					//WebElement priceDisplayed = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+count+"]/div/div[3]/div[2]/div[1]/span"));
			 				
							
			 				WebElement priceDisplayed = totalProductsInPageAfterSorting.get(count).findElement(By.tagName("span"));
			 				priceWithoutComma= priceDisplayed.getText().replace(",","");
			 				}
			 				 }
			 				catch(NoSuchElementException e){
			 					WebElement priceDisplayed = driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/form/div[1]/div/div[1]/div["+count+"]/div/div[2]/div[2]/div[1]/span"));
			 					priceWithoutComma= priceDisplayed.getText().replace(",","");
			 				}
			 				
			 			    
			 				System.out.println(priceWithoutComma); 
			 				JavascriptExecutor jse = (JavascriptExecutor)driver;
							jse.executeScript("window.scrollBy(0,50)", "");
						
			 		
					System.out.println("Price of "+count+"th product in " + page+ " the page is: " +priceWithoutComma);	
					newPriceAfterSorting= Integer.parseInt(priceWithoutComma.substring(1).trim()); 
					priceAfterSorting[arrayIndexAfterSorting]=newPriceAfterSorting;
					arrayIndexAfterSorting = arrayIndexAfterSorting + 1;
					Thread.sleep(1000); 
					}
					
						catch(Exception e){
			 				 e.printStackTrace();
				}
			}
		}
									
				System.out.print("\nAfter Sorting Functionality: Sorted list is :");
				for (int x: priceAfterSorting )
				    System.out.print(x + ",");
				
				 for (int i = 0; i < priceAfterSorting.length-1; i++) {
					 System.out.println(priceAfterSorting[i]);
				        if(priceAfterSorting[i] >= priceAfterSorting[i+1]) {
				            setflag = 1;
				        }
				        else{
				        	setflag = 0;
				        }
				    }
				 
				 if(setflag == 1){
					 System.out.println ("\nThe flights are sorted based on price- Higher to Lower");
					 excelreadwrite.insertData(currentTestName,
								commonUtility.getcurrentDateTime(), "Sorting module",
									"Checking whether products sorted based on price",
									"", true,
									"", "Products sorted based on price",
									"Products should be sorted based on price");
				 }
				 else{
					 System.out.println ("\nThe flights are NOT sorted based on price- Higher to Lower");
					 excelreadwrite.insertFailedData(currentTestName,
		 						commonUtility.getcurrentDateTime(), "Sorting module",
								"Checking whether products sorted based on price",
								"", true,
						"", "Products NOT sorted based on price",
							"Products should be sorted based on price");
		 				Assert.assertFalse(true,"Products should be sorted based on price");
				 }
				   		         			
				}							

			
			catch (Exception SortPriceError)
			{
				System.out.println ("\nSome issues found while trying to sort by Price: " +SortPriceError);
			}
		
		
			
		
}
		catch(Exception e){
			e.printStackTrace();
			excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
					"Checking whether products sorted based on price",
					"", true,
			"", "Products NOT sorted based on price",
				"Products should be sorted based on price");
				Assert.assertFalse(true,"Products should be sorted based on price");
		}

}

}
