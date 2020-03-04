package his_NEW;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;	
import java.util.Set;

import pages.his.FirstPage;
import pages.his.SearchPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;
import controls.ExcelRead;

public class TC_PView_Back_from_SCPage_scenario extends DriverSetup {
	private static final String String = null;
	public FirstPage fpage;
	ExcelRead excelRead;
	public ExcelReadWrite excelreadWrite;
	public CommonUtility commonUtility;
	String currenttestName;
	public Xls_Read xlsRead;

	@BeforeClass
	public void setUp() throws Exception {
		currenttestName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadWrite = new ExcelReadWrite(currenttestName,driver,getBrowser(),getScrenshotfilepath());
		xlsRead = new Xls_Read(null, xpathFilePath);
		fpage = new FirstPage(driver, excelreadWrite, xlsRead);
		
	}
	
	
	@DataProvider(name = "TC_PView_Back_to_browser_scenario")
    public Object[][] createData2() throws Exception {

//                    String s2 = System.getProperty("user.dir");
                    String s2 = System.getProperty("user.dir");
                    String path = s2 + "\\src\\resources\\HIS-TestData.xls";

                    System.out.println("path :" + path);

                    Object[][] retObjArr = excelRead.getTableArray(path, "TC",
                                                    "TC_PView_Back_to_browser_scenario");

                    return (retObjArr);
    }

