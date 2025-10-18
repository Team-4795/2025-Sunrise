package frc.robot.subsystems.arm;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class ArmIOSim implements ArmIO{

    public double volts = 0.0;

    private ArmFeedforward ffmodel = new ArmFeedforward(0.001,1.3, 1);
    private PIDController pid = new PIDController(100, 0, 2);
    private final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(3, 10);
    private final TrapezoidProfile profile = new TrapezoidProfile(constraints);
    private TrapezoidProfile.State goal = new TrapezoidProfile.State(0, 0);
    private TrapezoidProfile.State setpoint = new TrapezoidProfile.State(0, 0);

    private double pidVolts;
    private double ffVolts;

    private SingleJointedArmSim sim =
      new SingleJointedArmSim(
        LinearSystemId.createSingleJointedArmSystem(
        DCMotor.getCIM(2), 
        2.09670337984, 72), 
        DCMotor.getCIM(2),
        72,
        0.5,
        Units.degreesToRadians(-100),
        Units.degreesToRadians(90),
        true, 0);

    @Override
    public void updateInputs(ArmIOInputs inputs) {
        inputs.voltage = volts;
        inputs.velocity = sim.getVelocityRadPerSec();
        inputs.position = sim.getAngleRads();
        inputs.goal = goal.position;
        inputs.setpoint = setpoint.position;
        inputs.ffVolts = ffVolts;
        inputs.pidVolts = pidVolts;

        sim.update(0.02);
    }

    @Override
    public void setVoltage(double volts) {
        sim.setInputVoltage(volts);
        this.volts = volts;
    }

    @Override
    public void updateMotionProfile() {
        setpoint = profile.calculate(0.02, setpoint, goal);
        pidVolts = pid.calculate(sim.getAngleRads(), setpoint.position);
        ffVolts = ffmodel.calculate(sim.getAngleRads(), setpoint.velocity);
        setVoltage(ffVolts + pidVolts);
    }

    @Override
    public void setGoal(double position) {
        if (position != goal.position) {
            setpoint = new TrapezoidProfile.State(sim.getAngleRads(), sim.getVelocityRadPerSec());
            goal = new TrapezoidProfile.State(position, 0);
        }
    }
}
