package org.firstinspires.ftc.team17294;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.gogobot.botcore.kinematic.*;
import java.util.List;

class Robot_MecanumDrive {

    private LinearOpMode myOpMode = null;
    private FourMecanumKinematic kinematic = null;


    private DcMotor leftTopDrive;
    private DcMotor rightTopDrive;
    private DcMotor rightBotDrive;
    private DcMotor leftBotDrive;

    /* store these as meters per second*/
    private double driveAxial = 0;
    private double driveLateral = 0;
    private double driveYaw = 0;

    /***
     * void init(LinearOpMode opMode)
     * initializes all the members and data needed to operate the motors
     * @param opMode stores a copy of the current opMode to access members ex: game pad
     */
    public Robot_MecanumDrive(LinearOpMode opMode)
    {

        //save reference to Hardware map
        myOpMode = opMode;

        //config mecanum wheel drive
        FourMecanumKinematicConfiguration config = new FourMecanumKinematicConfiguration();
        config.wheelRadius = Global.WHEEL_DIAM / 2.0;
        config.lengthBetweenFrontAndRearWheels = Global.DIST_BETWEEN_FRONT_AND_REAR;
        config.lengthBetweenFrontWheels = Global.DIST_BETWEEN_FRONT_AND_FRONT;
        kinematic = new FourMecanumKinematic(config);

        //Controller deadzone
        myOpMode.gamepad1.setJoystickDeadzone(0.001f);

        //set up motor
        String[] motorNames = {Global.LEFT_TOP_MOTOR, Global.RIGHT_TOP_MOTOR,
                Global.RIGHT_BOT_MOTOR, Global.LEFT_BOT_MOTOR};
        DriverController dc = new DriverController(motorNames, myOpMode.hardwareMap);

        //copy data over
        leftTopDrive = dc.leftTopDrive;
        rightTopDrive = dc.rightTopDrive;
        rightBotDrive = dc.rightBotDrive;
        leftBotDrive = dc.leftBotDrive;

        //stop all motion.
        moveRobot(0,0,0) ;
    }

    public void doControllerTick()  {
        //get controller input
        //left stick represents the 2 degrees of freedom (x and y velocities)
        //right stick controls turning
        //triggers are scaled down for fine movements
        //sticks are linear curved while the sticks are exponential

        double controllerAxial = -myOpMode.gamepad1.left_stick_y;
        double controllerLateral = myOpMode.gamepad1.left_stick_x
                                         + (myOpMode.gamepad1.left_trigger * -0.3)
                                         + (myOpMode.gamepad1.right_trigger * 0.3);

        double controllerYaw = myOpMode.gamepad1.right_stick_x;

        /*should be in m/s*/
        controllerAxial = Range.clip(controllerAxial, -1, 1) * Global.MAX_SPEED;
        controllerLateral = Range.clip(controllerLateral, -1, 1) * Global.MAX_SPEED;
        controllerYaw = Range.clip(controllerYaw, -1, 1) * Global.MAX_SPEED;

        setAxial(controllerAxial); //m/s
        setLateral(controllerLateral); //m/s
        setYaw(controllerYaw); //m/s


    }




    /***
     * void moveRobot()
     * This method will calculate the motor speeds required to move the robot according to the
     * speeds that are stored in the three Axis variables: driveAxial, driveLateral, driveYaw.
     * This code is setup for a four wheel mecanum-drive.
     *
     * The code assumes the following conventions.
     * 1) Positive speed on the Axial axis means move FORWARD.
     * 2) Positive speed on the Lateral axis means move RIGHT.
     * 3) Positive speed on the Yaw axis means rotate COUNTER CLOCKWISE.
     *
     * This convention should NOT be changed.  Any new drive system should be configured to react accordingly.
     */

    public void moveRobot() {
        List<Double> trajectory = kinematic.cartesianVelocityToWheelVelocities(
            new CartesianVelocity(driveAxial, driveLateral, driveYaw)
        );

        //set power
        leftTopDrive.setPower(Global.angularSpeedToMotorPower(trajectory.get(0)));
        rightTopDrive.setPower(Global.angularSpeedToMotorPower(trajectory.get(1)));
        rightBotDrive.setPower(Global.angularSpeedToMotorPower(trajectory.get(2)));
        leftBotDrive.setPower(Global.angularSpeedToMotorPower(trajectory.get(3)));


        //telemetry
        myOpMode.telemetry.addData("Inputs",
                "Axial: (%.2f), Lateral: (%.2f), Yaw: (%.2f)",
                driveAxial,
                driveLateral,
                driveYaw);

        myOpMode.telemetry.addData("pre processed Motors",
                "m1: (%.2f), m2: (%.2f), m3: (%.2f),  m4: (%.2f)",
                trajectory.get(0),
                trajectory.get(1),
                trajectory.get(2),
                trajectory.get(3));

        myOpMode.telemetry.addData("Motors",
                "m1: (%.2f), m2: (%.2f), m3: (%.2f),  m4: (%.2f)",
                Global.angularSpeedToMotorPower(trajectory.get(0)),
                Global.angularSpeedToMotorPower(trajectory.get(1)),
                Global.angularSpeedToMotorPower(trajectory.get(2)),
                Global.angularSpeedToMotorPower(trajectory.get(3)));


        myOpMode.telemetry.addData("Encoder Values",
                "[" + leftTopDrive.getCurrentPosition() + "," +
                rightTopDrive.getCurrentPosition() + "," +
                rightBotDrive.getCurrentPosition() + "," +
                leftBotDrive.getCurrentPosition() + "]");

    }// beautiful

    /***
     * void moveRobot(double axial, double lateral, double yaw)
     * Set speed levels to motors based on axes requests
     * @param axial     Speed in Fwd Direction
     * @param lateral   Speed in lateral direction (+ve to right)
     * @param yaw       Speed of Yaw rotation.  (+ve is CCW)
     */
    public void moveRobot(double axial, double lateral, double yaw) {
        setAxial(axial);
        setLateral(lateral);
        setYaw(yaw);
        moveRobot();
    }



    //setters
    public void setAxial(double axial) {this.driveAxial = axial;}
    public void setLateral(double lateral) {this.driveLateral = lateral;}
    public void setYaw(double yaw) {this.driveYaw = yaw;}


}

