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


import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;
import controls.ExcelRead;

public class TC_PView_OverseasSearch extends DriverSetup {
	private static final String String = null;
	public FirstPage fpage;
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
		
	}
		
	@DataProvider(name = "TC_PView_OverseasSearch")
    public Object[][] createData2() throws Exception {

//                    String s2 = System.getProperty("user.dir");
                    String s2 = System.getProperty("user.dir");
                    String path = s2 + "\\src\\resources\\HIS-TestData.xls";

                    System.out.println("path :" + path);

                    Object[][] retObjArr = excelRead.getTableArray(path, "TC",
                                                    "TC_PView_OverseasSearch");

                    return (retObjArr);
    }

	@Test(dataProvider = "TC_PView_OverseasSearch")
	public void test(String origin,String destination, String departuredate, String arrivaldate, String adult,String child,String infant)
	{
		try{
			
			driver.navigate().to(getTestURL());
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			Thread.sleep(5000);
			
			//driver.get(getTestURL());	
			
			
			
			System.out.println("Going to click Overseas text link");
					
			driver.findElement(By.xpath(".//*[@id='overseaText']/a")).click();
			
			System.out.println("Overseas text link clicked");
			Thread.sleep(1000);
			driver.findElement(By.id("originSId")).clear();
			driver.findElement(By.id("originSId")).sendKeys(origin);
			excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Overseas Search", "Clicking Overseas link", "Checking whether Overseas link working", true, "Same as Expected", "Overseas link working", "Overseas link should be working");			
				driver.findElement(By.id("destinationSId")).clear();			
			    
				driver.findElement(By.id("destinationSId")).sendKeys(destination);
				driver.findElement(By.xpath("html/body/ul[2]/li[1]")).click();
				Thread.sleep(800);
				driver.findElement(By.id("alternateSfrom")).sendKeys(departuredate);
				Thread.sleep(800);
				driver.findElement(By.id("alternateSto")).sendKeys(arrivaldate);
				Thread.sleep(800);
				driver.findElement(By.name("fareSearchVO.noOfAdults")).sendKeys(adult);
				Thread.sleep(800);
				driver.findElement(By.name("fareSearchVO.noOfChild")).sendKeys(child);
				Thread.sleep(800);
				driver.findElement(By.name("fareSearchVO.noOfInfants")).sendKeys(infant);
				Thread.sleep(800);
				driver.findElement(By.xpath(".//*[@class='btn btn-lg btn-warning submitBtn']")).click();
			    Thread.sleep(20000);
				int adults =Integer.parseInt(adult);
				int children =Integer.parseInt(child);
				int infants =Integer.parseInt(infant);
				System.out.println("Adult count:"+adults);
				System.out.println("Child count:"+children);
				System.out.println("Infant count:"+infants);
			
				
			String proCountBasicListing=driver.findElement(By.xpath(".//*[@class='sortPaxDetails hidden-xs']//strong")).getText();
			System.out.println("Product count in basic listing page1:"+proCountBasicListing);
			String proCountBasicListings=proCountBasicListing.replace(",", "").trim();
			int q=Integer.parseInt(proCountBasicListings);
			System.out.println("Product count:"+q);
			Thread.sleep(800);
			excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Overseas Search", "Entering search criteria", "Performing search", true, "Same as Expected", "No: of products displayed:"+q, "Products should be available");
			
		//String originOfProduct = driver..//*[@id='totPrdtSizeDiv']/div[1]/div[1]/div[1]/div[3]/div/div[1]/div[1]
		
	
		Boolean isBookButtonPresent=  driver.findElements(By.xpath(".//*[@class='btn btn-primary btn-lg']")).size()>0;
	       
		if (isBookButtonPresent== true)
		{
		
		driver.findElement(By.xpath(".//*[@class='btn btn-primary btn-lg']")).click();
		Thread.sleep(2000);
		
		

		//wait until page is loaded
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
		    public Boolean apply(WebDriver d) {
		        return d.getCurrentUrl().startsWith("http://e-kensho.his-j.com");
		    }
		});
		
		Thread.sleep(2000);
		
		 String SCUrl2= driver.getCurrentUrl();
		 System.out.println("SC Url2:"+SCUrl2);
		 excelreadWrite.insertData(currenttestName, commonUtility.getcurrentDateTime(), "Back From SC page", "Clicking on book button", "Navigating to SC Page", true, "Same as Expected", "SC page is available", "SC page should be available after clicking book button");
	 
	}	
		 
		 driver.manage().deleteAllCookies();
		 Thread.sleep(2000);
		}	
		
		
		catch(Exception e){
			e.printStackTrace();
			excelreadWrite.insertFailedData(currenttestName, commonUtility.getcurrentDateTime(), "Overseas Search", "Verify whether Overseas Search working ", "Overseas Search Failed", true, "Not Same as Expected", "Overseas Search FAIL", "Overseas search should be working");
			Assert.assertFalse(true, "Overseas search FAIL");
			System.out.println("Overseas search Script FAIL");
			
			
			
	}
		
		
	}
	
}
