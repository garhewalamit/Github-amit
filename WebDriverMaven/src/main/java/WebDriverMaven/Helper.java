package WebDriverMaven;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;

public class Helper {

	static WebDriver driver = null;
	
	public static WebDriver createDriver(String browser){	
		
		if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver",
					"D:\\Eclipse-projects\\webdriver\\geckodriver-v0.19.1-win64\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			options.setAcceptInsecureCerts(true);
			driver = new FirefoxDriver(options);
		}else if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver","D:\\Eclipse-projects\\webdriver\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			options.addArguments("test-type");	
			options.addArguments("disable-infobars");
			options.setHeadless(true);
			options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
			options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setAcceptInsecureCerts(true);
			//driver = new RemoteWebDriver(new URL("http://10.x.x.x:4444/wd/hub"), options);
			driver = new ChromeDriver(options);
			System.out.println(((ChromeDriver)driver).getCapabilities());
			//TakesScreenshot ts = new FirefoxDriver();
			
			/*http://www.seleniumeasy.com/selenium-tutorials/using-chrome-options-for-webdriver-tests
			 * ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URL("http://10.x.x.x:4444/wd/hub"), options);*/
		}else{
			driver = new HtmlUnitDriver();//it does not implement TakesScreenShot interface
		}	
		
		return driver;
	}
	
	public static WebDriver getDriver (){
		if(driver == null) createDriver("chrome");
		return driver;
	}

	public static void takeScreenShot(WebDriver driver, String outFile) {
		//check for null driver
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile=new File(outFile);
		try {
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
