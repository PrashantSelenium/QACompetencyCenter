package com.tpg.appium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;


import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
/**
 * This is the base class for automation on android through appium.
 * @author Khushboo.kaur
 *
 */
public class BaseAppiumTestCaseClass {
    protected WebDriver driver;
    public String device_type;
    String package_name, base_activity;
    protected DesiredCapabilities capabilities;
    
    public void setUp() throws Exception {
       	File classpathRoot = new File(System.getProperty("user.dir"));
       	File appDir = new File(classpathRoot, "\\lib");
      	File app = new File(appDir, AppiumConstants.MONKEY_TALK_APK); 								//1
        capabilities = new DesiredCapabilities();
        /**
         * This capability needs to be changed to AppiumConstants.SELENDROID_DEVICE if the API Level is below 17.
         * Inclusive of API Level 17 or greater, this can be AppiumConstants.ANDROID_DEVICE.
         */
         
        device_type=AppiumConstants.ANDROID_DEVICE;
        capabilities.setCapability("device",device_type);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");				//In case of webview testing check thi
        capabilities.setCapability(CapabilityType.VERSION, "4.2.2");
        capabilities.setCapability(CapabilityType.PLATFORM, Platform.WINDOWS);
        
        capabilities.setCapability("app", app.getAbsolutePath());				//For Web automation change to Browser /*app.getAbsolutePath()*/
        package_name=AppiumConstants.MONKEY_TALK_PACKAGE_NAME;											//2
        capabilities.setCapability("app-package",package_name );
        base_activity= AppiumConstants.MONKEY_TALK__BASE_ACTIVITY;
        capabilities.setCapability("app-activity",base_activity );			//3
        capabilities.setCapability("dp", "4729");
        capabilities.setCapability("U", "emulator-5554");//4d00c81001584085
        driver = new RemoteWebDriver(new URL("http://0.0.0.0:4724/wd/hub"), capabilities);
    }
    public WebDriver getDriver(){
    	return driver;
    }
    /**
     * The Ids for any User Interface component of the application needs to be prefixed with the base package name of the application if the device type is Android.
     * Else this retuns the raw Id.
     * @param id
     * @return
     */
    public String getUserInterfaceId(String id){
    	if(device_type.equals(AppiumConstants.ANDROID_DEVICE)){
    		return  package_name+":id/"+id;
    	}
    	else
    		return id;
    	
    }
    /**
     * Depending on the type of the device By instance is created
     * @param text
     * @return
     */
    public By getByClause(String text){
    	
    	if(device_type.equals(AppiumConstants.ANDROID_DEVICE)){
    		return By.name(text);
    	}
    	else
    		return By.linkText(text);
    }
    
	@After
    public void tearDown() throws Exception {
        driver.quit();
    }
	
    public void takeScreenshot(){
    	WebDriver augmentedDriver=new Augmenter().augment(driver);
    	File screenshot=((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
    	/*try {
			//FileUtils.copyFile(screenshot, new File("screenshot.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }
    public void swipeScreen(double startx, double starty, double endx, double endy){
    	JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", startx);
		swipeObject.put("startY", starty);
		swipeObject.put("endX", endx);
		swipeObject.put("endY", endy);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile:swipe", swipeObject);
		System.out.println("Swipped");
    }
    public void keyevent(Integer keycode){		//82 for menu button
    	HashMap<String, Integer> keycode1 = new HashMap<String, Integer>();
    	keycode1.put("keycode", keycode);
    	((JavascriptExecutor)driver).executeScript("mobile: keyevent", keycode1);
    }
    public void longClick(){
    	WebElement element = driver.findElement(By.name("API Demo"));
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	HashMap<String, String> tapObject = new HashMap<String, String>();
    	tapObject.put("element", ((RemoteWebElement) element).getId());
    	js.executeScript("mobile: longClick", tapObject);
    }
    public void getWindowHandles(WebDriver driver){
    	
    	for(String winHandle : driver.getWindowHandles()){
    		System.out.println("Window :"+winHandle);		//NATIVE_APP, WEBVIEW_0
    		driver.switchTo().window(winHandle);
    	}
    }
    public void changeOrientatio(){
    	JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_LANDSCAPELEFT);");
//		js.executeScript("target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_PORTRAIT);");
		
		/*WebDriver augmentedDriver = new Augmenter().augment(driver);
		ScreenOrientation orientation=((Rotatable)augmentedDriver).getOrientation();
		String orientation1=orientation.value();
		System.out.println("Orientation Before="+orientation1);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		orientation1=orientation.value();
		System.out.println("Orientation After="+orientation1);*/
    }
    
    public void getTargetActivity() throws AWTException{
    	Robot robot=new Robot();
//    	robot.keyPress(KeyEvent.KEYCODE_CTRL_LEFT);
//    	robot.keyPress(KeyEvent.KEYCODE_F12);
    }
    
}