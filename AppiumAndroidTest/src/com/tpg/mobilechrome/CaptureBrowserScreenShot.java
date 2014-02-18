package com.tpg.mobilechrome;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

public class CaptureBrowserScreenShot {

	int image_number;
	int height_covered,pageHeightLeft,maxPageHeight;
	List<BufferedImage> imageslist;
	double numberOfScreenShots;
	
	public void init(WebDriver driver){
		maxPageHeight=height_covered=image_number=pageHeightLeft=0;
		imageslist=new ArrayList<BufferedImage>();
		//driver.get("http://developer.android.com/index.html");
		//driver.get("http://www.microsoft.com/en-us/default.aspx");
		driver.get("http://timesofindia.indiatimes.com/");
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
		
		System.out.println("HTML Page height="+pageheight+"   Max Page Height="+maxPageHeight+"  Round Trips="+numberOfScreenShots);
		
		for (int i=0; i<(int)numberOfScreenShots ; i++) {
			pageHeightLeft=maxPageHeight-height_covered;
			
			if(	pageHeightLeft < pageheight ){
				System.out.println("Snapshot of last page should be: "+pageHeightLeft+" (HTML Pixel) high from bottom");
				
				takeScreenshot(driver);
				
				/*Crops the Image*/
				BufferedImage lastScreenShot=imageslist.get(imageslist.size()-1);
				int lastScreenShotHeight=lastScreenShot.getHeight();
				System.out.println("Last Image height="+lastScreenShotHeight);
				
				double pagePercent=((double)(pageheight-pageHeightLeft)/pageheight)*100;
				System.out.println("Page Remove Percent from top="+pagePercent);	
				
				double imageToBeCropped=Math.round(((double)(pagePercent/100))*1701);
				System.out.println("Height of Image to be cropped from top="+imageToBeCropped);
				System.out.println("Height of resultant image="+(lastScreenShotHeight-imageToBeCropped));
				
				BufferedImage subImage=lastScreenShot.getSubimage(0, (int)imageToBeCropped, lastScreenShot.getWidth(), (int) (lastScreenShotHeight-imageToBeCropped));
				ImageIO.write(subImage, "jpg", new File("D:\\Github\\QACompetencyCenter\\AppiumAndroidTest\\snapshots\\screenshot"+image_number+".jpg"));
				imageslist.set(imageslist.size()-1, subImage);
				
			}else{
				takeScreenshot(driver);

				js.executeScript("window.scrollBy(0,"+pageheight+")");
				height_covered=height_covered+pageheight;
				System.out.println("Vertically Scrolled window by now "+height_covered+"  pixels.");
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
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		TakesScreenshot driver_secreenshot=(TakesScreenshot)augmentedDriver;
		
		//If you are executing the test case using only ChromeDriver then use the below code instead. You dont need Augmenter.
		//TakesScreenshot driver_secreenshot=(TakesScreenshot)driver;
		
		File file=driver_secreenshot.getScreenshotAs(OutputType.FILE);
		if(file!=null)
		try {
			BufferedImage image=ImageIO.read(file);
			imageslist.add(image);
			ImageIO.write(image, "jpg", new File("D:\\Github\\QACompetencyCenter\\AppiumAndroidTest\\snapshots\\screenshot"+image_number+".jpg"));
			
			image_number++;
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void joinImages() throws IOException{
		 
		int resultImageHeight=0;
		System.out.println("Height of all Images are:");
	     for(BufferedImage image:imageslist){
	    	System.out.println(image.getHeight());
	    	resultImageHeight=resultImageHeight+image.getHeight();
		 }
	     
	     System.out.println("Final Image Height="+resultImageHeight);
		 
	     BufferedImage resultImage = new BufferedImage(imageslist.get(0).getWidth(),resultImageHeight ,BufferedImage.TYPE_INT_RGB);
		 Graphics g = resultImage.createGraphics();
		 
		 int imageBelow=0;
		 for(BufferedImage screenshot : imageslist){

			 g.drawImage(screenshot, 0, imageBelow, null);
			 imageBelow=imageBelow+screenshot.getHeight();
		 }
		 ImageIO.write(resultImage, "jpg", new File("D:\\Github\\QACompetencyCenter\\AppiumAndroidTest\\snapshots\\FinalScreenshot.jpg"));
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
