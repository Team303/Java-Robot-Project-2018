package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {

	//bottom to top is ~65000 ticks
	TalonSRX lift;
	public static final int kTimeoutMs = 1000;
	int setpoint = 0;
	double iAcc = 0.0;
	double maxIAcc = 0.3;
	public static final double kI = 0.000000000000001; //TODO

	public Lift() {
		lift = new TalonSRX(RobotMap.LIFT_ID);
		lift.setInverted(RobotMap.LIFT_INV);
		Robot.climber.winch.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
		Robot.climber.winch.setSelectedSensorPosition(0, 0, 1000);

	}

	public void setSetpoint(int setpoint) {
		if(setpoint<70000 && setpoint>-1) {
			this.setpoint = setpoint; 			
		}
	}

	public void control() {
		int error = setpoint-getEncoder();
		double power;
		
		//kP
		if(error>0) {
			power = Math.abs(Math.pow(error/32500.0, 0.2));
		} else {
			power = (error/65000.0);
			power = Math.max(-0.5, power);  //https://www.youtube.com/watch?v=VsH0cngmLQM
		}
		
		//kI
		if(onTarget()) {
			iAcc = 0;
		} else {
			iAcc+=error*kI;			
		}
		power+=Math.copySign(Math.min(Math.abs(iAcc), maxIAcc), iAcc);
		
		setPercentVoltage(power);
		SmartDashboard.putNumber("lift error", error);
		SmartDashboard.putNumber("lift power", power);
		SmartDashboard.putNumber("lift setpoint", setpoint);
	}

	public boolean onTarget() {
		return Math.abs(setpoint-getEncoder())<1000;
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
