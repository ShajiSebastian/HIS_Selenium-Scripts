package his_NEW;

/*import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Simulator {

	*//**
	 * @param args
	 *//*
	@Test
	public void laUNCHEs() {
		
		Map<String,String> mobileemulation=new HashMap<String,String>();
		mobileemulation.put("deviceName", "Apple iPhone 5");
		Map<Object,Object> chromoptions=new HashMap<Object,Object>();
		chromoptions.put("mobileEmulation", "mobileEmulation");
		
		
		DesiredCapabilities capabilities= DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromoptions) ;
		System.setProperty("webdriver.chrome.driver", "D:\\HIS\\HIS\\lib\\chromedriver.exe");
		WebDriver driver=new ChromeDriver(capabilities);
		driver.get("http://google.com");
		driver.quit();
		

	}

}
*/











import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.Destination;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.his.LoginPage;
import pages.his.SearchPage;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import controls.ExcelRead;


//public class MobileEmulation {
 //static DesiredCapabilities  capabilities;
 //static String DeviceName;
// public static void main(String[] args) throws InterruptedException
 
 
 
 public class MobileEmulation_OW_Search  extends DriverSetup {
	    static DesiredCapabilities  capabilities;
	    static String DeviceName;
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
		
			
		@DataProvider(name = "MobileEmulation_OW_Search")
		public Object[][] createData2() throws Exception {
		         
			String s2 = System.getProperty("user.dir");
			String path = s2 + "\\src\\resources\\HIS-TestData.xls";
			System.out.println("Test data sheet path :" + path);
			Object[][] retObjArr = excelRead.getTableArray(path, "TC",
					"MobileEmulation_OW_Search");
			System.out.println ("\nHIS-TestData file taken successfully");
			return (retObjArr);
			
		}
		

		// Trying to get Login Credentials for Store Agent page

		@Test(dataProvider = "MobileEmulation_OW_Search")
		public void MobileEmulation_RT_Search(String destination,String exp_date,String devicename)
				throws InterruptedException, IOException 
 
     {
			
			 StringBuilder b = new StringBuilder(destination.length());
			  Formatter f = new Formatter(b);
			  for (char c : destination.toCharArray()) {
			    if (c < 128) {
			      b.append(c);
			    } else {
			      f.format("\\u%04x", (int) c);
			    }
			  }
			System.out.println("VALUE:"+b.toString());  
		
			String dest= StringEscapeUtils.unescapeJava(b.toString());
			 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Mobile Emulation", "Checking for device", "Validating device", true, "Same as Expected", "Available device:"+devicename, "Device should be available"); 
		
			  //some Sample Devices. Complete list can be found here: https://code.google.com/p/chromium/codesearch#chromium/src/chrome/test/chromedriver/chrome/mobile_device_list.cc
			  //pick any of the device
			  
			//  DeviceName = "Google Nexus 5";
			//  DeviceName = "Samsung Galaxy S4";
			//  DeviceName = "Samsung Galaxy Note 3";
			//  DeviceName = "Samsung Galaxy Note II";
			//  DeviceName = "Apple iPhone 4";
			 // DeviceName = "Apple iPhone 5";
			//  DeviceName = "Apple iPad 3 / 4";
			  DeviceName = devicename;
			  String ChromeDriverPath = "C:\\Users\\A-6304\\Desktop\\chromedriver_win32\\chromedriver.exe";
			  //String ChromeDriverPath= System.getProperty("user.dir") + "/lib/chromedriver.exe";
			  System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
			  
			  Map<String, String> mobileEmulation = new HashMap<String, String>();
			  mobileEmulation.put("deviceName", DeviceName);
			  
			  Map<String, Object> chromeOptions = new HashMap<String, Object>();
			  chromeOptions.put("mobileEmulation", mobileEmulation);

			  capabilities = DesiredCapabilities.chrome();
			  capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			  WebDriver driver = new ChromeDriver(capabilities);
			  
			  driver.manage().window().maximize();
			  driver.get(getTestURL());
			 // driver.navigate().to("http://192.168.158.11:8280/fb/shop/airfaresearch.htm?ehn=TYO");
			  Thread.sleep(8000);
			  
			  driver.findElement(By.xpath("//*[@id='oneWay']")).click();
				 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Mobile Emulation", "Checking for OW option", "Clicking on OW option", true, "Same as Expected", "OW option is selected", "OW option should be available"); 		  
			  Thread.sleep(1000);
			  driver.findElement(By.id("destinationSId")).sendKeys(dest);
			  Thread.sleep(8000);
			  driver.findElement(By.xpath("//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content']/li")).click();
			 
			  Thread.sleep(800);
			  driver.findElement(By.id("alternateSfrom")).click();
			  Thread.sleep(800);
			
			
			
			
			
			//Date Selection
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date1 = new Date();
			String date_with_time=dateFormat.format(date1);
			String[] out = dateFormat.format(date1).split("/");
			int curr_year= Integer.parseInt(out[0]);
			int curr_month=Integer.parseInt(out[1]);
			System.out.println("Year = " +curr_year);
			System.out.println("Month = " +curr_month);
			System.out.println("Dest:"+destination);
			Thread.sleep(8000);
			
			//String exp_date="2018/01/1";
			String[] out1 = exp_date.split("/");
			int exp_year=Integer.parseInt(out1[0]);
			int exp_month=Integer.parseInt(out1[1]);
			
			System.out.println("Exp Year = " +exp_year);
			System.out.println("exp Month = " +exp_month);
			
			
 if(exp_year==curr_year)
  { 
	  int index1=exp_month-1;
	  String index =  String.valueOf(index1);
	  System.out.println("Index:"+index);
	  Select month = new Select(driver.findElement(By.xpath(".//*[@class='ui-datepicker-month']")));
	  month.selectByValue(index); 
  }
  
  if(exp_year>curr_year)
 {   
	 
	 int diff_month=12-curr_month;
	 
	 for(int i=0; i<=diff_month; i++)
	 {
		 System.out.println("Diff month:"+diff_month);
		  Thread.sleep(800);
		  driver.findElement(By.xpath(".//*[@class='ui-datepicker-next ui-corner-all']/span")).click();
		  Thread.sleep(800);
	}
	 
	  int index1=exp_month-1;
	  String index =  String.valueOf(index1);
	  System.out.println("Index:"+index);
	  Select month = new Select(driver.findElement(By.xpath(".//*[@class='ui-datepicker-month']")));
	  month.selectByValue(index); 
	 
	
 }
  
 
 // String arrivalday ="13";
 // String deptdate="26";
  By onwardXpath = By.xpath("//td[(contains(@class,'undefined'))]/a[text()='"+ out1[2] + "']");
  driver.findElement(onwardXpath).click();
  
  //By returnXpath = By.xpath("//td[(contains(@class,'undefined'))]/a[text()='"+ deptdate + "']");
 // driver.findElement(returnXpath).click();
  

	// Intentional pause for 2 seconds.
	Thread.sleep(2000);
	 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Mobile Emulation", "Checking for destination and date field ", "Entering destination and date field", true, "Same as Expected", "Destination and date field is entered", "Destination and date field should be  available"); 
    driver.findElement(By.id("shoppingSearchButton")).click();
   Thread.sleep(8000);
	 excelreadwrite.insertData(currentTestName, commonUtility.getcurrentDateTime(), "Mobile Emulation", "Checking for search button ", "Clicking on search button", true, "Same as Expected", "Search is working", "Search  should be  working");  
  
  
  

 }

}



















