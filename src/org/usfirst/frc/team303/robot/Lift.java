package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {

	//bottom to top is ~80000 ticks
	TalonSRX lift;
	public static final int kTimeoutMs = 1000;
	int setpoint = 0;
	double iAcc = 0.0;
	double maxIAcc = 0.3;
	public static final double kI = 0.000001; //TODO
	
	public Lift() {
		lift = new TalonSRX(RobotMap.LIFT_ID);
		lift.setInverted(RobotMap.LIFT_INV);
		//Robot.climber.winch.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		//Robot.climber.winch.setSelectedSensorPosition(0, 0, 1000);
		lift.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		lift.setSelectedSensorPosition(0, 0, 1000);
		lift.setSensorPhase(false);
	
	}

	public void setSetpoint(int setpoint) {
		if(setpoint>-10000) {			
			this.setpoint = setpoint;
		}
	}
	
	public void autoControl() {
		int error = setpoint-getEncoder();
		double power;
		
		if(error>0) { //going up
			power = error*0.001;
			//power = Math.pow(error/32500.0, 0.2);
		} else {
			power = (error/65000.0);				
			power = Math.max(-0.5, power);
		}
		
		setPercentVoltage(power);
		SmartDashboard.putNumber("lift error", error);
		SmartDashboard.putNumber("lift power", power);
		SmartDashboard.putNumber("lift setpoint", setpoint);
	}

	
	public void control() {
		int error = setpoint-getEncoder();
		double power;
		
		//for the proportional term, we simply get the system error,
		//run it through a function, and set it to the motor power.
		
		if(error>0) { //going up
			//power = error*0.001;
			power = Math.pow(error/32500.0, 0.2);
		} else {
			//if we are going down, the power can be linear, 
			//since we want a smooth descent but don't want to unwind the winch too far
			power = (error/65000.0);				
			power = Math.max(-0.5, power);
		}
		
		//For the integral term, we add extra motor power every execution
		//we aren't at the setpoint. If we are there, don't do anything extra.
//		if(onTarget()) {
//			iAcc = 0;
//		} else {
//			iAcc+=error*kI;			
//		}
		
		//we don't want the integral term to get too high, or we'll overshoot the setpoint.
		//To fix this we limit I's maximum contribution.
//		power+=Math.copySign(Math.min(Math.abs(iAcc), maxIAcc), iAcc);
		
		setPercentVoltage(power);
		SmartDashboard.putNumber("lift error", error);
		SmartDashboard.putNumber("lift power", power);
		SmartDashboard.putNumber("lift setpoint", setpoint);
	}

	public boolean onTarget() {
		return Math.abs(setpoint-getEncoder())<500;
	}

	public int getSetpoint() {
		return setpoint;
	}

	public int getEncoder() {
		return -lift.getSelectedSensorPosition(0);
		//return Robot.climber.winch.getSelectedSensorPosition(0);
	}

	public void setPercentVoltage(double power) {
		lift.set(ControlMode.PercentOutput, power);
	}

}
