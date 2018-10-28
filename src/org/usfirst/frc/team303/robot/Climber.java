package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Climber {

	public TalonSRX winch;
	Solenoid hookDeploy;
	Solenoid buddyDeploy;
	
	public Climber(){
		winch = new TalonSRX(RobotMap.CLIMBER_WINCH_ID);
		winch.setInverted(RobotMap.CLIMBER_WINCH_INV);
		hookDeploy = new Solenoid(RobotMap.CLIMBER_HOOK_DEPLOY_SOLENOID_ID);
		buddyDeploy = new Solenoid(RobotMap.BUDDY_DEPLOY_SOLENOID_ID);
	}
	
	public void setWinch(double percentVoltage){
		winch.set(ControlMode.PercentOutput, percentVoltage);
	}
	
	public void setPistons(boolean deployed) {
		hookDeploy.set(deployed);
	}
	
	public void setBuddyPiston(boolean deployed) {
		buddyDeploy.set(deployed);
	}
}
