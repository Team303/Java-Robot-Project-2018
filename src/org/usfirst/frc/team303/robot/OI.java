package org.usfirst.frc.team303.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;

public class OI {
	//static NetworkTable preferences = NetworkTable.getTable("Preferences");
	static Joystick left = new Joystick(0);
	static Joystick right = new Joystick(1);
	static XboxController xbox = new XboxController(2);
	static boolean disabledState = false;
	
	static double lX = 0, lY = 0, lZ = 0;
	static double rX = 0, rY = 0, rZ = 0;
	static double xlX = 0, xlY = 0, xrX = 0, xrY = 0;
	static double xLeftTrigger=0, xRightTrigger=0;
	
	static boolean[] lBtn = new boolean[9];
	static boolean[] rBtn = new boolean[9];	
	static boolean xBtnA, xBtnB, xBtnX, xBtnY, xLeftBumper, xRightBumper, xBtnStart, xBtnBack, xLeftStickBtn, xRightStickBtn, lTrigger, rTrigger;
	static boolean lBtn1;
	
	public static void update() {
		
		for(int i=1;i<8;i++) { 
			lBtn[i] = left.getRawButton(i);
			rBtn[i] = right.getRawButton(i);
			SmartDashboard.putNumber("POV value", right.getPOV());
		}
		
		updateValues();
		//preferences = NetworkTable.getTable("Preferences");
		
	}
	
	public static void outputs() {
		
		if(RobotState.isOperatorControl() && RobotState.isAutonomous()) { //auto only outputs
			//SmartDashboard.putNumber("NavX PID Setpoint", Robot.navX.getSetpoint());
		} else if (RobotState.isOperatorControl() && RobotState.isEnabled()) { //teleop only outputs
			
		} 
		
		//amperage outputs
		/*SmartDashboard.putNumber("Right Drive Current", Robot.pdp.getCurrent(2));
		SmartDashboard.putNumber("Right Drive Current 2", Robot.pdp.getCurrent(3));*/
		
	}
	
	public static void updateValues() {
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
		SmartDashboard.putBoolean("a state", xBtnA);
		xBtnB = xbox.getBButton();
		SmartDashboard.putBoolean("b state", xBtnB);
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