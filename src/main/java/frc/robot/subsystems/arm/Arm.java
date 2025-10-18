package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{
    public static Arm instance;
    private ArmIO io;
    private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

    private Arm(ArmIO io) {
        this.io = io;

        setDefaultCommand(Commands.run(() -> updateMotionProfile(), this));
    }

    public static Arm initialize(ArmIO io) {
        instance = new Arm(io);
        return instance;
    }

    public static Arm getInstance() {
        return instance;
    }

    public void setVoltage(double volts) {
        io.setVoltage(volts);
    }

    public void setGoal(double position) {
        io.setGoal(position);
    }

    public void updateMotionProfile() {
        io.updateMotionProfile();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Arm", inputs);
    }
}