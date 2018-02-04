package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Climber {

	TalonSRX climber = new TalonSRX(RobotMap.CLIMBER_ID);
	
	
	public Climber(){
		climber.setInverted(RobotMap.CLIMBER_INV);
	}
	
	public void set(boolean run, double percentVoltage){
		if (run) {
			climber.set(ControlMode.PercentOutput, percentVoltage);
		} else {
			climber.set(ControlMode.PercentOutput, 0);
		}
		
	}
}
