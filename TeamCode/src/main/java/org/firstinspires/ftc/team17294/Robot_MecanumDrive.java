package org.firstinspires.ftc.team17294;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


class Robot_MecanumDrive {

    private LinearOpMode myOpMode;

    private DcMotor leftTopDrive = null;
    private DcMotor rightTopDrive= null;
    private DcMotor leftBotDrive = null;
    private DcMotor rightBotDrive = null;

    private double driveAxial = 0;
    private double driveLateral = 0;
    private double driveYaw = 0;

    private Mecanum robot = null;

    public Robot_MecanumDrive() {} //default constructor

    public void init(LinearOpMode opMode)
    {
        //save reference to Hardware map
        myOpMode = opMode;


        //init robot
        robot = new Mecanum();

        //define and init motors
        //with encoders since they are installed.

        leftTopDrive = myOpMode.hardwareMap.get(DcMotor.class, Global.LEFT_TOP_MOTOR);
        rightTopDrive= myOpMode.hardwareMap.get(DcMotor.class, Global.RIGHT_TOP_MOTOR);
        leftBotDrive = myOpMode.hardwareMap.get(DcMotor.class, Global.LEFT_BOT_MOTOR);
        rightBotDrive = myOpMode.hardwareMap.get(DcMotor.class, Global.RIGHT_BOT_MOTOR);

        leftTopDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBotDrive.setDirection(DcMotor.Direction.FORWARD);
        rightTopDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBotDrive.setDirection(DcMotor.Direction.REVERSE);

        leftTopDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTopDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBotDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBotDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //stop all motion.


        moveRobot(0,0,0) ;
    }

    public void manualDrive()  {
        double dAxial = Math.pow(-myOpMode.gamepad1.left_stick_y, 3); //need to invert

        double dLateral = Math.pow(myOpMode.gamepad1.left_stick_x, 3)
                + (-myOpMode.gamepad1.left_trigger*0.5 + myOpMode.gamepad1.right_trigger*0.5);

        double dYaw = Math.pow(myOpMode.gamepad1.right_stick_x,3);

        setAxial(dAxial);
        setLateral(dLateral);
        setYaw(dYaw);
    }

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
        // calculate required motor speeds to achieve axis motions
        double[] vector = robot.tick(driveYaw, driveAxial, driveLateral);

        // Set drive motor power levels.
        leftTopDrive.setPower(vector[0]);
        leftBotDrive.setPower(vector[1]);
        rightTopDrive.setPower(vector[2]);
        rightBotDrive.setPower(vector[3]);


    }



    //setters
    public void setAxial(double axial) {this.driveAxial = axial;}
    public void setLateral(double lateral) {this.driveLateral = lateral;}
    public void setYaw(double yaw) {this.driveYaw = yaw;}


}

