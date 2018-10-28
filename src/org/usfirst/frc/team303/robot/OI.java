package org.usfirst.frc.team303.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;

public class OI {
	static Joystick left = new Joystick(0);
	static Joystick right = new Joystick(1);
	static XboxController xbox = new XboxController(2);
	static boolean disabledState = false;
	
	static double lX = 0, lY = 0, lZ = 0;
	static double rX = 0, rY = 0, rZ = 0;
	static double xlX = 0, xlY = 0, xrX = 0, xrY = 0;
	static double xLeftTrigger=0, xRightTrigger=0;
	
	static int xPov = 0;
	static int lPov = 0;
	static int rPov = 0;
	
	static boolean[] lBtn = new boolean[9];
	static boolean[] rBtn = new boolean[9];	
	static boolean xBtnA, xBtnB, xBtnX, xBtnY, xLeftBumper, xRightBumper, xBtnStart, xBtnBack, xLeftStickBtn, xRightStickBtn, lTrigger, rTrigger;
	
	public static void update() {
		for(int i=1;i<8;i++) { 
			lBtn[i] = left.getRawButton(i);
			rBtn[i] = right.getRawButton(i);
		}
		lPov = left.getPOV();
		rPov = right.getPOV();
		updateXboxValues();
	}
	
	public static void outputs() {
	}
	
	public static void updateXboxValues() {
		lX = left.getX();
		lY = left.getY();
		lZ = left.getZ();
				
		rX = right.getX();
		rY = right.getY();
		rZ = right.getZ();
		
		xlX = xbox.getX(Hand.kLeft);
		xlY = xbox.getY(Hand.kLeft);
		xrX = xbox.getX(Hand.kRight);
		xrY = xbox.getY(Hand.kRight);
		
		xBtnA = xbox.getAButton();
		xBtnB = xbox.getBButton();
		xBtnX = xbox.getXButton();
		xBtnY = xbox.getYButton();
		xLeftBumper = xbox.getBumper(Hand.kLeft);
		xRightBumper = xbox.getBumper(Hand.kRight);
		xBtnStart = xbox.getStartButton();
		xBtnBack = xbox.getBackButton();
		xLeftStickBtn = xbox.getStickButton(Hand.kLeft);
		xRightStickBtn = xbox.getStickButton(Hand.kRight);
		xLeftTrigger = xbox.getTriggerAxis(Hand.kLeft);
		xRightTrigger = xbox.getTriggerAxis(Hand.kRight);
		
		xPov = xbox.getPOV();
	}
	
	public static boolean pulse(boolean input){
		if(input){
			if(!disabledState){
				disabledState = true;
				return true;
			}
			return false;
		}
		disabledState = false;
		return false;
	}
	
	
}