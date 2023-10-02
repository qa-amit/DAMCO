package Tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class UndostresVarification extends Base.UsdostresInitiation {
	@Test(priority = 0)
	public static void VerifyHomePage() {
		try {
			// Launch browser and navigate to the application
			setUp();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
