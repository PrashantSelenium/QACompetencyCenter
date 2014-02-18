package com.tpg.testcases;

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
 * This class automates chrome browser through appium. You need to set the path of the chrome driver.
 * @author Khushboo.kaur
 */
public class SeleniumChromeDriver /*extends BaseAppiumTestCaseClass*/ {
	
	public static void main(String[] args) throws MalformedURLException{
		DesiredCapabilities  capabilities = new DesiredCapabilities();
		capabilities.setCapability("device","Android");
		capabilities.setCapability("app", "Chrome");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, "4.3");
		capabilities.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.get("http://www.yahoo.com");
	}
}
