package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Lift {

	//bottom to top is ~53000 ticks
	TalonSRX lift;
	TalonSRX liftEncoder;
	public static final int kTimeoutMs = 1000;
	int setpoint = 0;

	//Stop range is the range above and below the setpoint where the robot should stop moving
	//stopRange < outerRange
		//When you are inside the outerRange, set the power to 0.4
	int stopRange = 3000;
	int outerRange = 10000;

	public Lift() {
		lift = new TalonSRX(RobotMap.LIFT_ID);
		liftEncoder = new TalonSRX(RobotMap.MIDDLE_LEFT);
		lift.setInverted(RobotMap.LIFT_INV);
		liftEncoder.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
	}
	
	public void setSetpoint(int setpoint) {
		this.setpoint = setpoint; 
	}
	
	public boolean autoControl() {
		int encoderPosition = getEncoder();
		
		int topStopRange = setpoint + stopRange;
		int bottomStopRange = setpoint - stopRange;
		int topOuterRange = setpoint + outerRange;
		int bottomOuterRange = setpoint - outerRange;

		if (setpoint > 0) {
			if (encoderPosition > topStopRange) {
				//Go down because the encoder is greater than the range for the setpoint
				if (encoderPosition < topOuterRange) {
					setPercentVoltage(-0.4);
				} else if (encoderPosition > topOuterRange) {
					setPercentVoltage(-0.8);
				}
				return false;
			} else if (encoderPosition < bottomStopRange) {
				//Go up because the encoder is less than the range for the setpoint
				if (encoderPosition > bottomOuterRange) {
					setPercentVoltage(0.4);
				} else if (encoderPosition < bottomOuterRange) {
					setPercentVoltage(0.8);
				}
				return false;
			} else if (encoderPosition > bottomStopRange && encoderPosition < topStopRange) {
				//Stop lift because it is within the range
				setPercentVoltage(0);
				return true;
			}
		} else if (setpoint == 0) {
			if (encoderPosition > topOuterRange) {
				setPercentVoltage(-0.6);
				return false;
			} else if (encoderPosition < topOuterRange && encoderPosition > topStopRange) {
				setPercentVoltage(-0.2);
				return false;
			} else if (encoderPosition < topStopRange) {
				setPercentVoltage(0);
				return true;
			}
		} else {
			return true;
		}
		
		return true;
	}
	
	public int getEncoder() {
		return liftEncoder.getSelectedSensorPosition(0);
	}
	
	public void setPercentVoltage(double power) {
		lift.set(ControlMode.PercentOutput, power);
	}

}
