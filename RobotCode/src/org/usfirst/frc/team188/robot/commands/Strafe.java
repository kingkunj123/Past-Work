package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Base;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Strafe extends Command {

	Timer t;
	
	double power;
	double time;
	
	CANTalon frontLeft = Base.frontLeft;
	CANTalon backLeft = Base.backLeft;
	CANTalon frontRight = Base.frontRight;
	CANTalon backRight = Base.backRight;
	
    public Strafe(double power, double time) {
    	this.power = power;
    	this.time = time;
    }

    protected void initialize() {
    	t = new Timer();
    	t.reset();
    	t.start();
    }

    protected void execute() {
    	frontLeft.set(-power);
//    	backLeft.set(power);
//    	frontRight.set(-power);
    	backRight.set(power);
    }

    protected boolean isFinished() {
        return t.get() >= time;
    }

    protected void end() {
    	Base.stop();
    }

    protected void interrupted() {
    	Base.stop();
    }
}
