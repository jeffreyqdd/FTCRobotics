package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DriverController {

    public DcMotor leftTopDrive;
    public DcMotor rightTopDrive;
    public DcMotor rightBotDrive;
    public DcMotor leftBotDrive;

    public DcMotor liftMotor;

    public Servo leftFrontServo;
    public Servo rightFrontServo;
    public Servo frontLeftClawServo;
    public Servo frontRightClawServo;



    public DistanceSensor leftWaffleSensor;
    public DistanceSensor rightWaffleSensor;

    public DriverController(HardwareMap hardwareMap)
    {
        assert hardwareMap != null;


        /* do the range sensors */
        leftWaffleSensor = hardwareMap.get(DistanceSensor.class, Global.FRONT_LEFT_WAFFLE_SENSOR);
        rightWaffleSensor = hardwareMap.get(DistanceSensor.class, Global.FRONT_RIGHT_WAFFLE_SENSOR);

        /* do the servos */
        leftFrontServo = hardwareMap.get(Servo.class, Global.LEFT_FRONT_SERVO);
        rightFrontServo = hardwareMap.get(Servo.class, Global.RIGHT_FRONT_SERVO);

        frontLeftClawServo = hardwareMap.get(Servo.class, Global.FRONT_LEFT_CLAW_SERVO);
        frontRightClawServo = hardwareMap.get(Servo.class, Global.FRONT_RIGHT_CLAW_SERVO);

        frontLeftClawServo.setDirection(Servo.Direction.FORWARD);
        frontRightClawServo.setDirection(Servo.Direction.REVERSE);



        /* do the one lift motor*/
        //liftMotor = hardwareMap.get(DcMotor.class, Global.LIFT_MOTOR);
        //liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftMotor.setDirection(DcMotor.Direction.FORWARD);
        //liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        /* do the drive motors */
        //set names
        leftTopDrive = hardwareMap.get(DcMotor.class, Global.LEFT_TOP_MOTOR);
        rightTopDrive = hardwareMap.get(DcMotor.class, Global.RIGHT_TOP_MOTOR);
        rightBotDrive = hardwareMap.get(DcMotor.class, Global.RIGHT_BOT_MOTOR);
        leftBotDrive = hardwareMap.get(DcMotor.class, Global.LEFT_BOT_MOTOR);


        //set directions
        leftTopDrive.setDirection(DcMotor.Direction.FORWARD);
        rightTopDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBotDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBotDrive.setDirection(DcMotor.Direction.FORWARD);

        //set mode to run using encoders
        leftTopDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTopDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBotDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBotDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*//reset the encoders
        leftTopDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightTopDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBotDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBotDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/

        //set zero power behaviors
        leftTopDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightTopDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBotDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBotDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}
