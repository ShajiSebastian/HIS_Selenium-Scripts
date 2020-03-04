package his_NEW;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
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

public class TC_SA_TaxDetails  extends DriverSetup {
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
	
		
	@DataProvider(name = "TC_SA_TaxDetails")
	public Object[][] createData2() throws Exception {
	         
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_SA_TaxDetails");
		System.out.println ("\nHIS-TestData file taken successfully");
		return (retObjArr);
		
	}
	

	// Trying to get Login Credentials for Store Agent page

	@Test(dataProvider = "TC_SA_TaxDetails")
	public void ApplyHandlingFeeRule(String ToCity1, String Depart1,
			String Depart2, String NoOfAdults,String NoOfChild,String NoOfInfant,String fromCity)
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
			int adult =Integer.parseInt(NoOfAdults);
			int child =Integer.parseInt(NoOfChild);
			int infant =Integer.parseInt(NoOfInfant);
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
	Select select = new Select(driver.findElement(By.xpath(".//*[@id='fareRTType']")));
    select.selectByVisibleText("PEX (Published) Fares");
    Thread.sleep(800);
	
	driver.findElement(By.xpath(".//*[@id='shoppingSearchButton']")).click();
	System.out.println ("Search button clicked");
	Thread.sleep(2000);
	
	
	
	driver.findElement(By.xpath(".//*[@class='button_book_agent']")).click();
	Thread.sleep(30000);
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking for Select button", "Navigating to details page ", true, "Same as Expected", "Page 3 is available", "Page 3 should be available");
	
	
	
	
	
	
	driver.findElement(By.xpath(".//*[@id='showTaxDtls']")).click();
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking for Tax details link", "Navigating to  Tax details link ", true, "Same as Expected", "Tax details link is clickable", "Tax details link should be clickable");
	Thread.sleep(800);
	
	
	if(adult!=0 && child!=0 && infant!=0)
	
