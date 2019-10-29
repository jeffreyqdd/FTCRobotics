package com.gogobot.botcore.kinematic;


import java.util.List;

public class CartesianVelocity {
	
	public double longitudinalVelocity;
	public double transversalVelocity;
	public double angularVelocity;
	
	public CartesianVelocity() {
		super();
	}


	
	public CartesianVelocity(double longitudinalVelocity, double transversalVelocity, double angularVelocity) {
		super();
		this.longitudinalVelocity = longitudinalVelocity;
		this.transversalVelocity = transversalVelocity;
		this.angularVelocity = angularVelocity;
	}


	public CartesianVelocity (List<Double> v) {
		this(v.get(0), v.get(1), v.get(2));	
	}
	
	// note: if the longitudinal velocity is zero, it returns NaN
	public double getTheta() {
		if (longitudinalVelocity <= 0.001)
			return Double.NaN;
		return Math.atan(transversalVelocity/longitudinalVelocity);
	}

	@Override
	public String toString() {
		return "Plane3DoFVelocity [longitudinalVelocity=" + longitudinalVelocity + ", transversalVelocity="
				+ transversalVelocity + ", angularVelocity=" + angularVelocity + "]";
	}
	
	
	public CartesianVelocity xyVelocity() {
		return new CartesianVelocity(longitudinalVelocity, transversalVelocity, 0);
	}
	
	public CartesianVelocity orientationVelocity() {
		return new CartesianVelocity(0, 0, angularVelocity);
	}
	
}
