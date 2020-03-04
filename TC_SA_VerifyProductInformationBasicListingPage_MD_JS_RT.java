//package his_NEW;
package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.IOException;


//verify Preferred Airline And Fares
/**
 * To verify VerifyProductInformationBasicListingPage in japan store
 *
 * Search Criteria: 
 *
 * ToCity1	FromDate	ToDate   	NoOfAdults	NoOfChild	NoOfInfant	PreferredAirline	fromCity
 * BKK	    2015/10/01	2015/10/05	2	        2	        1	        KMÂ -Â ãƒžãƒ«ã‚¿èˆªç©º	           æ�?±äº¬
 *
 
 */
 
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;


//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class TC_SA_VerifyProductInformationBasicListingPage_MD_JS_RT extends DriverSetup {
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
	public WebDriverWait MyWaitVar60Sec;
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
		MyWaitVar60Sec = new WebDriverWait(driver,60);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		

	}
	
		
	@DataProvider(name = "TC_SA_VerifyProductInformationBasicListingPage_MD_JS_RT")
	public Object[][] createData2() throws Exception {
	         		
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
	
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_VerifyProductInformationBasicListingPage_MD_JS_RT");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_VerifyProductInformationBasicListingPage_MD_JS_RT")
	public void ProductInfoBasicListingStore(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults, String NoOfChild,
			String NoOfInfant, String fromCity, String ToCity2) throws InterruptedException,
			IOException {

		try {
			String url = getTestURL();
			System.out.println("url is " + url);

			Loginpage.login(url);

			MyWaitVar30Sec.until(ExpectedConditions
					.visibilityOfElementLocated(By
							.xpath(".//*[@id='menu']/ul/li[1]/a"))); // waiting
																		// till
																		// Airshopping
																		// link
																		// is
																		// shown
																		// in
																		// page

			driver.findElement(By.xpath(".//*[@id='menu']/ul/li[1]/a")).click();
			System.out.println("\nAirshopping page link clicked");
			Thread.sleep(4000);

			commonUtility.switchToThisWin_WithTitle(driver.getTitle(), driver);

			System.out.println("CURRENT WINDOW IS " + driver.getTitle());
			MyWaitVar30Sec.until(ExpectedConditions
					.visibilityOfElementLocated(By
							.xpath(".//*[@id='originSId']"))); // waiting till
																// Search page
																// is shown in
																// page

			// Checking whether airshopping page displayed correctly or not

			String actualTitleairshopping = driver.getTitle();
			System.out.println("Title of launched page is : "
					+ actualTitleairshopping);

			String expectedTitleairshopping = "H.I.S. Online Flight Reservation Site";
			if (actualTitleairshopping.toLowerCase().trim().contains(expectedTitleairshopping.toLowerCase().trim())) 
			{
				System.out.println("\nSearch result page came");
				System.out.println("----------------------------------");
				System.out.println("\nUpdating Report VerifyProductInformationBasicListingPageJS.xls in reports/xls");
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
				System.out.println("\nUpdating Report VerifyProductInformationBasicListingPageJS.xls in reports/xls");
				String errorText = driver.findElement(By.xpath(".//*[@id='errorID']")).getText();
				excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "Sorting module",
						"Checking for result page",
						"", true, "", "Search page result NOT shown."
								+ errorText, "Search page result should be shown");
				Assert.assertFalse(true,"H.I.S. Flight Search Results is not displayed");
			}
			// trying to take screen shot of airshoppingPage
			try {
				// take screenshot and save it in a file
				File screenshotAirShoppingPage = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);

				// copy the file to the required path
				FileUtils
						.copyFile(
								screenshotAirShoppingPage,
								new File(
										"D:\\VinilSelenium\\Selenium\\ScreenShots\\AirShoppingPage.jpeg"));

			} catch (Exception screenShotError) {
				// if it fails to take screenshot then this block will execute
				System.out.println("Failure to take screenshot "
						+ screenShotError);
			}

			System.out.println("Going to click RT option");

			System.out.println("page refreshed");

			System.out.println("RT search option selected by default");

			driver.findElement(By.xpath(".//*[@id='originSId']")).clear();
			driver.findElement(By.id("originSId")).sendKeys(fromCity);
			Thread.sleep(2000);
			driver.findElement(By.xpath("html/body/ul[1]/li[1]/a")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(".//*[@id='destinationSId']")).clear();
			driver.findElement(By.xpath(".//*[@id='destinationSId']")).sendKeys(ToCity1);
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/ul[2]/li[1]/a")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='addCityButton']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='destinationSId']")).clear();
			driver.findElement(By.xpath(".//*[@id='destinationSId']")).sendKeys(ToCity2);
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/ul[2]/li[1]/a")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(".//*[@id='addCityButton']")).click();
			Thread.sleep(2000);
			
			driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).clear();
			driver.findElement(By.xpath(".//*[@id='alternateSfrom']"))
					.sendKeys(Depart1);
			driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).clear();
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).sendKeys(
					Depart2);
			driver.findElement(By.xpath(".//*[@id='prfrdRetrnTimeId']"))
					.click();

			Thread.sleep(1000);
			// Selecting passenger counts (Dropdown list)
			Select adultCount = new Select(driver.findElement(By
					.name("fareSearchVO.noOfAdults")));
			adultCount.selectByVisibleText(NoOfAdults);
			Thread.sleep(1000);
			Select childCount = new Select(driver.findElement(By
					.name("fareSearchVO.noOfChild")));
			childCount.selectByVisibleText(NoOfChild);
			Thread.sleep(1000);
			// waittimeShort() ;
			Select infantCount = new Select(driver.findElement(By
					.name("fareSearchVO.noOfInfants")));
			infantCount.selectByVisibleText(NoOfInfant);
			Thread.sleep(1000);

			System.out.println("Search criteria entered");
			// waittimeMedium();
			Thread.sleep(2000);
			// Clicking on Search button
			driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']"))
					.click();
			System.out.println("Search button clicked");

			try {
				MyWaitVar5Sec
						.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath(".//*[@id='fareProductSearchForm.errors']")));
				String errorMessage = driver.findElement(
						By.xpath(".//*[@id='fareProductSearchForm.errors']"))
						.getText();
				System.out.println("Error Message: " + errorMessage);

				excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(), "Shopping page",
						"Checking whether search is succssfull", "", true, "",
						errorMessage,
						"Products should be listed in Shopping page");
				Assert.assertFalse(true,
						"In Fare matrix display actual text is not valid");
				// continue;
			} catch (Exception e01) {
				try {
					// MyWaitVar5Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/center/h1/span[2]")));
					String errorMessage2 = driver.findElement(
							By.xpath("html/body/center/h1/span[2]")).getText();
					System.out.println("Error Message: " + errorMessage2);

					excelreadwrite.insertFailedData(currentTestName,
							commonUtility.getcurrentDateTime(),
							"Shopping page",
							"Checking whether Search is succssfull", "", true,
							"", errorMessage2,
							"Products should be listed in Shopping page");
					Assert.assertFalse(true,
							"In Fare matrix display actual text is not valid");
				} catch (Exception e02) {
					System.out.println("Search completed");

				}
			}

			

			String actualTitlebasiclistingpage = driver.getTitle();
			System.out.println("Webpage Title of newly lanched page is : "
					+ actualTitlebasiclistingpage);

			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Shopping page",
					"Checking whether products are listed", "", true, "",
					"Products listed in shopping page",
					"Products should be listed in shopping page");

			Thread.sleep(1000);
			System.out.println("Done till here");

			if (driver.findElements(By.id("example")).isEmpty()) {
				System.out.println("No products returned");
				excelreadwrite.insertFailedData(currentTestName,
						commonUtility.getcurrentDateTime(),
						"Basic Listing page", "Checking for products", "",
						true, "", "No products found.",
						"Products should be listed");
				Assert.assertFalse(true, "Products are not displayed");
				return;
			}

			else {
				productCount = Integer.parseInt(driver.findElement(
						By.xpath(".//*[@id='productSpanId']")).getText());
				System.out
						.println("Total Number of Products returned in shopping page: "
								+ productCount);
				excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(),
						"Basic Listing page", "Checking for products", "",
						true, "", "" + "Products found.",
						"Products should be listed");
			}


			// List<WebElement> totalProductsinFirstPage =
			// driver.findElements(By.xpath(".//*[@id='example']//*[@class='prdouct_develop_grp']"));
			List<WebElement> totalProductsinFirstPage = driver.findElements(By
					.className("highlight_row_products"));
			System.out.println("Number of products in 1st page is : "
					+ totalProductsinFirstPage.size());
			int c = (totalProductsinFirstPage.size() * 4) - 1;

			System.out.println("\nVerifying the Price of each product'");
			System.out.println("*************************************\n");

			int priceCount;
			for (priceCount = 3; priceCount <= c;) {
				
				Thread.sleep(3000);
				WebElement priceDisplayed = driver
						.findElement(By
								.xpath("/html/body/div[1]/div[5]/div[3]/div[1]/div/div/form/div[3]/div/div[2]/div["+priceCount+"]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[1]/h4/span"));
				String priceWithoutComma = priceDisplayed.getText().replace(
						",", "");
				System.out.println("Price of th product is: "
						+ priceWithoutComma);
				
				if (priceWithoutComma.isEmpty()) {
					System.out.println("Price is not present for " + priceCount
							+ "th product");
					excelreadwrite.insertFailedData(currentTestName,
							commonUtility.getcurrentDateTime(),
							"Basic Listing Page", "Checking for result page",
							"", true, "", "Price is not present for "
									+ priceCount + "th product" + "error",
							"Price should present for all products");
					Assert.assertFalse(true,
							"Price should present for all products");
					break;
				}
				priceCount = priceCount + 4;
			}
				System.out.println("priceCount" + priceCount);
				System.out.println("totalProductsinFirstPage.size()"
						+ totalProductsinFirstPage.size());

				if (priceCount > totalProductsinFirstPage.size()) {
					System.out.println("Price is present for all products");
					excelreadwrite.insertData(currentTestName,
							commonUtility.getcurrentDateTime(),
							"Basic Listing Page",
							"Checking for Price field of product", "", true,
							"", "Price field shown",
							"Price field should be shown");
				}

				System.out.println ("\nVerifying the Airline of each product'");
				System.out.println ("*************************************\n");
						
				int airlineCount;
				
						for(airlineCount=3; airlineCount<= c; ) 
					 	{
							System.out.println("\nAirlines of th Product are:");
							WebElement airlineDisplayed = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[3]/div[1]/div/div/form/div[3]/div/div[2]/div["+airlineCount+"]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[4]/div/p"));
							
							
							System.out.println("Airline Name: " +airlineDisplayed.getText());
							
							
							  
							  if (airlineDisplayed.getText().isEmpty())
								{
								System.out.println("Airline is not present for "+airlineCount+"th product");	
								excelreadwrite.insertFailedData(currentTestName,
										commonUtility.getcurrentDateTime(), "Basic Listing Page",
										"Checking for Airline Field",
										"", true, "", "Airline is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
												+ "error", "Airline should present for all products except Opaque");
								Assert.assertFalse(true,"Airline should present for all products except Opaque");
								
								 }
							  
							  airlineCount = airlineCount+4;
					 	}
						
						System.out.println ("\nVerifying the Origin of each Airline'");
						System.out.println ("*************************************\n");
							
						int flag = 0;
						for(airlineCount=3; airlineCount<= c; ) 
							 	{
									System.out.println("\nOrigin of th Product are:");
									WebElement airlineFromDisplayed = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[3]/div[1]/div/div/form/div[3]/div/div[2]/div["+airlineCount+"]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[9]/dl/dt/label"));
									
									
									System.out.println("Origin: " +airlineFromDisplayed.getText());	
									System.out.println("Expected Origin: " +fromCity);
									
									 if (airlineFromDisplayed.getText().isEmpty())
										{
										System.out.println("Origin is not present for "+airlineCount+"th product");	
										excelreadwrite.insertFailedData(currentTestName,
												commonUtility.getcurrentDateTime(), "Basic Listing Page",
												"Checking for Origin Field",
												"", true, "", "Origin is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
														+ "error", "Origin should present for all products except Opaque");
										Assert.assertFalse(true,"Origin should present for all products except Opaque");
										
										 }
									 else
									 {
									
										if(airlineFromDisplayed.getText().equals(fromCity))
										{
											System.out.println("Actual and Expected Origins are same");
										}
										else
										{
											System.out.println("Actual and Expected Origins are not same");
											flag = 1;
											
										}
									  
									 }
									  
									  airlineCount = airlineCount+4;
							 	}
						if ( flag == 0)
						{
							excelreadwrite.insertData(currentTestName,
									commonUtility.getcurrentDateTime(), "Basic Listing Page",
									"Checking for Origins field of each product",
									"", true, "", "Origins field check done for all products",
													 "Origins field should be shown except Opaque");
						}
						else if (flag == 1)
						{
							excelreadwrite.insertFailedData(currentTestName,
									commonUtility.getcurrentDateTime(), "Basic Listing Page",
									"Checking for Origin Field",
									"", true, "", "Origin is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
											+ "error", "Origin should Same for the products");
							Assert.assertFalse(true,"Origin should Same for the products");
						}
						System.out.println ("\nVerifying the Destination of each Airline'");
						System.out.println ("*************************************\n");
								
						for(airlineCount=3; airlineCount<= c; ) 
							 	{
									System.out.println("\nDestination of th Product are:");
									WebElement airlineToDisplayed = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[3]/div[1]/div/div/form/div[3]/div/div[2]/div["+airlineCount+"]/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[10]/dl/dt/label"));
									
									
									System.out.println("Destination: " +airlineToDisplayed.getText());	
									System.out.println("Expected Destinations are : " +ToCity1+" and "+ToCity2);
									if(airlineToDisplayed.getText().equals(ToCity1))
									{
										System.out.println("Actual and Expected Destination are same");
									}
									else if(airlineToDisplayed.getText().equals(ToCity2))
									{
										System.out.println("Actual and Expected Destination are same");
										
									}
									else
									{
										System.out.println("Actual and Expected Destination are not same");
										
									}
									  
									  if (airlineToDisplayed.getText().isEmpty())
										{
										System.out.println("Destination is not present for "+airlineCount+"th product");	
										excelreadwrite.insertFailedData(currentTestName,
												commonUtility.getcurrentDateTime(), "Basic Listing Page",
												"Checking for Destination Field",
												"", true, "", "Destination is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
														+ "error", "Destination should present for all products except Opaque");
										Assert.assertFalse(true,"Destination should present for all products except Opaque");
										
										 }
									  
									  airlineCount = airlineCount+4;
							 	}
						
						
						
						System.out.println ("\nVerifying the Origin(Return) of each Airline'");
						System.out.println ("*************************************\n");
								
						for(airlineCount=3; airlineCount<= c; ) 
							 	{
									System.out.println("\nOrigin(Return) of th Product are:");
									WebElement airlineToDisplayed = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[3]/div/div/div/form/div[3]/div/div[2]/div["+airlineCount+"]/div/div/table/tbody/tr/td[1]/table/tbody/tr[2]/td[5]/dl/dt/label"));
									
									
									System.out.println("Origin(Return) : " +airlineToDisplayed.getText());	
									System.out.println("Expected Origin(Return) are : " +ToCity1+" and "+ToCity2);
									if(airlineToDisplayed.getText().equals(ToCity1))
									{
										System.out.println("Actual and Expected Origin(Return) are same");
									}
									else if(airlineToDisplayed.getText().equals(ToCity2))
									{
										System.out.println("Actual and Expected Origin(Return) are same");
										
									}
									else
									{
										System.out.println("Actual and Expected Origin(Return) are not same");
										
									}
									  
									  if (airlineToDisplayed.getText().isEmpty())
										{
										System.out.println("Origin(Return) is not present for "+airlineCount+"th product");	
										excelreadwrite.insertFailedData(currentTestName,
												commonUtility.getcurrentDateTime(), "Basic Listing Page",
												"Checking for Origin(Return) Field",
												"", true, "", "Origin(Return) is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
														+ "error", "Origin(Return) should present for all products except Opaque");
										Assert.assertFalse(true,"Origin(Return) should present for all products except Opaque");
										
										 }
									  
									  airlineCount = airlineCount+4;
							 	}
						
						
						
						
						//---------------------
						
						System.out.println ("\nVerifying the Destination (Return) of each Airline'");
						System.out.println ("*************************************\n");
							
						flag = 0;
						for(airlineCount=3; airlineCount<= c; ) 
							 	{
									System.out.println("\nDestination (Return) of th Product are:");
									WebElement airlineFromDisplayed = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[3]/div/div/div/form/div[3]/div/div[2]/div["+airlineCount+"]/div/div/table/tbody/tr/td[1]/table/tbody/tr[2]/td[6]/dl/dt/label"));
									
									
									System.out.println("Destination (Return): " +airlineFromDisplayed.getText());	
									System.out.println("Expected Destination (Return): " +fromCity);
									
									 if (airlineFromDisplayed.getText().isEmpty())
										{
										System.out.println("Destination (Return) is not present for "+airlineCount+"th product");	
										excelreadwrite.insertFailedData(currentTestName,
												commonUtility.getcurrentDateTime(), "Basic Listing Page",
												"Checking for Destination (Return) Field",
												"", true, "", "Destination (Return) is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
														+ "error", "Destination (Return) should present for all products except Opaque");
										Assert.assertFalse(true,"Destination (Return) should present for all products except Opaque");
										
										 }
									 else
									 {
									
										if(airlineFromDisplayed.getText().equals(fromCity))
										{
											System.out.println("Actual and Expected Destination (Return) are same");
										}
										else
										{
											System.out.println("Actual and Expected Destination (Return) are not same");
											flag = 1;
											
										}
									  
									 }
									  
									  airlineCount = airlineCount+4;
							 	}
						if ( flag == 0)
						{
							excelreadwrite.insertData(currentTestName,
									commonUtility.getcurrentDateTime(), "Basic Listing Page",
									"Checking for Destination (Return) field of each product",
									"", true, "", "Destination (Return) field check done for all products",
													 "Destination (Return) field should be shown except Opaque");
						}
						else if (flag == 1)
						{
							excelreadwrite.insertFailedData(currentTestName,
									commonUtility.getcurrentDateTime(), "Basic Listing Page",
									"Checking for Destination (Return) Field",
									"", true, "", "Destination (Return) is not present for "+airlineCount+"th product. Check whether it is an Opaque or NOT"
											+ "error", "Destination (Return) should Same for the products");
							Assert.assertFalse(true,"Destination (Return) should Same for the products");
						}
						
						//----------
						
						
						
							
				if (airlineCount > totalProductsinFirstPage.size())
				{
					System.out.println("\nAirline field check done for all products");
					excelreadwrite.insertData(currentTestName,
							commonUtility.getcurrentDateTime(), "Basic Listing Page",
							"Checking for Airline field of each product",
							"", true, "", "Airline field check done for all products",
											 "Airline field should be shown except Opaque");
				}

				
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}