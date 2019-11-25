package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;


public class Robot_Lift {
    private LinearOpMode myOpMode;
    private DcMotor liftMotor; //motor that controls the lift


    private int level;
    private int desiredEncoderValue;
    private double power;
    private boolean leftBumperDown;
    private boolean rightBumperDown;

    /* constants */
    private static final double MIN_ERROR = 50;
    private static final double MAX_UP_POWER = 1.0;
    private static final double MAX_DOWN_POWER= -0.3;

    public Robot_Lift(LinearOpMode opMode, DriverController dc)
    {
        //store opMode
        myOpMode = opMode;

        //init lift motor
        liftMotor = dc.leftTopDrive;

        //Controller deadzone
        myOpMode.gamepad2.setJoystickDeadzone(0.001f);


        //init remaining members
        leftBumperDown = false;
        rightBumperDown = false;

        level = 0;
        desiredEncoderValue = 0;

        power = 0.0;
    }

    public void tick()
    {
        doControllerTick();

        moveArm();


        updateTelemetry();
    }
    public void doControllerTick()
    {
        //toggle...one click is one change in level
        if(myOpMode.gamepad2.left_bumper && !leftBumperDown)
        {
            leftBumperDown = true;
            level = Range.clip(level - 1, 0, 5);

            desiredEncoderValue = Global.MOTOR_POSITION[level];
        }

        if(myOpMode.gamepad2.right_bumper && !rightBumperDown)
        {
            rightBumperDown = true;
            level = Range.clip(level + 1, 0, 5);

            desiredEncoderValue = Global.MOTOR_POSITION[level];
        }

        if(!myOpMode.gamepad2.left_bumper)
            leftBumperDown = false;
        if(!myOpMode.gamepad2.right_bumper)
            rightBumperDown = false;



    }


    public void moveArm()
    {
        /*double error = desiredEncoderValue - liftMotor.getCurrentPosition();

        if(Math.abs(error) > MIN_ERROR)
        {
            power = Range.clip(error * 0.001, MAX_DOWN_POWER, MAX_UP_POWER);
        }
        else
        {
            power = 0;
        }
        myOpMode.telemetry.addData("Move","Error: " + error + " Power: "+ power);
        liftMotor.setPower(power);
        */

        double test = -myOpMode.gamepad2.left_stick_y;
        test = Range.clip(test, -0.1, 0.7);


        liftMotor.setPower(test);
    }

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Lift inputs: ", "Left bumper down: " + leftBumperDown
                                    + " RIght bumper down: " + rightBumperDown + " level: " + level + ": ->"+  Global.MOTOR_POSITION[level]);


        myOpMode.telemetry.addData("Lift position:", liftMotor.getCurrentPosition());
        myOpMode.telemetry.addData("Lift level:", liftMotor.getCurrentPosition());
    }


}
