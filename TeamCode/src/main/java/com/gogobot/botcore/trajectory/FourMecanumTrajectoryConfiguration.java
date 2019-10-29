package com.gogobot.botcore.trajectory;

public class FourMecanumTrajectoryConfiguration {
	
	public double pParam=20;
	public double avgV=1.8;
	public double maxV=2;
	public double pParamRot=10;
	public double avgVRot=2;
	public double maxVRot=3;
	public double accelF=0.035;
	
	
	@Override
	public String toString() {
		return "FourMecanumTrajectoryConfiguration [pParam=" + pParam + ", maxV=" + maxV + ", pParamRot=" + pParamRot
				+ ", maxVRot=" + maxVRot + ", accelF=" + accelF + "]";
	}
	
	
}
