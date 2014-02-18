package com.tpg.testcases;

import static org.junit.Assert.assertNotNull;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tpg.appium.BaseAppiumTestCaseClass;

public class MonkeyTalkTestCases extends BaseAppiumTestCaseClass {

	WebDriver driver;
	
	
    public static void main(String args[]){
		try {
			new MonkeyTalkTestCases().initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void initialize() throws Exception {
		setUp();
		driver=getDriver();
		//getTargetActivity();
		formTest();
		webTest();
	}
	/**
	 * The Test cases are written with respect to Android and Selendroid.
	 */
	public void formTest(){
		String id=getUserInterfaceId("tab_txt");
		System.out.println("TAB ID="+id);
		List<WebElement> tabElement = driver.findElements(By.id(id));
		assertNotNull(tabElement);
		System.out.println("Number of Tabs are:"+tabElement.size());
		WebElement tab=tabElement.get(1);
		assertEquals(tab.getText(), "forms");
		tab.click();
		
		WebElement spinnerElement = driver.findElement(By.tagName("Spinner"));
		assertNotNull(spinnerElement);
		spinnerElement.click();
		
		WebElement buttonElement = driver.findElement(getByClause("Boron"));
		assertNotNull(buttonElement);
		buttonElement.click();
		
		WebElement onElement = driver.findElement(By.xpath("//RadioGroup//RadioButton[2]"));
		onElement.click();
		
		WebElement textview=driver.findElement(By.id(getUserInterfaceId("forms_val")));
		assertNotNull(textview);
		assertEquals(textview.getText().substring(0, 5), "Boron");
		
		
	}
	public void webTest(){
		List<WebElement> tabElement = driver.findElements(By.id(getUserInterfaceId("tab_txt")));
		assertNotNull(tabElement);
		WebElement tab=tabElement.get(3);
		assertEquals(tab.getText(), "web");
		tab.click();
	
		/**
		 * DesiredCapability has to be "Selendroid".
		 */
		/*String windowHandle=driver.getWindowHandle();
		System.out.println("Window handle="+windowHandle);
		*/
		driver.switchTo().window("WEBVIEW");
		
		WebElement txtArea=driver.findElement(By.id("txtarea"));
		assertNotNull(txtArea);
		txtArea.clear();
		txtArea.sendKeys("Three Pillar Global");
		
		WebElement radioButton=driver.findElement(By.xpath("//input[@value=\"C\"]"));
		assertNotNull(radioButton);
		radioButton.click();
		
		WebElement textfieldElement =driver.findElement(By.name("btn"));
		assertNotNull(textfieldElement);
		textfieldElement.click();
		driver.navigate().to("http://www.google.com");  //launches new browser.
		getWindowHandles(driver);
		WebDriverWait wait=new WebDriverWait(driver, 30);
//		wait.until(ExpectedConditions.titleIs(""));
		WebElement search=driver.findElement(By.name("q"));
		assertNotNull(search);
		search.sendKeys("Three Pillar Global");
		search.submit();
	}
}