	{
	
		//Adult Tax	
	
	
	
	Boolean isPresent= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).size()>0;
	float totAdtTax=0;
	if (isPresent==true)
	{
	
	String totAdtTax1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).getText();
	String[] totAdtTax2 = totAdtTax1.split("X");
	String totAdtTax3= totAdtTax2[0];
	System.out.println("Without X value:"+totAdtTax3);
	String totAdtTax4 = totAdtTax3.substring(totAdtTax3.indexOf("¥") + 1);
	System.out.println("Modified fare:"+totAdtTax4);
	String totAdtTax5=totAdtTax4.replace(",", "").trim();
	System.out.println("Trimmed adult fare:"+totAdtTax5);
	totAdtTax=Float.parseFloat(totAdtTax5);
	System.out.println("Total Adt tax:"+totAdtTax);
	}
	else
	{
		System.out.println("Adt tax not found");
	}
	
	
	float calculatedTax = 0;
	
	Boolean isPresentAirportDeptFee=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).size()>0;
	if(isPresentAirportDeptFee==true)
	{
		
		String ArptDeptFee1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).getText();
		String ArptDeptFee2=ArptDeptFee1.replace(",", "").trim();
		System.out.println("Trimmed airport dept:"+ArptDeptFee2);
		float ArptDeptFee=Float.parseFloat(ArptDeptFee2);
		System.out.println("Total airport dept:"+ArptDeptFee);
		calculatedTax=ArptDeptFee;
	
	}
	else
	{
		System.out.println("No Airport Departure fee found");
		float ArptDeptFee=0;
		calculatedTax=ArptDeptFee;
	}
	
	
	
	Boolean isPresentAirportFuel=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).size()>0;
	if(isPresentAirportFuel==true)
	{
		
		String AirportFuel1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).getText();
		String AirportFuel2=AirportFuel1.replace(",", "").trim();
		System.out.println("Trimmed airport fuel:"+AirportFuel2);
		float AirportFuel=Float.parseFloat(AirportFuel2);
		System.out.println("Total airport fuel:"+AirportFuel);
		calculatedTax=calculatedTax+AirportFuel;
	
	}
	else
	{
		System.out.println("No Airport Fuel fee found");
		float AirportFuel=0;
		calculatedTax=calculatedTax+AirportFuel;
	}
	
	
	
	
	Boolean isPresentPsngScty=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).size()>0;
	if(isPresentPsngScty==true)
	{
		
		String PsngScty1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).getText();
		String PsngScty2=PsngScty1.replace(",", "").trim();
		System.out.println("Trimmed PsngScty:"+PsngScty2);
		float PsngScty=Float.parseFloat(PsngScty2);
		System.out.println("Total PsngScty:"+PsngScty);
		calculatedTax=calculatedTax+PsngScty;
	
	}
	else
	{
		System.out.println("No PsngScty fee found");
		float PsngScty=0;
		calculatedTax=calculatedTax+PsngScty;
		
	}
	
	
	
	Boolean isPresentOthrTax=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).size()>0;
	if(isPresentOthrTax==true)
	{
		
		String OthrTax1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).getText();
		String OthrTax2=OthrTax1.replace(",", "").trim();
		System.out.println("Trimmed OthrTax:"+OthrTax2);
		float OthrTax=Float.parseFloat(OthrTax2);
		System.out.println("Total OthrTax:"+OthrTax);
		calculatedTax=calculatedTax+OthrTax;
		
	
	}
	else
	{
		System.out.println("No OthrTax fee found");
		float OthrTax=0;
		calculatedTax=calculatedTax+OthrTax;
	}
	
	
	if(calculatedTax==totAdtTax)
	{
		System.out.println("Adt Tax is correct");
		Thread.sleep(8000);
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Adult Tax", "Validating Total Adult Tax ", true, "Same as Expected", "Total Adult tax is:"+calculatedTax, "Total Adult tax should be:"+totAdtTax);
	}
	
	else
	{
		System.out.println("Adt Tax is wrong");
		Thread.sleep(8000);
		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Adult is working or not", "Tax Details of Adult failed", true, "Not Same as Expected", "Tax Details of Adult failed", "Tax Details of Adult should be working");
		Assert.assertFalse(true, "Tax Details in Japan SA");
	}
	
	
	
	//Child Tax
	Boolean isPresentChd= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).size()>0;
	float totAdtTaxChd=0;
	if (isPresentChd==true)
	{
	
	String totAdtTaxChd1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).getText();
	String[] totAdtTaxChd2 = totAdtTaxChd1.split("X");
	String totAdtTaxChd3= totAdtTaxChd2[0];
	System.out.println("Without X value:"+totAdtTaxChd3);
	String totAdtTaxChd4 = totAdtTaxChd3.substring(totAdtTaxChd3.indexOf("¥") + 1);
	System.out.println("Modified Chd fare:"+totAdtTaxChd4);
	String totAdtTaxChd5=totAdtTaxChd4.replace(",", "").trim();
	System.out.println("Trimmed Chd fare:"+totAdtTaxChd5);
	totAdtTaxChd=Float.parseFloat(totAdtTaxChd5);
	System.out.println("Total Chd tax:"+totAdtTaxChd);
	}
	else
	{
		System.out.println("Chd tax not found");
	}
	
	
	float calculatedTaxChd = 0;
	
	Boolean isPresentAirportDeptFeeChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).size()>0;
	if(isPresentAirportDeptFeeChd==true)
	{
		
		String ArptDeptFeeChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).getText();
		String ArptDeptFeeChd2=ArptDeptFeeChd1.replace(",", "").trim();
		System.out.println("Trimmed airport dept Chd:"+ArptDeptFeeChd2);
		float ArptDeptFeeChd=Float.parseFloat(ArptDeptFeeChd2);
		System.out.println("Total airport dept Chd:"+ArptDeptFeeChd);
		calculatedTaxChd=ArptDeptFeeChd;
	
	}
	else
	{
		System.out.println("No Airport Departure fee found");
		float ArptDeptFeeChd=0;
		calculatedTaxChd=ArptDeptFeeChd;
	}
	
	
	
	Boolean isPresentAirportFuelChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).size()>0;
	if(isPresentAirportFuelChd==true)
	{
		
		String AirportFuelChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).getText();
		String AirportFuelChd2=AirportFuelChd1.replace(",", "").trim();
		System.out.println("Trimmed airport fuel Chd:"+AirportFuelChd2);
		float AirportFuelChd=Float.parseFloat(AirportFuelChd2);
		System.out.println("Total airport fuel:"+AirportFuelChd);
		calculatedTaxChd=calculatedTaxChd+AirportFuelChd;
	
	}
	else
	{
		System.out.println("No ChdAirport Fuel fee found");
		float AirportFuelChd=0;
		calculatedTaxChd=calculatedTaxChd+AirportFuelChd;
	}
	
	
	
	
	Boolean isPresentPsngSctyChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).size()>0;
	if(isPresentPsngSctyChd==true)
	{
		
		String PsngSctyChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).getText();
		String PsngSctyChd2=PsngSctyChd1.replace(",", "").trim();
		System.out.println("Trimmed PsngScty Chd:"+PsngSctyChd2);
		float PsngSctyChd=Float.parseFloat(PsngSctyChd2);
		System.out.println("Total PsngScty Chd:"+PsngSctyChd);
		calculatedTaxChd=calculatedTaxChd+PsngSctyChd;
	
	}
	else
	{
		System.out.println("No ChdPsngScty fee found");
		float PsngSctyChd=0;
		calculatedTaxChd=calculatedTaxChd+PsngSctyChd;
		
	}
	
	
	
	Boolean isPresentOthrTaxChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).size()>0;
	if(isPresentOthrTaxChd==true)
	{
		
		String OthrTaxChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).getText();
		String OthrTaxChd2=OthrTaxChd1.replace(",", "").trim();
		System.out.println("Trimmed ChdOthrTax:"+OthrTaxChd2);
		float OthrTaxChd=Float.parseFloat(OthrTaxChd2);
		System.out.println("Total Chd OthrTax:"+OthrTaxChd);
		calculatedTaxChd=calculatedTaxChd+OthrTaxChd;
		
	
	}
	else
	{
		System.out.println("No OthrTax fee found");
		float OthrTaxChd=0;
		calculatedTaxChd=calculatedTaxChd+OthrTaxChd;
	}
	
	
	if(calculatedTaxChd==totAdtTaxChd)
	{
		System.out.println("Chd Tax is correct");
		Thread.sleep(8000);
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Child Tax", "Validating Total Child Tax ", true, "Same as Expected", "Total Child tax is:"+calculatedTaxChd, "Total Child tax should be:"+totAdtTaxChd);
	}
	
	else
	{
		System.out.println(" Chd Tax is wrong");
		Thread.sleep(8000);
		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Child is working or not", "Tax Details of Child failed", true, "Not Same as Expected", "Tax Details of Child failed", "Tax Details of Child should be working");
		Assert.assertFalse(true, "Tax Details in Japan SA");
	}
	
	
	
	//Infant Tax
	
	
	Boolean isPresentInf= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[10]/dt[2]")).size()>0;
	float totAdtTaxInf=0;
	if (isPresentInf==true)
	{
	
	String totAdtTaxInf1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[10]/dt[2]")).getText();
	String[] totAdtTaxInf2 = totAdtTaxInf1.split("X");
	String totAdtTaxInf3= totAdtTaxInf2[0];
	System.out.println("Without X value:"+totAdtTaxInf3);
	String totAdtTaxInf4 = totAdtTaxInf3.substring(totAdtTaxInf3.indexOf("¥") + 1);
	System.out.println("Modified Inf fare:"+totAdtTaxInf4);
	String totAdtTaxInf5=totAdtTaxInf4.replace(",", "").trim();
	System.out.println("Trimmed Inf fare:"+totAdtTaxInf5);
	totAdtTaxInf=Float.parseFloat(totAdtTaxInf5);
	System.out.println("Total Inf tax:"+totAdtTaxInf);
	}
	else
	{
		System.out.println("Inf tax not found");
	}
	
	
	float calculatedTaxInf = 0;
	

	
	
	Boolean isPresentAirportFuelInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[1]/dt[2]")).size()>0;
	if(isPresentAirportFuelInf==true)
	{
		
		String AirportFuelInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[1]/dt[2]")).getText();
		String AirportFuelInf2=AirportFuelInf1.replace(",", "").trim();
		System.out.println("Trimmed airport fuel Inf:"+AirportFuelInf2);
		float AirportFuelInf=Float.parseFloat(AirportFuelInf2);
		System.out.println("Total airport fuel:"+AirportFuelInf);
		calculatedTaxInf=calculatedTaxInf+AirportFuelInf;
	
	}
	else
	{
		System.out.println("No InfAirport Fuel fee found");
		float AirportFuelInf=0;
		calculatedTaxInf=calculatedTaxInf+AirportFuelInf;
	}
	
	
	
	
	
	
	
	
	Boolean isPresentOthrTaxInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[2]/dt[2]")).size()>0;
	if(isPresentOthrTaxInf==true)
	{
		
		String OthrTaxInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[2]/dt[2]")).getText();
		String OthrTaxInf2=OthrTaxInf1.replace(",", "").trim();
		System.out.println("Trimmed InfOthrTax:"+OthrTaxInf2);
		float OthrTaxInf=Float.parseFloat(OthrTaxInf2);
		System.out.println("Total Inf OthrTax:"+OthrTaxInf);
		calculatedTaxInf=calculatedTaxInf+OthrTaxInf;
		
	
	}
	else
	{
		System.out.println("No OthrTax fee found");
		float OthrTaxInf=0;
		calculatedTaxInf=calculatedTaxInf+OthrTaxInf;
	}
	
	
	if(calculatedTaxInf==totAdtTaxInf)
	{
		System.out.println("Inf Tax is correct");
		Thread.sleep(8000);
		excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Infant Tax", "Validating Total Infant Tax ", true, "Same as Expected", "Total Infant tax is:"+calculatedTaxInf, "Total Infant tax should be:"+totAdtTaxInf);
	}
	
	else
	{
		System.out.println(" Inf Tax is wrong");
		Thread.sleep(8000);
		excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Infant is working or not", "Tax Details of Infant failed", true, "Not Same as Expected", "Tax Details of Infant failed", "Tax Details of Infant should be working");
		Assert.assertFalse(true, "Tax Details in Japan SA");
	}
	
	
	}
	
	if (adult!=0 && child==0 && infant==0)
	{
	
		//Adult Tax	
		
		
		
		Boolean isPresent= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).size()>0;
		float totAdtTax=0;
		if (isPresent==true)
		{
		
		String totAdtTax1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).getText();
		String[] totAdtTax2 = totAdtTax1.split("X");
		String totAdtTax3= totAdtTax2[0];
		System.out.println("Without X value:"+totAdtTax3);
		String totAdtTax4 = totAdtTax3.substring(totAdtTax3.indexOf("¥") + 1);
		System.out.println("Modified fare:"+totAdtTax4);
		String totAdtTax5=totAdtTax4.replace(",", "").trim();
		System.out.println("Trimmed adult fare:"+totAdtTax5);
		totAdtTax=Float.parseFloat(totAdtTax5);
		System.out.println("Total Adt tax:"+totAdtTax);
		}
		else
		{
			System.out.println("Adt tax not found");
		}
		
		
		float calculatedTax = 0;
		
		Boolean isPresentAirportDeptFee=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportDeptFee==true)
		{
			
			String ArptDeptFee1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).getText();
			String ArptDeptFee2=ArptDeptFee1.replace(",", "").trim();
			System.out.println("Trimmed airport dept:"+ArptDeptFee2);
			float ArptDeptFee=Float.parseFloat(ArptDeptFee2);
			System.out.println("Total airport dept:"+ArptDeptFee);
			calculatedTax=ArptDeptFee;
		
		}
		else
		{
			System.out.println("No Airport Departure fee found");
			float ArptDeptFee=0;
			calculatedTax=ArptDeptFee;
		}
		
		
		
		Boolean isPresentAirportFuel=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).size()>0;
		if(isPresentAirportFuel==true)
		{
			
			String AirportFuel1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).getText();
			String AirportFuel2=AirportFuel1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel:"+AirportFuel2);
			float AirportFuel=Float.parseFloat(AirportFuel2);
			System.out.println("Total airport fuel:"+AirportFuel);
			calculatedTax=calculatedTax+AirportFuel;
		
		}
		else
		{
			System.out.println("No Airport Fuel fee found");
			float AirportFuel=0;
			calculatedTax=calculatedTax+AirportFuel;
		}
		
		
		
		
		Boolean isPresentPsngScty=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).size()>0;
		if(isPresentPsngScty==true)
		{
			
			String PsngScty1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).getText();
			String PsngScty2=PsngScty1.replace(",", "").trim();
			System.out.println("Trimmed PsngScty:"+PsngScty2);
			float PsngScty=Float.parseFloat(PsngScty2);
			System.out.println("Total PsngScty:"+PsngScty);
			calculatedTax=calculatedTax+PsngScty;
		
		}
		else
		{
			System.out.println("No PsngScty fee found");
			float PsngScty=0;
			calculatedTax=calculatedTax+PsngScty;
			
		}
		
		
		
		Boolean isPresentOthrTax=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).size()>0;
		if(isPresentOthrTax==true)
		{
			
			String OthrTax1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).getText();
			String OthrTax2=OthrTax1.replace(",", "").trim();
			System.out.println("Trimmed OthrTax:"+OthrTax2);
			float OthrTax=Float.parseFloat(OthrTax2);
			System.out.println("Total OthrTax:"+OthrTax);
			calculatedTax=calculatedTax+OthrTax;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTax=0;
			calculatedTax=calculatedTax+OthrTax;
		}
		
		
		if(calculatedTax==totAdtTax)
		{
			System.out.println("Adt Tax is correct");
			Thread.sleep(8000);
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Adult Tax", "Validating Total Adult Tax ", true, "Same as Expected", "Total Adult tax is:"+calculatedTax, "Total Adult tax should be:"+totAdtTax);
		}
		
		else
		{
			System.out.println("Adt Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Adult is working or not", "Tax Details of Adult failed", true, "Not Same as Expected", "Tax Details of Adult failed", "Tax Details of Adult should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
		
		
		
		//Child Tax
		Boolean isPresentChd= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).size()>0;
		float totAdtTaxChd=0;
		if (isPresentChd==true)
		{
		
		String totAdtTaxChd1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).getText();
		String[] totAdtTaxChd2 = totAdtTaxChd1.split("X");
		String totAdtTaxChd3= totAdtTaxChd2[0];
		System.out.println("Without X value:"+totAdtTaxChd3);
		String totAdtTaxChd4 = totAdtTaxChd3.substring(totAdtTaxChd3.indexOf("¥") + 1);
		System.out.println("Modified Chd fare:"+totAdtTaxChd4);
		String totAdtTaxChd5=totAdtTaxChd4.replace(",", "").trim();
		System.out.println("Trimmed Chd fare:"+totAdtTaxChd5);
		totAdtTaxChd=Float.parseFloat(totAdtTaxChd5);
		System.out.println("Total Chd tax:"+totAdtTaxChd);
		}
		else
		{
			System.out.println("Chd tax not found");
		}
		
		
		float calculatedTaxChd = 0;
		
		Boolean isPresentAirportDeptFeeChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportDeptFeeChd==true)
		{
			
			String ArptDeptFeeChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).getText();
			String ArptDeptFeeChd2=ArptDeptFeeChd1.replace(",", "").trim();
			System.out.println("Trimmed airport dept Chd:"+ArptDeptFeeChd2);
			float ArptDeptFeeChd=Float.parseFloat(ArptDeptFeeChd2);
			System.out.println("Total airport dept Chd:"+ArptDeptFeeChd);
			calculatedTaxChd=ArptDeptFeeChd;
		
		}
		else
		{
			System.out.println("No Airport Departure fee found");
			float ArptDeptFeeChd=0;
			calculatedTaxChd=ArptDeptFeeChd;
		}
		
		
		
		Boolean isPresentAirportFuelChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).size()>0;
		if(isPresentAirportFuelChd==true)
		{
			
			String AirportFuelChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).getText();
			String AirportFuelChd2=AirportFuelChd1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel Chd:"+AirportFuelChd2);
			float AirportFuelChd=Float.parseFloat(AirportFuelChd2);
			System.out.println("Total airport fuel:"+AirportFuelChd);
			calculatedTaxChd=calculatedTaxChd+AirportFuelChd;
		
		}
		else
		{
			System.out.println("No ChdAirport Fuel fee found");
			float AirportFuelChd=0;
			calculatedTaxChd=calculatedTaxChd+AirportFuelChd;
		}
		
		
		
		
		Boolean isPresentPsngSctyChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).size()>0;
		if(isPresentPsngSctyChd==true)
		{
			
			String PsngSctyChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).getText();
			String PsngSctyChd2=PsngSctyChd1.replace(",", "").trim();
			System.out.println("Trimmed PsngScty Chd:"+PsngSctyChd2);
			float PsngSctyChd=Float.parseFloat(PsngSctyChd2);
			System.out.println("Total PsngScty Chd:"+PsngSctyChd);
			calculatedTaxChd=calculatedTaxChd+PsngSctyChd;
		
		}
		else
		{
			System.out.println("No ChdPsngScty fee found");
			float PsngSctyChd=0;
			calculatedTaxChd=calculatedTaxChd+PsngSctyChd;
			
		}
		
		
		
		Boolean isPresentOthrTaxChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).size()>0;
		if(isPresentOthrTaxChd==true)
		{
			
			String OthrTaxChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).getText();
			String OthrTaxChd2=OthrTaxChd1.replace(",", "").trim();
			System.out.println("Trimmed ChdOthrTax:"+OthrTaxChd2);
			float OthrTaxChd=Float.parseFloat(OthrTaxChd2);
			System.out.println("Total Chd OthrTax:"+OthrTaxChd);
			calculatedTaxChd=calculatedTaxChd+OthrTaxChd;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTaxChd=0;
			calculatedTaxChd=calculatedTaxChd+OthrTaxChd;
		}
		
		
		if(calculatedTaxChd==totAdtTaxChd)
		{
			System.out.println("Chd Tax is correct");
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Child Tax", "Validating Total Child Tax ", true, "Same as Expected", "Total Child tax is:"+calculatedTaxChd, "Total Child tax should be:"+totAdtTaxChd);
			Thread.sleep(8000);
		}
		
		else
		{
			System.out.println(" Chd Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Child is working or not", "Tax Details of Child failed", true, "Not Same as Expected", "Tax Details of Child failed", "Tax Details of Child should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
		
		
		
		//Infant Tax
		
		
		Boolean isPresentInf= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[10]/dt[2]")).size()>0;
		float totAdtTaxInf=0;
		if (isPresentInf==true)
		{
		
		String totAdtTaxInf1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[10]/dt[2]")).getText();
		String[] totAdtTaxInf2 = totAdtTaxInf1.split("X");
		String totAdtTaxInf3= totAdtTaxInf2[0];
		System.out.println("Without X value:"+totAdtTaxInf3);
		String totAdtTaxInf4 = totAdtTaxInf3.substring(totAdtTaxInf3.indexOf("¥") + 1);
		System.out.println("Modified Inf fare:"+totAdtTaxInf4);
		String totAdtTaxInf5=totAdtTaxInf4.replace(",", "").trim();
		System.out.println("Trimmed Inf fare:"+totAdtTaxInf5);
		totAdtTaxInf=Float.parseFloat(totAdtTaxInf5);
		System.out.println("Total Inf tax:"+totAdtTaxInf);
		}
		else
		{
			System.out.println("Inf tax not found");
		}
		
		
		float calculatedTaxInf = 0;
		

		
		
		Boolean isPresentAirportFuelInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportFuelInf==true)
		{
			
			String AirportFuelInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[1]/dt[2]")).getText();
			String AirportFuelInf2=AirportFuelInf1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel Inf:"+AirportFuelInf2);
			float AirportFuelInf=Float.parseFloat(AirportFuelInf2);
			System.out.println("Total airport fuel:"+AirportFuelInf);
			calculatedTaxInf=calculatedTaxInf+AirportFuelInf;
		
		}
		else
		{
			System.out.println("No InfAirport Fuel fee found");
			float AirportFuelInf=0;
			calculatedTaxInf=calculatedTaxInf+AirportFuelInf;
		}
		
		
		
		
		
		
		
		
		Boolean isPresentOthrTaxInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[2]/dt[2]")).size()>0;
		if(isPresentOthrTaxInf==true)
		{
			
			String OthrTaxInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[3]/dl[2]/dt[2]")).getText();
			String OthrTaxInf2=OthrTaxInf1.replace(",", "").trim();
			System.out.println("Trimmed InfOthrTax:"+OthrTaxInf2);
			float OthrTaxInf=Float.parseFloat(OthrTaxInf2);
			System.out.println("Total Inf OthrTax:"+OthrTaxInf);
			calculatedTaxInf=calculatedTaxInf+OthrTaxInf;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTaxInf=0;
			calculatedTaxInf=calculatedTaxInf+OthrTaxInf;
		}
		
		
		if(calculatedTaxInf==totAdtTaxInf)
		{
			System.out.println("Inf Tax is correct");
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Infant Tax", "Validating Total Infant Tax ", true, "Same as Expected", "Total Infant tax is:"+calculatedTaxInf, "Total Infant tax should be:"+totAdtTaxInf);
			Thread.sleep(8000);
		}
		
		else
		{
			System.out.println(" Inf Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Infant is working or not", "Tax Details of Infant failed", true, "Not Same as Expected", "Tax Details of Infant failed", "Tax Details of Infant should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
	}
	
	if(adult!=0 && child!=0 && infant==0)
	{
		//Adult Tax	
		
		
		
		Boolean isPresent= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).size()>0;
		float totAdtTax=0;
		if (isPresent==true)
		{
		
		String totAdtTax1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).getText();
		String[] totAdtTax2 = totAdtTax1.split("X");
		String totAdtTax3= totAdtTax2[0];
		System.out.println("Without X value:"+totAdtTax3);
		String totAdtTax4 = totAdtTax3.substring(totAdtTax3.indexOf("¥") + 1);
		System.out.println("Modified fare:"+totAdtTax4);
		String totAdtTax5=totAdtTax4.replace(",", "").trim();
		System.out.println("Trimmed adult fare:"+totAdtTax5);
		totAdtTax=Float.parseFloat(totAdtTax5);
		System.out.println("Total Adt tax:"+totAdtTax);
		}
		else
		{
			System.out.println("Adt tax not found");
		}
		
		
		float calculatedTax = 0;
		
		Boolean isPresentAirportDeptFee=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportDeptFee==true)
		{
			
			String ArptDeptFee1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).getText();
			String ArptDeptFee2=ArptDeptFee1.replace(",", "").trim();
			System.out.println("Trimmed airport dept:"+ArptDeptFee2);
			float ArptDeptFee=Float.parseFloat(ArptDeptFee2);
			System.out.println("Total airport dept:"+ArptDeptFee);
			calculatedTax=ArptDeptFee;
		
		}
		else
		{
			System.out.println("No Airport Departure fee found");
			float ArptDeptFee=0;
			calculatedTax=ArptDeptFee;
		}
		
		
		
		Boolean isPresentAirportFuel=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).size()>0;
		if(isPresentAirportFuel==true)
		{
			
			String AirportFuel1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).getText();
			String AirportFuel2=AirportFuel1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel:"+AirportFuel2);
			float AirportFuel=Float.parseFloat(AirportFuel2);
			System.out.println("Total airport fuel:"+AirportFuel);
			calculatedTax=calculatedTax+AirportFuel;
		
		}
		else
		{
			System.out.println("No Airport Fuel fee found");
			float AirportFuel=0;
			calculatedTax=calculatedTax+AirportFuel;
		}
		
		
		
		
		Boolean isPresentPsngScty=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).size()>0;
		if(isPresentPsngScty==true)
		{
			
			String PsngScty1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).getText();
			String PsngScty2=PsngScty1.replace(",", "").trim();
			System.out.println("Trimmed PsngScty:"+PsngScty2);
			float PsngScty=Float.parseFloat(PsngScty2);
			System.out.println("Total PsngScty:"+PsngScty);
			calculatedTax=calculatedTax+PsngScty;
		
		}
		else
		{
			System.out.println("No PsngScty fee found");
			float PsngScty=0;
			calculatedTax=calculatedTax+PsngScty;
			
		}
		
		
		
		Boolean isPresentOthrTax=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).size()>0;
		if(isPresentOthrTax==true)
		{
			
			String OthrTax1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).getText();
			String OthrTax2=OthrTax1.replace(",", "").trim();
			System.out.println("Trimmed OthrTax:"+OthrTax2);
			float OthrTax=Float.parseFloat(OthrTax2);
			System.out.println("Total OthrTax:"+OthrTax);
			calculatedTax=calculatedTax+OthrTax;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTax=0;
			calculatedTax=calculatedTax+OthrTax;
		}
		
		
		if(calculatedTax==totAdtTax)
		{
			System.out.println("Adt Tax is correct");
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Adult Tax", "Validating Total Adult Tax ", true, "Same as Expected", "Total Adult tax is:"+calculatedTax, "Total Adult tax should be:"+totAdtTax);
			Thread.sleep(8000);
		}
		
		else
		{
			System.out.println("Adt Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Adult is working or not", "Tax Details of Adult failed", true, "Not Same as Expected", "Tax Details of Adult failed", "Tax Details of Adult should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
		
		
		
		//Child Tax
		Boolean isPresentChd= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).size()>0;
		float totAdtTaxChd=0;
		if (isPresentChd==true)
		{
		
		String totAdtTaxChd1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).getText();
		String[] totAdtTaxChd2 = totAdtTaxChd1.split("X");
		String totAdtTaxChd3= totAdtTaxChd2[0];
		System.out.println("Without X value:"+totAdtTaxChd3);
		String totAdtTaxChd4 = totAdtTaxChd3.substring(totAdtTaxChd3.indexOf("¥") + 1);
		System.out.println("Modified Chd fare:"+totAdtTaxChd4);
		String totAdtTaxChd5=totAdtTaxChd4.replace(",", "").trim();
		System.out.println("Trimmed Chd fare:"+totAdtTaxChd5);
		totAdtTaxChd=Float.parseFloat(totAdtTaxChd5);
		System.out.println("Total Chd tax:"+totAdtTaxChd);
		}
		else
		{
			System.out.println("Chd tax not found");
		}
		
		
		float calculatedTaxChd = 0;
		
		Boolean isPresentAirportDeptFeeChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportDeptFeeChd==true)
		{
			
			String ArptDeptFeeChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).getText();
			String ArptDeptFeeChd2=ArptDeptFeeChd1.replace(",", "").trim();
			System.out.println("Trimmed airport dept Chd:"+ArptDeptFeeChd2);
			float ArptDeptFeeChd=Float.parseFloat(ArptDeptFeeChd2);
			System.out.println("Total airport dept Chd:"+ArptDeptFeeChd);
			calculatedTaxChd=ArptDeptFeeChd;
		
		}
		else
		{
			System.out.println("No Airport Departure fee found");
			float ArptDeptFeeChd=0;
			calculatedTaxChd=ArptDeptFeeChd;
		}
		
		
		
		Boolean isPresentAirportFuelChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).size()>0;
		if(isPresentAirportFuelChd==true)
		{
			
			String AirportFuelChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).getText();
			String AirportFuelChd2=AirportFuelChd1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel Chd:"+AirportFuelChd2);
			float AirportFuelChd=Float.parseFloat(AirportFuelChd2);
			System.out.println("Total airport fuel:"+AirportFuelChd);
			calculatedTaxChd=calculatedTaxChd+AirportFuelChd;
		
		}
		else
		{
			System.out.println("No ChdAirport Fuel fee found");
			float AirportFuelChd=0;
			calculatedTaxChd=calculatedTaxChd+AirportFuelChd;
		}
		
		
		
		
		Boolean isPresentPsngSctyChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).size()>0;
		if(isPresentPsngSctyChd==true)
		{
			
			String PsngSctyChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).getText();
			String PsngSctyChd2=PsngSctyChd1.replace(",", "").trim();
			System.out.println("Trimmed PsngScty Chd:"+PsngSctyChd2);
			float PsngSctyChd=Float.parseFloat(PsngSctyChd2);
			System.out.println("Total PsngScty Chd:"+PsngSctyChd);
			calculatedTaxChd=calculatedTaxChd+PsngSctyChd;
		
		}
		else
		{
			System.out.println("No ChdPsngScty fee found");
			float PsngSctyChd=0;
			calculatedTaxChd=calculatedTaxChd+PsngSctyChd;
			
		}
		
		
		
		Boolean isPresentOthrTaxChd=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).size()>0;
		if(isPresentOthrTaxChd==true)
		{
			
			String OthrTaxChd1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).getText();
			String OthrTaxChd2=OthrTaxChd1.replace(",", "").trim();
			System.out.println("Trimmed ChdOthrTax:"+OthrTaxChd2);
			float OthrTaxChd=Float.parseFloat(OthrTaxChd2);
			System.out.println("Total Chd OthrTax:"+OthrTaxChd);
			calculatedTaxChd=calculatedTaxChd+OthrTaxChd;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTaxChd=0;
			calculatedTaxChd=calculatedTaxChd+OthrTaxChd;
		}
		
		
		if(calculatedTaxChd==totAdtTaxChd)
		{
			System.out.println("Chd Tax is correct");
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Child Tax", "Validating Total Child Tax ", true, "Same as Expected", "Total Child tax is:"+calculatedTaxChd, "Total Child tax should be:"+totAdtTaxChd);
			Thread.sleep(8000);
		}
		
		else
		{
			System.out.println(" Chd Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Child is working or not", "Tax Details of Child failed", true, "Not Same as Expected", "Tax Details of Child failed", "Tax Details of Child should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
		
		
		
		
	}
	
	if(adult!=0 && child==0 && infant!=0)
	{
		//Adult Tax	
		
		
		
		Boolean isPresent= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).size()>0;
		float totAdtTax=0;
		if (isPresent==true)
		{
		
		String totAdtTax1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[2]/dt[2]")).getText();
		String[] totAdtTax2 = totAdtTax1.split("X");
		String totAdtTax3= totAdtTax2[0];
		System.out.println("Without X value:"+totAdtTax3);
		String totAdtTax4 = totAdtTax3.substring(totAdtTax3.indexOf("¥") + 1);
		System.out.println("Modified fare:"+totAdtTax4);
		String totAdtTax5=totAdtTax4.replace(",", "").trim();
		System.out.println("Trimmed adult fare:"+totAdtTax5);
		totAdtTax=Float.parseFloat(totAdtTax5);
		System.out.println("Total Adt tax:"+totAdtTax);
		}
		else
		{
			System.out.println("Adt tax not found");
		}
		
		
		float calculatedTax = 0;
		
		Boolean isPresentAirportDeptFee=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportDeptFee==true)
		{
			
			String ArptDeptFee1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[1]/dt[2]")).getText();
			String ArptDeptFee2=ArptDeptFee1.replace(",", "").trim();
			System.out.println("Trimmed airport dept:"+ArptDeptFee2);
			float ArptDeptFee=Float.parseFloat(ArptDeptFee2);
			System.out.println("Total airport dept:"+ArptDeptFee);
			calculatedTax=ArptDeptFee;
		
		}
		else
		{
			System.out.println("No Airport Departure fee found");
			float ArptDeptFee=0;
			calculatedTax=ArptDeptFee;
		}
		
		
		
		Boolean isPresentAirportFuel=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).size()>0;
		if(isPresentAirportFuel==true)
		{
			
			String AirportFuel1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[2]/dt[2]")).getText();
			String AirportFuel2=AirportFuel1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel:"+AirportFuel2);
			float AirportFuel=Float.parseFloat(AirportFuel2);
			System.out.println("Total airport fuel:"+AirportFuel);
			calculatedTax=calculatedTax+AirportFuel;
		
		}
		else
		{
			System.out.println("No Airport Fuel fee found");
			float AirportFuel=0;
			calculatedTax=calculatedTax+AirportFuel;
		}
		
		
		
		
		Boolean isPresentPsngScty=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).size()>0;
		if(isPresentPsngScty==true)
		{
			
			String PsngScty1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[3]/dt[2]")).getText();
			String PsngScty2=PsngScty1.replace(",", "").trim();
			System.out.println("Trimmed PsngScty:"+PsngScty2);
			float PsngScty=Float.parseFloat(PsngScty2);
			System.out.println("Total PsngScty:"+PsngScty);
			calculatedTax=calculatedTax+PsngScty;
		
		}
		else
		{
			System.out.println("No PsngScty fee found");
			float PsngScty=0;
			calculatedTax=calculatedTax+PsngScty;
			
		}
		
		
		
		Boolean isPresentOthrTax=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).size()>0;
		if(isPresentOthrTax==true)
		{
			
			String OthrTax1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[1]/dl[4]/dt[2]")).getText();
			String OthrTax2=OthrTax1.replace(",", "").trim();
			System.out.println("Trimmed OthrTax:"+OthrTax2);
			float OthrTax=Float.parseFloat(OthrTax2);
			System.out.println("Total OthrTax:"+OthrTax);
			calculatedTax=calculatedTax+OthrTax;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTax=0;
			calculatedTax=calculatedTax+OthrTax;
		}
		
		
		if(calculatedTax==totAdtTax)
		{
			System.out.println("Adt Tax is correct");
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Adult Tax", "Validating Total Adult Tax ", true, "Same as Expected", "Total Adult tax is:"+calculatedTax, "Total Adult tax should be:"+totAdtTax);
			Thread.sleep(8000);
		}
		
		else
		{
			System.out.println("Adt Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Adult is working or not", "Tax Details of Adult failed", true, "Not Same as Expected", "Tax Details of Adult failed", "Tax Details of Adult should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
		
		
		
		//Child Tax
		Boolean isPresentInf= driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).size()>0;
		float totAdtTaxInf=0;
		if (isPresentInf==true)
		{
		
		String totAdtTaxInf1 = driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/dl[6]/dt[2]")).getText();
		String[] totAdtTaxInf2 = totAdtTaxInf1.split("X");
		String totAdtTaxInf3= totAdtTaxInf2[0];
		System.out.println("Without X value:"+totAdtTaxInf3);
		String totAdtTaxInf4 = totAdtTaxInf3.substring(totAdtTaxInf3.indexOf("¥") + 1);
		System.out.println("Modified Inf fare:"+totAdtTaxInf4);
		String totAdtTaxInf5=totAdtTaxInf4.replace(",", "").trim();
		System.out.println("Trimmed Inf fare:"+totAdtTaxInf5);
		totAdtTaxInf=Float.parseFloat(totAdtTaxInf5);
		System.out.println("Total Inf tax:"+totAdtTaxInf);
		}
		else
		{
			System.out.println("Inf tax not found");
		}
		
		
		float calculatedTaxInf = 0;
		
		Boolean isPresentAirportDeptFeeInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).size()>0;
		if(isPresentAirportDeptFeeInf==true)
		{
			
			String ArptDeptFeeInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[1]/dt[2]")).getText();
			String ArptDeptFeeInf2=ArptDeptFeeInf1.replace(",", "").trim();
			System.out.println("Trimmed airport dept Inf:"+ArptDeptFeeInf2);
			float ArptDeptFeeInf=Float.parseFloat(ArptDeptFeeInf2);
			System.out.println("Total airport dept Inf:"+ArptDeptFeeInf);
			calculatedTaxInf=ArptDeptFeeInf;
		
		}
		else
		{
			System.out.println("No Airport Departure fee found");
			float ArptDeptFeeInf=0;
			calculatedTaxInf=ArptDeptFeeInf;
		}
		
		
		
		Boolean isPresentAirportFuelInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).size()>0;
		if(isPresentAirportFuelInf==true)
		{
			
			String AirportFuelInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[2]/dt[2]")).getText();
			String AirportFuelInf2=AirportFuelInf1.replace(",", "").trim();
			System.out.println("Trimmed airport fuel Inf:"+AirportFuelInf2);
			float AirportFuelInf=Float.parseFloat(AirportFuelInf2);
			System.out.println("Total airport fuel:"+AirportFuelInf);
			calculatedTaxInf=calculatedTaxInf+AirportFuelInf;
		
		}
		else
		{
			System.out.println("No InfAirport Fuel fee found");
			float AirportFuelInf=0;
			calculatedTaxInf=calculatedTaxInf+AirportFuelInf;
		}
		
		
		
		
		Boolean isPresentPsngSctyInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).size()>0;
		if(isPresentPsngSctyInf==true)
		{
			
			String PsngSctyInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[3]/dt[2]")).getText();
			String PsngSctyInf2=PsngSctyInf1.replace(",", "").trim();
			System.out.println("Trimmed PsngScty Inf:"+PsngSctyInf2);
			float PsngSctyInf=Float.parseFloat(PsngSctyInf2);
			System.out.println("Total PsngScty Inf:"+PsngSctyInf);
			calculatedTaxInf=calculatedTaxInf+PsngSctyInf;
		
		}
		else
		{
			System.out.println("No InfPsngScty fee found");
			float PsngSctyInf=0;
			calculatedTaxInf=calculatedTaxInf+PsngSctyInf;
			
		}
		
		
		
		Boolean isPresentOthrTaxInf=driver.findElements(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).size()>0;
		if(isPresentOthrTaxInf==true)
		{
			
			String OthrTaxInf1= driver.findElement(By.xpath(".//*[@class='width_100 leftfloat']/div[2]/dl[4]/dt[2]")).getText();
			String OthrTaxInf2=OthrTaxInf1.replace(",", "").trim();
			System.out.println("Trimmed InfOthrTax:"+OthrTaxInf2);
			float OthrTaxInf=Float.parseFloat(OthrTaxInf2);
			System.out.println("Total Inf OthrTax:"+OthrTaxInf);
			calculatedTaxInf=calculatedTaxInf+OthrTaxInf;
			
		
		}
		else
		{
			System.out.println("No OthrTax fee found");
			float OthrTaxInf=0;
			calculatedTaxInf=calculatedTaxInf+OthrTaxInf;
		}
		
		
		if(calculatedTaxInf==totAdtTaxInf)
		{
			System.out.println("Inf Tax is correct");
			excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking Total Infant Tax", "Validating Total Infant Tax ", true, "Same as Expected", "Total Infant tax is:"+calculatedTaxInf, "Total Infant tax should be:"+totAdtTaxInf);
			Thread.sleep(8000);
		}
		
		else
		{
			System.out.println(" Inf Tax is wrong");
			Thread.sleep(8000);
			excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whether Tax Details of Infant is working or not", "Tax Details of Infant failed", true, "Not Same as Expected", "Tax Details of Infant failed", "Tax Details of Infant should be working");
			Assert.assertFalse(true, "Tax Details in Japan SA");
		}
		driver.findElement(By.xpath(".//*[@id='hideTaxDtls']")).click();
		
	}
	excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking for Tax details link", "Navigating to  Tax details link ", true, "Same as Expected", "Tax details link is closed on clicking 'Close tax details link'", "TTax details link should be closed on clicking 'Close tax details link'");
	
			}
			catch(Exception e)
			{
				e.printStackTrace();

			
				excelreadwrite.insertFailedData(currentTestName, commonUtility.getcurrentDateTime(), "Tax Details in Japan SA ", "Checking whetherTax Details in Japan SA is working or not", "Tax Details in Japan SA failed", true, "Not Same as Expected", "Tax Details in Japan SA failed", "Tax Details in Japan SA should be working");
				Assert.assertFalse(true, "Tax Details in Japan SA");
				
			}
	}
}







