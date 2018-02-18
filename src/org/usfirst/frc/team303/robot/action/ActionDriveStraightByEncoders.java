package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class ActionDriveStraightByEncoders implements Action {

	public int distance = 0;
	public double power = 0;
	public double timeout = 0;
	public boolean firstRun = false;
	Timer timer = new Timer();

	public ActionDriveStraightByEncoders() {
		//default distance of 10000 ticks
		this(10000);
	}

	public ActionDriveStraightByEncoders(int distance) {
		this(distance, 0.5);
	}

	public ActionDriveStraightByEncoders(int distance, double power) {
		this(distance, power, 15);
	}

	public ActionDriveStraightByEncoders(int distance, double power, double timeout) {
		//this refers to the instance variable
		//4096 ticks for one rotation
		this.distance = distance;
		//0 to 1 range
		this.power = power;
		//time in seconds
		this.timeout = timeout;
	}

	public void run() {
		if (!firstRun) {
			//Zero Encoder and Zero Yaw and start time ON THE FIRST RUN
			//And set firstRun to true so this is not called again
			Robot.drivebase.zeroEncoder();
			Robot.navX.zeroYaw();
			timer.start();
			firstRun = true;
		}

		//call drive straight - returns a double array with first index as left POWER, and second index as right POWER
		//driveStraight(power, angle difference, tuning constant)
		double[] pow = Action.driveStraight(power, Robot.navX.getYaw(), 0.01);
		Robot.drivebase.drive(pow[0], pow[1]);		
	}


	public boolean isFinished() {
		//Return true if the current encoder value is more or equal to the distance
		//OR return true if the time is more or equal to the timeout
		return (Robot.drivebase.getLeftEncoder() >= distance) || (timer.get() >= timeout);
	}

}
