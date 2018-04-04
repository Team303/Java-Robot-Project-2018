package org.usfirst.frc.team303.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {

	public Camera() {
		Thread cameraThread = new Thread(()->{
			//CameraServerJNI.setTelemetryPeriod(1);
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			//camera.setResolution(640, 480);
			//camera.setFPS(30);
			//camera.setWhiteBalanceAuto();
			//camera.setExposureAuto();
			
			//CvSink cvSink = CameraServer.getInstance().getVideo();
			//CvSource outputStream = CameraServer.getInstance().putVideo("output-feed", 640, 480);
			
			//Mat source = new Mat();
			//Mat output = new Mat();
			
			//while(!Thread.interrupted()) {
			//	cvSink.grabFrame(source);
			//	Imgproc.putText(output, "fps: "+camera.getActualFPS(), new Point(50, 50), Core.FONT_HERSHEY_SIMPLEX, 1, new Scalar(200, 200, 200), 4);
			//	outputStream.putFrame(output);
			//}
		});
		cameraThread.setDaemon(true);
		cameraThread.start();
	}
	
	
}
