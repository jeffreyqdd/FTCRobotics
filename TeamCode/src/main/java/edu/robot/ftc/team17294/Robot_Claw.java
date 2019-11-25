package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot_Claw{
    private LinearOpMode myOpMode;
    private Servo leftServo;
    private Servo rightServo;



    public Robot_Claw(LinearOpMode opMode, DriverController dc){
        myOpMode = opMode;

        leftServo = dc.frontLeftClawServo;
        rightServo = dc.frontRightClawServo;

        leftServo.setPosition(0.5);
        rightServo.setPosition(0.5);

    }

    public void tick()
    {
        doControllerTick();

        updateTelemetry();
    }


    public void doControllerTick()
    {
        if(myOpMode.gamepad2.a){
            moveToGrip();
        }
        if(myOpMode.gamepad2.b){
            moveToRelease();
        }

    }


    public void moveToGrip() {

        leftServo.setPosition(0.75);
        rightServo.setPosition(0.8);
    }

    public void moveToRelease() {

        leftServo.setPosition(0.2);
        rightServo.setPosition(0.23);
    }

    public void updateTelemetry()
    {
        myOpMode.telemetry.addData("Claw Servo Position: ", leftServo.getPosition() + "," +  rightServo.getPosition());
    }

}