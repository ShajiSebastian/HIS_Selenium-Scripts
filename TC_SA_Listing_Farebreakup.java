//package his_NEW;
package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
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


import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class TC_SA_Listing_Farebreakup  extends DriverSetup {
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
		

	}
	
		
	@DataProvider(name = "TC_SA_Listing_Farebreakup")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_Listing_Farebreakup");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_Listing_Farebreakup")
	public void ApplyHandlingFeeRule(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults,String NoOfChild,String NoOfInfant,String fromCity )
			throws InterruptedException, IOException {
			String url = getTestURL();
			System.out.println ("url is "+url);
		System.out.println ("\nGOING TO Loginpage common function");
		Loginpage.login(url);
		System.out.println ("\nBACK FROM Loginpage common function");

			// Clicking Airshopping page link
			try
			{
			//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='menu']/ul/li[1]/a"))); // waiting till Airshopping link is shown in page
			
			driver.findElement(By.xpath(".//*[@id='menu']/ul/li[1]/a")).click();
			System.out.println ("\nAirshopping page link clicked");
			Thread.sleep(4000);
			
			commonUtility.switchToThisWin_WithTitle(driver.getTitle(), driver);
		    System.out.println("CURRENT WINDOW IS "+driver.getTitle());
			MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='originSId']"))); // waiting till Search page is shown in page
			

				
			   
		System.out.println ("Going to click RT option");
		 			System.out.println ("page refreshed");
		    System.out.println ("RT search option selected by default");
	
		driver.findElement(By.xpath(".//*[@id='originSId']")).clear();	  
		driver.findElement(By.id("originSId")).sendKeys(fromCity);
		driver.findElement(By.xpath(".//*[@id='originSId']")).click();	
		Thread.sleep(2000);
		
		driver.findElement(By.id("destinationSId")).click();
		driver.findElement(By.id("destinationSId")).clear();
		driver.findElement(By.id("destinationSId")).sendKeys(ToCity1);
		Thread.sleep(1000);
		driver.findElement(By.id("destinationSId")).click();
		
		driver.findElement(By.id("destinationSId")).sendKeys(Keys.TAB);
		
		
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).clear();
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).sendKeys(Depart1);
		driver.findElement(By.xpath(".//*[@id='alternateSfrom']")).click();	
		Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).clear();
			driver.findElement(By.xpath(".//*[@id='alternateSto']")).sendKeys(Depart2);
			//driver.findElement(By.xpath(".//*[@id='prfrdRetrnTimeId']")).click();	
	
		Thread.sleep(1000);	
	//Selecting passenger counts (Dropdown list)
	Select adultCount = new Select (driver.findElement(By.name("fareSearchVO.noOfAdults")));
	adultCount.selectByVisibleText(NoOfAdults);
	Thread.sleep(1000);	
	Select childCount = new Select (driver.findElement(By.name("fareSearchVO.noOfChild")));
	childCount.selectByVisibleText(NoOfChild);
	Thread.sleep(1000);	
	
	Select infantCount = new Select (driver.findElement(By.name("fareSearchVO.noOfInfants")));
	infantCount.selectByVisibleText(NoOfInfant);
	Thread.sleep(1000);	
	
	System.out.println ("Search criteria entered");
	
	//Thread.sleep(2000);
	
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	Thread.sleep(2000);
	
    driver.findElement(By.xpath("//*[starts-with(@id,'fare_break')]")).click();
	Thread.sleep(10000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for farebreak-up option", "Clicking on farebreak-up", true, "Same as Expected", "Fare break-up is clickable", "Fare break-up should be clickable");
	//Basic Fare
	
	Boolean isPresentAdt = driver.findElements(By.xpath("//*[contains(@id,'BasicFare_HISADT') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value:"+isPresentAdt);
    String Adtfare1=driver.findElement(By.xpath("//*[contains(@id,'BasicFare_HISADT') and contains(@id,'paxTotal')]")).getText();
    String Adtfare2=Adtfare1.replace(",", "").trim();
    float Adtfare=Float.parseFloat(Adtfare2);
    System.out.println("Total Adult fare:"+Adtfare);
    excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for Adult fare", "Validating Adult fare", true, "Same as Expected", "Adult fare is available", "Adult fare should be available");
    
    Boolean isPresentChd = driver.findElements(By.xpath("//*[contains(@id,'BasicFare_HISCNN') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value Chd"+isPresentChd);
    float ChdFare= 0;
    if(isPresentChd==true)
    {
    	String ChdFare1 = driver.findElement(By.xpath("//*[contains(@id,'BasicFare_HISCNN') and contains(@id,'paxTotal')]")).getText();
        System.out.println("ChildFare:"+ChdFare1);
        String ChdFare2=ChdFare1.replace(",", "").trim();
        ChdFare=Float.parseFloat(ChdFare2);
        System.out.println("Total Child fare:"+ChdFare);
        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for Child fare", "Validating Child fare", true, "Same as Expected", "Child fare is available", "Child fare should be available");
    }
    
   
    
    Boolean isPresentInf = driver.findElements(By.xpath("//*[contains(@id,'BasicFare_HISINF') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value Inf:"+isPresentInf);
    float InfFare= 0;
    if(isPresentInf==true)
    {
    	String InfFare1 = driver.findElement(By.xpath("//*[contains(@id,'BasicFare_HISINF') and contains(@id,'paxTotal')]")).getText();
        System.out.println("InfFare:"+InfFare1);
        String InfFare2=InfFare1.replace(",", "").trim();
        InfFare=Float.parseFloat(InfFare2);
        System.out.println("Total Inf fare:"+InfFare);
        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for Infant fare", "Validating Infant fare", true, "Same as Expected", "Infant fare is available", "Infant fare should be available");
    }
    
   
    
    
    
    //Tax
    
    Boolean isPresentAdtTax = driver.findElements(By.xpath("//*[contains(@id,'Taxes_HISADT') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value:"+isPresentAdtTax);
    String AdtTax1=driver.findElement(By.xpath("//*[contains(@id,'Taxes_HISADT') and contains(@id,'paxTotal')]")).getText();
    String AdtTax2=AdtTax1.replace(",", "").trim();
    float AdtTax=Float.parseFloat(AdtTax2);
    System.out.println("Total Adult Tax:"+AdtTax);
    excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for Adult tax", "Validating Adult tax", true, "Same as Expected", "Adult tax is available", "Adult tax should be available");
    
    Boolean isPresentChdTax = driver.findElements(By.xpath("//*[contains(@id,'Taxes_HISCNN') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value Chd"+isPresentChdTax);
    float ChdTax= 0;
    if(isPresentChdTax==true)
    {
    	String ChdTax1 = driver.findElement(By.xpath("//*[contains(@id,'Taxes_HISCNN') and contains(@id,'paxTotal')]")).getText();
        System.out.println("ChildTax:"+ChdTax1);
        String ChdTax2=ChdTax1.replace(",", "").trim();
        ChdTax=Float.parseFloat(ChdTax2);
        System.out.println("Total Child Tax:"+ChdTax);
        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for Child tax", "Validating Child tax", true, "Same as Expected", "Child tax is available", "Child tax should be available");
    }
    
   
    
    Boolean isPresentInfTax = driver.findElements(By.xpath("//*[contains(@id,'Taxes_HISINF') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value Inf:"+isPresentInfTax);
    float InfTax= 0;
    if(isPresentInfTax==true)
    {
    	String InfTax1 = driver.findElement(By.xpath("//*[contains(@id,'Taxes_HISINF') and contains(@id,'paxTotal')]")).getText();
        System.out.println("InfTax:"+InfTax1);
        String InfTax2=InfTax1.replace(",", "").trim();
        InfTax=Float.parseFloat(InfTax2);
        System.out.println("Total Inf Tax:"+InfTax);
        excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Checking for Infant tax", "Validating Infant tax", true, "Same as Expected", "Infant tax is available", "Infant tax should be available");
    }
    
    
    
    
    //Other Charges
    Boolean isPresentAdtOtherCharge = driver.findElements(By.xpath("//*[contains(@id,'OtherCharges_HISADT') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value:"+isPresentAdtOtherCharge);
    String AdtOtherCharge1=driver.findElement(By.xpath("//*[contains(@id,'OtherCharges_HISADT') and contains(@id,'paxTotal')]")).getText();
    String AdtOtherCharge2=AdtOtherCharge1.replace(",", "").trim();
    float AdtOtherCharge=Float.parseFloat(AdtOtherCharge2);
    System.out.println("Total Adult Other Charge:"+AdtOtherCharge);
    
    Boolean isPresentChdOtherCharge = driver.findElements(By.xpath("//*[contains(@id,'OtherCharges_HISCNN') and contains(@id,'paxTotal')]")).size() > 0;
    System.out.println("Value Chd"+isPresentChdOtherCharge);
    float ChdOtherCharge= 0;
    if(isPresentChdOtherCharge==true)
    {
    	String ChdOtherCharge1 = driver.findElement(By.xpath("//*[contains(@id,'OtherCharges_HISCNN') and contains(@id,'paxTotal')]")).getText();
        System.out.println("ChildOtherCharge:"+ChdOtherCharge1);
        String ChdOtherCharge2=ChdOtherCharge1.replace(",", "").trim();
        ChdOtherCharge=Float.parseFloat(ChdOtherCharge2);
        System.out.println("Total Child Other Charge:"+ChdOtherCharge);
    }
    
   
    
    //TotalFare
    
    String TotalCharge1 = driver.findElement(By.xpath("//*[starts-with(@id,'totProdPriceWithAgntFee')]")).getText();
    System.out.println("TotalCharge:"+TotalCharge1);
    String TotalCharge2=TotalCharge1.replace(",", "").trim();
    float TotalCharge=Float.parseFloat(TotalCharge2);
    System.out.println("Total Charge:"+TotalCharge);
    
    
    //TotalCalculatedCharge
   
float TotalCalculatedCharge= Adtfare+ChdFare+InfFare+AdtTax+ChdTax+InfTax+AdtOtherCharge+ChdOtherCharge;
if (TotalCalculatedCharge==TotalCharge)
{
	System.out.println("FareBreak-up is correct");
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Fare break-up Listing page", "Validating Total fare of product", "Checking Total fare of product", true, "Same as Expected", "Total fare of product is:"+TotalCharge, "Total fare of product should be:"+TotalCalculatedCharge);
}
    
    
    //waittimeShort() ;

		
//MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='basicListingForm']/table/tbody/tr/td/span"))); // waiting till the given field is shown in page

			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
	}
}



