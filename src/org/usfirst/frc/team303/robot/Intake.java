package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
	
	public static final int kTimeoutMs = 1000;
	TalonSRX rightIntake;
	TalonSRX leftIntake;	
	Solenoid gripper;
	Solenoid rotation;	
	
	public Intake(){
		leftIntake = new TalonSRX(RobotMap.INTAKE_LEFT_ID);
		rightIntake = new TalonSRX(RobotMap.INTAKE_RIGHT_ID);
		gripper = new Solenoid(RobotMap.INTAKE_ACTUATE_SOLENOID_ID);
		rotation = new Solenoid(RobotMap.INTAKE_ROTATION_SOLENOID_ID);
	
		leftIntake.setInverted(RobotMap.INTAKE_LEFT_INV);
		rightIntake.setInverted(RobotMap.INTAKE_RIGHT_INV);
	}
	
	public void setWheels(double percentVoltage){
		leftIntake.set(ControlMode.PercentOutput, percentVoltage);
		rightIntake.set(ControlMode.PercentOutput, percentVoltage);
	}
	
	public void setRotation(boolean extended) {
		rotation.set(extended);
	}
	
	public void setGripper(boolean gripped) {
		gripper.set(gripped);
	}
	
}
