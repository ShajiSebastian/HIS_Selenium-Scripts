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

public class TC_PView_OWSearch extends DriverSetup {
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
		 System.out.println("Inside OW Search script1");
		 commonpageHIS = new HISCommonFns(driver, excelreadWrite, xlsRead);
	}
	
	
	@DataProvider(name = "TC_PView_OWSearch")
    public Object[][] createData2() throws Exception {

//                  String s2 = System.getProperty("user.dir");
                    String s2 = System.getProperty("user.dir");
                    String path = s2 + "\\src\\resources\\HIS-TestData.xls";
                    System.out.println("Inside RT Search script2");
                    System.out.println("path :" + path);

                    Object[][] retObjArr = excelRead.getTableArray(path, "TC",
                                                    "TC_PView_OWSearch");

                    return (retObjArr);
    }
	
	public WebDriver getDriver() {
		System.out.println("inside getDriver() (OWSearch.java)");
		return driver;
	}

	public void setDriver(WebDriver driver) {
		System.out.println("inside setDriver() (OWSearch.java)");
		this.driver = driver;
	}

	@Test(dataProvider = "TC_PView_OWSearch")
	public void test(String origin,String destination, String departuredate, String arrivaldate, String adult,String child,String infant) throws InterruptedException
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
		
		*/
		System.out.println("Trying to call Common script OW Search");
		int prodcount = commonpageHIS.OWSearch(url, origin,destination, departuredate, adult, child, infant);
		System.out.println("Common script execution for OW completed");
		
		if ( prodcount >0 )
		{
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "OW Search", "Verify whether OW Search working fine", "OW Search is working", true, "Same as Expected", "OW Search gave results", "OW Search should give products");
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "OW Search", "Checking number of products returned", "OW Search is working", true, "Same as Expected", "Number of OW product: "+prodcount, "OW Search should give products");
		}
		
		System.out.println("Trying to call Common script OW Modify Search");
		int Modifyprodcount = commonpageHIS.OWModifySearch();

		
		if ( Modifyprodcount >0 )
		{
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "OW Modify Search", "Verify whether OW Modify Search working fine", "OW Modify Search is working", true, "Same as Expected", "OW Modify Search gave results", "OW Modify Search should give products");
		excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "OW Modify Search", "Checking number of products returned", "OW Modify Search is working", true, "Same as Expected", "Number of OW product: "+Modifyprodcount, "OW Modify Search should give products");
		}		

		
		else
		{
			excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "OW Search", "Verify whether OW Search working fine", "OW Search Fail", true, "Not Same as Expected", "RT Search Fail", "OW Search should give products");	
		}
		}
		
		catch(Exception e)
		{
				e.printStackTrace();
				excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "OW Search", "Verify whether OW Search working fine", "OW Search Fail", true, "Not Same as Expected", "OW Search Fail", "OW Search should give products");
				Assert.assertFalse(true, "OW Search Fail");
				System.out.println("OW Search Fail");
		}
		
		 driver.manage().deleteAllCookies();
		    Thread.sleep(8000);
		
		}
	

	}
