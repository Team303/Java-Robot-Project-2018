package org.usfirst.frc.team303.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Lift {

	TalonSRX lift = new TalonSRX(RobotMap.LIFT_ID);
	public static final int kTimeoutMs = 1000;

	public Lift(){
		lift.setInverted(RobotMap.LIFT_INV);

		//Profile Slot 0
		lift.selectProfileSlot(0, 0);
		lift.config_kF(0, 0.2, kTimeoutMs);
		lift.config_kP(0, 0.2, kTimeoutMs);
		lift.config_kI(0, 0, kTimeoutMs);
		lift.config_kD(0, 0, kTimeoutMs);

		//Profile Slot 1
		lift.selectProfileSlot(1, 0);
		lift.config_kF(1, 0.2, kTimeoutMs);
		lift.config_kP(1, 0.2, kTimeoutMs);
		lift.config_kI(1, 0, kTimeoutMs);
		lift.config_kD(1, 0, kTimeoutMs);
		//lift.config_IntegralZone(0, 100, Constants.kTimeoutMs);

	}


	public void set(double setPoint, int slot) {
		lift.selectProfileSlot(slot, 0);
		lift.set(ControlMode.Position, setPoint);
	}


}
