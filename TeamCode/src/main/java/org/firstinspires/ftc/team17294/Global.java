package org.firstinspires.ftc.team17294;

public class Global {
    public static final String LEFT_TOP_MOTOR = "left drive top";
    public static final String RIGHT_TOP_MOTOR = "right drive top";
    public static final String LEFT_BOT_MOTOR = "left drive bot";
    public static final String RIGHT_BOT_MOTOR = "right drive bot";

    public static final double LENGTH = 0.1; //m
    public static final double WIDTH = 0.1; //m

    public static final double WHEEL_CIRCUM = 0.098 * Math.PI;
    public static final double WHEEL_DIAM = 0.098;

    public static final double MAX_RPM = 300;
    public static final double MAX_SPEED = (MAX_RPM / 60) * WHEEL_CIRCUM;
}
