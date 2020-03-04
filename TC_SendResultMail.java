package his_NEW;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;	
import java.util.Set;

import pages.his.FirstPage;
import pages.his.SearchPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.his.HISCommonFns;
import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;
import controls.ExcelRead;

public class TC_SendResultMail extends DriverSetup {
	private static final String String = null;
	public FirstPage fpage;
 HISCommonFns commonpageHIS;
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
		 System.out.println("Inside RT Search script1");
		 commonpageHIS = new HISCommonFns(driver, excelreadWrite, xlsRead);
	}
	
	/*
	@DataProvider(name = "TC_PView_RTSearch")
    public Object[][] createData2() throws Exception {

//                  String s2 = System.getProperty("user.dir");
                    String s2 = System.getProperty("user.dir");
                    String path = s2 + "\\src\\resources\\HIS-TestData.xls";
                    System.out.println("Inside RT Search script2");
                    System.out.println("path :" + path);

                    Object[][] retObjArr = excelRead.getTableArray(path, "TC",
                                                    "TC_PView_RTSearch");

                    return (retObjArr);
    }
    
    */
	
	public WebDriver getDriver() {
		System.out.println("inside getDriver() (DriverSetup.java)");
		return driver;
	}

	public void setDriver(WebDriver driver) {
		System.out.println("inside setDriver() (DriverSetup.java)");
		this.driver = driver;
	}
	
	
	/*

	@Test(dataProvider = "TC_PView_RTSearch")
	public void test(String origin,String destination, String departuredate, String arrivaldate, String adult,String child,String infant)  throws InterruptedException
	{
		 
		
		try
		{
		String url= getTestURL();	
		/*
		
		int rtSearch = logipagevariable.RTSearch(url, origin,destination, departuredate, arrivaldate, adult, child, infant);
		if(rtSearch== 0){
			System.out.println("Success");
		}else{
			System.out.println("Failed");
		}
		

		System.out.println("Trying to call Common script RT Search");
		int prodcount = commonpageHIS.RTSearch(url, origin,destination, departuredate, arrivaldate, adult, child, infant);
		System.out.println("Common script execution completed");
		
		if ( prodcount >0 )
		{
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "RT Search", "Verify whether RT Search working fine", "RT Search is working", true, "Same as Expected", "RT Search gave results", "RT Search should give products");
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "RT Search", "Checking number of products returned", "RT Search is working", true, "Same as Expected", "Number of RT product: "+prodcount, "RT Search should give products");
		
		System.out.println("Trying to call Common script RT Modify Search");
		int Modifyprodcount = commonpageHIS.RTModifySearch();

		
		if ( Modifyprodcount >0 )
		{
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "RT Modify Search", "Verify whether RT Modify Search working fine", "RT Modify Search is working", true, "Same as Expected", "RT Modify Search gave results", "RT Modify Search should give products");
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "RT Modify Search", "Checking number of products returned", "RT Modify Search is working", true, "Same as Expected", "Number of RT product: "+Modifyprodcount, "RT Modify Search should give products");
		}		
		
		}
		
		else
		{
			excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "RT Modify Search", "Verify whether RT Modify Search working fine", "RT Modify Search Fail", true, "Not Same as Expected", "RT Modify Search Fail", "RT Modify Search should give products");	
		}
		}
		
		catch(Exception e)
		{
				e.printStackTrace();
				excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "RT Search", "Verify whether RT Search working fine", "RT Search Fail", true, "Not Same as Expected", "RT Search Fail", "RT Search should give products");
				Assert.assertFalse(true, "RT Modify Search Fail");
				System.out.println("RT Modify Search Fail");
		}
		
		 driver.manage().deleteAllCookies();
		    Thread.sleep(8000);
		
		
		}
		
		*/

	}
