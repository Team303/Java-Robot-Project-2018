package org.usfirst.frc.team303.robot.action;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team303.robot.Path;
import org.usfirst.frc.team303.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ActionDriveByTrajectory implements Action {
	public Path path;
	boolean firstRun = true;
	double l;
	double r;
	double temp;
	Notifier notifier;
	int initialEncoderL;
	int initialEncoderR;
	
	public ActionDriveByTrajectory(Trajectory trajectory, boolean isReversed) {
		path = new Path(trajectory);
		notifier = new Notifier(()->{
	
			if(isReversed) { //TODO probably also need to invert sensors for isReversed
				temp = l;
				l = -path.testEncRight.calculate((Robot.drivebase.getRightEncoder()-initialEncoderR));
				r = -path.testEncLeft.calculate((Robot.drivebase.getLeftEncoder()-initialEncoderL));
			} else {
				l = path.testEncLeft.calculate(Robot.drivebase.getLeftEncoder()-initialEncoderL);
				r = path.testEncRight.calculate(Robot.drivebase.getRightEncoder()-initialEncoderR);
			}
			
			double theta = Robot.navX.getYaw();
			double desiredHeading = Pathfinder.r2d(path.testEncLeft.getHeading());
			double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading-theta);
			double turn = 0.025*angleDifference;

			System.out.println(l+" "+r+" with turn value "+turn);
			Robot.drivebase.drive(l, r);
			SmartDashboard.putNumber("L", Robot.drivebase.getLeftEncoder());
			SmartDashboard.putNumber("R", Robot.drivebase.getRightEncoder());
			if(!path.testEncLeft.isFinished() && !path.testEncRight.isFinished()) {
				SmartDashboard.putNumber("Theoretical Heading", path.testEncLeft.getHeading());
				SmartDashboard.putNumber("Theoretical Lx", path.testEncLeft.getSegment().x);
				SmartDashboard.putNumber("Theoretical Rx", path.testEncRight.getSegment().x);
				SmartDashboard.putNumber("Theoretical Ly", path.testEncLeft.getSegment().y);
				SmartDashboard.putNumber("Theoretical Ry", path.testEncRight.getSegment().y);
				SmartDashboard.putBoolean("Theoretical heading = robot heading?", Math.abs(Pathfinder.r2d(path.testEncLeft.getHeading()) - Robot.navX.getYaw()) <= 2.0);
			}

			if(!DriverStation.getInstance().isAutonomous() || DriverStation.getInstance().isDisabled()) {
				notifier.stop();
			}
		
		});
	}

	public void run() {
		if(firstRun) {
			System.out.println("Started trajectory");
			initialEncoderL = Robot.drivebase.getLeftEncoder();
			initialEncoderR = Robot.drivebase.getRightEncoder();
			notifier.startPeriodic(0.02);
		}
		
		//this doesn't really do much, because the notifier does it

		firstRun = false;
	}

	public boolean isFinished() {
		boolean finished = path.testEncLeft.isFinished() && path.testEncRight.isFinished();
		if(finished) {
			notifier.stop();
		}
		return finished;
	}

}