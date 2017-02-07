package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Base;
import org.usfirst.frc.team188.robot.subsystems.PIDDriveGyro;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDTurn extends Command {
	
	public static PIDDriveGyro pidDriveGyro;
	
	Timer t;
	
	double Gp = 0.03;
	double Gi = 0.00005;
	double Gd = 0.07;
	
	double time = 0;
	int angle = 0;
	
    public PIDTurn(int angle, double time) {
    	this.angle = angle;
    	this.time = time;
    	pidDriveGyro = new PIDDriveGyro(angle, Gp, Gi, Gd, 0);
//    	pidDriveGyro = new PIDDriveGyro(angle, 0.02, 0.00001, 0.07, 0.75);
//    	pidDriveGyro = new PIDDriveGyro(angle, SmartDashboard.getNumber("GyroTurnP"), SmartDashboard.getNumber("GyroTurnI"), SmartDashboard.getNumber("GyroTurnD"), 0.5);
        requires(pidDriveGyro);
        t =  new Timer();
    }

    protected void initialize() {
    	t.reset();
    	t.start();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	if(time != 0)
    		return RobotState.isDisabled() || t.get() >= time;
    	else
    		return RobotState.isDisabled() || pidDriveGyro.onTarget();
    }

    protected void end() {
    	pidDriveGyro.disable();
    	Base.stop();
    }

    protected void interrupted() {
    	pidDriveGyro.disable();
    	Base.stop();
    }
}
