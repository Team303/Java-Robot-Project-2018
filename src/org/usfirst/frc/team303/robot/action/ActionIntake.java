package org.usfirst.frc.team303.robot.action;

import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class ActionIntake implements Action {
	private Timer timer;
	private boolean firstRun;
	private double power;
	private double time;

	public ActionIntake() {
		this(0.5);
	}

	public ActionIntake(double power) {
		this(0.5, 1);
	}

	public ActionIntake(double power, double time) {
		this.power = power;
		this.time = power;
		firstRun = false;
	}


	public void run() {
		if (firstRun) {
			timer.start();
			firstRun = true;
		}

		Robot.intake.setWheels(power, power);
	}

	public boolean isFinished() {

		boolean end = false;
		if (timer.get() >= time) {
			Robot.intake.setWheels(0, 0);
			end = true;
		}

		return end;
	}

}
