// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */

 
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

private final RobotContainer m_robotContainer;
private XboxController Driver;


private SparkMax cageMotor;
private SparkMax leftIntakeMotor;
private SparkMax rightIntakeMotor;
private SparkMax leftRollerMotor;
private SparkMax rightRollerMotor;


private final DoubleSolenoid Armsolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 7, 8);
private boolean solenoidOpen = false;

public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    Driver = new XboxController(0);




    cageMotor = new SparkMax(Constants.OperatorConstants.cageMotorID, MotorType.kBrushless);
    leftIntakeMotor = new SparkMax(Constants.OperatorConstants.intakeMotorID, MotorType.kBrushless);
    rightIntakeMotor = new SparkMax(Constants.OperatorConstants.intakeMotorID2, MotorType.kBrushless);
    leftRollerMotor = new SparkMax(Constants.OperatorConstants.rollerMotorID, MotorType.kBrushless);
    rightRollerMotor = new SparkMax(Constants.OperatorConstants.rollerMotorID2, MotorType.kBrushless);
}
  


  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
   // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic(){

  

      //Cage pivot controls

 //     // Get the trigger values
 //     double leftTrigger = xboxController.getLeftTriggerAxis();
 //     double rightTrigger = xboxController.getRightTriggerAxis();

      // Set the motor speed based on trigger values
      if (Driver.getRightTriggerAxis() > 0.1) {
          // Move motor forward
          cageMotor.set(-.2); // Scale speed down to 50%
      } else if (Driver.getLeftTriggerAxis() > 0.05) {
          // Move motor backward
          cageMotor.set(.97); // Scale speed down to 50%
      } else {
          // Stop motor
          cageMotor.set(0);
      }


      // Intake cage controls



       // Check if the left bumper is pressed 
       //boolean leftBumperPressed = Driver.getLeftBumperButtonPressed();
       // Check if the right bumper is pressed
       //boolean rightBumperPressed = Driver.getRightBumperButtonPressed();

       // Control the motors based on bumper inputs
       if (Driver.getLeftBumperButton()) {
           // Spin motors forward
         //  leftIntakeMotor.set(.6);  // 80% speed forward
           rightIntakeMotor.set(.8); // 80% speed forward
       } else if (Driver.getRightBumperButton()) {
           // Spin motors backward
         //  leftIntakeMotor.set(-.6);  // 80% speed backward
           rightIntakeMotor.set(-.8); // 80% speed backward
       } else{
           // Stop motors
          // leftIntakeMotor.set(0);
           rightIntakeMotor.set(0);
       }

       if(Driver.getPOV() == 180){
        
        rightRollerMotor.set(.2);
        leftRollerMotor.set(-.6);

       }else if(Driver.getPOV() == 0){
        
        rightRollerMotor.set(-.2);
        leftRollerMotor.set(.6);
       }else{
        rightRollerMotor.set(0);
        leftRollerMotor.set(0);
       }



       
 




   }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
