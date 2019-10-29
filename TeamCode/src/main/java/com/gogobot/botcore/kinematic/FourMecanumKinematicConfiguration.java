package com.gogobot.botcore.kinematic;

public class FourMecanumKinematicConfiguration {
	

	
	public double wheelRadius;
	public double lengthBetweenFrontWheels;
	public double lengthBetweenFrontAndRearWheels;
	
	public double maxV = 2;
	public double maxRotV = 3;
	public double accel = 0.2;
	public double accelRot = 0.24;
	
    //how far sideways in one wheel rotation compared to forward
	public double slideRatio = 1.0;

	public double rotationRatio = 1.0;
    
   	@Override
	public String toString() {
		return "FourMecanumKinematicConfiguration [wheelRadius=" + wheelRadius + ", lengthBetweenFrontWheels="
				+ lengthBetweenFrontWheels + ", lengthBetweenFrontAndRearWheels=" + lengthBetweenFrontAndRearWheels
				+ ", slideRatio=" + slideRatio + ", rotationRatio=" + rotationRatio + "]";
	}

	
}
