package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {

	//bottom to top is ~65000 ticks
	TalonSRX lift;
	public static final int kTimeoutMs = 1000;
	int setpoint = 0;
	//double kP = 0.00002;

	final static int STOP_RANGE = 2000; //stop when <= 2000 ticks away from setpoint
	final static int INNER_RANGE = 10000; //use partial power when between OUTER_RANGE and INNER_RANGE ticks away from setpoint
	final static int OUTER_RANGE = 20000; //use full power when >OUTER_RANGE ticks away from setpoint 
	
	public Lift() {
		lift = new TalonSRX(RobotMap.LIFT_ID);
		lift.setInverted(RobotMap.LIFT_INV);
		Robot.climber.winch.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		Robot.climber.winch.setSelectedSensorPosition(0, 0, 1000);
		
	}
	
	public void setSetpoint(int setpoint) {
		if(setpoint<72000 && setpoint>-1) {
			this.setpoint = setpoint; 			
		}
	}
	
	public void proportionalControl() {
		int error = setpoint-getEncoder();
		double power;
		if(error>0) {
			power = Math.abs(Math.pow(error/32500.0, 0.23));
		} else {
			power = (error/65000.0);
			power = Math.max(-0.5, power);  //https://www.youtube.com/watch?v=VsH0cngmLQM
		}
		
		setPercentVoltage(power);
		SmartDashboard.putNumber("lift error", error);
		SmartDashboard.putNumber("lift power", power);
		SmartDashboard.putNumber("lift setpoint", setpoint);
	}
	
	@Deprecated
	public void autoControl() {
		int encoderPosition = getEncoder();
		int error = Math.abs(setpoint-encoderPosition);
		double power = 0;
		
		if(setpoint==0) {
			setPercentVoltage(-0.5);
			return;
		}
		
		if(error>OUTER_RANGE) power = 1;
		else if(error>INNER_RANGE) power = 0.8;
		else if(error>STOP_RANGE) power = 0.6;
		
		if(encoderPosition>setpoint) {
			setPercentVoltage(-power);
		} else if(encoderPosition<setpoint) {
			setPercentVoltage(power);
		}
		
	}
	
	public int getSetpoint() {
		return setpoint;
	}
	
	public int getEncoder() {
		return Robot.climber.winch.getSelectedSensorPosition(0);
	}
	
	public void setPercentVoltage(double power) {
		lift.set(ControlMode.PercentOutput, power);
	}

}
