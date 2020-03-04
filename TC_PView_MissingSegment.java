package his_NEW;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;	
import java.util.Set;

import pages.his.FirstPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class TC_PView_MissingSegment extends DriverSetup {
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
	
	
	@DataProvider(name = "TC_PView_MissingSegment")
    public Object[][] createData2() throws Exception {

//                    String s2 = System.getProperty("user.dir");
                    String s2 = System.getProperty("user.dir");
                    String path = s2 + "\\src\\resources\\HIS-TestData.xls";

                    System.out.println("path :" + path);

                    Object[][] retObjArr = excelRead.getTableArray(path, "TC",
                                                    "TC_PView_MissingSegment");

                    return (retObjArr);
    }

	@Test(dataProvider = "TC_PView_MissingSegment")
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
			
			//String proCountBasicListing=driver.findElement(By.xpath("html/body/div[1]/div[6]/div/div[3]/div[1]/div[3]/div/div/p[1]/strong")).getText();
			String proCountBasicListing=driver.findElement(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]/strong")).getText();
			
			System.out.println("Product count in basic listing page1:"+proCountBasicListing);
			String proCountBasicListings=proCountBasicListing.replace(",", "").trim();
			int q=Integer.parseInt(proCountBasicListings);
			System.out.println("Product count:"+q);
			Thread.sleep(800);
			int TotalPages;
			/*if (q >20)
			{
			driver.findElement(By.xpath(".//*[@id='paging']/a[7]")).click();
			Thread.sleep(2000); 
			String NumberOfPages = driver.findElement(By.xpath(".//*[@id='paging']/a[5]")).getText();
			 TotalPages =Integer.parseInt(NumberOfPages);
			System.out.println("\nNumber of pages:" +TotalPages);
			//clicking on << to come back to first page	
					driver.findElement(By.xpath(".//*[@id='paging']/a[1]")).click();
					Thread.sleep(2000);
			
			List <WebElement> initial_count= driver.findElements(By.xpath(".//*[@class='btn btn-primary btn-lg']"));
			int count=initial_count.size();
			System.out.println("Count 1:"+count);
			}*/
			
		
	        driver.findElement(By.xpath("//div[contains(@class, 'bookBtn seatAvailability')]/button")).click();
			
			
	
			/*
			String a="Check Availability";
			//driver.findElement(By.xpath(".//*[@class='bookBtn seatAvailability']")).click();
			List<WebElement> label =driver.findElements(By.xpath(".//*[@class='btn btn-primary btn-lg']"));
			for (WebElement menu2 : label)
			{
			System.out.println(menu2.getText().trim());
			}
			for (WebElement menu2 : label) {
			if (menu2.getText().equalsIgnoreCase(a.trim())) 
			{
				driver.findElement(By.xpath(".//*[@class='btn btn-primary btn-lg']")).click();
			}
			}
					*/
			
			
			 
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			Thread.sleep(8000);
			
			excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Missing Segment", "Verify whether Missing Segment products are available", "Checking missing segment products", true, "Same as Expected", "Missing Segment products are available", "Missing segment products should be available");
			Thread.sleep(80000);
			
			String proCountBasicListing2=driver.findElement(By.xpath(".//*[@id='productPageDiv']/div[3]/div/div/p[1]/strong")).getText();
			System.out.println("Product count in basic listing page2:"+proCountBasicListing2);
			String proCountBasicListings2=proCountBasicListing2.replace(",", "").trim();
			int proCountBasicListings21=Integer.parseInt(proCountBasicListings2);
			System.out.println("Product count:"+proCountBasicListings21);
			
			int missingSegmentProductCount=proCountBasicListings21-q;
			System.out.println("Missing segment product count"+missingSegmentProductCount);
			excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Missing Segment", "Verify whether seat is available for Missing Segment products", "Checking seats", true, "Same as Expected", "No: of products returned on clicking Check seat availablity:"+missingSegmentProductCount, "Check seat availability button should be available");
		
			if(missingSegmentProductCount==0)
			{
				excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Missing Segment", "Verify whether seat is available for Missing Segment products", "Checking seats", true, "Same as Expected", "No seats is available for selected missing segment product", "Check seat availability button should be available");
			}
			
			else
			{
				excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "Missing Segment", "Verify whether seat is available for Missing Segment products", "Checking seats", true, "Same as Expected", "Seats are available for selected missing segment product", "Check seat availability button should be available");
				Assert.assertFalse(true, "Missing segment products not available");
			}
			
		}
			catch(Exception e){
				e.printStackTrace();
				excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "Missing Segment", "Verify whether Missing Segment products are available in basic listing page", "Missing Segment products not found", true, "Not Same as Expected", "Missing segment products not available", "Missing segment products should be available");
				Assert.assertFalse(true, "Missing segment products not available");
				System.out.println("fail");
			}
		
		
		
		}

	}
