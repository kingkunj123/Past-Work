package org.usfirst.frc.team188.robot;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//electrical components  
	public static Compressor compressor = new Compressor(12);
	
    //base motors
	public static CANTalon frontLeft = new CANTalon(2);
    public static CANTalon backLeft = new CANTalon(4);
    public static CANTalon frontRight = new CANTalon(1);
    public static CANTalon backRight = new CANTalon(5);   
    
    //antitip
    public static DoubleSolenoid antiTip = new DoubleSolenoid(12, 2, 3);
	
    //intake
    public static CANTalon intakeRight = new CANTalon (7);
    public static CANTalon intakeLeft = new CANTalon (8);
    public static DoubleSolenoid intakePiston = new DoubleSolenoid(12, 0, 1);
    public static DoubleSolenoid binPiston = new DoubleSolenoid(12, 6, 7);
    
    //elevator motors
    public static CANTalon elevatorLeft = new CANTalon(3);
    public static CANTalon elevatorRight = new CANTalon(6);
    
    //bin grabber motor
    public static CANTalon binWinch = new CANTalon(10);
    
    //sensors/servos
    //i2c
    public static ADXL345_I2C acc2 = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
    //digital
    public static DigitalInput light = new DigitalInput(0);
    //analog
    public static Gyro gyro = new Gyro(0);
    public static AnalogInput ultra = new AnalogInput(2);
    //pwm
    public static Servo binLauncherLeft = new Servo(1);
    public static Servo binLauncherRight = new Servo(0);
    //built in
    public static BuiltInAccelerometer accel = new BuiltInAccelerometer();
	
	//voltage distance from ultrasonic
	public static double ultraRefined = 0;
	
	//gyro angle
	public static double gyroRefined = 0;
	
	//auto select
	public static int autoSelected = 0; //0 is none, 1 is zone, 2 is tote, 3 is bin pickup, 4 is bin burglar
	
	//camera code REMOVE FOR COMPETITION
//	AxisCamera camera = new AxisCamera("169.254.118.139");
}
