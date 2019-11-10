package org.firstinspires.ftc.team17294;

import com.qualcomm.robotcore.util.Range;

public class Global {
    public static final String LEFT_TOP_MOTOR = "top left drive";
    public static final String RIGHT_TOP_MOTOR = "top right drive";
    public static final String RIGHT_BOT_MOTOR = "bot right drive";
    public static final String LEFT_BOT_MOTOR = "bot left drive";
    public static final String LIFT_MOTOR = "lift motor";
    public static final String LEFT_FRONT_SERVO = "front left servo";
    public static final String RIGHT_FRONT_SERVO = "front right servo";

    public static final double DIST_BETWEEN_FRONT_AND_FRONT = 0.405; //m
    public static final double DIST_BETWEEN_FRONT_AND_REAR = 0.202; //m

    public static final double WHEEL_CIRCUM = 0.098 * Math.PI; //m
    public static final double WHEEL_DIAM = 0.098; //m

    public static final double MAX_RPM = 100; //rotations per minute
    public static final double MAX_SPEED = (MAX_RPM / 60) * WHEEL_CIRCUM; //m/s

    public static final int TICKS_PER_REV = 1440;
    public static final double[] MOTOR_POSITION = {0.1, 1, 1.5, 2.0, 2.5};

    /***
     * double angularSpeedToMotorPower(double angularSpeed)
     * @param angularSpeed, radians per second
     * @return power the motor should run at. Clipped at 1
     */
    public static double angularSpeedToMotorPower(double angularSpeed)
    {
        //radian per second / (60 * 2pi) = rpm
        double rpm = angularSpeed * 60 / (2 * Math.PI);
        return Range.clip(rpm / MAX_RPM, -1, 1);
    }

}
