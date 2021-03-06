
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.wiringpi.SoftPwm;



public class TestRobot {

	private static TestRobot TR;
	private String currentVersion = "1.2";


	public static void main(String[] args) {
		TR = new TestRobot();
		System.out.println("Testing the new robot");
		System.out.println("Current version: " + TR.getCurrentVersion());
		TestRightWheel();
		TestLeftWheel();
	}


	private static void TestRightWheel() {
		System.out.println("Testing the right wheel.");
		System.out.println("Forwards.");
		TR.setWheelVelocities(0, 100, 2000);

	}
		
		private static void TestLeftWheel() {
		System.out.println("Testing the left wheel.");
		System.out.println("Forward.");
		TR.setWheelVelocities(100, 0, 2000);
		System.out.println("Finished testing.");
	}

		
	}

	//Movement
	public void setWheelVelocities(int leftVelocity, int rightVelocity, int timeToHold) {

		//Parameters: leftVelocity. Integer. Speed of left wheel using a number between 1 and 100. 1 being the slowest. 100 being the fastest.
		//rightVelocity. Integer. Speed of right wheel using a number between 1 and 100. 1 being the slowest. 100 being the fastest.
		//timeToHold. Integer. Integer. Time to turn the wheel in milliseconds.
		//leftVelocity. Integer. Speed of left wheel using a number between 1 and 100. 1 being the slowest. 100 being the fastest.

		int LEFT_MOTOR_PIN_A = 14;
		int LEFT_MOTOR_PIN_B = 10;
		int RIGHT_MOTOR_PIN_A = 12;
		int RIGHT_MOTOR_PIN_B = 13;



		//			Create GPIO Controller
		GpioController gpio = GpioFactory.getInstance();

		//Old Robot pin access
		//GpioPinDigitalOutput motor1pinE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "m1E");
		//GpioPinDigitalOutput motor2pinE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "m2E");

		//New Robot Pin access.
		GpioPinDigitalOutput turnOnMotors = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "m1E");	

		// Create Pulse Width Modulation
		SoftPwm.softPwmCreate(LEFT_MOTOR_PIN_A, 0, 100);
		SoftPwm.softPwmCreate(LEFT_MOTOR_PIN_B, 0, 100);
		SoftPwm.softPwmCreate(RIGHT_MOTOR_PIN_A, 0, 100);
		SoftPwm.softPwmCreate(RIGHT_MOTOR_PIN_B, 0, 100);

		// Set Pins to High

		//Old robot pins.
		//motor1pinE.high();
		//motor2pinE.high();

		//New robot pin.
		turnOnMotors.high();

		// Movement
		if (leftVelocity < 0) {
			SoftPwm.softPwmWrite(LEFT_MOTOR_PIN_A, 0);
			SoftPwm.softPwmWrite(LEFT_MOTOR_PIN_B, Math.abs(leftVelocity));
		} else if (leftVelocity <= 100) {
			leftVelocity=100;
			SoftPwm.softPwmWrite(LEFT_MOTOR_PIN_A, leftVelocity);
			SoftPwm.softPwmWrite(LEFT_MOTOR_PIN_B, 0);
		}

		if (rightVelocity < 0) {
			SoftPwm.softPwmWrite(RIGHT_MOTOR_PIN_A, 0);
			SoftPwm.softPwmWrite(RIGHT_MOTOR_PIN_B, Math.abs(rightVelocity));
		} else if (rightVelocity <= 100) {
			rightVelocity=100;
			SoftPwm.softPwmWrite(RIGHT_MOTOR_PIN_A, rightVelocity);
			SoftPwm.softPwmWrite(RIGHT_MOTOR_PIN_B, 0);
		}

		if (timeToHold > 0) {
			timeToHold = 10000;
			try {
				Thread.sleep(timeToHold);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SoftPwm.softPwmWrite(RIGHT_MOTOR_PIN_A, 0);
			SoftPwm.softPwmWrite(RIGHT_MOTOR_PIN_B, 0);
			SoftPwm.softPwmWrite(LEFT_MOTOR_PIN_A, 0);
			SoftPwm.softPwmWrite(LEFT_MOTOR_PIN_B, 0);
		}

		// Turn off Pin
		//motor1pinE.low();
		//motor2pinE.low();
		turnOnMotors.low();

		// Shutdown GPIO
		gpio.shutdown();

		// Unprovision Pins
		//gpio.unprovisionPin(motor1pinE);
		//gpio.unprovisionPin(motor2pinE);
		gpio.unprovisionPin(turnOnMotors);
	}

	public String getCurrentVersion() {
		return currentVersion;
	}
	

		
		
		
		
		
		
		
		
	}
