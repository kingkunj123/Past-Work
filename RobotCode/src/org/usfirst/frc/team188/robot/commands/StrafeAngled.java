package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Base;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StrafeAngled extends Command {

	Timer t;
	
	double power;
	double time;
	double modifier;
	
	CANTalon frontLeft = Base.frontLeft;
	CANTalon backLeft = Base.backLeft;
	CANTalon frontRight = Base.frontRight;
	CANTalon backRight = Base.backRight;
	
    public StrafeAngled(double power, double time, double modifier) {
    	this.power = power;
    	this.time = time;
    	this.modifier = modifier;
    }

    protected void initialize() {
    	t = new Timer();
    	t.reset();
    	t.start();
    }

    protected void execute() {
    	frontLeft.set(-power);
    	backLeft.set(power * modifier);
    	frontRight.set(-power * modifier);
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
