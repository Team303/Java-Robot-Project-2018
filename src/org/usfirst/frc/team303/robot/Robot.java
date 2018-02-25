/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team303.robot;

import org.usfirst.frc.team303.robot.action.ActionWait;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	public static enum Auto {DO_NOTHING, FORWARD, SCALE_TO_SWITCH, SWITCH_TO_SCALE, SWITCH, SCALE;}
	private String gameMessage;
	private SendableChooser<Position> positionChooser = new SendableChooser<>();
	private SendableChooser<Auto> configRR = new SendableChooser<>();
	private SendableChooser<Auto> configRL = new SendableChooser<>();
	private SendableChooser<Auto> configLL = new SendableChooser<>();
	private SendableChooser<Auto> configLR = new SendableChooser<>();

	public static NavX navX;
	//public static Lift lift;
	//public static Climber climber;
	public static Drivebase drivebase;
	//public static Intake intake;
	public static Autonomous auto;
	//public static Compressor compressor;
	//private double wheelSpeed = -0.75;

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
		//lift = new Lift();
		//climber = new Climber();
		//intake = new Intake();
		drivebase = new Drivebase();
		auto = new Autonomous();
		//compressor = new Compressor();

		auto.initWaypoints();
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

		//Message is three character string with first letter as switch and second as scale
		gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		while(gameMessage.length()!=3) {
			gameMessage = DriverStation.getInstance().getGameSpecificMessage();
		}
		SmartDashboard.putString("game string", gameMessage);

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
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		} else if(gameMessage.startsWith("LR")) {
			Auto selected = configLR.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		} else if(gameMessage.startsWith("RR")) {
			Auto selected = configRR.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		} else if(gameMessage.startsWith("RL")) {
			Auto selected = configRL.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		}
	}

	public void assembleRightAutoModes() {
		if(gameMessage.startsWith("LL")) {
			Auto selected = configLL.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		} else if(gameMessage.startsWith("LR")) {
			Auto selected = configLR.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		} else if(gameMessage.startsWith("RR")) {
			Auto selected = configRR.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		} else if(gameMessage.startsWith("RL")) {
			Auto selected = configRL.getSelected();
			if(selected==Auto.DO_NOTHING) {}
			else if(selected==Auto.FORWARD) auto.assembleForward();
			else if(selected==Auto.SCALE_TO_SWITCH) {}
			else if(selected==Auto.SWITCH_TO_SCALE) {}
			else if(selected==Auto.SWITCH) {}
			else if(selected==Auto.SCALE) {}
		}
	}

	public void assembleCenterAutoModes() {
		if(gameMessage.startsWith("L")) {
			Auto selected = configLL.getSelected();
			Auto selected2 = configLR.getSelected();
			if(selected==Auto.DO_NOTHING || selected2==Auto.DO_NOTHING) return;
			else auto.assembleCenterSwitchLeft();
		} else if(gameMessage.startsWith("R")) {
			Auto selected = configRR.getSelected();
			Auto selected2 = configRL.getSelected();
			if(selected==Auto.DO_NOTHING || selected2==Auto.DO_NOTHING) return;
			else auto.assembleCenterSwitchRight();
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
	public void teleopInit() {}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		OI.update();

		//drivebase
		drivebase.drive(OI.lY, OI.rY);

		//intake wheels
//		if(OI.lBtn[2]) { //in
//			intake.setWheels(-wheelSpeed, wheelSpeed);
//		} else if(OI.lPov==0) { //out
//			intake.setWheels(wheelSpeed, -wheelSpeed);
//		} else if(OI.lBtn[5]) { //left
//			intake.setWheels(wheelSpeed, wheelSpeed);
//		} else if(OI.lBtn[6]) { //right
//			intake.setWheels(-wheelSpeed, -wheelSpeed);
//		} else {
//			intake.setWheels(0, 0);
//		}
//
//		//intake gripper
//		if(OI.xBtnX) {
//			intake.setGripper(true);
//		} else if(OI.xBtnB) {
//			intake.setGripper(false);
//		}
//
//		//intake rotation
//		if(OI.xBtnY) {
//			intake.setRotation(false);
//		} else if(OI.xBtnA) {
//			intake.setRotation(true);
//		}
//
//		//climber
//		if(OI.xLeftBumper) {
//			climber.setPistons(true);
//		} else if(OI.xRightBumper) {
//			climber.setPistons(false);
//		}
//
//		if(Math.abs(OI.xrY)>0.1) {
//			climber.setWinch(OI.xrY);			
//		}

		//		//lift
		//		if(OI.xPov==0) { //up
		//			lift.setSetpoint(30000);
		//		} else if(OI.xPov==180) { //down
		//			lift.setSetpoint(0);
		//		}

//		lift.setPercentVoltage(-OI.xlY);
		//		lift.autoControl();
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("left encoder", drivebase.getLeftEncoder());
		SmartDashboard.putNumber("right encoder", drivebase.getRightEncoder());
//		SmartDashboard.putNumber("lift encoder", lift.getEncoder());
		SmartDashboard.putNumber("theta", navX.getYaw());
//		SmartDashboard.putBoolean("compressor on", compressor.enabled());
		navX.collisionDetected();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
