package frc.robot.subsystems.arm;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class ArmIOSim implements ArmIO{

    public double volts = 0.0;

    private SingleJointedArmSim sim =
      new SingleJointedArmSim(LinearSystemId.createSingleJointedArmSystem(DCMotor.getCIM(1), 0.004, 1), DCMotor.getCIM(1),
    1,
    0.5,
    Units.degreesToRadians(-100),
    Units.degreesToRadians(90),
    true, 0);

    @Override
    public void updateInputs(ArmIOInputs inputs) {
        sim.update(0.02);
        inputs.voltage = volts;
        inputs.velocity = sim.getVelocityRadPerSec();
        inputs.position = sim.getAngleRads();
    }

    @Override
    public void setVoltage(double volts) {
        sim.setInputVoltage(volts);
        this.volts = volts;
    }
}
