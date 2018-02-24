package org.usfirst.frc.team303.robot.action;
import org.usfirst.frc.team303.robot.Robot;

public class ActionTurnAngle implements Action{
	
	private double angle;
	private double power;
	private boolean firstRun;

	public ActionTurnAngle(double angle, double power) {
		this.angle = angle;
		this.power = power;
	}

	public void run() {
		if(!firstRun) {
			Robot.navX.zeroYaw();
			firstRun = true;
		}

		//Turn left by default
		//If you want to turn right, negate the power
		Robot.drivebase.drive(-power, power);
	}

	public boolean isFinished(){
		//If the yaw of navX is greater than the angle, stop calling run()
		return Robot.navX.getYaw() >= angle;
	}

}
