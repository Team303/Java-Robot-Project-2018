package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Lift {

	public TalonSRX lift;
	public TalonSRX liftEncoder;
	public static final int kTimeoutMs = 1000;

	public Lift() {
		lift = new TalonSRX(RobotMap.LIFT_ID);
		liftEncoder = new TalonSRX(RobotMap.MIDDLE_LEFT);
		lift.setInverted(RobotMap.LIFT_INV);
		liftEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
	}

	public void setPercentVoltage(double power) {
		lift.set(ControlMode.PercentOutput, power);
	}

	public int getEncoder() {
		return liftEncoder.getSelectedSensorPosition(0);
	}
	
}
