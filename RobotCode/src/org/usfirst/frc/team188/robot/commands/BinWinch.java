package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.BinGrabber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BinWinch extends Command {

	char direction = 'o';
	double time = 0;
	double power = 1;

	Timer t;

	public BinWinch(char direction, double time) {
		this.direction = direction;
		this.time = time;
	}
	
	public BinWinch(char direction, double time, double power) {
		this.direction = direction;
		this.time = time;
		this.power = power;
	}

	protected void initialize() {
		t = new Timer();
		t.reset();
		t.start();
	}

	protected void execute() {
		if(direction == 'o')
			BinGrabber.binWinch.set(-power);
		else
			BinGrabber.binWinch.set(power);
	}

	protected boolean isFinished() {
		return t.get() >= time;
	}

	protected void end() {
		BinGrabber.stop();
	}

	protected void interrupted() {
		BinGrabber.stop();
	}
}
