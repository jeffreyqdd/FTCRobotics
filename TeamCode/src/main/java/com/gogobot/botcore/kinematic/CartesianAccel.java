package com.gogobot.botcore.kinematic;

public class CartesianAccel {
	
	public double longitudinalAcceleration;
	public double transversalAcceleration;
	public double angularAcceleration;
	
	public CartesianAccel() {
		super();
	}

	
	
	public CartesianAccel(double longitudinalAcc, double transversalAcc, double angularAcc) {
		super();
		this.longitudinalAcceleration = longitudinalAcc;
		this.transversalAcceleration = transversalAcc;
		this.angularAcceleration = angularAcc;
	}



	@Override
	public String toString() {
		return " [longitudinalAcceleration=" + longitudinalAcceleration + ", transversalAcceleration="
				+ transversalAcceleration + ", angularAcceleration=" + angularAcceleration + "]";
	}
	
	
	

}
