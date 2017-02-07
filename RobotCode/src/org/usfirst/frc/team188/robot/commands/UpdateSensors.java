package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.OI;
import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateSensors extends Command {

	boolean logAutoSub = false, logAutoPlus = false;
	
    public UpdateSensors() {
    }

    protected void initialize() {
    }

    protected void execute() {
    	
    	//auto move selection + last state check
//    	if(OI.driverButton5.get() && !logAutoSub)
//    		RobotMap.autoSelected--;
//    	else if(OI.driverButton6.get() && !logAutoPlus)
//    		RobotMap.autoSelected++;
//    	
//    	logAutoSub = OI.driverButton5.get();
//    	logAutoPlus = OI.driverButton6.get();
//    	
//    	if(RobotMap.autoSelected < 0)
//    		RobotMap.autoSelected = 4;
//    	else if(RobotMap.autoSelected > 4)
//    		RobotMap.autoSelected = 0;
    	
    	if(OI.driverButton1.get())
    		RobotMap.autoSelected = 1;
    	else if(OI.driverButton2.get())
    		RobotMap.autoSelected = 2;
    	else if(OI.driverButton3.get())
    		RobotMap.autoSelected = 3;
    	else if(OI.driverButton4.get())
    		RobotMap.autoSelected = 4;
    	else if(OI.driverButton5.get())
    		RobotMap.autoSelected = 0;
    	
		if(RobotMap.autoSelected == 0)
			SmartDashboard.putString("Auto Mode", "None");
		else if(RobotMap.autoSelected == 1)
			SmartDashboard.putString("Auto Mode", "Robot Set");
		else if(RobotMap.autoSelected == 2)
			SmartDashboard.putString("Auto Mode", "3 Tote Auto");
		else if(RobotMap.autoSelected == 3)
			SmartDashboard.putString("Auto Mode", "Bin Pickup");
		else
			SmartDashboard.putString("Auto Mode", "Bin Steal");
    	
//		resetting sensors through OI button
		if(OI.resetSensors()){
		   	RobotMap.gyro.initGyro();
		   	RobotMap.gyro.reset();
		   	RobotMap.gyro.setSensitivity(0.001647);
		}
        
		//updating SmartDashboard
		if(RobotMap.ultra.getVoltage() <= 1.5){
			RobotMap.ultraRefined = RobotMap.ultra.getVoltage();
			SmartDashboard.putNumber("ultra", RobotMap.ultraRefined);
		}
		if(Math.abs(RobotMap.gyro.getAngle()) < 720){
			RobotMap.gyroRefined = -RobotMap.gyro.getAngle();
		}
//		  SmartDashboard.putNumber("ADXL345-X", RobotMap.acc2.getX());
//		  SmartDashboard.putNumber("ADXL345-Y", RobotMap.acc2.getY());
//		  SmartDashboard.putNumber("ADXL345-Z", RobotMap.acc2.getZ());
//    	  SmartDashboard.putNumber("AccelX", RobotMap.accel.getX());
//        SmartDashboard.putNumber("AccelY", RobotMap.accel.getY());
//        SmartDashboard.putNumber("AccelZ", RobotMap.accel.getZ());
//        SmartDashboard.putNumber("AccelXAVG", RobotMap.accelXAVG);
//        SmartDashboard.putNumber("AccelYAVG", RobotMap.accelYAVG);
//        SmartDashboard.putNumber("AccelZAVG", RobotMap.accelZAVG * 0);
//        SmartDashboard.putNumber("Gyro", -RobotMap.gyro.getAngle());
		  SmartDashboard.putNumber("Gyro", RobotMap.gyroRefined);
//        SmartDashboard.putNumber("Displacement Acceleration", Robot.avgFilter);
//        SmartDashboard.putNumber("xDisplace", RobotMap.displace);
//        SmartDashboard.putNumber("frontLeftCurrent", RobotMap.frontLeft.get());
//        SmartDashboard.putNumber("backLeftCurrent", RobotMap.backLeft.get());
//        SmartDashboard.putNumber("frontRightCurrent", RobotMap.frontRight.get());
//        SmartDashboard.putNumber("backRightCurrent", RobotMap.backRight.get());
//        SmartDashboard.putNumber("Gyro Rate Tele", RobotMap.gyro.getRate());
//        SmartDashboard.putNumber("DriverZ", OI.driver.getZ());
//        SmartDashboard.putNumber("frontLeftAngleOutput", OI.leftFrontAngleDrive());
//        SmartDashboard.putNumber("backLeftAngleOutput", OI.leftBackAngleDrive());
//        SmartDashboard.putNumber("frontRightAngleOutput", OI.rightFrontAngleDrive());
//        SmartDashboard.putNumber("backRightAngleOutput", OI.rightBackAngleDrive());
//        SmartDashboard.putNumber("joystickAngle", OI.driver.getDirectionDegrees()+90);
//        SmartDashboard.putNumber("StableGyro", PIDDriveGyro.stabilizeGyro());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
