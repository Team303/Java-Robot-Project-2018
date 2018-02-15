package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase {
	
	private WPI_TalonSRX rightBack = new WPI_TalonSRX(RobotMap.REAR_RIGHT);
	private WPI_TalonSRX rightMiddle = new WPI_TalonSRX(RobotMap.MIDDLE_RIGHT);
	private WPI_TalonSRX rightFront = new WPI_TalonSRX(RobotMap.FRONT_RIGHT);
	private WPI_TalonSRX leftFront = new WPI_TalonSRX(RobotMap.FRONT_LEFT);
	private WPI_TalonSRX leftMiddle = new WPI_TalonSRX(RobotMap.MIDDLE_LEFT);
	private WPI_TalonSRX leftBack = new WPI_TalonSRX(RobotMap.REAR_LEFT);
	
	SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightBack, rightMiddle, rightFront);
	SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftBack, leftMiddle, leftFront);
	DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

	public Drivebase() {
		rightBack.setInverted(RobotMap.REAR_RIGHT_INV);
		rightMiddle.setInverted(RobotMap.MIDDLE_RIGHT_INV);
		rightFront.setInverted(RobotMap.FRONT_RIGHT_INV);
		
		leftBack.setInverted(RobotMap.REAR_LEFT_INV);
		leftMiddle.setInverted(RobotMap.MIDDLE_LEFT_INV);
		leftFront.setInverted(RobotMap.FRONT_LEFT_INV);
		
		rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
	
	public void drive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public void zeroEncoder() {
		leftFront.setSelectedSensorPosition(0, 0, 0);
		rightBack.setSelectedSensorPosition(0, 0, 0);
	}
	
	public int getLeftEncoder() {
		return leftFront.getSelectedSensorPosition(0);
	}
	
	public int getRightEncoder() {
		return rightBack.getSelectedSensorPosition(0);
	}

}
