package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


public class Robot_Lift {
    private LinearOpMode myOpMode;
    private DcMotor liftMotor; //motor that controls the lift


    private int level;
    private boolean leftBumperDown;
    private boolean rightBumperDown;
    private boolean isBusy;

    private float power;

    public Robot_Lift(LinearOpMode opMode)
    {
        //store opMode
        myOpMode = opMode;

        //init lift motor
        DriverController dc = new DriverController(myOpMode.hardwareMap);
        liftMotor = dc.liftMotor;

        //Controller deadzone
        myOpMode.gamepad2.setJoystickDeadzone(0.001f);


        //init remaining members
        leftBumperDown = false;
        rightBumperDown = false;

        level = 0;
        power = 0;
        isBusy = false;

    }

    public void tick()
    {
        doControllerTick();

        tuneArm();
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

            isBusy = true;
        }

        if(myOpMode.gamepad2.right_bumper && !rightBumperDown)
        {
            rightBumperDown = true;
            level = Range.clip(level + 1, 0, 5);

            isBusy = true;
        }

        if(!myOpMode.gamepad2.left_bumper)
            leftBumperDown = false;
        if(!myOpMode.gamepad2.right_bumper)
            rightBumperDown = false;

        /*only for emergencies*/
        if(myOpMode.gamepad1.dpad_down)
            liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        power = -myOpMode.gamepad2.left_stick_y;

    }
    public void tuneArm()
    {
        liftMotor.setPower(power);
    }


    public void moveArm()
    {
        //only set target if something has changed.
        if(isBusy == false) return;
        isBusy = false;

        liftMotor.setTargetPosition(Global.MOTOR_POSITION[level]);
    }

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Lift position:", liftMotor.getCurrentPosition());
        myOpMode.telemetry.addData("Lift level:", liftMotor.getCurrentPosition());
    }


}
