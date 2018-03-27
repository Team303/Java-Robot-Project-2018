package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.CANifier;

import edu.wpi.first.wpilibj.DriverStation;

public class Canifier {

	CANifier leds;
	
	public Canifier() {
		leds = new CANifier(26);
	}
	
	public void setBlue() {
		leds.setLEDOutput(100, CANifier.LEDChannel.LEDChannelA);
		leds.setLEDOutput(0, CANifier.LEDChannel.LEDChannelB);
		leds.setLEDOutput(0, CANifier.LEDChannel.LEDChannelC);
	}
	
	public void setGreen() {
		leds.setLEDOutput(0, CANifier.LEDChannel.LEDChannelA);
		leds.setLEDOutput(100, CANifier.LEDChannel.LEDChannelB);
		leds.setLEDOutput(0, CANifier.LEDChannel.LEDChannelC);
	}
	
	public void setRed() {
		leds.setLEDOutput(0, CANifier.LEDChannel.LEDChannelA);
		leds.setLEDOutput(0, CANifier.LEDChannel.LEDChannelB);
		leds.setLEDOutput(100, CANifier.LEDChannel.LEDChannelC);
	}
	
	public void setWhite() {
		leds.setLEDOutput(100, CANifier.LEDChannel.LEDChannelA);
		leds.setLEDOutput(100, CANifier.LEDChannel.LEDChannelB);
		leds.setLEDOutput(100, CANifier.LEDChannel.LEDChannelC);	
	}
}
