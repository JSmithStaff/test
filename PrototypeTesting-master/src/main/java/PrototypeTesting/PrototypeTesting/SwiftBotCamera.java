package PrototypeTesting.PrototypeTesting;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;




public class SwiftBotCamera {
	
	private static TestRobot TR;
	private String currentVersion = "1.2";
	
	public static void main(String[] args) {
		TR = new TestRobot();
		TestCamera("/home/pi","testImage",200,200,1000);
	
	}

	
	
	private static void TestCamera(String directory, String imageName, int width, int height, int timeout) {
		//https://github.com/Hopding/JRPiCam
		//leftVelocity. Integer. Speed of left wheel using a number between 1 and 100. 1 being the slowest. 100 being the fastest.
		//directory. String. Folder that you would like to store the photo to be taken
		//imageName. String. Name of the image that you are taking. 
		//Note, image will be stored at directory/imageName once taken.
		//width. Integer. Width of photo to be taken in pixels.
		//height. Integer. Height of photo to be taken in pixels.
		//timeout. Integer. Amount of time to wait before taking the image in milliseconds. If 0, then camera will run indefinitely.
		//Number of seconds
		
		int secs = 10;
		//Record current time
		long before = System.currentTimeMillis();
		
		//Counter for filename
		int i = 1;
		
		while(System.currentTimeMillis() - before < 1000*secs)
		{
		System.out.println("Taking picture.");
		try {
			RPiCamera piCamera = new RPiCamera(directory);
			piCamera.setWidth(width).setHeight(height) // Set Camera to produce images.
			.setExposure(Exposure.AUTO) // Set Camera's exposure.
			.setQuality(Integer.MAX_VALUE).setTimeout(timeout) // Set Camera's timeout.
			.setAddRawBayer(true); // Add Raw Bayer data to image files created by Camera.
			piCamera.takeStill(imageName);
			
			piCamera.setToDefaults();
		} catch (FailedToRunRaspistillException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Picture taken.");
	}
	}
	
	
	
	
}
