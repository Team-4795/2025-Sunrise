package frc.robot.subsystems.arm;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
    @AutoLog
    public class ArmIOInputs {
        public double voltage = 0;
        public double velocity = 0;
        public double position = 0;
        public double goal = 0;
        public double setpoint = 0;
        public double pidVolts = 0;
        public double ffVolts = 0;
    }

    public default void updateInputs(ArmIOInputs inputs) {
       }
    public default void setVoltage(double volts) {
        }
    public default void updateMotionProfile() {
    }
    public default void setGoal(double position) {
    }
}

