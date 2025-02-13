package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Joint1Subsystem extends SubsystemBase {
    private final SparkMax motor;
    private final RelativeEncoder encoder;
    private final PIDController pidController;
    private static final double GEAR_RATIO = 16.0; // 16:1 gearbox
    private static final double SPOOL_DIAMETER = 0.05; // Spool diameter in meters (example value)
    private static final double ARM_LENGTH = 0.127; // Length of the arm from pivot to string attachment point in meters (example value)
    private static final double RISE_SPEED_MULTIPLIER = 0.6; // Multiplier for rising speed
    private static final double DESCENT_SPEED_MULTIPLIER = 0.1; // Multiplier for descent speed

    private double restAngle = 13.0; // Default rest position of the arm in degrees


    public Joint1Subsystem() {
        motor = new SparkMax(Constants.OperatorConstants.Joint1MotorID, MotorType.kBrushless);
        encoder = motor.getEncoder();
        pidController = new PIDController(Constants.PIDConstants.Joint1P, Constants.PIDConstants.Joint1I, Constants.PIDConstants.Joint1D);
    }

    public void setSetpoint(Rotation2d setpoint) {
        double targetAngle = setpoint.getDegrees();
        double currentAngle = getCurrentAngle();
        double output = pidController.calculate(getCurrentAngle(), targetAngle);
        // Adjust the speed based on the direction of movement
        if (targetAngle > currentAngle) {
            // Rising
            output *= RISE_SPEED_MULTIPLIER;
        } else {
            // Descending
            output *= DESCENT_SPEED_MULTIPLIER;
        }

        // Safety check to prevent moving beyond physical limits
        if (targetAngle < 0 || targetAngle > 180) {
            motor.set(0); // Stop the motor if the target angle is out of bounds
            System.out.println("Target angle out of bounds: " + targetAngle);
        } else {
            motor.set(output);
        }
    }
    public void setRestAngle(double restAngle) {
        this.restAngle = restAngle;
    }

    public void resetEncoder() {
        encoder.setPosition(0);
    }

    public void jog(double speed) {
        motor.set(speed);
    }

    public void stop() {
        motor.set(0);
    }

    private double getCurrentAngle() {
        // Read the current angle from the encoder
        double encoderPosition = encoder.getPosition();
        // Convert encoder position to spool rotations considering the gear ratio
        double spoolRotations = encoderPosition / GEAR_RATIO;
        // Calculate the length of the string pulled by the spool
        double stringLength = spoolRotations * Math.PI * SPOOL_DIAMETER;
        // Calculate the angle of the arm based on the string length and arm length
        double currentAngle = Math.toDegrees(stringLength / ARM_LENGTH);
        // Add the rest position offset
        currentAngle += restAngle;
        return currentAngle;
    }
}