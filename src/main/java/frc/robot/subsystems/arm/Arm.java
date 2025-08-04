package frc.robot.subsystems.arm;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{

    public static Arm instance;
    private ArmIO io;

    public Arm(ArmIO io) {
        this.io = io;
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
}
