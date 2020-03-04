package his_NEW;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedConditions;// This is used for explicit wait
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait; // This is used for explicit wait

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit; // this is used for implicit wait
import java.util.Iterator;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Set;

//import javax.swing.text.html.HTMLDocument.Iterator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.sun.xml.internal.ws.api.pipe.NextAction;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;


public class TC_NFE_CreateFareRule extends DriverSetup {
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
		
		System.out.println("VerifyVerifyNFEFareRuleProduct.java execution starts now");
		System.out.println("\nGetting current test case name (VerifyNFEFareRuleProduct.java");
		System.out.println("-------------------------------");
		currentTestName = getTestName();
		System.out.println("\nTest Case name is :" +currentTestName+"\n");
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		System.out.println("\nTrying to create a Report file with Test Case name in /reports/xls (VerifyNFEFareRuleProduct.java");
		System.out.println("-----------------------------------------------------------------");
		excelreadwrite = new ExcelReadWrite(currentTestName, driver,
				getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		SearchPage = new SearchPage(driver, excelreadwrite, xls_Read);
		

		MyWaitVar30Sec = new WebDriverWait(driver,30);
		MyWaitVar5Sec = new WebDriverWait(driver,5);

	}
	@DataProvider(name = "TC_NFE_CreateFareRule")
	//@DataProvider (name="VerifyNFEFareRuleProduct");
	public Object[][] createData2() throws Exception {

		System.out.println("\nTrying to to read input test data from input datasheet (VerifyNFEFareRuleProduct.java -->ExcelRead.java");
		System.out.println("--------------------------------------------------------");
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\src\\resources\\HIS-TestData.xls";
		System.out.println("Test data sheet path :" + path);
		Object[][] retObjArr = excelRead.getTableArray(path, "TC","TC_NFE_CreateFareRule");
		return (retObjArr);
	}
	/**
	 * @param NoOfAdults
	 * @param NoOfChild
	 * @param NoOfInfant
	 * @param fare
	 * @param tax
	 * @param airline
	 * @param Bookingdate
	 * @param Bookingenddate
	 * @param bookingclass
	 * @param origin
	 * @param dest
	 * @param pax
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dataProvider="TC_NFE_CreateFareRule")
	public void VerifyNFEFareRuleProduct1(String NoOfAdults,String NoOfChild, String NoOfInfant, String fare,
			String tax, String airline,String Bookingdate,String Bookingenddate,String bookingclass,String origin, String dest,String pax)
			throws InterruptedException, IOException {
		System.out.println(NoOfAdults);
		System.out.println(NoOfChild);
		System.out.println(NoOfInfant);
		System.out.println(fare);
		System.out.println(tax);
		System.out.println(airline);
		System.out.println(Bookingdate);
		System.out.println(Bookingenddate);
		System.out.println(bookingclass);
		System.out.println(origin);
		System.out.println(dest);
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
		
		
		System.out.println("\nTrying to invoke HIS page to Execute 'VerifyNFEFareRuleProduct' Test Case  (VerifyNFEFareRuleProduct.java)");
		System.out.println("----------------------------------------------------------------------\n");
			
		driver.get(getTestURL());
		
		System.out.println("\nVerifying Login Functionality");
	    System.out.println("*******************************");
	    driver.findElement(By.xpath(".//*[@id='j_username']")).clear();	        
		driver.findElement(By.xpath(".//*[@id='j_username']")).sendKeys(userID);
		driver.findElement(By.xpath(".//*[@id='j_password']")).clear();	  
		driver.findElement(By.xpath(".//*[@id='j_password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		//NFE page
		try
		{
			//driver.switchTo().window(first);
				//String winHandleBefore = driver.getWindowHandle();
		driver.findElement(By.linkText("Negotiated Fare Engine")).click();
		Thread.sleep(1000);
		System.out.println ("Negotiated Fare Screen");
				
		excelreadwrite.insertData(currentTestName,
						commonUtility.getcurrentDateTime(), "NFE Screen",
						"Checking the presence of NFE Screen",
						"", true,
						"", "NFE Screen rule link option available",
						"NFE Screen rule link option Should be available");
		
		  //for(String winHandle : driver.getWindowHandles()){
	        //.switchTo().window(winHandle); 
		   Set <String> set=driver.getWindowHandles();  
		   Iterator<String> it=set.iterator();
		   String first=it.next();
		   String second=it.next();
		   System.out.println(first);
		   System.out.println(second);
		   Thread.sleep(5000);
		   driver.switchTo().window(second);
		   
		//Get all the window handles in a set
		Thread.sleep(10000);
        	driver.findElement(By.xpath(".//*[@id='addGroup']/a")).click();
        	Thread.sleep(1000);
    		excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE Screen",
					"Checking whether control is in NFE ADD Fare Screen",
					"", true,
					"", "Control shifted to Add fare and link should be clicked in NFE Screen",
					"Control should shift to Add fare and link should be clicked in NFE Screen");
        	driver.findElement(By.xpath(".//*[@id='pdtRegMenuItmA']/a")).click();
        	Thread.sleep(2000);
        	excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE Screen",
					"Checking NFE Product registration Screen",
					"", true,
					"", "NFE Product Registration screen shown after clicked on the link",
					"NFE Product Registration screen should come after clicked on the link");
        	
        	String nfeid=driver.findElement(By.id("farePdtId")).getAttribute("value");
        	System.out.println(nfeid);
        	Thread.sleep(2000);
        	driver.findElement(By.xpath(".//*[@id='ticketingAirline']")).sendKeys(airline);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='alternatefrom']")).sendKeys(Bookingdate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='alternatefrompopup']")).sendKeys(Bookingdate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='alternatefrompopupdept']")).sendKeys(Bookingdate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='advancePurchaseDays']")).sendKeys("0");
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='airlineticket']")).click();
        	Thread.sleep(1000);
        	//driver.findElement(By.xpath(".//*[@id='startDatedemo0']")).sendKeys(Bookingdate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='endDatedemo0']")).sendKeys(Bookingenddate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='startDateTrvdemo0']")).sendKeys(Bookingdate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='endDateTrvdemo0']")).sendKeys(Bookingenddate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='copySA']")).click();
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='fareMenuItmA']/a")).click();
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='alternateFlightFrom0']")).sendKeys(Bookingdate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='alternateFlightTo0']")).sendKeys(Bookingenddate);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='bookingClass1_0']")).sendKeys(bookingclass);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='grossFareForStoreAgentAdult_0']")).sendKeys(fare);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='grossFareForOnlineCustomerAdult_0']")).sendKeys(fare);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='grossFareForOTAAdult_0']")).sendKeys(fare);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='routeMenuItmA']/a")).click();
        	Thread.sleep(1000);
        	excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE Screen",
					"Checking NFE Route Screen",
					"", true,
					"", "NFE Route Screen shown after clicked on the link",
					"NFE Route Screen screen should come after clicked on the link");
        	driver.findElement(By.xpath(".//*[@id='originList_viewallpopup_0']")).sendKeys(origin);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='destnList_addzonepopup_0']")).sendKeys(dest);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='bkgMenuItmA']/a")).click();
        	Thread.sleep(1000);
        	
        	driver.findElement(By.xpath(".//*[@id='sourceOfContract']")).sendKeys(airline);
        	Thread.sleep(1000);
        	Select select = new Select(driver.findElement(By.xpath(".//*[@id='bookingCRS']")));
        	Thread.sleep(1000);
        	select.selectByVisibleText("Amadeus");
        	Thread.sleep(1000);
        	Select select1 = new Select(driver.findElement(By.xpath(".//*[@id='bookingCRSSA']")));
        	Thread.sleep(1000);
        	select1.selectByVisibleText("Amadeus");
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='taxSurMenuItmA']/a")).click();
        	Thread.sleep(1000);
        	excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE Screen",
					"Checking NFE Tax Screen",
					"", true,
					"", "NFE NFE Tax Screen shown after clicked on the link",
					"NFE NFE Tax Screen should come after clicked on the link");
        	driver.findElement(By.xpath(".//*[@id='taxSurchargeConfigH4']")).click();
        	Thread.sleep(1000);
    
     	Select sel = new Select(driver.findElement(By.xpath(".//*[@id='originCod0']")));
        	Thread.sleep(1000);
        	sel.selectByVisibleText(origin);
        	Thread.sleep(1000);
        	Select sel1 = new Select(driver.findElement(By.xpath(".//*[@id='destCod0']")));
        	Thread.sleep(1000);
        	sel1.selectByVisibleText(dest);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='totTaxRqd0']")).click();
        	Thread.sleep(3000);
        	System.out.println("PAXTYPE SELECT Before");
         	Select typepax=new Select(driver.findElement(By.xpath("(//select[@id='paxType'])[2]")));
        	//driver.findElement(By.xpath(".//*[@id='paxType']")).click();
        	System.out.println("PAXTYPE SELECT");
        	typepax.selectByVisibleText(pax);
        	//driver.findElement(By.xpath(".//*[@id='paxType']")).click();
        	//driver.findElements(By.xpath("//option[@value='ADT']"));
    /*   List<WebElement> fromCityOptions = driver.findElements(By
    				.xpath(".//*[@id='paxType']"));
    		
    		for (WebElement menu : fromCityOptions) {
    			if (menu.getText().trim().equalsIgnoreCase(pax.trim())) {
    			
    				System.out.println("Input From City/Airport: "+pax.trim());
    				System.out.println("Selected From City/Airport: "+menu.getText().trim());
    				
    				Thread.sleep(1000);

    				Actions action = new Actions(driver);
					
    				action.sendKeys(menu, Keys.ENTER).build().perform();
					
    				Thread.sleep(1000);
					
    				break;
    			}
    		}
    		*/
        	Thread.sleep(1000);
        	
