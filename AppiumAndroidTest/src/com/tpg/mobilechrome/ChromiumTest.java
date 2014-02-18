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
 * This class is automating Chrome browser on android emulator. You need to place ChromeDriver on system path.
 * @author Khushboo.kaur
 *
 */
public class ChromiumTest {

	int image_number;
	int height_covered,last_screenshot_size,maxPageHeight;
	List<BufferedImage> imageslist;
	double numberOfScreenShots;
	
	public static void main(String[] args) throws MalformedURLException {
	
		new ChromiumTest().init();
	}

	private void init() {
		maxPageHeight=height_covered=image_number=last_screenshot_size=0;
		
		imageslist=new ArrayList<BufferedImage>();
		
		DesiredCapabilities capabilities=DesiredCapabilities.chrome();
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOptions("androidPackage", "com.android.chrome");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		WebDriver driver=new ChromeDriver(capabilities);
		System.setProperty("webdriver.chrome.driver", "C://Users//khushboo.kaur//Documents//Appium//ChromeDriver//chromedriver.exe");
		//driver.get("http://developer.android.com/index.html");
		driver.get("http://flipkart.com");
		try {
			seleniumCaptureBrowserScreenShot(driver);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void seleniumCaptureBrowserScreenShot(WebDriver driver) throws InterruptedException, IOException {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		Long pageheight1=(Long)js.executeScript("return window.innerHeight");
		Long maxPageHeight1=(Long)js.executeScript("return Math.max(document.documentElement.scrollHeight, document.body.scrollHeight," +
				"document.documentElement.clientHeight, window.innerHeight)");
		float sections=(float)maxPageHeight1/pageheight1;
		numberOfScreenShots=Math.ceil(sections);
		
		int pageheight=pageheight1.intValue();
		maxPageHeight=maxPageHeight1.intValue();
		
		System.out.println("Page height="+pageheight+"   Max Page Height="+maxPageHeight+"  Round Trips="+numberOfScreenShots);
		
		for (int i=0; i<(int)numberOfScreenShots ; i++) {
			if(	(maxPageHeight-height_covered) < pageheight ){
				last_screenshot_size=maxPageHeight-height_covered;
				System.out.println("Height of Last Image should be: "+last_screenshot_size);
				takeScreenshot(driver);
				
				/*Crops the Image*/
				BufferedImage lastScreenShot=imageslist.get(imageslist.size()-1);
				int removeHeight=lastScreenShot.getHeight()-last_screenshot_size;
				
				BufferedImage subImage=lastScreenShot.getSubimage(0, removeHeight, lastScreenShot.getWidth(), last_screenshot_size);
				ImageIO.write(subImage, "jpg", new File("D:\\Android workspace\\AppiumAndroidTest\\snapshots\\screenshot"+image_number+".jpg"));
				imageslist.set(imageslist.size()-1, subImage);
				
			}else{
				takeScreenshot(driver);

				js.executeScript("window.scrollBy(0,"+pageheight+")");
				height_covered=height_covered+pageheight;
			}
			
			Thread.sleep(1000);
		}
		try {
			joinImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void takeScreenshot(WebDriver driver) {
		TakesScreenshot driver_secreenshot=(TakesScreenshot)driver;
		File file=driver_secreenshot.getScreenshotAs(OutputType.FILE);
		if(file!=null)
		try {
			BufferedImage image=ImageIO.read(file);
			imageslist.add(image);
			ImageIO.write(image, "jpg", new File("D:\\Android workspace\\AppiumAndroidTest\\snapshots\\screenshot"+image_number+".jpg"));
			image_number++;
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void joinImages() throws IOException{
		 
		int resultImageHeight=0;
	     for(BufferedImage image:imageslist){
	    	resultImageHeight=resultImageHeight+image.getHeight();
		 }
		 BufferedImage resultImage = new BufferedImage(imageslist.get(0).getWidth(),resultImageHeight ,BufferedImage.TYPE_INT_RGB);
		 Graphics g = resultImage.createGraphics();
		 int imageBelow=0;
		 for(BufferedImage screenshot : imageslist){

			 g.drawImage(screenshot, 0, imageBelow, null);
			 imageBelow=imageBelow+screenshot.getHeight();
		 }
		 ImageIO.write(resultImage, "jpg", new File("D:\\Android workspace\\AppiumAndroidTest\\snapshots\\FinalScreenshot.jpg"));
	}
	
	private void setMaximizeBrowser(WebDriver driver){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("var oldY = document.documentElement.clientHeight;" +
				"window.resizeTo(800, 600);" +
				"var padX = 800 - document.documentElement.clientWidth; var padY = 600 - document.documentElement.clientHeight;" +
				"window.resizeTo(padX, oldY + padY);");
		System.out.println("Sets the Browser Size");
		
	}
}
