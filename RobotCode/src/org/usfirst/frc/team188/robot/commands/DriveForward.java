package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.RobotMap;
import org.usfirst.frc.team188.robot.subsystems.Base;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

	Timer t;

	double power;
	double time;

	CANTalon frontLeft = Base.frontLeft;
	CANTalon backLeft = Base.backLeft;
	CANTalon frontRight = Base.frontRight;
	CANTalon backRight = Base.backRight;

	public DriveForward(double power, double time) {
		this.power = power;
		this.time = time;
	}

	protected void initialize() {
		if(time != 0){
			t = new Timer();
			t.reset();
			t.start();
		}
	}

	protected void execute() {
		frontLeft.set(power);
		backLeft.set(power);
		frontRight.set(-power);
		backRight.set(-power);
	}

	protected boolean isFinished() {
		if(time == 0){
			return RobotMap.light.get();
		}
		else
			return t.get() >= time;
	}

	protected void end() {
		Base.stop();
	}

	protected void interrupted() {
		Base.stop();
	}
}
