/*
 * Created By: Amit Kumar
 * Brief: This is the base class of the POM framework
 * It consists of all the repetitive activity/functions that are going to to access by the multiple test file in this framework * 
 * 
 */

package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.qa.Undostres.Utility.Undostres_IAutoConst;

public class UsdostresInitiation implements Undostres_IAutoConst {

	// Variable declaration
	protected static WebDriver driver;
	protected static Properties prop;

	// A constructor to load the properties file
	protected UsdostresInitiation() {
		File file = new File(CONFIG_PROPERTIES_PATH1);

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop = new Properties();

		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Function to scroll down until the respective element gets visible
	public static void ScrollToElemet(WebElement Element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scrolling down the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}
	
	public static void Click(String ele) {
		driver.findElement(By.xpath(ele)).click();

	}

	// This is the setup method which launches the application URL in different
	// browsers
	public static void setUp() {
		// Launch browser based of the condition as declared below
		String browserName = prop.getProperty("Browser_Name");
		if (browserName.equals("Chrome")) {
			// Set properties of chrome browser
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			System.setProperty("webdriver.chrome.driver", ChromeDriver_Path);
			driver = new ChromeDriver();
		}
		if (browserName.equals("FireFox")) {
			// Set properties of FireFox browser
			System.setProperty("webdriver.chrome.driver", GeckoDriver_Path);
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		// Navigate to the URL		
		driver.get(prop.getProperty("url"));
		//prop.setProperty("url", "https://mail.aol.com/");

	}

	// Window Popup handling
	public static void HandleWinowPopUP() throws InterruptedException {

		Thread.sleep(1000);
		WebElement element = driver.findElement(By.xpath("//div[@class='alert alert-info loginToProceed']"));
		WebElement ele = driver.findElement(By.xpath("//img[@class='fbloginButton']"));

		if (element.isDisplayed() && ele.isDisplayed()) {
			System.out.println("PopUp displayed succesfully on the UI");

			// Get window pop up and iterate one by one
			Set<String> handler = driver.getWindowHandles();
			Iterator<String> Itr = handler.iterator();
			String ParentWindowID = Itr.next();
			System.out.println("Parent Window ID is:" + ParentWindowID);

			// Enter data in the window pop up
			driver.findElement(By.xpath(prop.getProperty("ID"))).sendKeys(prop.getProperty("email"));
			driver.findElement(By.xpath(prop.getProperty("PW"))).sendKeys(prop.getProperty("password"));

			// Srcoll down to in th payment page
			WebElement ele1 = driver.findElement(By.xpath(prop.getProperty("Accesso")));
			ScrollToElemet(ele1);

			// Click the I'not robot check box
			driver.findElement(By.xpath(prop.getProperty("NotRobort"))).click();
			ele1.click();

			// Wait to get proper visibility of the elements
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

			// Check the visibility condition for the 'Accesso' button
			driver.findElement(By.xpath(prop.getProperty("Accesso"))).isDisplayed();
			driver.findElement(By.xpath(prop.getProperty("Accesso"))).isEnabled();
			driver.findElement(By.xpath(prop.getProperty("Accesso"))).click();
			Thread.sleep(1000);

			// Switching between the parent and child window
			String ChildWindowID = Itr.next();
			System.out.println("Parent Window ID is:" + ChildWindowID);
			driver.switchTo().window(ChildWindowID);
			driver.close();
			driver.switchTo().window(ParentWindowID);

		} else {
			System.out.println("PopUp not displayed succesfully on the UI");
		}

	}

	// Function to verify the method is successfully done or not
	public static void CheckPaymentDone() {
		WebElement ele = driver.findElement(By.xpath("//div[contains(text(),'Permite las notificaciones')]"));
		if (ele.isDisplayed()) {
			System.out.println("Payment Successfuly done");
		} else {
			System.out.println(
					"Due to some interrupstion payment got failed, Check the screenshot for the excat root cause");

		}

	}
}
