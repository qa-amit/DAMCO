package Tests;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Mail_AOL extends Base.UsdostresInitiation {
	// @SuppressWarnings("deprecation")
	@Test(priority = 1)
	public static void SendMail() throws InterruptedException {

		// implicit wait for loading of web elements
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.xpath(prop.getProperty("LoginIcon"))).click();

		driver.findElement(By.xpath(prop.getProperty("Input_usernam"))).click();

		driver.findElement(By.xpath(prop.getProperty("Input_usernam"))).sendKeys(prop.getProperty("Username"));

		driver.findElement(By.xpath(prop.getProperty("NXT_Btn"))).click();

		driver.findElement(By.xpath(prop.getProperty("Input_pwd"))).click();

		driver.findElement(By.xpath(prop.getProperty("Input_pwd"))).sendKeys(prop.getProperty("Password"));

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath(prop.getProperty("NXT_Btn2"))).click();
		// driver.manage().timeouts().setScriptTimeout(3L, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		if (driver.findElement(By.xpath(prop.getProperty("Logo"))).isDisplayed()) {
			driver.findElement(By.xpath(prop.getProperty("Compose_Btn"))).click();
			driver.findElement(By.xpath(prop.getProperty("To"))).click();
			driver.findElement(By.xpath(prop.getProperty("To"))).sendKeys(prop.getProperty("Username") + "@aol.com");
			driver.findElement(By.xpath(prop.getProperty("Subject"))).click();
			driver.findElement(By.xpath(prop.getProperty("Subject"))).sendKeys(prop.getProperty("Subject_LABLE"));
			driver.findElement(By.xpath(prop.getProperty("Msg_body"))).click();
			driver.findElement(By.xpath(prop.getProperty("Bullet"))).click();
			driver.findElement(By.xpath(prop.getProperty("Bullet_Opt"))).click();
			driver.findElement(By.xpath(prop.getProperty("Msg_body"))).sendKeys(prop.getProperty("Message1"));
			Actions ac = new Actions(driver);
			ac.sendKeys(Keys.ENTER).perform();
			driver.findElement(By.xpath(prop.getProperty("Msg_body"))).sendKeys(prop.getProperty("Message2"));
			ac.sendKeys(Keys.ENTER).perform();
			driver.findElement(By.xpath(prop.getProperty("Msg_body"))).sendKeys(prop.getProperty("Message3"));

			// Upload a file
			driver.findElement(By.xpath(prop.getProperty("Attachment")))
					.sendKeys("C:\\Users\\AMIT KUMAR\\OneDrive\\Pictures\\INVESTMENT_MEDIUMS.png");

			driver.findElement(By.xpath(prop.getProperty("send_btn"))).click();

			// driver.findElement(By.xpath(prop.getProperty("Inbox"))).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//			Boolean ele = wait.until(
//					ExpectedConditions.invisibilityOf((WebElement) By.xpath(prop.getProperty("Compose_lauout"))));

			Boolean ele = wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath(prop.getProperty("Compose_lauout"))));
			if (ele == true) {
				driver.findElement(By.xpath(prop.getProperty("Back"))).click();
				driver.navigate().refresh();

				// Print all the required details
				List<WebElement> MessageList = driver.findElements(By.xpath(prop.getProperty("Inbox_Msg_List")));
				for (int i = 0; i < MessageList.size(); i++) {
					System.out.println("The Inbox email list is : " + MessageList.get(i).getText());
					if (MessageList.get(i).getText().contains(prop.getProperty("Subject_LABLE"))) {
						System.out.println("Email is successfully delivered");

						MessageList.get(i).click();

						// Validate the email & Body message
						if (driver.findElement(By.xpath(prop.getProperty("Inbox_Header"))).isDisplayed()) {
							String mail_header = driver.findElement(By.xpath(prop.getProperty("Inbox_Header")))
									.getText();
							Assert.assertEquals(mail_header, prop.getProperty("Subject_LABLE"));
							List<WebElement> Msg_Body1 = driver
									.findElements(By.xpath(prop.getProperty("Inbox_Msg_body")));
							for (int i1 = 0; i1 < Msg_Body1.size(); i1++) {
								System.out.println(Msg_Body1.get(i1).getText());
								// System.out.println (prop.getProperty(("Message") + i1++));
								Assert.assertEquals(Msg_Body1.get(i1).getText(), prop.getProperty("Message1") + "\n"
										+ prop.getProperty("Message2") + "\n" + prop.getProperty("Message3"));
							}
							System.out.println("Email is successfully Validated");

							// Download the attached file & validate the size
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("Attached_File"))));
							if (driver.findElement(By.xpath(prop.getProperty("Attached_File"))).isDisplayed()) {
								String size=prop.getProperty("FlieSizeInBytes");
								Actions act = new Actions(driver);
								act.moveToElement(driver.findElement(By.xpath(prop.getProperty("Attached_File"))))
										.build().perform();
								driver.findElement(By.xpath(prop.getProperty("Download_Icon"))).click();

								File file = new File(prop.getProperty("Downloaded_File_Path"));
								System.out.println(file.length());
								Assert.assertEquals(file.length(),357195);
							} else {
								System.out.println("Validation failed for attached file");
							}

						} else {
							System.out.println("Email Validation failed");
						}

					} else {

						System.out.println("Email is not in the inbox list");

					}
				}

			} else {
				System.out.println("Email is not delivered, Refresh the page...");
				driver.navigate().refresh();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				// Print all the required details
				List<WebElement> MessageList = driver.findElements(By.xpath(prop.getProperty("Inbox_Msg_List")));
				for (int i = 0; i < MessageList.size(); i++) {
					if (MessageList.get(i).getText().equals(prop.getProperty("Subject_LABLE"))) {
						System.out.println("Email is successfully delivered");
						MessageList.get(i).click();

					} else {

						System.out.println("Email is not in the inbox list");

					}
				}
			}

		}

//		driver.findElement(By.xpath(prop.getProperty("To_City"))).sendKeys(prop.getProperty("To_City_Name"));
//
//		driver.findElement(By.xpath(prop.getProperty("Mumbai_Option"))).click();
//
//		
//
//		driver.findElement(By.xpath(prop.getProperty("Search_Btn"))).click();
//
//		// Assert the search result
//		String URLele = driver.getCurrentUrl();
//		if (URLele.contains("https://www.makemytrip.com/flight/search?")) {
//			System.out.println("Flight search has been initiated");
//
//		} else {
//			System.out.println("Flight search process got interrupted" + " " + driver.getCurrentUrl());
//
//		}
//
//		// implicit wait for loading of web elements
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//		WebElement ele = wait
//				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("Okay_Got_It_BTN"))));
//
//		if (ele.isDisplayed()) {
//
//			driver.findElement(By.xpath(prop.getProperty("Okay_Got_It_BTN"))).click();
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("SearchResult"))));
//
//		} else {
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("SearchResult"))));
//
//		}
//
//		driver.findElement(By.xpath(prop.getProperty("DepartureFilter"))).click();
//
//		// Print all the required details
//		List<WebElement> AllRates = driver.findElements(By.xpath(prop.getProperty("FlightRow")));
//		for (int i = 0; i < AllRates.size(); i++) {
//			String elementText = AllRates.get(i).getText();
//			System.out.println("The airline name, 2nd lowest price & other details are:" + elementText);
//		}
//		driver.close();
	}

}
