package com.tpg.mobilechrome;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
/**
 * This class automates Android Chrome Browser. The automation is done through the ChromeDriver directly.
 * There is no Appium involved here. You need to set the path of the ChromeDriver.
 * Just run this class directly.
 * @author Khushboo.kaur
 *
 */
public class ChromiumTest {

	public static void main(String[] args) throws MalformedURLException {
		
		DesiredCapabilities capabilities=DesiredCapabilities.chrome();
		ChromeOptions options=new ChromeOptions();
		/*1. Comment the below line to automate desktop browser*/
		options.setExperimentalOptions("androidPackage", "com.android.chrome");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		WebDriver driver=new ChromeDriver(capabilities);
		System.setProperty("webdriver.chrome.driver", "C://Users//khushboo.kaur//Documents//Appium//ChromeDriver//chromedriver.exe");
		new CaptureBrowserScreenShot().init(driver);
		
	}
}
