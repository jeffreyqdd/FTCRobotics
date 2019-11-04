package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriverController {

    public DcMotor leftTopDrive = null;
    public DcMotor rightTopDrive = null;
    public DcMotor rightBotDrive = null;
    public DcMotor leftBotDrive = null;


    public DriverController(String[] motorNames, HardwareMap hardwareMap)
    {
        assert motorNames.length == 4;
        assert hardwareMap != null;

        //set names
        leftTopDrive = hardwareMap.get(DcMotor.class, motorNames[0]);
        rightTopDrive = hardwareMap.get(DcMotor.class, motorNames[1]);
        rightBotDrive = hardwareMap.get(DcMotor.class, motorNames[2]);
        leftBotDrive = hardwareMap.get(DcMotor.class, motorNames[3]);

        //set directions
        leftTopDrive.setDirection(DcMotor.Direction.FORWARD);
        rightTopDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBotDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBotDrive.setDirection(DcMotor.Direction.REVERSE);

        //set mode to run using encoders
        leftTopDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTopDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBotDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBotDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
