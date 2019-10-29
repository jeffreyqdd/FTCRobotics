package org.firstinspires.ftc.team17294;

public class Global {
    public static final String LEFT_TOP_MOTOR = "top left drive";
    public static final String RIGHT_TOP_MOTOR = "top right drive";
    public static final String LEFT_BOT_MOTOR = "bot right drive";
    public static final String RIGHT_BOT_MOTOR = "bot left drive";

    public static final double LENGTH = 0.202; //m
    public static final double WIDTH = 0.405; //m

    public static final double WHEEL_CIRCUM = 0.098 * Math.PI; //m
    public static final double WHEEL_DIAM = 0.098; //m

    public static final double MAX_RPM = 300; //rotations per minute
    public static final double MAX_SPEED = (MAX_RPM / 60) * WHEEL_CIRCUM; //m/s
}
