package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class TC_SEOPage_OUMode  extends DriverSetup {
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
	static String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	private static ExtentReports extent;
	private static ExtentTest test;
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
		MyWaitVar5Sec = new WebDriverWait(driver,5);
		
		MyWaitVar60Sec = new WebDriverWait(driver,60);
		extent = new ExtentReports(System.getProperty("user.dir")+"/testReports/TestReport_"+timeStamp+".html",false);
		System.out.println("PATH : "+System.getProperty("user.dir"));
		test  = extent.startTest("SEO Page");		
	
	}	
		
	@DataProvider(name = "TC_SEOPage_OUMode")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SEOPage_OUMode");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SEOPage_OUMode")
	public void ApplyHandlingFeeRule(String countryCode, String cityCode)
		{
		
		try{
			
		
			
		String SEOUrl= testURL.concat(countryCode).concat("/").concat(cityCode).concat("/");
		driver.get(SEOUrl)	;	
		Boolean isSEOPage= driver.findElements(By.xpath(".//*[@class='highlightTxt']")).size()>0;
		if(isSEOPage==true)
		{
			System.out.println("SEO page");
			test.log(LogStatus.PASS,"Specific City Page is invoked");
		}
		
		else
		{
			System.out.println("Not SEO page");
			test.log(LogStatus.FAIL,"Specific City Page is not invoked");
			Assert.assertFalse(true,"Specific City Page is not invoked");
		}
		
		test.log(LogStatus.PASS,"Checking whether default month is having fares or not");
		String defaultMonth=driver.findElement(By.xpath(".//*[@class='tabWraper active']")).getText();
		String[] defaultLabel=defaultMonth.split("");
		int defaultLabelsize=defaultLabel.length;
		System.out.println("Size:"+defaultLabelsize);
		String indicator =defaultLabel[defaultLabelsize-1];
		System.out.println("Default month:"+indicator);
		
		if(!defaultLabel[defaultLabelsize-1].equals("-"))
			
		{ 
			test.log(LogStatus.PASS,"Selecting fares from Default month");
			driver.findElement(By.xpath(".//*[@class='tabWraper active']")).click();
			
		}
		
		else
		{
			test.log(LogStatus.PASS,"No fares in Default month");
			test.log(LogStatus.PASS,"Clicking on first month having fares");
		
		
		for(int i=1; i<=7; i++)
		{
			
		String monthLabel= driver.findElement(By.xpath(".//*[@id='monthSelect']/div["+i+"]//*[@class='tabWraper']")).getText();
		String[] tabLabel=monthLabel.split("");
		System.out.println(Arrays.toString(tabLabel));
		System.out.println("Monthlabel:"+tabLabel[10]);
		
		if (!tabLabel[10].equals("-")	)
		{
			driver.findElement(By.xpath(".//*[@id='monthSelect']/div["+i+"]//*[@class='tabWraper']/h4")).click();
			
			Thread.sleep(8000);
			
			break;
			
		}
		
		i++;
		
		
		
		}
		
		}
	
		MyWaitVar60Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='onwardCalendar']//a[@class='urlClass seo_calendar_fare_label currencyJapanse']")));
		List<WebElement> onwardFares= driver.findElements(By.xpath(".//*[@id='onwardCalendar']//a[@class='urlClass seo_calendar_fare_label currencyJapanse']"));
		
		test.log(LogStatus.PASS,"Number of  onward fares available in this month:"+onwardFares.size());
		System.out.println("Onward fares count:"+onwardFares.size());
		driver.findElement(By.xpath(".//*[@id='onwardCalendar']//a[@class='urlClass seo_calendar_fare_label currencyJapanse']")).click();
		
		Thread.sleep(8000);
		
		MyWaitVar60Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='returnCalendar']//a[@class='urlClass seo_calendar_fare_label currencyJapanse']")));
		List<WebElement> returnFares= driver.findElements(By.xpath(".//*[@id='returnCalendar']//a[@class='urlClass seo_calendar_fare_label currencyJapanse']"));
		System.out.println("Return fares count:"+returnFares.size());
		test.log(LogStatus.PASS,"Number of matching return fares available in this month:"+returnFares.size());
		driver.findElement(By.xpath(".//*[@id='returnCalendar']//a[@class='urlClass seo_calendar_fare_label currencyJapanse']")).click();
		
		
        MyWaitVar60Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='searchSEOLanding']")));
		driver.findElement(By.xpath(".//*[@id='searchSEOLanding']")).click();
		test.log(LogStatus.PASS,"Performing search");
		Thread.sleep(40000);
		
       Boolean isSEOLandingpage=driver.findElements(By.xpath(".//*[@class='headingSales']")).size()>0;
	   if(isSEOLandingpage==true)
	   {
		   System.out.println("SEO Selected product is available");
		   test.log(LogStatus.PASS,"SEO Selected product is available");
		   Boolean isTrue= driver.findElements(By.xpath(".//button[@class='btn btn-primary btn-lg']")).size()>0;
		   if(isTrue== true)
		   {
			   System.out.println("True");
		   }
		   
		   else
		   {
			   System.out.println("False");
		   }
		   driver.findElement(By.xpath(".//button[@class='btn btn-primary btn-lg']")).sendKeys(Keys.ENTER);
		   test.log(LogStatus.PASS,"Clicking on Book button");
		   Thread.sleep(35000);
		   test.log(LogStatus.PASS,"Navigated to SC Page");
		   test.log(LogStatus.PASS,"**********************");
		  /* String currentUrl=driver.getCurrentUrl();
		   System.out.println("CurrentURL:"+currentUrl);
		    Pattern pattern = Pattern.compile("Session=(.*?)&UniqueProductId");
		    Matcher matcher = pattern.matcher(currentUrl);
		    while (matcher.find()) {
		        System.out.println("Sessiionid:"+matcher.group(0));
		        test.log(LogStatus.PASS, "Navigated to SC page");   
		        test.log(LogStatus.PASS, "SessionID:"+matcher.group(0));   
		  
		}
		    
		    Pattern pattern2 = Pattern.compile("UniqueProductId=(.*?)&prodPosition");
		    Matcher matcher1 = pattern2.matcher(currentUrl);
		    while (matcher1.find()) {
		        System.out.println("ProductID:"+matcher1.group(1));
		        test.log(LogStatus.PASS, "ProductID:"+matcher1.group(1));   
		  
		}
		   */
		  
	   }
	   
	   else
	   {
		   System.out.println("SEO Selected product is not available");
		   test.log(LogStatus.FAIL, "SEO Selected product is not available");   
		   test.log(LogStatus.FAIL,"**********************");
			Assert.assertFalse(true,"SEO Selected product is not available");
	   }
		}
		
		
		catch(Exception e)
			{
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Unexpected error occured");   
			
			  test.log(LogStatus.FAIL,"**********************");
				Assert.assertFalse(true,"Unexpected error occured");
			}
}
	
	@AfterClass()
	public void deleteFolder() throws IOException {
		extent.endTest(test);
        extent.flush();

	}

	
}







