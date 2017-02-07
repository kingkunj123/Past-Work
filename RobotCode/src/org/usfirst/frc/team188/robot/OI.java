package org.usfirst.frc.team188.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
//	~~~JOYSTICK DECLARATIONS~~~
	
//	Driver Joystick (Joystick 0)
	
	public static Joystick driver = new Joystick(0);
	public static JoystickButton driverButton1 = new JoystickButton(driver, 1);
	public static JoystickButton driverButton2 = new JoystickButton(driver, 2);
	public static JoystickButton driverButton3 = new JoystickButton(driver, 3);
	public static JoystickButton driverButton4 = new JoystickButton(driver, 4);
	public static JoystickButton driverButton5 = new JoystickButton(driver, 5);
	public static JoystickButton driverButton6 = new JoystickButton(driver, 6);
	public static JoystickButton driverButton7 = new JoystickButton(driver, 7);
	public static JoystickButton driverButton8 = new JoystickButton(driver, 8);
	public static JoystickButton driverButton9 = new JoystickButton(driver, 9);
	public static JoystickButton driverButton10 = new JoystickButton(driver, 10);
	public static JoystickButton driverButton11 = new JoystickButton(driver, 11);
	public static JoystickButton driverButton12 = new JoystickButton(driver, 12);

//	Operator Joystick (Joystick 1)
	
	public static Joystick operator = new Joystick(1);
	public static JoystickButton operatorButton1 = new JoystickButton(operator, 1);
	public static JoystickButton operatorButton2 = new JoystickButton(operator, 2);
	public static JoystickButton operatorButton3 = new JoystickButton(operator, 3);
	public static JoystickButton operatorButton4 = new JoystickButton(operator, 4);
	public static JoystickButton operatorButton5 = new JoystickButton(operator, 5);
	public static JoystickButton operatorButton6 = new JoystickButton(operator, 6);
	public static JoystickButton operatorButton7 = new JoystickButton(operator, 7);
	public static JoystickButton operatorButton8 = new JoystickButton(operator, 8);
	public static JoystickButton operatorButton9 = new JoystickButton(operator, 9);
	public static JoystickButton operatorButton10 = new JoystickButton(operator, 10);
	public static JoystickButton operatorButton11 = new JoystickButton(operator, 11);
	public static JoystickButton operatorButton12 = new JoystickButton(operator, 12);

	//variables for drive calculation
	
	public static double mag = 0;
	static final double inputMax = 1;
	static final double inputMin = -1;
	static final double outputMax = 1;
	static final double outputMin = -1;
	static final double outputMaxZ = 160;
	static final double outputMinZ = -160;
	
	public OI(){
	}
	
//  ~~~ROBOT CONTROL METHODS~~~	
	
//	Currently used buttons
//		Driver - 2, 3, 5, 6, 7, 8, 11, 12
//		Operator - 1, 2, 3, 5, 6, 7, 8
	
//	Driver Joystick Map
	
	public static double driverX(){
		return (((driver.getX() - inputMin) * (outputMax - outputMin)) / (inputMax - inputMin)) + outputMin;
	}
	
	public static double driverY(){
		return (((driver.getY() - inputMin) * (outputMax - outputMin)) / (inputMax - inputMin)) + outputMin;
	}
	
	public static double driverZ(){
		return (((driver.getZ() - inputMin) * (outputMaxZ - outputMinZ)) / (inputMax - inputMin)) + outputMinZ;
	}
	
//	Base Controls
	
	//normal ~~~NOT IN USE~~~
	
	public static double leftFrontDrive(){
		return -driver.getY()+driver.getX()+driver.getZ();//+PIDTeleGyro.output;
	}

	public static double leftBackDrive(){
		return -driver.getY()-driver.getX()+driver.getZ();//+PIDTeleGyro.output;
	}

	public static double rightFrontDrive(){
		return driver.getY()+driver.getX()+driver.getZ();//+PIDTeleGyro.output;
	}

	public static double rightBackDrive(){
		return driver.getY()-driver.getX()+driver.getZ();//+PIDTeleGyro.output;
	}
	
	//modified drive (more efficient) 
	
	public static double magCalc(){
		return Math.sqrt(Math.pow(driverX(), 2) + Math.pow(driverY(), 2));
//		return (((Math.sqrt(Math.pow(driver.getX(), 2) + Math.pow(driver.getY(), 2)) - inputMin) * (outputMax - outputMin)) / (inputMax - outputMin)) + outputMin;
	}
	
	public static double leftFrontAngleDrive(){
		int invert = 1;
		mag = magCalc();	
		return invert * ((mag * Math.cos(OI.driver.getDirectionRadians() + 1.570796326794897 - 2.356194490192345)) + driver.getZ());//+PIDTeleGyro.output;
	}

	public static double leftBackAngleDrive(){
		int invert = 1;
		return invert * ((mag * Math.cos(OI.driver.getDirectionRadians() + 1.570796326794897 - 0.7853981633974483)) + driver.getZ());//+PIDTeleGyro.output;
	}

	public static double rightFrontAngleDrive(){
		int invert = 1;
		return invert * ((mag * Math.cos(OI.driver.getDirectionRadians() + 1.570796326794897 - 3.926990816987241)) + driver.getZ());//+PIDTeleGyro.output;
	}

	public static double rightBackAngleDrive(){
		int invert = 1;
		return invert * ((mag * Math.cos(OI.driver.getDirectionRadians() + 1.570796326794897 - 5.497787143782138)) + driver.getZ());//+PIDTeleGyro.output;
	}
	
	//button adjustments

	public static boolean slowmo(){
		return driverButton7.get();
	}
	
	//AntiTip Controls
	
	public static boolean lowerAntiTip(){
		return driverButton6.get();
	}
	
	public static boolean raiseAntiTip(){
		return driverButton5.get();
	}
	
//	Bin Grabber Controls
	
	public static boolean grabLauncher(){
		return operatorButton11.get();
	}
	
	public static boolean releaseLauncher(){
		return operatorButton12.get();
	}
	
	public static boolean winchIn(){
		return operatorButton5.get();
	}
	
	public static boolean winchOut(){
		return operatorButton7.get();
	}
	
//	Elevator Controls
	
	public static double leftElevator(){
		return operator.getY();
	}

	public static double rightElevator(){
		return -operator.getY();
	}

//	Intake Controls
	
	public static double leftIntake(){
		return -operator.getThrottle()*0.7;	
	}

	public static double rightIntake(){
		return -operator.getThrottle()*0.7;
	}
	
	public static boolean intakePistonIn(){
		return operatorButton8.get();	
	}
	public static boolean intakePistonOut(){
		return operatorButton6.get();	
	}

	public static boolean binPistonIn(){
		return driverButton11.get();
	}
	
	public static boolean binPistonOut(){
		return driverButton12.get();
	}
	
//	Misc. Controls
	
	public static boolean resetSensors(){
		return operatorButton1.get();
	}
	
}

