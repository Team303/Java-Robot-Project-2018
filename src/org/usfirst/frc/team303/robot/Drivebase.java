package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;

public class Drivebase {

	private WPI_TalonSRX rightBack;
	private WPI_TalonSRX rightFront;
	private WPI_TalonSRX leftFront;
	private WPI_TalonSRX leftBack;

	SpeedControllerGroup rightMotors;
	SpeedControllerGroup leftMotors;
	DifferentialDrive drive;

	public Drivebase() {
		rightBack = new WPI_TalonSRX(RobotMap.REAR_RIGHT);
		rightFront = new WPI_TalonSRX(RobotMap.FRONT_RIGHT);
		leftFront = new WPI_TalonSRX(RobotMap.FRONT_LEFT);
		leftBack = new WPI_TalonSRX(RobotMap.REAR_LEFT);
		rightMotors = new SpeedControllerGroup(rightBack, rightFront);
		leftMotors = new SpeedControllerGroup(leftBack, leftFront);
		drive = new DifferentialDrive(leftMotors, rightMotors);
		
		rightBack.setInverted(RobotMap.REAR_RIGHT_INV);
		rightFront.setInverted(RobotMap.FRONT_RIGHT_INV);
		leftBack.setInverted(RobotMap.REAR_LEFT_INV);
		leftFront.setInverted(RobotMap.FRONT_LEFT_INV);
		
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		zeroEncoder();
		Robot.navX.zeroYaw();
	}

	public void drive(double left, double right) {
		drive.tankDrive(-left, -right);
	}

	public void zeroEncoder() {
		leftFront.setSelectedSensorPosition(0, 0, 1000);
		rightFront.setSelectedSensorPosition(0, 0, 1000);
	}

	public int getLeftEncoder() {
		return -leftFront.getSelectedSensorPosition(0);
	}

	public int getRightEncoder() {
		return rightFront.getSelectedSensorPosition(0); //this is negative for practice bot
	}

}
