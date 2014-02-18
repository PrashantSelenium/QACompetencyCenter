package com.tpg.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.tpg.appium.AppiumConstants;
import com.tpg.appium.BaseAppiumTestCaseClass;

public class APIDemosTestCases extends BaseAppiumTestCaseClass {

	String package_name;
	 @Before
	    public void setUp() throws Exception {
	       	File classpathRoot = new File(System.getProperty("user.dir"));
	       	File appDir = new File(classpathRoot, "\\lib");
	      	File app = new File(appDir, AppiumConstants.API_DEMOS_APK); 								//1
	        capabilities = new DesiredCapabilities();
	        /**
	         * This capability needs to be changed to AppiumConstants.SELENDROID_DEVICE if the API Level is below 17.
	         * Inclusive of API Level 17 or greater, this can be AppiumConstants.ANDROID_DEVICE.
	         */
	         
	        device_type=AppiumConstants.ANDROID_DEVICE;
	        capabilities.setCapability("device",device_type);
	       // capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");				//In case of webview testing check thi
	        capabilities.setCapability(CapabilityType.VERSION, "4.2.2");
	        capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
	        
	        capabilities.setCapability("app", app.getAbsolutePath());				//For Web automation change to Browser /*app.getAbsolutePath()*/
	        package_name=AppiumConstants.MONKEY_TALK_PACKAGE_NAME;											//2
	        capabilities.setCapability("app-package",package_name );									
	        capabilities.setCapability("app-activity", AppiumConstants.MONKEY_TALK__BASE_ACTIVITY);			//3
	        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4765/wd/hub"), capabilities);
	    }
	
	@Test
	public void workWithId(){
		WebElement osElement = driver.findElement(By.name("OS"));
    	assertNotNull(osElement);
    	assertEquals(osElement.getText(), "OS");
    	osElement.click();
    	
    	WebElement smsElement = driver.findElement(By.name("SMS Messaging"));
    	assertNotNull(smsElement);
    	assertEquals(smsElement.getText(), "SMS Messaging");
    	smsElement.click();
    	
    	WebElement smsBodyElement = driver.findElement(By.id(getUserInterfaceId("sms_content")));
    	//driver.navigate().back();		//Keycode4 is for Back.Keycode 3 is for Home
    	//driver.navigate().back();
	}
	
	@Test
    public void ImageClick(){
    	WebElement appElement = driver.findElement(By.name("App"));
    	assertNotNull(appElement);
    	assertEquals(appElement.getText(), "App");
    	appElement.click();
    	
    	WebElement actionBarElement = driver.findElement(By.name("Action Bar"));
    	assertNotNull(actionBarElement);
    	assertEquals(actionBarElement.getText(), "Action Bar");
    	actionBarElement.click();
    	
    	WebElement actionBarPageElement = driver.findElement(By.name("Action Provider"));
    	assertNotNull(actionBarPageElement);
    	assertEquals(actionBarPageElement.getText(), "Action Provider");
    	actionBarPageElement.click();
    	
    	WebElement shareActionBarElement = driver.findElement(By.name("Share Action Provider"));
    	assertNotNull(shareActionBarElement);
    	assertEquals(shareActionBarElement.getText(), "Share Action Provider");
    	shareActionBarElement.click();
    	
    	/*WebElement actionBarImages1 = driver.findElement(By.xpath("//LinearLayout//FrameLayout//ImageView"));
    	//WebElement actionBarImages1 = driver.findElement(By.id("com.example.android.apis:id/image"));
    	actionBarImages1.click();
    	takeScreenshot();*/
    }

    @Test
    public void webviewTest(){
    	
    swipeScreen(0.5,0.75,0.5,0.25);	
    WebElement viewsElement = driver.findElement(By.name("Views"));
	assertNotNull(viewsElement);
	assertEquals(viewsElement.getText(), "Views");
	viewsElement.click();
	
	swipeScreen(0.5,0.75,0.5,0.25);	
	WebElement webElement;
	while (true) {
		try {
			webElement = driver.findElement(By.name("WebView"));
			break;
		} catch (NoSuchElementException e) {
			swipeScreen(0.5, 0.75, 0.5, 0.25);
		}
	}
	assertNotNull(webElement);
	assertEquals(webElement.getText(), "WebView");
	webElement.click();
	
	//device_type=AppiumConstants.SELENDROID_DEVICE;
	//capabilities.setCapability("device", device_type);
	
	List<WebElement> hyperlinks=driver.findElements(By.tagName("a"));
	assertNotNull(hyperlinks);
	System.out.println("Size of HyperLinks:"+hyperlinks.size());
    }
}
