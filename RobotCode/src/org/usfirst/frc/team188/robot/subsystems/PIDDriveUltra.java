package org.usfirst.frc.team188.robot.subsystems;

import org.usfirst.frc.team188.robot.RobotMap;
import org.usfirst.frc.team188.robot.commands.PIDStraight;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDDriveUltra extends PIDSubsystem {
	
	public static double output = 0.0;
	double tolerance = 0.05;
	double power = 0;
	
    public PIDDriveUltra(double p, double i, double d, double setPoint, double power) {
    	super("PIDDriveUltra", p, i, d);
    	this.power = power;
    	setAbsoluteTolerance(tolerance);
    	setSetpoint(setPoint);
    	if(setPoint == 0)
    		setSetpoint(RobotMap.ultraRefined);
    	enable();
    }
    
    public void initDefaultCommand() {
    }
    
    protected double returnPIDInput() {
    	SmartDashboard.putNumber("Ultrasonic Setpoint", getSetpoint());
        return RobotMap.ultraRefined;
    }
    
    protected void usePIDOutput(double output) {
    	SmartDashboard.putNumber("PIDUltraOutput", output);
    	PIDDriveUltra.output = output;
    	if(PIDStraight.pidDriveGyro.onTarget()){
    		Base.frontLeft.set(output+power);
    		Base.backLeft.set(-output+power);
    		Base.frontRight.set(output-power);
    		Base.backRight.set(-output-power);
    	}
    }
}
