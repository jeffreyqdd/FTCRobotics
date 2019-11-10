package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

//TODO
public class Robot_Arm {
    private LinearOpMode myOpMode;
    private DcMotor liftMotor; //motor that controls the lift


    private int level;
    private boolean leftBumperDown;
    private boolean rightBumperDown;

    private boolean clawIsReleased;

    Robot_Arm(LinearOpMode opMode)
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
        clawIsReleased = false;

        level = 0;
    }

    public void doControllerTick()
    {
        //toggle...one click is one change in level
        if(myOpMode.gamepad2.left_bumper && !leftBumperDown)
        {
            leftBumperDown = true;
            level = Range.clip(level - 1, 0, 5);
        }

        if(myOpMode.gamepad2.right_bumper && !rightBumperDown)
        {
            rightBumperDown = true;
            level = Range.clip(level + 1, 0, 5);
        }

        if(!myOpMode.gamepad2.left_bumper)
            leftBumperDown = false;
        if(!myOpMode.gamepad2.right_bumper)
            rightBumperDown = false;
    }

    public void moveArm()
    {
        int position = (int) Global.TICKS_PER_REV *  (int) Global.MOTOR_POSITION[level];
        liftMotor.setTargetPosition(position);
    }


}
