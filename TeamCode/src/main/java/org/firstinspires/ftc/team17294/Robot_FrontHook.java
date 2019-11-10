package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot_FrontHook {

    private double minVal = 0;
    private double maxVal = 1;

    private Servo rightFrontServo;
    private Servo leftFrontServo;


    private LinearOpMode myOpMode;

    public Robot_FrontHook(LinearOpMode opMode)
    {
        myOpMode = opMode;

        DriverController dc = new DriverController(myOpMode.hardwareMap);

        rightFrontServo = dc.rightFrontServo;
        leftFrontServo = dc.leftFrontServo;

        rightFrontServo.setPosition(minVal);
        leftFrontServo.setPosition(minVal);
    }

    public void toggleOn()
    {
        rightFrontServo.setPosition(maxVal);
        leftFrontServo.setPosition(maxVal);
    }

    public void toggleOff()
    {
        rightFrontServo.setPosition(minVal);
        leftFrontServo.setPosition(minVal);
    }

    public void doControllerTick()
    {
        if(myOpMode.gamepad1.right_bumper) toggleOn();
        if(myOpMode.gamepad2.left_bumper) toggleOff();
    }



}
