package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.RobotMap;
import org.usfirst.frc.team188.robot.subsystems.Base;
import org.usfirst.frc.team188.robot.subsystems.PIDDriveGyro;
import org.usfirst.frc.team188.robot.subsystems.PIDDriveUltra;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PIDStraight extends Command {
	
	double Gp = 0.03;
	double Gi = 0.0; //0.00005
	double Gd = 0.1;
	
//	double Gp = 0;
//	double Gi = 0;
//	double Gd = 0;
	
//	double Up = 8.5;
//	double Ui = 0; 
//	double Ud = 28;
	
	double Up = 0;
	double Ui = 0;
	double Ud = 0;
	
	double time = 0;
	double override = 0;
	
	public static PIDDriveGyro pidDriveGyro;
	public static PIDDriveUltra pidDriveUltra;
	
	DigitalInput light = RobotMap.light;
	
	Timer t;
	
    public PIDStraight(int angle, double time, double power, double dist, double override) {
    	this.time = time;
    	this.override = override;
//    	pidDriveGyro = new PIDDriveGyro(angle, 0.04, 0.0001, 0.08, power);
    	pidDriveGyro = new PIDDriveGyro(angle, Gp, Gi, Gd, power);
//    	pidDriveGyro = new PIDDriveGyro(angle, SmartDashboard.getNumber("GyroTurnP"), SmartDashboard.getNumber("GyroTurnI"), SmartDashboard.getNumber("GyroTurnD"));
//    	pidDriveUltra = new PIDDriveUltra(SmartDashboard.getNumber("UltraStrafeP"), SmartDashboard.getNumber("UltraStrafeI"), SmartDashboard.getNumber("UltraStrafeP"), RobotMap.ultraRefined + dist);
    	pidDriveUltra = new PIDDriveUltra(Up, Ui, Ud, dist, power);
        requires(pidDriveGyro);
    }
    
    public PIDStraight(int angle, double time, double power, double dist) {
    	this.time = time;
//    	pidDriveGyro = new PIDDriveGyro(angle, 0.01, 0, 0.006, power);
    	pidDriveGyro = new PIDDriveGyro(angle, Gp, Gi, Gd, power);
//    	pidDriveGyro = new PIDDriveGyro(angle, SmartDashboard.getNumber("GyroTurnP"), SmartDashboard.getNumber("GyroTurnI"), SmartDashboard.getNumber("GyroTurnD"));
//    	pidDriveUltra = new PIDDriveUltra(SmartDashboard.getNumber("UltraStrafeP"), SmartDashboard.getNumber("UltraStrafeI"), SmartDashboard.getNumber("UltraStrafeP"), RobotMap.ultraRefined + dist);
    	pidDriveUltra = new PIDDriveUltra(Up, Ui, Ud, dist, power);
        requires(pidDriveGyro);
    }

    protected void initialize() {
    	t = new Timer();
    	t.reset();
    	t.start();
    	pidDriveGyro.enable();
    	pidDriveUltra.enable();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
    	if(time != 0 && t.get() >= time)
    		return true;
    	else
    		return !light.get() || (override != 0 && t.get() >= override);
    }

    protected void end() {
    	pidDriveGyro.disable();
    	pidDriveUltra.disable();
    	Base.stop();
    }

    protected void interrupted() {
    	pidDriveGyro.disable();
    	pidDriveUltra.disable();
      	Base.stop();
    }
}
