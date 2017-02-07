package org.usfirst.frc.team188.robot.subsystems;

import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDriveGyro extends PIDSubsystem {
	
	public boolean isMoving = false;
	
	double gyroRatio = 1; //0.8
	double tolerance = 1; //0.5
	
	double power = 0;
	
    public PIDDriveGyro(int target, double p, double i, double d, double power) {
    	super("PIDDriveGyro", p, i, d);
    	this.power = power;
    	setAbsoluteTolerance(tolerance);
    	setSetpoint(target);
    	if(target == 0) isMoving = true;
    	enable();
    }
    
    public void initDefaultCommand() {
    }
    
    public static double stabilizeGyro(){
    	double accelCalc = Math.atan( Math.sqrt(Math.pow(RobotMap.accel.getX(), 2) + Math.pow(RobotMap.accel.getY(), 2)) / RobotMap.accel.getZ());
        double turnCalc = (accelCalc * 0.05) + (RobotMap.gyro.getAngle() * 0.95);
    	return turnCalc;
    }
    
    protected double returnPIDInput() {
//        return stabilizeGyro();
//    	return -RobotMap.gyro.getAngle();
    	return RobotMap.gyroRefined;
    }
    
    protected void usePIDOutput(double output) {
    	double ultraPID = PIDDriveUltra.output *= (1 - gyroRatio);
    	output *= gyroRatio;
    	SmartDashboard.putNumber("PID Turn Output", output);
    	if(this.isMoving){
	    	Base.frontLeft.set(output+power+ultraPID);
	    	Base.backLeft.set(output+power-ultraPID);
	    	Base.frontRight.set(output-power+ultraPID);
	    	Base.backRight.set(output-power-ultraPID);
    	}
    	else{
    		Base.frontLeft.set(output+ultraPID);
    		Base.backLeft.set(output-ultraPID);
    		Base.frontRight.set(output+ultraPID);
    		Base.backRight.set(output-ultraPID);
    	}
    }
}
