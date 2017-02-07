package org.usfirst.frc.team188.robot.subsystems;

import org.usfirst.frc.team188.robot.OI;
import org.usfirst.frc.team188.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDTeleGyro extends PIDSubsystem {
	
	static double getRate = RobotMap.gyro.getRate();
	public static double output = 0;
	
    public PIDTeleGyro() {
    	super(0.0175, 0.0, 0.004, 0.1);
    	setAbsoluteTolerance(0.02);
    	setSetpoint(OI.driverZ());
    	enable();
    }
    
    public void initDefaultCommand() {
    }
    
    public static double stabilizeGyro(){
    	getRate = RobotMap.gyro.getRate();
//        SmartDashboard.putNumber("Gyro Rate PID", getRate);
    	double accelCalc = Math.atan( Math.sqrt(Math.pow(RobotMap.accel.getX(), 2) + Math.pow(RobotMap.accel.getY(), 2)) / RobotMap.accel.getZ());
        double turnCalc = (accelCalc * 0.05) + (getRate * 0.95);
//        SmartDashboard.putNumber("Gyro CalcRate PID", turnCalc);
    	return turnCalc;
    }
    
    protected double returnPIDInput() {
    	setSetpoint(OI.driverZ());
        return stabilizeGyro();
    }
    
	protected void usePIDOutput(double output) {
    	PIDTeleGyro.output = output;
//    	SmartDashboard.putNumber("PIDOutput",output);
    }
}
