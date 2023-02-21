package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Flipkart {

	AndroidDriver<MobileElement> driver;

	@BeforeTest
	public void launchApp() throws MalformedURLException {
//		1. Launch Flipkart app
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "emulator-5554");
		cap.setCapability("appPackage", "com.flipkart.android");
		cap.setCapability("appActivity", "com.flipkart.android.SplashActivity");
		cap.setCapability("noReset", true);

		driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), cap);

	}

	@Test(priority = 0)
	public void searchProduct() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(50000, TimeUnit.MILLISECONDS);

//		2. Type ‘mobile’ in the search-box

		driver.findElement(By.xpath("//android.widget.TextView[@text='Search for products']")).click();

		driver.findElement(By.xpath("//android.widget.EditText[@text='Search for Products, Brands and More']"))
				.sendKeys("samsung mobiles");

		Thread.sleep(3000);
		driver.findElement(By.xpath("//android.widget.TextView[@text='samsung mobiles']")).click();

		// 3. Click on the second search result [Since first product is showing 'No seller ships to this pincode, i am taking the second product from the list']

		MobileElement secondPdt = driver.findElement(By.xpath(
				"//android.widget.FrameLayout[@resource-id='com.flipkart.android:id/main_content']/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.TextView[1]"));

		secondPdt.click();
	}

	@Test(priority = 1)
	public void addProductToCart() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);

//		4. Click on ‘Add to cart’ button
		driver.findElement(By.xpath("//android.widget.TextView[@text='Add to cart']")).click();

		Thread.sleep(5000);
		
		
//		5. Click on ‘Go to cart’ button

//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='Go to cart']\"")));
//		
		driver.findElement(By.xpath("//android.widget.TextView[@text='Go to cart']")).click();
		Thread.sleep(5000);

	}

	@Test(priority = 2)
	public void placeOrderAndVerify() {
		

//		6. Click on ‘Place Order’ button
		driver.findElement(By.xpath("//android.widget.TextView[@text='Place Order ']")).click();

//		7. Verify you see ‘Order Summary’ as heading of the activity

		boolean orderSummaryheader = driver.findElement(By.id("com.flipkart.android:id/title_action_bar"))
				.isDisplayed();

		Assert.assertTrue(orderSummaryheader);

	}

}
