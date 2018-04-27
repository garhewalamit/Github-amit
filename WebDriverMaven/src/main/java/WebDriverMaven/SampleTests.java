package WebDriverMaven;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SampleTests {
	private WebDriver driver;
	private String baseUrl = "http://demo.guru99.com/test/newtours/";

	@Test(enabled = true)
	public void testMethod1() {
		String expectedTitle = "Welcome: Mercury Tours";
		String actualTitle = driver.getTitle();
		AssertJUnit.assertEquals(actualTitle, expectedTitle);
	}

	@Test
	public void testMethod2() {
		// WebElement e =
		// driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr/td[2]/a"));
		WebElement e = driver.findElement(By.xpath("//a[@href = 'register.php']"));
		System.out.println("Instance of remote web element - " + (e instanceof RemoteWebElement));
		e.click();		
		AssertJUnit.assertEquals(driver.getTitle(), "Register: Mercury Tours");		
	}

	@Test(enabled=true)
	public void testMethod3() {
		driver.navigate().to("http://google.com");
		System.out.println("Title is - " + driver.getTitle());
		driver.navigate().back();
		AssertJUnit.assertEquals(driver.getTitle(), "Register: Mercury Tours");
	}

	@Test(dataProvider = "nameProvider", groups = "B", enabled = true)
	public void testMethod4(String name, int number) {
		System.out.println("TestMethod4 Name is - " + name + number);
	}
	
	@Test(dataProvider = "nameProvider", groups = "A", enabled = true)
	public void testMethod5(int number, String name) {
		System.out.println("TestMethod5 Name is - " + name + number);
	}
	
	@Test(priority = 0)
	public void testMethod6() {
		driver.get("http://demo.guru99.com/test/cookie/selenium_aut.php");        	
        driver.findElement(By.name("username")).sendKeys("abc123");							
        driver.findElement(By.name("password")).sendKeys("123xyz");							
        driver.findElement(By.name("submit")).click();       
		System.out.println("Number of Cookies are - " + driver.manage().getCookies().size());
		Cookie ck = driver.manage().getCookieNamed("Selenium");
	    if(ck !=null)System.out.println("Cookie Info: name: " + ck.getName() + " path: " + ck.getPath() + " value: " + ck.getValue());
	    driver.manage().deleteAllCookies();
	    System.out.println("Number of Cookies after deletion - " + driver.manage().getCookies().size());
	    driver.manage().addCookie(new Cookie("Selenium", "amit"));
	    System.out.println("Number of Cookies after addition - " + driver.manage().getCookies().size());
	    Cookie ck1 = driver.manage().getCookieNamed("Selenium");
	    if(ck1 !=null)System.out.println("Cookie Info: name: " + ck1.getName() + " path: " + ck1.getPath() + " value: " + ck1.getValue());
	}

	@DataProvider(name = "nameProvider")
	public Object[][] dataProvider(Method m) {
		if (m.getName().equalsIgnoreCase("testMethod4")) {
			return new Object[][] { { "amit", 1 }, { "garhewal", 2 }, { "test", 3 }, { "testng", 4 } };
		} else if (m.getName().equalsIgnoreCase("testMethod5")) {
			return new Object[][] { { 1, "selenium" }, { 2, "oes" } };
		}
		return null;
	}

	@DataProvider(name = "nameProvider1")
	public Object[][] dataProvider1(ITestContext c) {
		Object[][] groupArray = null;
		for (String group : c.getIncludedGroups()) {
			if (group.equalsIgnoreCase("A")) {
				groupArray = new Object[][] { { "Guru99", "India" }, { "Krishna", "UK" }, { "Bhupesh", "USA" } };
				break;
			} else if (group.equalsIgnoreCase("B")) {
				groupArray = new Object[][] { { 1, "selenium" }, { 2, "oes" } };
			}
			break;
		}
		return groupArray;
		//return new Object[][] { { 1, "selenium" }, { 2, "oes" } };
	}	

	@BeforeTest
	@Parameters("browser")
	public void beforeTest(@Optional("chrome") String browser) {
		System.out.println("Before test method");		
		driver = Helper.createDriver(browser);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);
		//System.out.println(driver.manage().ime().isActivated());
		Navigation navi = driver.navigate(); 		
	}

	@AfterTest
	public void afterTest() {
		System.out.println("After test method");
		driver.quit();
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("Before class method");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("After class method");
	}
}
