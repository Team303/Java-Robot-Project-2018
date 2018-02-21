package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Lift {

	//bottom to top is ~53000 ticks
	TalonSRX lift;
	TalonSRX liftEncoder;
	public static final int kTimeoutMs = 1000;
	int setpoint = 0;

	final static int STOP_RANGE = 2000;
	final static int OUTER_RANGE = 10000;
	final static int OUTER_RANGE_2 = 20000;
	
	public Lift() {
		lift = new TalonSRX(RobotMap.LIFT_ID);
		liftEncoder = new TalonSRX(RobotMap.MIDDLE_LEFT);
		lift.setInverted(RobotMap.LIFT_INV);
		liftEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
	}
	
	public void setSetpoint(int setpoint) {
		this.setpoint = setpoint; 
	}
	
	//depending on how this works we may just want to replace with a p loop
	public void autoControl() {
		int encoderPosition = getEncoder();
		int error = Math.abs(setpoint-getEncoder());
		double power = 0;
		
		if(setpoint==0) {
			setPercentVoltage(-0.5);
			return;
		}
		
		if(error>=OUTER_RANGE_2) power = 1;
		else if(error>=OUTER_RANGE) power = 0.8;
		else if(error>=STOP_RANGE) power = 0.6;
		
		if(encoderPosition>setpoint) {
			setPercentVoltage(-power);
		} else if(encoderPosition<setpoint) {
			setPercentVoltage(power);
		}
		
	}
	
	public int getEncoder() {
		return liftEncoder.getSelectedSensorPosition(0);
	}
	
	public void setPercentVoltage(double power) {
		lift.set(ControlMode.PercentOutput, power);
	}

}
