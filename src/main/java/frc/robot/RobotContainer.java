package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.SetJoint1AngleCommand;
import frc.robot.subsystems.Joint1Subsystem;
import frc.robot.commands.SetRestAngleCommand;
import frc.robot.commands.ResetEncoderCommand;
import frc.robot.commands.JogJoint1Command;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Joint1Subsystem joint1Subsystem = new Joint1Subsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    // Assign button A (button 1) to set the joint to 90 degrees
    m_driverController.a().onTrue(new SetJoint1AngleCommand(joint1Subsystem, Rotation2d.fromDegrees(90)));
    // Assign button B to set the rest angle to 0 degrees
    m_driverController.b().onTrue(new SetRestAngleCommand(joint1Subsystem, 0.0));
    // Assign button Y to set the rest angle to 13 degrees
    m_driverController.y().onTrue(new SetRestAngleCommand(joint1Subsystem, 30.0));
    // Assign button X to reset the encoder
    m_driverController.x().onTrue(new ResetEncoderCommand(joint1Subsystem));
    // Assign POV 90 (right) to jog the joint by +5 degrees
    m_driverController.povRight().whileTrue(new JogJoint1Command(joint1Subsystem, 0.4));
    // Assign POV 270 (left) to jog the joint by -5 degrees
    m_driverController.povLeft().whileTrue(new JogJoint1Command(joint1Subsystem, -0.4));

  }

  public Joint1Subsystem getJoint1Subsystem() {
    return joint1Subsystem;
  }
}