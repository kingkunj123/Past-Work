
package org.usfirst.frc.team188.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team188.robot.commands.AutonomousGroup;
import org.usfirst.frc.team188.robot.commands.DisabledGroup;
import org.usfirst.frc.team188.robot.commands.TeleoperatedGroup;
import org.usfirst.frc.team188.robot.subsystems.Base;
import org.usfirst.frc.team188.robot.subsystems.BinGrabber;
import org.usfirst.frc.team188.robot.subsystems.Elevator;
import org.usfirst.frc.team188.robot.subsystems.Intake;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static RobotMap map;
	public static OI oi;
	
	CommandGroup autoGroup;
	CommandGroup teleGroup;
	CommandGroup disabledGroup;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		map = new RobotMap();
		oi = new OI();
		disabledGroup = new DisabledGroup();
		disabledGroup.setRunWhenDisabled(true);
		disabledGroup.start();
		RobotMap.gyro.setSensitivity(0.001647);
		SmartDashboard.putNumber("GyroTurnP", 0.0);
		SmartDashboard.putNumber("GyroTurnI", 0.0);
		SmartDashboard.putNumber("GyroTurnD", 0.0);
	}
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
//		if(RobotMap.autoSelected == 4){
//			BinGrabber.binLauncherLeft.set(0.5);
//			BinGrabber.binLauncherRight.set(0);
//		}
		autoGroup = new AutonomousGroup();
		autoGroup.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();  
	}

	public void teleopInit() {
		Base.stop();
		Intake.stop();
		Elevator.stop();
		teleGroup = new TeleoperatedGroup();
		teleGroup.start();
	}

	/**
	 * This function is called when the disabled button is hit.
	 * You can use it to reset subsystems before shutting down.
	 */
	public void disabledInit(){
//		disabledGroup = new DisabledGroup();
//		disabledGroup.setRunWhenDisabled(true);
//		disabledGroup.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();   
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
