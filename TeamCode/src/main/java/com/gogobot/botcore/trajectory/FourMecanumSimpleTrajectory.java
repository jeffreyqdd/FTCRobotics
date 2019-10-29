package com.gogobot.botcore.trajectory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gogobot.botcore.kinematic.CartesianAccel;
import com.gogobot.botcore.kinematic.CartesianPosition;
import com.gogobot.botcore.kinematic.CartesianVelocity;

/**
 * THIS ONE CANNOT BE USED AS IS. 
 * this is a PID controller type .. it requires sensoring the bot position
 *
 */
public class FourMecanumSimpleTrajectory implements WheeledBaseTrajectory {
	
	private FourMecanumTrajectoryConfiguration config;
	

	private final CartesianPosition startPos;
	private final CartesianPosition stopPos;
	private final CartesianVelocity startVelocity;
	private final CartesianVelocity stopVelociy;
	private final CartesianAccel startAccl;
	private final CartesianAccel stopAccl;
	private final double T;
	
	
    private double previousForwBackVel=0;
    private double previousLeftRightVel=0;
    private double previousRotVel=0;
    
    private List<Double> previousWheelVeolicty;
    

	
    public FourMecanumSimpleTrajectory(FourMecanumTrajectoryConfiguration config, CartesianPosition startPos, CartesianPosition stopPos, double t) {
	
    	this ( config, startPos, stopPos, new CartesianVelocity(), new CartesianVelocity(), t);
	}
    
    public FourMecanumSimpleTrajectory(FourMecanumTrajectoryConfiguration config, CartesianPosition startPos, CartesianPosition stopPos, CartesianVelocity startVelocity,
			CartesianVelocity stopVelociy, double t) {
    	this ( config, startPos, stopPos, startVelocity, stopVelociy, new CartesianAccel(), new CartesianAccel(), t);
    }

    public FourMecanumSimpleTrajectory(FourMecanumTrajectoryConfiguration config, CartesianPosition startPos, CartesianPosition stopPos, CartesianVelocity startVelocity,
			CartesianVelocity stopVelociy, CartesianAccel startAccl, CartesianAccel stopAccl, double t) {
		super();
		this.config = config;
		this.startPos = startPos;
		this.stopPos = stopPos;
		this.startVelocity = startVelocity;
		this.stopVelociy = stopVelociy;
		this.startAccl = startAccl;
		this.stopAccl = stopAccl;
		T = t;
		
	}
    
    
	private List<Double>  getNextVelocity(CartesianPosition currentPos) {
		
		if (currentPos.arrivedAt(stopPos)) {
		    
		    return previousWheelVeolicty; 
		}
		
		double forwBackVel=currentPos.longitudinalPosition* config.pParam;
		double leftRightVel=currentPos.transversalPosition*config.pParam;
		
		double v =Math.sqrt(forwBackVel*forwBackVel+leftRightVel*leftRightVel);
		
		if (v>config.maxV) {
	        forwBackVel=forwBackVel*config.maxV/v;
			leftRightVel=leftRightVel*config.maxV/v;					
		}
		
		
		double rotVel=-currentPos.longitudinalPosition* config.pParamRot;
		
		if (Math.abs(rotVel)> config.maxVRot) {
			        rotVel=config.maxVRot*rotVel/Math.abs(rotVel);
		}
			    
		double df=forwBackVel-previousForwBackVel;
		double ds=leftRightVel-previousLeftRightVel;
		double dr=rotVel-previousRotVel;
			    
			    if (Math.abs(df)>config.maxV*config.accelF) {
			        df=Math.abs(df)*(config.maxV*config.accelF)/df;
			    }
			    
			    if (Math.abs(ds)>config.maxV*config.accelF) {
			        ds=Math.abs(ds)*(config.maxV*config.accelF)/ds;
			    }
			    
			    if (Math.abs(dr)>config.maxVRot*config.accelF) {
			        dr=Math.abs(dr)*(config.maxVRot*config.accelF)/dr;
			    }
			    
			    
			    forwBackVel=previousForwBackVel+df;
			    leftRightVel=previousLeftRightVel+ds;
			    rotVel=previousRotVel+dr;
			    
			    List<Double> wvl = new ArrayList<Double>();
			    
			    
			    wvl.add( -forwBackVel-leftRightVel-rotVel);
			    wvl.add( -forwBackVel+leftRightVel+rotVel);
			    wvl.add( -forwBackVel-leftRightVel+rotVel);
			    wvl.add( -forwBackVel+leftRightVel-rotVel);	
			    
			    previousWheelVeolicty = wvl;
			    
			    previousForwBackVel=forwBackVel;
			    previousLeftRightVel=leftRightVel;
			    previousRotVel=rotVel;
			    
			    return wvl;
		
	}

	@Override
	public List<Double> getWheelVelocityAt(double t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartesianVelocity getCartesianVelocityAt(double t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getT() {
		return T;
	}



}
