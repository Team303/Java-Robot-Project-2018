package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
	
	TalonSRX rightIntake = new TalonSRX(RobotMap.INTAKE_RIGHT_ID);
	TalonSRX leftIntake = new TalonSRX(RobotMap.INTAKE_LEFT_ID);
	
	TalonSRX moveIntake = new TalonSRX(RobotMap.INTAKE_MOVE_ID);
	
	public static final int kTimeoutMs = 1000;
	
	Solenoid gripperOpen = new Solenoid(RobotMap.SOLENOID_OPEN_ID);
	Solenoid gripperClose = new Solenoid(RobotMap.SOLENOID_CLOSE_ID);
	
	public Intake(){
		rightIntake.setInverted(RobotMap.INTAKE_RIGHT_INV);
		leftIntake.setInverted(RobotMap.INTAKE_LEFT_INV);
		
		//Profile Slot 0
		moveIntake.selectProfileSlot(0, 0);
		moveIntake.config_kF(0, 0.2, kTimeoutMs);
		moveIntake.config_kP(0, 0.2, kTimeoutMs);
		moveIntake.config_kI(0, 0, kTimeoutMs);
		moveIntake.config_kD(0, 0, kTimeoutMs);
		
		//Profile Slot 1
		moveIntake.selectProfileSlot(1, 0);
		moveIntake.config_kF(1, 0.2, kTimeoutMs);
		moveIntake.config_kP(1, 0.2, kTimeoutMs);
		moveIntake.config_kI(1, 0, kTimeoutMs);
		moveIntake.config_kD(1, 0, kTimeoutMs);
		//moveIntake.config_IntegralZone(0, 100, Constants.kTimeoutMs);

	}
	
	public void set(double percentVoltage){
		leftIntake.set(ControlMode.PercentOutput, percentVoltage);
		rightIntake.set(ControlMode.PercentOutput, percentVoltage);
	}
	
	public void setPiston(boolean open) {
		gripperOpen.set(open);
		gripperClose.set(!open);	
	}
	
	public void moveIntake(double setPoint, int slot) {
		moveIntake.selectProfileSlot(slot, 0);
		moveIntake.set(ControlMode.Position, setPoint);
	}
	
	
	
	
}
