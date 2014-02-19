package com.tpg.mobilechrome;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
/**
 * This class automates Android Chrome browser through appium. You need to place ChromeDriver on your 
 * system Path for windows machine. Start Appium server before executing this class.
 * @author Khushboo.kaur
 */
public class SeleniumChromeDriver {
	
	public static void main(String[] args) throws MalformedURLException{
		DesiredCapabilities  capabilities = new DesiredCapabilities();
//		capabilities.setCapability("device","Android");
		capabilities.setCapability("app", "Chrome");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
//		capabilities.setCapability(CapabilityType.VERSION, "4.3");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//		String url="http://www.yahoo.com");
		
		String url="http://ebay.in";
		//String url="http://developer.android.com/index.html";
		//String url="http://www.microsoft.com/en-us/default.aspx";
		//String url="http://timesofindia.indiatimes.com/";
		//String url="http://godaddy.com";
		new CaptureBrowserScreenShot().init(driver, url);
	}
}
