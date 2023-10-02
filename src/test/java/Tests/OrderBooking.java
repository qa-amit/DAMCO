package Tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.UnitsBottom;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Base.UsdostresInitiation;
import dev.failsafe.Timeout;

public class OrderBooking extends UsdostresInitiation {
	@Test(priority = 1)
	public static void PaymentPage() throws InterruptedException {

		// implicit wait for loading of web elements
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Search Flight from Delhi to Mumbai
		driver.findElement(By.xpath(prop.getProperty("close_signUp_Page"))).click();

		driver.findElement(By.xpath(prop.getProperty("FromCity"))).click();

		driver.findElement(By.xpath(prop.getProperty("FromCity"))).sendKeys(prop.getProperty("From_City_Name"));

		driver.findElement(By.xpath(prop.getProperty("DelhiOption"))).click();

		driver.findElement(By.xpath(prop.getProperty("To_City"))).click();

		driver.findElement(By.xpath(prop.getProperty("To_City"))).sendKeys(prop.getProperty("To_City_Name"));

		driver.findElement(By.xpath(prop.getProperty("Mumbai_Option"))).click();

		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.ESCAPE).perform();

		driver.findElement(By.xpath(prop.getProperty("Search_Btn"))).click();

		// Assert the search result
		String URLele = driver.getCurrentUrl();
		if (URLele.contains("https://www.makemytrip.com/flight/search?")) {
			System.out.println("Flight search has been initiated");

		} else {
			System.out.println("Flight search process got interrupted" + " " + driver.getCurrentUrl());

		}

		// implicit wait for loading of web elements
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement ele = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("Okay_Got_It_BTN"))));

		if (ele.isDisplayed()) {

			driver.findElement(By.xpath(prop.getProperty("Okay_Got_It_BTN"))).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("SearchResult"))));

		} else {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("SearchResult"))));

		}

		driver.findElement(By.xpath(prop.getProperty("DepartureFilter"))).click();

		// Print all the required details
		List<WebElement> AllRates = driver.findElements(By.xpath(prop.getProperty("FlightRow")));
		for (int i = 0; i < AllRates.size(); i++) {
			String elementText = AllRates.get(i).getText();
			System.out.println("The airline name, 2nd lowest price & other details are:" + elementText);
		}
	
		driver.close();
	}

}
