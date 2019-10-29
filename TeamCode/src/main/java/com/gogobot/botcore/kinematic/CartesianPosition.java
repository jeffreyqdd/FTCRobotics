package com.gogobot.botcore.kinematic;

public class CartesianPosition {
	
	public double longitudinalPosition;
	public double transversalPosition;
	public double orientation;
	
	public CartesianPosition() {
		super();
	}

	
	public CartesianPosition(double longitudinalPosition, double transversalPosition, double orientation) {
		super();
		this.longitudinalPosition = longitudinalPosition;
		this.transversalPosition = transversalPosition;
		this.orientation = orientation;
	}
	

	public CartesianPosition (double[] da) {
		this(da[0], da[1], da[2]);
	}

	
	@Override
	public String toString() {
		return "CartesianPosition [longitudinalPosition=" + longitudinalPosition + ", transversalPosition="
				+ transversalPosition + ", orientation=" + orientation + "]";
	}


	public CartesianPosition delta(CartesianPosition startPos) {
		// TODO Auto-generated method stub
		return new CartesianPosition(this.longitudinalPosition-startPos.longitudinalPosition, 
				this.transversalPosition - startPos.transversalPosition,
				this.orientation - startPos.orientation);
	}
	
	// 
	public boolean arrivedAt(CartesianPosition targetPos) {
		CartesianPosition d = this.delta(targetPos);
		return Math.abs(d.longitudinalPosition) < 0.001 && Math.abs(d.transversalPosition) < 0.001 
				&& Math.abs(d.orientation) < 0.017; //  long/tran < 0.001m |orientation| < 1degree (rad) 
	}
	
	//
	public double xyDistance() {
		return Math.sqrt(longitudinalPosition * longitudinalPosition + transversalPosition * transversalPosition);
	}
	
	// 
	public double xyDistanceTo(CartesianPosition pos) {
		CartesianPosition d = this.delta(pos);
		return d.xyDistance();
				
	}
	
	public double orientationDistanceTo (CartesianPosition pos) {
		CartesianPosition d = this.delta(pos);
		return Math.abs(d.orientation);
	}
		
	//
	public CartesianPosition xyPosition() {
		return new CartesianPosition(longitudinalPosition, transversalPosition, 0);
	}
	
	public CartesianPosition orientationPosition() {
		return new CartesianPosition(0, 0, orientation);
	}


	public double xDistanceTo(CartesianPosition stopPos) {
		CartesianPosition d = this.delta(stopPos);
		return Math.abs(d.longitudinalPosition);
	}

	public double yDistanceTo(CartesianPosition stopPos) {
		CartesianPosition d = this.delta(stopPos);
		return Math.abs(d.transversalPosition);
	}

		
	
}
