package org.usfirst.frc.team188.robot.commands;

import org.usfirst.frc.team188.robot.subsystems.Base;
import org.usfirst.frc.team188.robot.subsystems.BinGrabber;
import org.usfirst.frc.team188.robot.subsystems.Elevator;
import org.usfirst.frc.team188.robot.subsystems.Intake;
import org.usfirst.frc.team188.robot.subsystems.PIDTeleGyro;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveJoystick extends Command {

	PIDTeleGyro pidtelegyro;
	
	public MoveJoystick() {
	}

	protected void initialize() {
	}

	protected void execute() {
		Base.driveJoystick();
		Base.pistonAntiTip();
		Elevator.moveElevator();
		Intake.moveRoller();
		Intake.movePiston();
		Intake.binPiston();
		BinGrabber.moveJoystick();
	}

	protected boolean isFinished() {
		return RobotState.isDisabled();
	}

	protected void end() {
//		pidtelegyro.disable();
	}

	protected void interrupted() {
//		pidtelegyro.disable();
	}
}
