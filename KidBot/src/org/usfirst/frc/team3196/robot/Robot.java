/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3196.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public Joystick joystickDrive = new Joystick(0);

	public WPI_TalonSRX frontRight = new WPI_TalonSRX(1);
	public WPI_TalonSRX rearRight = new WPI_TalonSRX(2);
	public SpeedControllerGroup right = new SpeedControllerGroup(frontRight, rearRight);

	public WPI_TalonSRX frontLeft = new WPI_TalonSRX(3);
	public WPI_TalonSRX rearLeft = new WPI_TalonSRX(4);
	public SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);
	
	public DifferentialDrive drive = new DifferentialDrive(left, right);
	
	
	public double thrustLimiter = 0.7;

	
	// Simple deadband method, ignores >-0.05 and <0.05
	public double deadband(double val) {
		if(val > -0.05 && val < 0.05) return 0;
		return val;
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putNumber("thrustLimiter", 70);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// Read thrustLimiter value from dashboard
		thrustLimiter = ((double)SmartDashboard.getNumber("thrustLimiter", 70))/100.0;
		
		// Drive with left stick; y = forward/backward, x = rotation
		double driveSpeed = deadband(joystickDrive.getY(Hand.kLeft));
		double driveRot = deadband(joystickDrive.getX(Hand.kLeft));
		drive.arcadeDrive(-(driveSpeed*thrustLimiter), (driveRot*thrustLimiter));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