	@Test(dataProvider = "TC_PView_Back_to_browser_scenario")
	public void test(String origin,String destination, String departuredate, String arrivaldate, String adult,String child,String infant)
	{
		try{
			driver.get(getTestURL());
			
			driver.findElement(By.id("fromCombo")).click();
			List<WebElement> fromCityOptions = driver.findElements(By.xpath(".//*[@id='fromCombo']//option"));
			System.out.println(fromCityOptions);
			for (WebElement menu : fromCityOptions)
			{
			System.out.println(menu.getText().trim());
			}
			for (WebElement menu : fromCityOptions) {
			if (menu.getText().trim().equalsIgnoreCase(origin.trim())) {			
						System.out.println("Input From City/Airport: "+origin.trim());
						System.out.println("Selected From City/Airport: "+menu.getText().trim());				
						Thread.sleep(1000);
						Actions action = new Actions(driver);
						action.sendKeys(menu, Keys.ENTER).build().perform();
						Thread.sleep(1000);
					  break;
					}
				}
			    driver.findElement(By.id("destinationSId")).clear();
				driver.findElement(By.id("destinationSId")).sendKeys(destination);
				driver.findElement(By.xpath("html/body/ul[2]/li[1]")).click();
				Thread.sleep(800);
				driver.findElement(By.id("alternateSfrom")).sendKeys(departuredate);
				Thread.sleep(800);
				driver.findElement(By.id("alternateSto")).sendKeys(arrivaldate);
				Thread.sleep(800);
				driver.findElement(By.name("fareSearchVO.noOfAdults")).sendKeys(adult);
				Thread.sleep(800);
				driver.findElement(By.name("fareSearchVO.noOfChild")).sendKeys(child);
				Thread.sleep(800);
				driver.findElement(By.name("fareSearchVO.noOfInfants")).sendKeys(infant);
				Thread.sleep(800);
				driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
			    Thread.sleep(20000);
				int adults =Integer.parseInt(adult);
				int children =Integer.parseInt(child);
				int infants =Integer.parseInt(infant);
				System.out.println("Adult count:"+adults);
				System.out.println("Child count:"+children);
				System.out.println("Infant count:"+infants);
			
			String proCountBasicListing=driver.findElement(By.xpath(".//*[@class='sortPaxDetails hidden-xs']//strong")).getText();
			System.out.println("Product count in basic listing page1:"+proCountBasicListing);
			String proCountBasicListings=proCountBasicListing.replace(",", "").trim();
			int q=Integer.parseInt(proCountBasicListings);
			System.out.println("Product count:"+q);
			Thread.sleep(800);
			excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Entering search criteria", "Performing search", true, "Same as Expected", "No: of products displayed:"+q, "Products should be available");
			
		Boolean isBookButtonPresent=  driver.findElements(By.xpath(".//*[@class='btn btn-primary btn-lg']")).size()>0;
	       
		if (isBookButtonPresent== true)
		{
		
		driver.findElement(By.xpath(".//*[@class='btn btn-primary btn-lg']")).click();
	  
	      
		//wait until page is loaded
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
		    public Boolean apply(WebDriver d) {
		        return d.getCurrentUrl().startsWith("http://e-kensho.his-j.com");
		    }
		});
		
		Thread.sleep(2000);
		
		 String SCUrl= driver.getCurrentUrl();
		 System.out.println("SC Url:"+SCUrl);
		 excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Clicking on book button", "Navigating to SC Page", true, "Same as Expected", "SC page is available", "SC page should be available");
		 
		 System.out.println("baseURL: "+baseURL);	 
		String result = baseURL.split("airfaresearch")[0];
	
		
		System.out.println("Splitted string:"+result);	
		 	 
		 String sessionId=SearchPage.split(SCUrl)[0];
		 System.out.println("SessionID:"+sessionId);
		 String productID=SearchPage.split(SCUrl)[1];
		 System.out.println("ProductID:"+productID);
		  
	   String listingPageNavURL=  result.concat("navigate.htm?to_page=2&sessionId=").concat(sessionId).concat("&uniqueProductId=").concat(productID).concat("&APIResult=0");
	   System.out.println("Nav URL:"+listingPageNavURL);
	   driver.get(listingPageNavURL);
	   Thread.sleep(20000);
	   String proCountBasicListing2=driver.findElement(By.xpath(".//*[@class='sortPaxDetails hidden-xs']//strong")).getText();
	   System.out.println("Product count after navigating back from SC page:"+proCountBasicListing2);
	   excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Invoking navigation back URL to listing page", "Checking for products in listing page", true, "Same as Expected", "No: of products displayed:"+proCountBasicListing2, "Products should be available");
	   	   
	    String searchPageNavURL=  result.concat("navigate.htm?to_page=1&sessionId=").concat(sessionId).concat("&uniqueProductId=").concat(productID).concat("&APIResult=0");
		Thread.sleep(8000);
		driver.get(searchPageNavURL);
		Thread.sleep(8000);
		driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
		Thread.sleep(20000);
		String proCountBasicListing3=driver.findElement(By.xpath(".//*[@class='sortPaxDetails hidden-xs']//strong")).getText();
	    System.out.println("Product count in basic listing page1:"+proCountBasicListing3);
		String proCountBasicListings3=proCountBasicListing3.replace(",", "").trim();
		int q3=Integer.parseInt(proCountBasicListings3);
		System.out.println("Product count:"+q3);
		Thread.sleep(800);
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Navigating back to search page", "Performing search and Checking for products", true, "Same as Expected", "No: of products returned:"+q3, "Products should be available");
		Boolean isBookButtonPresent2=  driver.findElements(By.xpath(".//*[@class='btn btn-primary btn-lg']")).size()>0;
	       
	   if (isBookButtonPresent2== true)
			{
			
			driver.findElement(By.xpath(".//*[@class='btn btn-primary btn-lg']")).click();
		  
		   
		   
			//wait until page is loaded
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			    public Boolean apply(WebDriver d) {
			        return d.getCurrentUrl().startsWith("http://e-kensho.his-j.com");
			    }
			});
			
			Thread.sleep(2000);
			
			 String SCUrl2= driver.getCurrentUrl();
			 System.out.println("SC Url2:"+SCUrl2);
			 excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Clicking on book button", "Navigating to SC Page", true, "Same as Expected", "SC page is available", "SC page should be available");
		 
		}
			else
			{
				System.out.println("Fail");
			}
		}
		
		}
		
				
		
			catch(Exception e){
				e.printStackTrace();
				excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Verify whether Back From SC page is working or not ", "Back From SC page failed", true, "Not Same as Expected", "Back From SC page failed", "Back From SC page should be working");
				Assert.assertFalse(true, "Back From SC page failed");
				System.out.println("fail");
			}
		
		
		
		}

	}
