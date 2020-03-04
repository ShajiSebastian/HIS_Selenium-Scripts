//package his_NEW;
package his_NEW;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;

public class TC_SA_AddOverSeasFeeRule extends DriverSetup {
	SearchPage SearchPage;
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
				
	         
		System.out.println("TC_SA_AddOverSeasFeeRule.java execution starts now");
		System.out.println("\nGetting current test case name (TC_SA_AddOverSeasFeeRule.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (TC_SA_AddOverSeasFeeRule.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);

		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);
	}
	
	@DataProvider(name = "TC_SA_AddOverSeasFeeRule")
	public Object[][] createData2() throws Exception {

	  	System.out.println("\nTrying to to read input test data from input datasheet (TC_SA_AddOverSeasFeeRule.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC","TC_SA_AddOverSeasFeeRule");
		return (retObjArr);
	}
	
	
	@Test(dataProvider = "TC_SA_AddOverSeasFeeRule")
	public void TC_SA_AddOverSeasFeeRuleRule(String eihon, String startDate,
			String fareType, String overSeasFee)
			throws InterruptedException, IOException {
		
		//Trying to get Login credentials from input data sheet
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
		

		System.out.println("\nTrying to invoke HIS page to Execute 'TC_SA_AddOverSeasFeeRule' Test Case  (TC_SA_AddOverSeasFeeRule.java)");
		System.out.println("----------------------------------------------------------------------\n");
		driver.get(getTestURL());
		System.out.println("\nVerifying Login Functionality");
	    System.out.println("*******************************");
	    
	    driver.findElement(By.xpath(".//*[@id='j_username']")).clear();	        
		driver.findElement(By.xpath(".//*[@id='j_username']")).sendKeys(userID);
		driver.findElement(By.xpath(".//*[@id='j_password']")).clear();	  
		driver.findElement(By.xpath(".//*[@id='j_password']")).sendKeys(password);
		
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		// Clicking Manage buisiness rule link
		try
		{
		
		driver.findElement(By.xpath(".//*[@id='menu']/ul/li[2]/a")).click();
		System.out.println ("Manage buisiness rule link clicked");
		
		//Writing the assertion status in output report			
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Manage buisiness rule link option",
						"", true,
						"", "Manage buisiness rule link option available",
						"Manage buisiness rule link option Should be available");
				
		//Clicking on Agent Fees link
		driver.findElement(By.xpath(".//*[@id='menu']/ul/li[2]/a")).click();
		System.out.println ("Agent Fees link clicked");
		
		//Writing the assertion status in output report	
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Agent Fees link option",
						"", true,
						"", "Agent Fees link option available",
						"Agent Fees link option Should be available");
			
		//Selecting Handling Fee Rule (Radio button)
		driver.findElement(By.cssSelector("input[value='O']")).click();
		System.out.println ("Overseas Fee Rule selected");
		
		//Writing the assertion status in output report	
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Overseas Fee option",
						"", true,
						"", " Overseas Fee Rule option available",
						" Overseas Fee Rule option Should be available");
				
		//Clicking on Add button
		driver.findElement(By.xpath(".//*[@id='adOvrsButton']")).click();
		System.out.println ("Add button clicked ");
		
		//Writing the assertion status in output report	
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Add button option",
						"", true,
						"", "Add button available",
						"Add button Should be available");
				
	
		MyWaitVar30Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='overseasFeeVO.eihonVO.eihonID']")));
		// Entering values in the popup
		//	Thread.sleep(5000);
		System.out.println("\nTrying to enter Eihon in Add Overseas Fee screen");
		System.out.println("------------------------------------");
		driver.findElement(By.xpath(".//*[@id='overseasFeeVO.eihonVO.eihonID']")).click();
		List<WebElement> eihons = driver.findElements(By
						.xpath(".//*[@id='overseasFeeVO.eihonVO.eihonID']//option"));
				//looping through each options in the List
				for (WebElement menu : eihons) 
				{
					//comparing the dropdown text with the text read from input data sheet
					if (menu.getText().trim().equalsIgnoreCase(eihon.trim())) 
					{
					
						System.out.println("Input Eihon: "+eihon.trim());
						System.out.println("Selected Eihon in Add rule screen: "+menu.getText().trim());
						Actions action = new Actions(driver);
						action.sendKeys(menu, Keys.ENTER).build().perform();
						Thread.sleep(1000);
						break;
					}
					
				}
		
		//Writing the assertion status in output report	
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Eihon field",
						"", true,
						"", "Eihon field available",
						"Eihon field Should be available");
				
		System.out.println("\nTrying to enter Booking start date");
		System.out.println("------------------------------");
		driver.findElement(By.xpath(".//*[@id='alternateaddFrom']")).sendKeys(startDate);
		
		//Writing the assertion status in output report	
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Booking start date field",
						"", true,
						"", "Booking start date field available",
						"Booking start date field Should be available");
				
				
		System.out.println("\nTrying to enter Fare Type");
		System.out.println("---------------------------");
		driver.findElement(By.xpath(".//*[@id='overseasFeeVO.fareType']")).click();
		MyWaitVar5Sec.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='overseasFeeVO.fareType']//option")));
		//Thread.sleep(5000);
		List<WebElement> fareTypes = driver.findElements(By.xpath(".//*[@id='overseasFeeVO.fareType']//option"));
				//looping through each options in the List
				for (WebElement menu1 : fareTypes) 
				{
					System.out.println("Dropdown fareType: "+menu1.getText().trim());
					System.out.println("Excel fareType: "+fareType.trim());
					//comparing the dropdown text with the text read from input data sheet
					if (menu1.getText().trim().equalsIgnoreCase(fareType.trim())) 
					{
					
						System.out.println("Input FareType: "+fareType.trim());
						System.out.println("Selected FareType in Add rule screen: "+menu1.getText().trim());
						Actions action = new Actions(driver);
						action.sendKeys(menu1, Keys.ENTER).build().perform();
						Thread.sleep(1000);
						break;
					}
				}
				
		//Writing the assertion status in output report	
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking the presence of Fare Type field",
						"", true,
						"", " Fare Type field available",
						" Fare Type field Should be available");
				
		driver.findElement(By.xpath(".//*[@id='feeAmount']")).sendKeys(overSeasFee);
		Thread.sleep(2000);
				
		// Clicking on Save button
		driver.findElement(By.xpath(".//*[@id='popupSaveButton']")).click();
		System.out.println ("Save button clicked");
					
		//Reading the message shown in Alert window
		isAlertPresent();
		handleAlert();
				
		}
		catch (Exception HandlingFeeError)
		{
		//Writing the assertion status in output report	
		excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Buisiness rule Module",
					"Trying to Add OverSeas Fee rule",
					"", true,
					"", "Some issues happend. Rule not added",
					"Overseas Rule should be added successfully");
		Assert.assertFalse(true,"In Fare matrix display actual text is not valid");
		}
}
	
	
	public void handleAlert() throws InterruptedException
	{
	//Checking whether alert is present
	 if(isAlertPresent())
	 {
		  // if alert is present
			System.out.println ("Alert message found");
		    String alertMessage= driver.switchTo().alert().getText();
			System.out.println ("Alert is "+alertMessage);
			
			if (alertMessage.toLowerCase().trim().contains("Added Successfully".toLowerCase().trim())) 
			{
			//Writing the assertion status in output report		
			excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "Buisiness rule module",
						"Checking whether we can Save rule",
						"", true,
						"", " Overseas Fee Rule added successfully",
						" Rule Should be added successfully");
			}
	
			else
			{
			//Writing the assertion status in output report		
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Buisiness rule Module",
					"Trying to Add Overseas rule",
					"", true,
					"", alertMessage,
					"Rule should be added successfully");
			Assert.assertFalse(true,"In Fare matrix display actual text is not valid");
		
			}
			driver.switchTo().alert().accept();
	 }
	 
	 else
	 {
		//if alert is not present	
			System.out.println ("Alert is NOT present");
			
			try
			{
			String handFeeError = driver.findElement(By.xpath(".//*[@id='overseasFeeForm.errors']")).getText();
			System.out.println ("Error:" +handFeeError);
			
			//Writing the assertion status in output report	
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Buisiness rule Module",
					"Trying to Add Handling Fee rule",
					"", true,
					"", handFeeError,
					"Rule should be added successfully");
			Assert.assertFalse(true,"In Fare matrix display actual text is not valid");
			}
			catch (Exception OverSeasFee)
			{
				
			//Writing the assertion status in output report	
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "Buisiness rule Module",
					"Trying to Add Handling Fee rule",
					"", true,
					"", "Some issues occured. Overseas Fee not added Successfully",
					"Rule should be added successfully");
			Assert.assertFalse(true,"In Fare matrix display actual text is not valid");
			}
			
			
	  }
	}
	public boolean isAlertPresent() throws InterruptedException
	{
	 try
	 {
		//	Thread.sleep(5000);
	 MyWaitVar5Sec.until(ExpectedConditions.alertIsPresent());
	  driver.switchTo().alert();
	  return true;
	 }
	 catch(NoAlertPresentException ex)
	 {
	  return false;
	 }
	}


}


