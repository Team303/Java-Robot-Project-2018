/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team303.robot;

import org.usfirst.frc.team303.robot.action.ActionWait;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {	
	public static enum Position {LEFT, RIGHT, CENTER;}
	public static enum Auto {DO_NOTHING, FORWARD, SCALE_AND_SWITCH, EXCHANGE, SWITCH, SCALE, HALF_CROSS, QUICK_SCALE;}
	private String gameMessage;
	private SendableChooser<Position> positionChooser = new SendableChooser<>();
	private SendableChooser<Auto> configRR = new SendableChooser<>();
	private SendableChooser<Auto> configRL = new SendableChooser<>();
	private SendableChooser<Auto> configLL = new SendableChooser<>();
	private SendableChooser<Auto> configLR = new SendableChooser<>();

	public static Camera camera;
	public static NavX navX;
	public static Lift lift;
	public static Climber climber;
	public static Drivebase drivebase;
	public static Intake intake;
	public static Autonomous auto;
	public static Compressor compressor;
	private double wheelSpeed = 0.6;
	public static Canifier canifier;
	public boolean navXControl = false;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	
		
		positionChooser.addObject("LEFT", Position.LEFT);
		positionChooser.addObject("CENTER", Position.CENTER);
		positionChooser.addObject("RIGHT", Position.RIGHT);

		
		
		for(Auto auto : Auto.values()) {
			configRR.addObject(auto.toString(), auto);
			configRL.addObject(auto.toString(), auto);
			configLL.addObject(auto.toString(), auto);
			configLR.addObject(auto.toString(), auto);
		}

		SmartDashboard.putData("Position", positionChooser);
		SmartDashboard.putData("Config RR", configRR);
		SmartDashboard.putData("Config RL", configRL);
		SmartDashboard.putData("Config LL", configLL);
		SmartDashboard.putData("Config LR", configLR);	

		navX = new NavX();
		intake = new Intake();
		drivebase = new Drivebase();
		climber = new Climber();
		lift = new Lift();
		auto = new Autonomous();
		compressor = new Compressor();
		canifier = new Canifier();
		camera = new Camera();
		
		auto.initWaypoints();
	
		canifier.setWhite();
		
//		boolean trajectoriesRealized = false;
//		while(!trajectoriesRealized) {
//			try {
//				auto.realizeTrajectories();
//				trajectoriesRealized = true;
//			} catch (NullPointerException e) {
//				try {Thread.sleep(500);} catch (InterruptedException e2) {}
//			}
//		}

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		auto.realizeTrajectories();
		compressor.setClosedLoopControl(false);
		compressor.stop();
		
		//Message is three character string with first letter as switch and second as scale
		gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		while(gameMessage==null) {
			gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		}
		SmartDashboard.putString("game string", gameMessage);
		System.out.println("game string is: " + gameMessage);

		Position position = positionChooser.getSelected();
		if(position==Position.LEFT) {
			assembleLeftAutoModes();
		} else if(position==Position.CENTER) {
			assembleCenterAutoModes();
		} else if(position==Position.RIGHT) {
			assembleRightAutoModes();
		}

		auto.arr.add(new ActionWait(9999999));
	}

	public void assembleLeftAutoModes() {
		if(gameMessage.startsWith("LL")) {
			Auto selected = configLL.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {	auto.assembleLeftLeftSwitchScale();}
			else if(selected==Auto.EXCHANGE) {auto.assembleTest();} //TODO REMOVE THIS PLEASE
			else if(selected==Auto.SWITCH) {auto.assembleLeftLeftSwitch();}
			else if(selected==Auto.SCALE) {auto.assembleLeftLeftScale();}
			else if(selected==Auto.QUICK_SCALE) {auto.assembleLeftLeftQuickScale();}
		} else if(gameMessage.startsWith("LR")) {
			Auto selected = configLR.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {}
			else if(selected==Auto.EXCHANGE) {}
			else if(selected==Auto.SWITCH) {auto.assembleLeftLeftSwitch();}
			else if(selected==Auto.SCALE) { auto.newLeftRightScale();}
			else if(selected==Auto.HALF_CROSS) { auto.assembleLeftRightScaleHalf();}
		} else if(gameMessage.startsWith("RR")) {
			Auto selected = configRR.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {}
			else if(selected==Auto.EXCHANGE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {auto.newLeftRightScale();}
			else if(selected==Auto.HALF_CROSS) { auto.assembleLeftRightScaleHalf();}
		} else if(gameMessage.startsWith("RL")) {
			Auto selected = configRL.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {}
			else if(selected==Auto.EXCHANGE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {auto.assembleLeftLeftScale();}
			else if(selected==Auto.QUICK_SCALE) {auto.assembleLeftLeftQuickScale();}
		}
	}

	public void assembleRightAutoModes() {
		if(gameMessage.startsWith("LL")) {
			Auto selected = configLL.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {}
			else if(selected==Auto.EXCHANGE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {auto.newRightLeftScale();}
			else if(selected==Auto.HALF_CROSS) { auto.assembleRightLeftScaleHalf();}
		} else if(gameMessage.startsWith("LR")) {
			Auto selected = configLR.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {}
			else if(selected==Auto.EXCHANGE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {auto.assembleRightRightScale();}
			else if(selected==Auto.QUICK_SCALE) {auto.assembleRightRightQuickScale();}
		} else if(gameMessage.startsWith("RR")) {
			Auto selected = configRR.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
//			else if(selected==Auto.SCALE_AND_SWITCH) {auto.assembleRightRightSwitchRightScale();}
			else if(selected==Auto.EXCHANGE) {} //this should do nothing
			else if(selected==Auto.SWITCH) {auto.assembleRightRightSwitch();}
			else if(selected==Auto.SCALE) {auto.assembleRightRightScale();}
			else if(selected==Auto.SCALE_AND_SWITCH) {auto.assembleRightRightSwitchScale();}
			else if(selected==Auto.QUICK_SCALE) {auto.assembleRightRightQuickScale();}
		} else if(gameMessage.startsWith("RL")) {
			Auto selected = configRL.getSelected();
			System.out.println(selected);
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_AND_SWITCH) {}
			else if(selected==Auto.EXCHANGE) {}
			else if(selected==Auto.SWITCH) {auto.assembleRightRightSwitch();}
			else if(selected==Auto.SCALE) {auto.newRightLeftScale();}
			else if(selected==Auto.HALF_CROSS) { auto.assembleRightLeftScaleHalf();}

		}
	}

	public void assembleCenterAutoModes() {
		if(gameMessage.startsWith("L")) {
			Auto selected = configLL.getSelected();
			Auto selected2 = configLR.getSelected();
			if(selected==Auto.DO_NOTHING || selected2==Auto.DO_NOTHING) return;
			else {
				System.out.println("SWITCH LEFT");
				auto.assembleCenterSwitchLeft();

			}
		} else if(gameMessage.startsWith("R")) {
			Auto selected = configRR.getSelected();
			Auto selected2 = configRL.getSelected();
			if(selected==Auto.DO_NOTHING || selected2==Auto.DO_NOTHING) return;
			else {
				System.out.println("SWITCH RIGHT");
				auto.assembleCenterSwitchRight();
			}
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		auto.run();
	}

	@Override
	public void teleopInit() {
		compressor.setClosedLoopControl(true);
	}
	
	boolean disabledState;
	public boolean pulse(boolean input){	
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
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//drivebase
		double driveAlter = (-Math.abs(1-((lift.getEncoder()+230000)/230000.0)))+1;
		double lDrivePower = (OI.lBtn[4]) ? OI.lY : OI.lY*driveAlter;
		double rDrivePower = (OI.lBtn[4]) ? OI.rY : OI.rY*driveAlter;
		
		if(OI.lPov!=90) {	 //drive normally	
			if(navXControl) {
				//navX.turnController.disable();
				navXControl = false;
			}
			drivebase.drive(lDrivePower, rDrivePower);
		} else { //do pid turn
			navXControl = true;
			if(pulse(navXControl)) {				
				navX.setSetpoint(90);
				navX.turnController.enable();
			}
			double output = navX.getPidOutput();
			drivebase.drive(-output, output);
		}
		

		//intake wheels
		if(OI.lBtn[1]) { //in
			intake.setWheels(-wheelSpeed, wheelSpeed);
		} else if(OI.lBtn[2]) { //out
			intake.setWheels(wheelSpeed, -wheelSpeed);
		} else if(OI.lBtn[5]) { //left
			intake.setWheels(wheelSpeed, wheelSpeed);
		} else if(OI.lBtn[6]) { //right
			intake.setWheels(-wheelSpeed, -wheelSpeed);
		} else if(OI.lBtn[3]) { //slow out
			intake.setWheels(0.4, -0.4);
		} else if(OI.lBtn[7]){ //slow in
			intake.setWheels(-0.3, 0.3);
		} else {
			intake.setWheels(0, 0);
		}

		//intake gripper
		if(OI.rBtn[2] || OI.xBtnX) {
			intake.setGripper(true);
		} else if(OI.rBtn[1] || OI.xBtnB) {
			intake.setGripper(false);
		}

		//intake rotation
		if(OI.xBtnY || OI.rPov==0) {
			intake.setRotation(false);
		} else if(OI.xBtnA || OI.rPov==180) {
			intake.setRotation(true);
		}

		//climber
		if(OI.xLeftBumper) {
			climber.setPistons(true);
		} else if(OI.xRightBumper) {
			climber.setPistons(false);
		}
		
		//buddy climber
		if(OI.xBtnStart) {
			climber.setBuddyPiston(true);
			intake.setGripper(false);
			intake.setRotation(false);
		} else if(OI.xBtnBack) {
			climber.setBuddyPiston(false);
		}

		if(Math.abs(OI.xrY)>0.5) {
			climber.setWinch(-OI.xrY);			
		} else {
			climber.setWinch(0);
		}
		SmartDashboard.putNumber("climber winch power", climber.winch.getMotorOutputPercent());
		

		//lift
		if(OI.xPov==0) { //up
			lift.setSetpoint(70000);
		} else if(OI.xPov==180) { //down
			lift.setSetpoint(0);
		} else if(OI.xPov==90) { //right
			lift.setSetpoint(10000);
		} else if(OI.xPov==270) { //left
			lift.setSetpoint(27000);
		}

		lift.control();
		
		if(OI.xlY>0.5) {
			lift.setSetpoint(lift.getSetpoint()-400);
		} else if(OI.xlY<-0.5){
			lift.setSetpoint(lift.getSetpoint()+400);			
		}
		
	}

	@Override
	public void robotPeriodic() {
		OI.update();
		SmartDashboard.putNumber("left encoder", drivebase.getLeftEncoder());
		SmartDashboard.putNumber("right encoder", drivebase.getRightEncoder());
		SmartDashboard.putNumber("lift encoder", lift.getEncoder());
		SmartDashboard.putNumber("theta", navX.getYaw());
		SmartDashboard.putBoolean("compressor on", compressor.enabled());
		navX.collisionDetected();

	//	if(OI.rZ<0.5) {
	//		lift.lift.setSelectedSensorPosition(0, 0, 100);
	//		lift.setSetpoint(0);
	//	}
		
		if(OI.lZ<0.5) {
			drivebase.zeroEncoder();
			navX.zeroYaw();
		}
		

		
//		int random = (int)(Math.random()*3);
//		if(random==0) {
//			canifier.setRed();
//		} else if(random==1) {
//			canifier.setBlue();
//		} else if(random==2) {
//			canifier.setGreen();
//		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
