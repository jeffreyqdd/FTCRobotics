package edu.robot.ftc.team17294;

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

    public void tick()
    {
        doControllerTick();
        updateTelemetry();
    }


    public void doControllerTick()
    {
        if(myOpMode.gamepad1.right_bumper) toggleOn();
        if(myOpMode.gamepad1.left_bumper) toggleOff();
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

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Front Hook Servo Positions: ",
                "s1: (%.2f), s2: (%.2f)",
                leftFrontServo.getPosition(),
                rightFrontServo.getPosition());
    }

}
