// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
  public static final int kDriverControllerPort = 0;
  public static final int Joint1MotorID = 17; // CAN ID for Joint 1 motor   this one
  public static final int Joint2MotorID = 16; // CAN ID for Joint 2 motor
  public static final int cageMotorID = 13; // CAN ID for cage motor   bottom
  public static final int intakeMotorID = 14; // CAN ID for intake motor later
  public static final int intakeMotorID2 = 15; // CAN ID for intake motor 2   blah end
  public static final int rollerMotorID = 18; // CAN ID for intake motor later
  public static final int rollerMotorID2 = 19; // CAN ID for intake motor 2   blah end

  } 
  public static class PIDConstants {
    public static final double Joint1P = 0.01;
    public static final double Joint1I = 0.0;
    public static final double Joint1D = 0.0;

    public static final double Joint2P = 0.1;
    public static final double Joint2I = 0.0;
    public static final double Joint2D = 0.0;
}
public static class FeedforwardConstants {
  public static final double kS = 0.2; // Static gain
  public static final double kG = 1.0; // Gravity gain
  public static final double kV = 0.1; // Velocity gain
  public static final double kA = 0.01; // Acceleration gain
}


}
