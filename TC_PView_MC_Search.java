package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import pages.his.HISCommonFns;
import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;

import common.Xls_Read;

import controls.ExcelRead;

public class TC_PView_MC_Search  extends DriverSetup {
	SearchPage SearchPage;
	LoginPage Loginpage;
	HISCommonFns commonpageHIS;
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
	         
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);
		Loginpage = new LoginPage(driver, excelreadwrite, xls_Read);
		commonpageHIS = new HISCommonFns(driver, excelreadwrite, xls_Read);
		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		

	}
	
		
	@DataProvider(name = "TC_PView_MC_Search")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_PView_MC_Search");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	
	public WebDriver getDriver() {
		System.out.println("inside getDriver() (DriverSetup.java)");
		return driver;
	}

	public void setDriver(WebDriver driver) {
		System.out.println("inside setDriver() (DriverSetup.java)");
		this.driver = driver;
	}
	
	
	@Test(dataProvider = "TC_PView_MC_Search")
	public void test(String mccount,String origin1, String dest1, String date1, String dest2,String date2,String dest3,String date3,String dest4,String date4,String dest5,String date5,String adult,String child,String infant )
			throws InterruptedException, IOException{
		try{
		String url= getTestURL();	
		System.out.println("url is: "+url);
		//The below line mainly uses to indicate that execution starts for a test data
		excelreadwrite.insertData("Execution Starts for MC search", "", "", "", "", true, "", "", "");
		System.out.println("Trying to call Common script MC Search");
		int prodcount = commonpageHIS.MCSearch(url,mccount,origin1,dest1,date1,dest2,date2,dest3,date3,dest4,date4,dest5,date5,adult,child,infant);
		System.out.println("Product count :"+prodcount);
		System.out.println("MC Common script execution completed");
		
		if ( prodcount >0 )
		{
			excelreadwrite.insertData("TC_PView_MC_Search", "Japan Online", "MC Search", "Verify whether MC Search working fine", "MC Search is working", true, "Same as Expected", "MC Search gave results", "MC Search should give products");
			excelreadwrite.insertData("TC_PView_MC_Search", "Japan Online", "MC Search", "Checking number of products returned", "MC Search is working", true, "Same as Expected", "Number of MC product: "+prodcount, "MC Search should give products");
		
		System.out.println("Trying to call Common script MC Modify Search");
		int Modifyprodcount = commonpageHIS.MCModifySearch();

		
		if ( Modifyprodcount >0 )
		{
			excelreadwrite.insertData("TC_PView_MC_Search", "Japan Online", "MC Modify Search", "Verify whether MC Modify Search working fine", "MC Modify Search is working", true, "Same as Expected", "MC Modify Search gave results", "MC Modify Search should give products");
			excelreadwrite.insertData("TC_PView_MC_Search", "Japan Online", "MC Modify Search", "Checking number of products returned", "MC Modify Search is working", true, "Same as Expected", "Number of MC product: "+Modifyprodcount, "MC Modify Search should give products");
		}		
		
		}
		
		else
		{
			excelreadwrite.insertFailedData("TC_PView_MC_Search", "Japan Online", "MC Modify Search", "Verify whether MC Modify Search working fine", "MC Modify Search Fail", true, "Not Same as Expected", "MC Modify Search Fail", "MC Modify Search should give products");	
		}
		}
		
		catch(Exception e)
		{
				e.printStackTrace();
				System.out.println(e);
				excelreadwrite.insertFailedData("TC_PView_MC_Search", "Japan Online", "MC Search", "Verify whether MC Search working fine", "MC Search Fail", true, "Not Same as Expected", "MC Search Fail", "MC Search should give products");
				Assert.assertFalse(true, "MC Modify Search Fail");
				System.out.println("MC Modify Search Fail");
		}
		
		 driver.manage().deleteAllCookies();
		    Thread.sleep(8000);
		
		
		}

}
/*
	@Test(dataProvider = "TC_PView_MC_Search")
	public void ApplyHandlingFeeRule(String mccount,String origin1, String dest1, String date1, String dest2,String date2,String dest3,String date3,String dest4,String date4,String dest5,String date5,String adult,String child,String infant )
			throws InterruptedException, IOException {
		driver.get(getTestURL());
		Thread.sleep(8000);

		int x=Integer.parseInt(mccount);
		System.out.println("MCCount:"+x);
		 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Testing MC search with slices:"+x, "MC search is invoked with slices:"+x, true, "Same as Expected", "MC search with slices is invoked:"+x, "MC products  should be invoked with slices:"+x);
	    driver.findElement(By.xpath(".//*[@value='MC']")).click();
	    Thread.sleep(800);
	    //driver.findElement(By.id(".//*[@id='add_slice_block2']/a")).click();
	   //Thread.sleep(800);
	   //System.out.println("sucess");
	    if(x==2)
	    {
	    	driver.findElement(By.id("orgComboId1")).click();
	    	Thread.sleep(800);
	    	List<WebElement> fromCityOptions = driver.findElements(By.xpath(".//*[@id='orgComboId1']//option"));
			System.out.println(fromCityOptions);
			for (WebElement menu : fromCityOptions)
			{
			System.out.println(menu.getText().trim());
			}
			for (WebElement menu : fromCityOptions) {
			if (menu.getText().trim().equalsIgnoreCase(origin1.trim())) {			
						System.out.println("Input From City/Airport: "+origin1.trim());
						System.out.println("Selected From City/Airport: "+menu.getText().trim());				
						Thread.sleep(1000);
						Actions action = new Actions(driver);
						action.sendKeys(menu, Keys.ENTER).build().perform();
						Thread.sleep(1000);
					  break;
					}
				}
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Entering Origin:"+origin1, "Origin of MC search is  correct", true, "Same as Expected", "Origin of MC search is fetched from Test data", "Origin of MC search should be fetched from Test data");
			driver.findElement(By.id("destinationSId1")).clear();
			driver.findElement(By.id("destinationSId1")).sendKeys(dest1);
			Thread.sleep(800);
			driver.findElement(By.xpath("html/body/ul[3]/li[1]")).click();
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Entering First destination:"+dest1, "First destination of MC search is  correct", true, "Same as Expected", "First destination of MC search is fetched from Test data", "First destination of MC search should be fetched from Test data");
			
	        //driver.findElement(By.id("destinationSId1")).click();
			Thread.sleep(800);
			driver.findElement(By.id("altSrchFrmDate1")).click();
			driver.findElement(By.id("altSrchFrmDate1")).sendKeys(date1);
			Thread.sleep(800);
			
			
	        
	       driver.findElement(By.xpath(".//*[@id='destComboId2']")).click();
			Thread.sleep(800);
			List<WebElement> fromCityOptions1 = driver.findElements(By.xpath(".//*[@id='destComboId2']//option"));
			System.out.println(fromCityOptions1);
			for (WebElement menu : fromCityOptions1)
			{
			System.out.println(menu.getText().trim());
			}
			for (WebElement menu : fromCityOptions1) {
			if (menu.getText().trim().equalsIgnoreCase(dest2.trim())) {			
						System.out.println("Input From City/Airport: "+dest2.trim());
						System.out.println("Selected From City/Airport: "+menu.getText().trim());				
						Thread.sleep(1000);
						Actions action = new Actions(driver);
						action.sendKeys(menu, Keys.ENTER).build().perform();
						Thread.sleep(1000);
					  break;
					}
				}
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Entering Arrival:"+dest2, "Arrival of MC search is  correct", true, "Same as Expected", "Arrival of MC search is fetched from Test data", "Arrival of MC search should be fetched from Test data");
			driver.findElement(By.id("altSrchFrmDate2")).click();
			driver.findElement(By.id("altSrchFrmDate2")).sendKeys(date3);
	        Thread.sleep(800);
	        
	        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
	      
	        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Searching for products", "Performing search", true, "Same as Expected", "MC Search is working", "MC Search should be working");
	        Thread.sleep(20000);
	        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
	        Thread.sleep(20000);
	        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Validating MC search with 2 slices", "MC search with 2 slices is correct", true, "Same as Expected", "MC products with 2 slices is displayed in basic listing page", "MC products with 2 slices should be displayed in basic listing page");
	     
	        Boolean isPresent= driver.findElements(By.xpath(" html/body/div[1]/div[6]/div/div[3]/div[1]/div[3]/div/div/p[1]/strong")).size()>0;
	        
	        if (isPresent==true)
	        {
	        String prodcount=driver.findElement(By.xpath(" html/body/div[1]/div[6]/div/div[3]/div[1]/div[3]/div/div/p[1]/strong")).getText();
	     System.out.println("Product count:"+prodcount);
	     excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Landing page of MC search with 2 slices", "MC search with 2 slices is correct", true, "Same as Expected", "Total products displayed:"+prodcount, "MC products with 2 slices should be displayed in basic listing page");
	        }
	        else
	        {
	        	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Landing page of MC search with 2 slices", "MC search with 2 slices is correct", true, "Same as Expected", "Total products displayed:0", "MC products with 2 slices should be displayed in basic listing page");
	        }
	    }
	    
	    if(x==3)
	    {
	    	//WebElement element = driver.findElement(By.xpath(".//*[@class='btn btn-default btn-success btn-xs pull-right']"));
		 //fpage.safeJavaScriptClick(element, driver);
	    	
	    	driver.findElement(By.xpath(".//*[@class='btn btn-default btn-success btn-xs pull-right']")).click();
		    driver.findElement(By.id("orgComboId1")).click();
			List<WebElement> fromCityOptions = driver.findElements(By.xpath(".//*[@id='orgComboId1']//option"));
			System.out.println(fromCityOptions);
			for (WebElement menu : fromCityOptions)
			{
			System.out.println(menu.getText().trim());
			}
			for (WebElement menu : fromCityOptions) {
			if (menu.getText().trim().equalsIgnoreCase(origin1.trim())) {			
						System.out.println("Input From City/Airport: "+origin1.trim());
						System.out.println("Selected From City/Airport: "+menu.getText().trim());				
						Thread.sleep(1000);
						Actions action = new Actions(driver);
						action.sendKeys(menu, Keys.ENTER).build().perform();
						Thread.sleep(1000);
					  break;
					}
				}
			driver.findElement(By.id("destinationSId1")).clear();
			driver.findElement(By.id("destinationSId1")).sendKeys(dest1);
			Thread.sleep(800);
			driver.findElement(By.xpath("html/body/ul[3]/li[1]")).click();
			Thread.sleep(800);
			driver.findElement(By.id("altSrchFrmDate1")).click();
			driver.findElement(By.id("altSrchFrmDate1")).sendKeys(date1);
		    Thread.sleep(800);
			
			driver.findElement(By.id("destinationSId2")).clear();
			driver.findElement(By.id("destinationSId2")).click();
			driver.findElement(By.id("destinationSId2")).sendKeys(dest2);
			Thread.sleep(800);
			driver.findElement(By.xpath("html/body/ul[7]/li[1]")).click();
			Thread.sleep(800);
			driver.findElement(By.id("altSrchFrmDate2")).click();
			driver.findElement(By.id("altSrchFrmDate2")).clear();
			driver.findElement(By.id("altSrchFrmDate2")).sendKeys(date2);
	        Thread.sleep(800);
	        
	       
	        
	        
	       driver.findElement(By.xpath(".//*[@id='destComboId3']")).click();
			Thread.sleep(800);
			List<WebElement> fromCityOptions1 = driver.findElements(By.xpath(".//*[@id='destComboId3']//option"));
			System.out.println(fromCityOptions1);
			for (WebElement menu : fromCityOptions1)
			{
			System.out.println(menu.getText().trim());
			}
			for (WebElement menu : fromCityOptions1) {
			if (menu.getText().trim().equalsIgnoreCase(dest3.trim())) {			
						System.out.println("Input From City/Airport: "+dest3.trim());
						System.out.println("Selected From City/Airport: "+menu.getText().trim());				
						Thread.sleep(1000);
						Actions action = new Actions(driver);
						action.sendKeys(menu, Keys.ENTER).build().perform();
						Thread.sleep(1000);
					  break;
					}
				}
			driver.findElement(By.id("altSrchFrmDate3")).click();
			driver.findElement(By.id("altSrchFrmDate3")).clear();
			driver.findElement(By.id("altSrchFrmDate3")).sendKeys(date3);
	        Thread.sleep(800);
	        
	        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
	        Thread.sleep(20000);
	        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
	        Thread.sleep(20000);
	        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Validating MC search with 3 slices", "MC search with 3 slices is correct", true, "Same as Expected", "MC products with 3 slices is displayed in basic listing page", "MC products with 3 slices should be displayed in basic listing page");
	    }
		
	
		
	if(x==4)
	{
		for( int i=0;i<=1;i++)
		{
		//WebElement element = driver.findElement(By.xpath(".//*[@id='add_slice_block2']/a"));
	   // fpage.safeJavaScriptClick(element, driver);
			driver.findElement(By.xpath(".//*[@class='btn btn-default btn-success btn-xs pull-right']")).click();
			Thread.sleep(800);
		}
		
		 driver.findElement(By.id("orgComboId1")).click();
	    List<WebElement> fromCityOptions = driver.findElements(By.xpath(".//*[@id='orgComboId1']//option"));
		System.out.println(fromCityOptions);
		for (WebElement menu : fromCityOptions)
		{
		System.out.println(menu.getText().trim());
		}
		for (WebElement menu : fromCityOptions) {
		if (menu.getText().trim().equalsIgnoreCase(origin1.trim())) {			
					System.out.println("Input From City/Airport: "+origin1.trim());
					System.out.println("Selected From City/Airport: "+menu.getText().trim());				
					Thread.sleep(1000);
					Actions action = new Actions(driver);
					action.sendKeys(menu, Keys.ENTER).build().perform();
					Thread.sleep(1000);
				  break;
				}
			}
		driver.findElement(By.id("destinationSId1")).clear();
		driver.findElement(By.id("destinationSId1")).sendKeys(dest1);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[3]/li[1]")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate1")).click();
		driver.findElement(By.id("altSrchFrmDate1")).sendKeys(date1);
		Thread.sleep(800);
		
		driver.findElement(By.id("destinationSId2")).clear();
		driver.findElement(By.id("destinationSId2")).sendKeys(dest2);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[7]/li[1]")).click();
		//driver.findElement(By.id("destinationSId2")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate2")).click();
		driver.findElement(By.id("altSrchFrmDate2")).clear();
		driver.findElement(By.id("altSrchFrmDate2")).sendKeys(date2);
        Thread.sleep(800);
        
        
        driver.findElement(By.id("destinationSId3")).clear();
		driver.findElement(By.id("destinationSId3")).sendKeys(dest3);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[9]/li[1]")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate3")).click();
		driver.findElement(By.id("altSrchFrmDate3")).clear();
		driver.findElement(By.id("altSrchFrmDate3")).sendKeys(date3);
        Thread.sleep(800);
        
        
       driver.findElement(By.xpath(".//*[@id='destComboId4']")).click();
		Thread.sleep(800);
		List<WebElement> fromCityOptions1 = driver.findElements(By.xpath(".//*[@id='destComboId4']//option"));
		System.out.println(fromCityOptions1);
		for (WebElement menu : fromCityOptions1)
		{
		System.out.println(menu.getText().trim());
		}
		for (WebElement menu : fromCityOptions1) {
		if (menu.getText().trim().equalsIgnoreCase(dest4.trim())) {			
					System.out.println("Input From City/Airport: "+dest4.trim());
					System.out.println("Selected From City/Airport: "+menu.getText().trim());				
					Thread.sleep(1000);
					Actions action = new Actions(driver);
					action.sendKeys(menu, Keys.ENTER).build().perform();
					Thread.sleep(1000);
				  break;
				}
			}
		driver.findElement(By.id("altSrchFrmDate4")).click();
		driver.findElement(By.id("altSrchFrmDate4")).sendKeys(date4);
        Thread.sleep(800);
        
        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
        Thread.sleep(20000);
        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
        Thread.sleep(20000);
        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Validating MC search with 4 slices", "MC search with 4 slices is correct", true, "Same as Expected", "MC products with 4 slices is displayed in basic listing page", "MC products with 4 slices should be displayed in basic listing page");
	}
	if(x==5)
	{
		for( int i=0;i<=2;i++)
		{
		//WebElement element = driver.findElement(By.xpath(".//*[@id='add_slice_block2']/a"));
	   // fpage.safeJavaScriptClick(element, driver);
			driver.findElement(By.xpath(".//*[@class='btn btn-default btn-success btn-xs pull-right']")).click();
			Thread.sleep(800);
		}
		
		driver.findElement(By.xpath(".//*[@id='orgComboId1']")).click();
		Thread.sleep(800);
		List<WebElement> fromCityOptions = driver.findElements(By.xpath(".//*[@id='orgComboId1']//option"));
		//System.out.println("helloo");
		System.out.println(fromCityOptions);
		for (WebElement menu : fromCityOptions)
		{
		System.out.println(menu.getText().trim());
		}
		for (WebElement menu : fromCityOptions) {
		if (menu.getText().trim().equalsIgnoreCase(origin1.trim())) {			
					System.out.println("Input From City/Airport: "+origin1.trim());
					System.out.println("Selected From City/Airport: "+menu.getText().trim());				
					Thread.sleep(1000);
					Actions action = new Actions(driver);
					action.sendKeys(menu, Keys.ENTER).build().perform();
					Thread.sleep(1000);
				  break;
				}
			}
		driver.findElement(By.id("destinationSId1")).clear();
		driver.findElement(By.id("destinationSId1")).sendKeys(dest1);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[3]/li[1]")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate1")).click();
		driver.findElement(By.id("altSrchFrmDate1")).sendKeys(date1);
		Thread.sleep(800);
		
		driver.findElement(By.id("destinationSId2")).clear();
		driver.findElement(By.id("destinationSId2")).sendKeys(dest2);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[7]/li[1]")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate2")).click();
		driver.findElement(By.id("altSrchFrmDate2")).clear();
		driver.findElement(By.id("altSrchFrmDate2")).sendKeys(date2);
        Thread.sleep(800);
        
        
        driver.findElement(By.id("destinationSId3")).clear();
		driver.findElement(By.id("destinationSId3")).sendKeys(dest3);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[9]/li[1]")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate3")).click();
		driver.findElement(By.id("altSrchFrmDate3")).clear();
		driver.findElement(By.id("altSrchFrmDate3")).sendKeys(date3);
        Thread.sleep(800);
        
        
        driver.findElement(By.id("destinationSId4")).clear();
		driver.findElement(By.id("destinationSId4")).sendKeys(dest4);
		Thread.sleep(800);
		driver.findElement(By.xpath("html/body/ul[11]/li[1]")).click();
		Thread.sleep(800);
		driver.findElement(By.id("altSrchFrmDate4")).click();
		driver.findElement(By.id("altSrchFrmDate4")).clear();
		driver.findElement(By.id("altSrchFrmDate4")).sendKeys(date4);
        Thread.sleep(800);
        
        driver.findElement(By.id("destinationSId4")).click();
		
		
		
		
		
		driver.findElement(By.xpath(".//*[@id='destComboId5']")).click();
		Thread.sleep(800);
		List<WebElement> fromCityOptions1 = driver.findElements(By.xpath(".//*[@id='destComboId5']//option"));
		System.out.println(fromCityOptions1);
		for (WebElement menu : fromCityOptions1)
		{
		System.out.println(menu.getText().trim());
		}
		for (WebElement menu : fromCityOptions1) {
		if (menu.getText().trim().equalsIgnoreCase(dest5.trim())) {			
					System.out.println("Input From City/Airport: "+dest5.trim());
					System.out.println("Selected From City/Airport: "+menu.getText().trim());				
					Thread.sleep(1000);
					Actions action = new Actions(driver);
					action.sendKeys(menu, Keys.ENTER).build().perform();
					Thread.sleep(1000);
				  break;
				}
			}
		driver.findElement(By.id("altSrchFrmDate5")).click();
		driver.findElement(By.id("altSrchFrmDate5")).clear();
		driver.findElement(By.id("altSrchFrmDate5")).sendKeys(date5);
        Thread.sleep(800);
        
        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
        Thread.sleep(20000);
        driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
        Thread.sleep(20000);
        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "MC search", "Validating MC search with 5 slices", "MC search with 5 slices is correct", true, "Same as Expected", "MC products with 5 slices is displayed in basic listing page", "MC products with 5 slices should be displayed in basic listing page"); 
		
	}
	    
	    driver.manage().deleteAllCookies();
	    Thread.sleep(8000);
	   
	   
	
}
}
	*/