package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
//import java.sql.Date;
import java.util.Date;
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
import org.openqa.selenium.TakesScreenshot;

import pages.his.LoginPage;
import pages.his.SearchPage;
import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;
import controls.ExcelRead;

public class TC_OTA_RTSearch  extends DriverSetup {
	SearchPage SearchPage;
	LoginPage Loginpage;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	int productCount;
	int testDataCount=0;
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
	}
	
		
	@DataProvider(name = "TC_OTA_RTSearch")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_OTA_RTSearch");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_OTA_RTSearch")
	public void ApplyHandlingFeeRule(String URL)
		{
		
		try{
			
		//	extent = new ExtentReports(System.getProperty("user.dir")+"/testReports/TC_OTA_RTSearch_TestReport_"+timeStamp+".html",false);
			//System.out.println("PATH : "+System.getProperty("user.dir"));
			//test  = extent.startTest("OTA-RT Search- TC Name");	
			driver.get(URL);
			Thread.sleep(15000);
			
			testDataCount= testDataCount+1;
			String AllflightsCount=driver.findElement(By.xpath("//li[@id='allFlights']//span[@class='badgeText']")).getText();
		    System.out.println("All flights count:"+AllflightsCount);
		    test.log(LogStatus.PASS, "TestData count:" +testDataCount );
		    test.log(LogStatus.FAIL, "All flights count FAIL:"+AllflightsCount);
		    
		    String screenshotPath = getScreenshot(driver, getTestName());
			//To add it in the extent report 
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
			
		    
		    String NonStopflightsCount=driver.findElement(By.xpath("//li[@id='noStop']//span[@class='badgeText']")).getText();
		    System.out.println("No stop  flights count:"+NonStopflightsCount);
		    test.log(LogStatus.PASS, "No stop  flights count:"+NonStopflightsCount);
		    
		    
		    String OneStopflightsCount=driver.findElement(By.xpath("//li[@id='oneStop']//span[@class='badgeText']")).getText();
		    System.out.println("One stop  flights count:"+OneStopflightsCount);
		    test.log(LogStatus.PASS, "One stop  flights count:"+OneStopflightsCount);
		    
		    String TwoStopflightsCount=driver.findElement(By.xpath("//li[@id='twoStop']//span[@class='badgeText']")).getText();
		    System.out.println("Two stop  flights count:"+TwoStopflightsCount);
		    test.log(LogStatus.PASS, "Two stop  flights count:"+TwoStopflightsCount);
		         
		   		 
		    MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(" //button[@class='btn btn-primary btn-lg']")));
		    driver.findElement(By.xpath(" //button[@class='btn btn-primary btn-lg']")).click();
		    test.log(LogStatus.PASS, "Selecting onward slice");
		    Thread.sleep(8000); 
		  
		    String AllflightsCountRet=driver.findElement(By.xpath("//li[@id='allFlights']//span[@class='badgeText']")).getText();
		    System.out.println("All flights count:"+AllflightsCountRet);
		    test.log(LogStatus.PASS, "All flights count in return listing page:"+AllflightsCountRet);
		    
		    String NonStopflightsCountRet=driver.findElement(By.xpath("//li[@id='noStop']//span[@class='badgeText']")).getText();
		    System.out.println("No stop  flights count:"+NonStopflightsCountRet);
		    test.log(LogStatus.PASS, "No stop  flights count in return listing page:"+NonStopflightsCountRet);
		    
		    String OneStopflightsCountRet=driver.findElement(By.xpath("//li[@id='oneStop']//span[@class='badgeText']")).getText();
		    System.out.println("One stop  flights count:"+OneStopflightsCountRet);
		    test.log(LogStatus.PASS, "One stop  flights count in return listing page:"+OneStopflightsCountRet);
		    
		    String TwoStopflightsCountRet=driver.findElement(By.xpath("//li[@id='twoStop']//span[@class='badgeText']")).getText();
		    System.out.println("Two stop  flights count:"+TwoStopflightsCountRet);
		    test.log(LogStatus.PASS, "Two stop  flights count in return listing page:"+TwoStopflightsCountRet);
		    
		    MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(" //button[@class='btn btn-primary btn-lg']")));
			driver.findElement(By.xpath(" //button[@class='btn btn-primary btn-lg']")).click(); 
			test.log(LogStatus.PASS, "Clicking on Book button");
		   
			  
			Thread.sleep(15000);
		    String currentUrl=driver.getCurrentUrl();
		    Pattern pattern = Pattern.compile("Session=(.*?)&UniqueProductId");
		    Matcher matcher = pattern.matcher(currentUrl);
		    while (matcher.find()) 
		    {
		    System.out.println("Sessiionid:"+matcher.group(1));
		    test.log(LogStatus.PASS, "Navigated to SC page");   
		    test.log(LogStatus.PASS, "SessionID:"+matcher.group(1));   
		  
		    }
		    
		    Pattern pattern2 = Pattern.compile("UniqueProductId=(.*?)&prodPosition");
		    Matcher matcher1 = pattern.matcher(currentUrl);
		    while (matcher1.find()) 
		    {
		        System.out.println("ProductID:"+matcher1.group(1));
		        test.log(LogStatus.PASS, "ProductID:"+matcher1.group(1));   
		  
		    }
		    driver.manage().deleteAllCookies();
		    Thread.sleep(8000);
		
		   
		
		}
		
		
		
		catch(Exception e)
			{
			e.printStackTrace();
			test.log(LogStatus.FAIL, "Unexpected error occured");   
			extent.endTest(test);
	        extent.flush();
			}
		
		
		
}
	
	 private String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		// TODO Auto-generated method stub

			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
	                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
			String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
			return destination;
		//return null;
	}


	
	@BeforeClass()	
	public void ReportGeneration() throws IOException, InterruptedException 
	{
extent = new ExtentReports(System.getProperty("user.dir")+"/testReports/TC_OTA_TestReport_"+timeStamp+".html",true);
System.out.println("PATH : "+System.getProperty("user.dir"));
test  = extent.startTest("OTA-RT Search");	
	}
	

	@AfterClass()
	public void deleteFolder() throws IOException, InterruptedException {
		extent.endTest(test);
       extent.flush();
       
        driver.manage().deleteAllCookies();
        System.out.println("All cookies deleted. end of OTA RT");
	    Thread.sleep(1000);
        

	}
	
	
	
}









