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

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;
import java.util.Arrays;
//import controls.*;
public class TC_NFE_VerifyUploadedACSfile extends DriverSetup {
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
				
	         
		System.out.println("\ninside setup() fn (TC_NFE_VerifyUploadedACSfile.java)");
		System.out.println("TC_NFE_VerifyUploadedACSfile.java execution starts now");
		System.out.println("\nGetting current test case name (TC_NFE_VerifyUploadedACSfile.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
	//	drop = new DropDown(driver,del);
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (TC_NFE_VerifyUploadedACSfile.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

	}
	
	@DataProvider(name = "TC_NFE_VerifyUploadedACSfile")
	public Object[][] createData2() throws Exception 
	{
	         
		System.out.println("inside TC_NFE_VerifyUploadedACSfile.java");
		System.out.println("\nTrying to to read input test data from input datasheet (TC_NFE_VerifyUploadedACSfile.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		System.out.println("The identification text of test data to be given below function. If not mentioned correctly TC will skip");
		Object[][] retObjArr = excelRead.getTableArray(path, "TC",
				"TC_NFE_VerifyUploadedACSfile");
	
		return (retObjArr);
	}
	@Test(dataProvider = "TC_NFE_VerifyUploadedACSfile")
	//@Test(dataProvider = "VerifySortByArrival_ReturnEarlierToLater")
	//public void TC_NFE_VerifyUploadedACSfile(String location) throws InterruptedException 
	public void VerifyACSfile(String uploadfilelocation)
			throws InterruptedException, IOException {
		try{
	    System.out.println(uploadfilelocation);
		System.out.println("Trying to get Login Credentials for Store Agent page"); 
	 	
	 	String s2 = System.getProperty("user.dir");
		String path= s2 + "\\src\\resources\\HIS-TestData.xls";
		String sheetName= "Login_Store";
		
		File file =    new File(path);
		//File file =    new File("D:\\Shaji\\Selenium\\SeleniumScriptsFiles"+"\\"+"HIS_TestData.xlsx");	   

		//Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);
		Workbook vscWorkbook = null;
		vscWorkbook = new HSSFWorkbook(inputStream);
			

		//Read sheet inside the workbook by its name
		Sheet vscSheet = vscWorkbook.getSheet(sheetName);

		//Create a loop over all the rows of excel file to read it

		System.out.println ("Login credentials as per test data sheet");
							
				Row row1 = vscSheet.getRow(1);
				String userID = row1.getCell(0).getStringCellValue();
				String password = row1.getCell(1).getStringCellValue();
				System.out.println ("   User ID  :" + userID);
				System.out.println ("   Password :" + password);
		
		
	//	System.out.println("\nTrying to invoke HIS page to Execute 'VerifyAddUrgentFee' Test Case  (VerifyAddOverSeasFee.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		driver.get(getTestURL());
		
		System.out.println("\nVerifying Login Functionality");
	    System.out.println("*******************************");
	    
	    driver.findElement(By.xpath(".//*[@id='j_username']")).clear();	        
		driver.findElement(By.xpath(".//*[@id='j_username']")).sendKeys(userID);
		driver.findElement(By.xpath(".//*[@id='j_password']")).clear();	  
		driver.findElement(By.xpath(".//*[@id='j_password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Thread.sleep(2000);
		try{
			driver.findElement(By.xpath(".//*[@id='menu']/ul/li[5]/a")).click();
			Thread.sleep(1000);
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE module",
					"Checking the presence of NFE link option",
					"", true,
					"", "NFE link option available",
					"NFE link option Should be available");
			
			 Set <String> set=driver.getWindowHandles();  
			   Iterator<String> it=set.iterator();
			   String first=it.next();
			   String second=it.next();
			   System.out.println(first);
			   System.out.println(second);
			   Thread.sleep(5000);
			   driver.switchTo().window(second);
			   
			//Get all the window handles in a set
			Thread.sleep(5000);
			driver.findElement(By.id("confGroup")).click();
			driver.findElement(By.linkText("Upload ACS Data")).click();
			Thread.sleep(10000);
			//driver.findElement(By.id("csvFile")).sendKeys(uploadfile);
			driver.findElement(By.id("csvFile")).click();
			Thread.sleep(7000);
			Runtime.getRuntime().exec(uploadfilelocation);//*************************
			Thread.sleep(7000);
			driver.findElement(By.xpath(".//*[@id='uploadACSData']/div/div/div[2]/input[2]")).click();
			Thread.sleep(7000);
			String message= driver.switchTo().alert().getText();
			String expectedmessage="ACS Data has been uploaded Successfully!!";
			if(message.contains(expectedmessage))
			{
				driver.switchTo().alert().accept();
				excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "NFE",
						"ACS Data File Upload ",
						"", true,
						"", "ACS Data Uploaded successfully",
						"ACS File should be uploaded successfully");
			driver.switchTo().window(first);
			Thread.sleep(300000);
			}
			else
		{
				excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE",
					"Trying to Upload file",
					"", true,
					"", "Some issues happend. File not Not uploaded",
					"ACS File should be uploaded successfully");
				Assert.assertFalse(true,"Some issues happend. File not Not uploaded");
				}
		}
		catch (Exception HandlingFeeError){
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE",
					"Trying to Upload file",
					"", true,
					"", "Some issues happend. File not Not uploaded",
	
					"ACS File should be uploaded successfully");
			Assert.assertFalse(true,"Some issues happend. File not Not uploaded");
		}
		
	}
		catch(Exception e)
		{
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE",
					"Trying to Upload file",
					"", true,
					"", "Some issues happend. File not Not uploaded",
	
					"ACS File should be uploaded successfully");
			Assert.assertFalse(true,"Some issues happend. File not Not uploaded");
		}}
	}