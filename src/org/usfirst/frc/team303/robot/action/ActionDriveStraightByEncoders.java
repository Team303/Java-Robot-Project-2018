package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class ActionDriveStraightByEncoders implements Action {

	public int distance = 0;
	public double power = 0;
	public double timeout = 0;
	public boolean firstRun = true;
	Timer timer = new Timer();
	public double initalYaw = 0;
	
	public ActionDriveStraightByEncoders(int distance, double power) {
		this(distance, power, 15);
	}

	public ActionDriveStraightByEncoders(int distance, double power, double timeout) {
		this.distance = distance;
		this.power = power;
		this.timeout = timeout; //in seconds
	}

	public void run() {
		if (firstRun) {
			initalYaw = Robot.navX.getYaw();
			timer.start();
			firstRun = false;
		}

		//call drive straight - returns a double array with first index as left POWER, and second index as right POWER
		//driveStraight(power, angle difference, tuning constant)
		double[] pow = Action.driveStraight(-power, Robot.navX.getYaw()-initalYaw, 0.01);
		Robot.drivebase.drive(pow[0], pow[1]);		
	}


	public boolean isFinished() {
		//Return true if the current encoder value is more or equal to the distance
		//OR return true if the time is more or equal to the timeout
		if(timer.get()>=timeout) timer.stop();
		return (Robot.drivebase.getLeftEncoder() >= distance) || (timer.get() >= timeout);
	}

}
