package org.usfirst.frc.team303.robot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Path {

	static double timeStep = 0.02;
	static double maxVel = 6.5; //3  //11
	static double maxAccel = 20; //12
	static double maxJerk = 50; //100
	double wheelBaseWidth = 2.333;
	int ticksPerRev = 2419; 
	double wheelDiameter = 0.3283333333333333;
	//done in feet for now

	double p = 0.15; //.115
	double i = 0;
	double d = 0;
	double velocityRatio = 1/10;
	double accelGain = 0.02;

	// The first argument is the proportional gain. Usually this will be quite high
	// The second argument is the integral gain. This is unused for motion profiling
	// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
	// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
	//	      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
	// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker

	double l;
	double r;
	public Trajectory[] trajectoryArray;
	public Trajectory forwardLeftTrajectory;
	public Trajectory forwardRightTrajectory;
	public EncoderFollower testEncLeft;
	public EncoderFollower testEncRight;


	public Path(Trajectory forwardTrajectory) {
		try{	

			TankModifier testModifier = new TankModifier(forwardTrajectory).modify(wheelBaseWidth);

			forwardLeftTrajectory = testModifier.getLeftTrajectory();
			forwardRightTrajectory = testModifier.getRightTrajectory();

			testEncLeft = new EncoderFollower(forwardLeftTrajectory);
			testEncRight = new EncoderFollower(forwardRightTrajectory);
			testEncLeft.configureEncoder(Robot.drivebase.getLeftEncoder(), ticksPerRev, wheelDiameter);
			testEncRight.configureEncoder(Robot.drivebase.getRightEncoder(), ticksPerRev, wheelDiameter);
			testEncLeft.configurePIDVA(p, i, d, velocityRatio, accelGain);
			testEncRight.configurePIDVA(p, i, d, velocityRatio, accelGain);

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error in Path Construction" + e.getMessage());
		}
	}

	private static final String DIRECTORY = "/home/lvuser/frc/trajectories/";

	public static void setBackupTrajectories(HashMap<String, Trajectory> map) {
		try {
			File file = new File(DIRECTORY+"testTraj.dat");
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.flush();
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, Trajectory> getBackupTrajectories() {
		try {
			File file = new File(DIRECTORY+"testTraj.dat");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Trajectory> map = (HashMap<String, Trajectory>) ois.readObject();
			ois.close();
			fis.close();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String serializeWaypointMap(HashMap<String, Waypoint[]> map) {
		String serializedWaypoints = "";
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(map);
			so.flush();
			so.close();
			bo.close();
			serializedWaypoints = new String(Base64.getEncoder().encode(bo.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serializedWaypoints;
	}

	public static HashMap<String, Trajectory> deserializeTrajectoryMap(String hashmap) {
		HashMap<String, Trajectory> map = null;
		try {
			byte[] b = Base64.getDecoder().decode(hashmap.getBytes()); 
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream si = new ObjectInputStream(bi);
			map = (HashMap<String, Trajectory>) si.readObject();
			bi.close();
			si.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}



}