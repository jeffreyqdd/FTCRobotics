package edu.robot.ftc.team17294;

import com.qualcomm.robotcore.util.Range;

public class Global {
    public static final String LEFT_TOP_MOTOR = "top left drive";
    public static final String RIGHT_TOP_MOTOR = "top right drive";
    public static final String RIGHT_BOT_MOTOR = "bot right drive";
    public static final String LEFT_BOT_MOTOR = "bot left drive";
    public static final String LIFT_MOTOR = "lift motor";

    public static final String LEFT_FRONT_SERVO = "front left servo";
    public static final String RIGHT_FRONT_SERVO = "front right servo";

    public static final String FRONT_LEFT_CLAW_SERVO = "left claw servo";
    public static final String FRONT_RIGHT_CLAW_SERVO = "right claw servo";

    public static final String FRONT_LEFT_WAFFLE_SENSOR = "left waffle sensor";
    public static final String FRONT_RIGHT_WAFFLE_SENSOR = "right waffle sensor";

    public static final double DIST_BETWEEN_FRONT_AND_FRONT = 0.405; //m
    public static final double DIST_BETWEEN_FRONT_AND_REAR = 0.202; //m

    public static final double WHEEL_CIRCUM = 0.098 * Math.PI; //m
    public static final double WHEEL_DIAM = 0.098; //m

    public static final double MAX_RPM = 300; //rotations per minute
    public static final double MAX_SPEED = (MAX_RPM / 60) * WHEEL_CIRCUM; //m/s
    public static final double MAX_ANGULAR_SPEED = MAX_SPEED * 3.33;
    public static final int[] MOTOR_POSITION = {0, 50, 350, 650, 950, 1100};

    public static final double PROPORTION = 0.5;
    public static final double INTEGRAL = 0.0;
    public static final double DERIVATIVE = 0.0;
    public static final double DIST_FROM_WAFFLE = 33;
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