        	System.out.println("PAXTYPE SELECT DONE");
        	Thread.sleep(1000);
        	driver.findElement(By.xpath("(.//*[@id='taxCod'])[2]")).sendKeys("SW");
        	Thread.sleep(1000);
        	driver.findElement(By.xpath("(.//*[@id='taxAmt'])[2]")).sendKeys(tax);
        	Thread.sleep(1000);
        	driver.findElement(By.xpath("(.//*[@id='curCod'])[2]")).sendKeys("JPY");
        	Thread.sleep(1000);
        	driver.findElement(By.xpath(".//*[@id='saveTaxSurcharge']")).click();
        	Thread.sleep(1000);
        	
        	Alert alret=driver.switchTo().alert();
        	String a=alret.getText();
        	alret.accept();
        	Thread.sleep(2000);
        	excelreadwrite.insertData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE Screen",
					"Checking NFE Tax Screen",
					"", true,
					"", "Rule has added successfully",
					"NFE Rule Should be added successfully");
        	String b="Added Successfully";
	          if (a.contains(b))
	        	  {
	        	  System.out.println("NFE Product is"+nfeid);
	        	  excelreadwrite.insertData(currentTestName,
	  					commonUtility.getcurrentDateTime(), "NFE Screen",
	  					"Checking NFE Tax Screen",
	  					"", true,
	  					"", "NFEFareRulewithproductID:"+nfeid+ " has added successfully",
	  					"NFE Rule Should be added successfully");
	        	  driver.switchTo().window(first);
	        	  
	        	  Thread.sleep(1000);
	        	  };
	        	  
	        	  
	       }
		catch (Exception HandlingFeeError){
			excelreadwrite.insertFailedData(currentTestName,
					commonUtility.getcurrentDateTime(), "NFE Fare Creation",
					"Trying to create NFE rule",
					"", true,
					"", "Some issues happend. Rule not added",
					"NFE Fare Rule should be added successfully");
		Assert.assertFalse(true,"Not able to entered the date properly");
		}
}}