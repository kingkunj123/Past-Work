package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.BinGrabber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BinLauncher extends Command {

	char direction = 'o';

	public BinLauncher(char direction) { //'i', 'o'
		this.direction = direction;
	}

	protected void initialize() {
	}

	protected void execute() {
		if(direction == 'i'){
			BinGrabber.binLauncherLeft.set(0.5);
			BinGrabber.binLauncherRight.set(0.25);
		}
		else{
			BinGrabber.binLauncherLeft.set(0.25);
			BinGrabber.binLauncherRight.set(0.5);
		}
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
