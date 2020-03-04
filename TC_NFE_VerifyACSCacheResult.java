
package his_NEW;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.commands.GetText;

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;
import java.util.Arrays;
//import controls.*;
public class TC_NFE_VerifyACSCacheResult  extends DriverSetup {
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
				
	         
		System.out.println("\ninside setup() fn (TC_NFE_VerifyACSCacheResult .java)");
		System.out.println("TC_NFE_VerifyACSCacheResult .java execution starts now");
		System.out.println("\nGetting current test case name (TC_NFE_VerifyACSCacheResult .java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
	//	drop = new DropDown(driver,del);
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (TC_NFE_VerifyACSCacheResult .java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "TC_NFE_VerifyACSCacheResult ")
	public Object[][] createData2() throws Exception 
	{
	         
		System.out.println("inside TC_NFE_VerifyACSCacheResult .java");
		System.out.println("\nTrying to to read input test data from input datasheet (TC_NFE_VerifyACSCacheResult .java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		System.out.println("The identification text of test data to be given below function. If not mentioned correctly TC will skip");
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_NFE_VerifyACSCacheResult ");
	
		return (retObjArr);
	}
	@Test(dataProvider = "TC_NFE_VerifyACSCacheResult ")
	//@Test(dataProvider = "VerifySortByArrival_ReturnEarlierToLater")
	//public void TC_NFE_VerifyACSCacheResult (String location) throws InterruptedException 
	public void VerifyACSResult(String URL,String from,String to,String DepartDate,String ReturnDate,String usertype,String sellingeihon,String prefairline,String onwardflight,String returnflight )
			throws InterruptedException, IOException {
		try{
		driver.get(URL);
		driver.findElement(By.xpath(".//*[@id='rtWayId']")).click();
		driver.findElement(By.xpath(".//*[@id='owBlock1']/td[1]/input")).sendKeys(from);
		driver.findElement(By.xpath(".//*[@id='owBlock1']/td[2]/input")).sendKeys(to);
		driver.findElement(By.xpath(".//*[@id='txt_Depart']")).sendKeys(DepartDate);
		
		driver.findElement(By.xpath(".//*[@id='txt_Return']")).sendKeys(ReturnDate);
	
		driver.findElement(By.xpath("html/body/form/center/table/tbody/tr[14]/td[1]/input")).sendKeys(prefairline);
	
		driver.findElement(By.xpath("html/body/form/center/table/tbody/tr[17]/td[1]/input")).sendKeys(usertype);
	
		driver.findElement(By.xpath("html/body/form/center/table/tbody/tr[17]/td[2]/input")).sendKeys(sellingeihon);
	
		driver.findElement(By.xpath("html/body/form/center/table/tbody/tr[18]/td/input[4]")).click();
		Thread.sleep(2000);
	
	String schedules=driver.findElement(By.name("msg")).getText();
	Thread.sleep(2000);
	String e=from+to+"("+onwardflight+")"+"/"+to+from+"("+returnflight+")";
	Thread.sleep(2000);
	System.out.println(e);
	if(schedules.contains(e))
		{Thread.sleep(2000);
	excelreadwrite.insertData(currentTestName,
			commonUtility.getcurrentDateTime(), "CheckingACSResults",
			"Checking ACS Schedule",
			"", true,
			"", "ACS Schedule is present",
			"ACS Schedule should present");
	Thread.sleep(3000);
	}
	else{
		excelreadwrite.insertData(currentTestName,
			commonUtility.getcurrentDateTime(), "CheckingACSResults",
			"Checking ACS Schedule",
			"", true,
			"", "Matching ACS Results not found",
			"Matching ACS Results not found");}
		}
	
	
	catch (Exception HandlingFeeError){
		excelreadwrite.insertFailedData(currentTestName,
				commonUtility.getcurrentDateTime(), "ACS CacheResults",
				"Trying for matching ACS Results",
				"", true,
				"", "Some issue occured which checking the ACS results",
				"ACS Results are not checking");
	Assert.assertFalse(true,"Not able to entered the date properly");}
	}}