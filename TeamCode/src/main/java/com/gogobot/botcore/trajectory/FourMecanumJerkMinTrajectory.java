package com.gogobot.botcore.trajectory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.rank.Percentile.EstimationType;

import com.gogobot.botcore.kinematic.CartesianAccel;
import com.gogobot.botcore.kinematic.CartesianPosition;
import com.gogobot.botcore.kinematic.CartesianVelocity;
import com.gogobot.botcore.kinematic.FourMecanumKinematic;
import com.gogobot.botcore.kinematic.FourMecanumKinematicConfiguration;

/**
 * There is a constraint of using this class: only use it for X/Y trajectory or orientation trajectory.
 * DO NOT USE IT for both at the same time. See TrajectoryDemo for the use.
 *
 */

public class FourMecanumJerkMinTrajectory implements WheeledBaseTrajectory {
	
	private final FourMecanumKinematicConfiguration config;
	private final CartesianPosition startPos;
	private final CartesianPosition stopPos;
	private final CartesianVelocity startVelocity;
	private final CartesianVelocity stopVelociy;
	private final CartesianAccel startAccl;
	private final CartesianAccel stopAccl;
	private final double T;
	
	
	private final List<JerkMinTrajectory> wheelTrajectories;

	private JerkMinTrajectory trajLongitudial,  trajTransversal, trajAngular;
	
//	public CartesianPosition getPositionAt(double t) {
//		return new CartesianPosition(trajLongitudial.getPositionAt(t), trajTransversal.getPositionAt(t), trajAngular.getPositionAt(t));
//	}
	
	@Override
	public CartesianVelocity getCartesianVelocityAt(double t) {
		
		CartesianVelocity oldcv= new CartesianVelocity(trajLongitudial.getVelocityAt(t), trajTransversal.getVelocityAt(t), trajAngular.getVelocityAt(t));
		System.out.println("oldcv:"+oldcv);
		
		FourMecanumKinematicConfiguration config = new FourMecanumKinematicConfiguration();
		
		config.wheelRadius = 0.05;
		config.lengthBetweenFrontAndRearWheels = 0.2;
		config.lengthBetweenFrontWheels = 0.5701;
		
		FourMecanumKinematic k = new FourMecanumKinematic(config);
		
		List<Double> kl = k.cartesianVelocityToWheelVelocities(new CartesianVelocity(trajLongitudial.getVelocityAt(t), 0, 0));
		List<Double> kt = k.cartesianVelocityToWheelVelocities(new CartesianVelocity(0, trajTransversal.getVelocityAt(t),0));
		List<Double> ka = k.cartesianVelocityToWheelVelocities(new CartesianVelocity(0, 0, trajAngular.getVelocityAt(t)));
		
		List<Double> newWheelVel = new ArrayList<Double>();
		
		newWheelVel.add(kl.get(0)+kt.get(0)+ka.get(0));
		newWheelVel.add(kl.get(1)+kt.get(1)+ka.get(1));
		newWheelVel.add(kl.get(2)+kt.get(2)+ka.get(2));
		newWheelVel.add(kl.get(3)+kt.get(3)+ka.get(3));


		CartesianVelocity ret = k.wheelVelocitiesToCartesianVelocity(newWheelVel);

		System.out.println("newcv:"+ret);
		
		 return ret;
	}
	
	public List<Double> getWheelVelocityAt(double t) {
		List<Double> wv = new ArrayList<Double>();
		
		for (int i=0; i< wheelTrajectories.size(); i++) {
			JerkMinTrajectory traj = wheelTrajectories.get(i);
			wv.add(traj.getVelocityAt(t));
		}
		return wv;
	}
	

	public JerkMinTrajectory getTrajLongitudial() {
		return trajLongitudial;
	}


	public JerkMinTrajectory getTrajTransversal() {
		return trajTransversal;
	}


	public JerkMinTrajectory getTrajAngular() {
		return trajAngular;
	}

	/**
	 * From Start (static) to Stop Position (Static). Expected a gradual acceleration from v=0 and then deacceleration v=0
	 * @param startPos
	 * @param stopPos
	 * @param t
	 */
	public FourMecanumJerkMinTrajectory(FourMecanumKinematicConfiguration config, CartesianPosition startPos, CartesianPosition stopPos, double t) {
		this ( config, startPos, stopPos, new CartesianVelocity(), new CartesianVelocity(), t);
	}

	/**
	 * From Start (with speed) to Stop Position (with speed). Expected a gradual acceleration (from start speed) and then 
	 * deacceleration (to stop speed). Start and stop acceleration is 0.
	 * @param startPos
	 * @param stopPos
	 * @param t
	 */
	public FourMecanumJerkMinTrajectory(FourMecanumKinematicConfiguration config, CartesianPosition startPos, CartesianPosition stopPos,  CartesianVelocity startVelocity,
			CartesianVelocity stopVelociy, double t) {
		this ( config, startPos, stopPos, startVelocity, stopVelociy, new CartesianAccel(), new CartesianAccel(), t);
	}
	
	/**
	 * Maintain start velocity, accel and stop velocity, de-accel. move the bot from start position to end position 
	 * 
	 * @param startPos
	 * @param stopPos
	 * @param startVelocity
	 * @param stopVelociy
	 * @param startAccl
	 * @param stopAccl
	 * @param t
	 */
	
	public FourMecanumJerkMinTrajectory(FourMecanumKinematicConfiguration config, CartesianPosition startPos, CartesianPosition stopPos, CartesianVelocity startVelocity,
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
		
		wheelTrajectories = new ArrayList<JerkMinTrajectory>();
		
		solverPosVelAcc();
	}


	/**
	 * 
	 */
	private void solverPosVelAcc() {
		
		/*
		
		double[] start_longitudinal = { startPos.longitudinalPosition, startVelocity.longitudinalVelocity, startAccl.longitudinalAcceleration };
		double[] start_transversal = { startPos.transversalPosition, startVelocity.transversalVelocity, startAccl.transversalAcceleration};
		double[] start_angular = { startPos.orientation, startVelocity.angularVelocity, startAccl.angularAcceleration};
		
		double[] stop_longitudinal = { stopPos.longitudinalPosition, stopVelociy.longitudinalVelocity, stopAccl.longitudinalAcceleration };
		double[] stop_transversal = { stopPos.transversalPosition, stopVelociy.transversalVelocity, stopAccl.transversalAcceleration};
		double[] stop_angular = { stopPos.orientation, stopVelociy.angularVelocity, stopAccl.angularAcceleration};

		
		trajLongitudial = new JerkMinTrajectory (  start_longitudinal, stop_longitudinal, T ) ;
		trajTransversal = new JerkMinTrajectory (  start_transversal, stop_transversal, T);
		trajAngular = new JerkMinTrajectory (  start_angular, stop_angular, T);
		*/
		
		// new way, find wheel position from start to end, and then apply JMT to wheel Position change.
		FourMecanumKinematic k = new FourMecanumKinematic(config);
		
		CartesianPosition deltaP = stopPos.delta(startPos);
		
		if (deltaP.arrivedAt(new CartesianPosition()))
			return;
		
		List<Double> deltaWheelP = k.cartesianPositionToWheelPositions(deltaP);
		
		List<Double> startWheelVelocity = k.cartesianVelocityToWheelVelocities(startVelocity);
		List<Double> stopWheelVelocity = k.cartesianVelocityToWheelVelocities(stopVelociy);
		
		
		//TBE: TO-BE-EXTENDED. Did not implement start and stop ACCLERATION. assuming it's 0.
		
		for (int i = 0; i< deltaWheelP.size(); i++) {
			double[] start_s_sdot_sddot = {0, startWheelVelocity.get(i), 0};
			double[] end_s_sdot_sddot = {deltaWheelP.get(i), stopWheelVelocity.get(i), 0 };
			JerkMinTrajectory wheelTraj = new JerkMinTrajectory(start_s_sdot_sddot, end_s_sdot_sddot, T);
			wheelTrajectories.add(wheelTraj);
		}
						
	}

	public static FourMecanumJerkMinTrajectory xyTrajectory(FourMecanumKinematicConfiguration config, CartesianPosition startPos, CartesianPosition stopPos) {
		return xyTrajectory(config, startPos, stopPos, new CartesianVelocity(), new CartesianVelocity());
	}
	

	public static FourMecanumJerkMinTrajectory xyTrajectory(FourMecanumKinematicConfiguration config, 
			CartesianPosition startPos, CartesianPosition stopPos,  CartesianVelocity startVelocity, CartesianVelocity stopVelociy) {
		
		double dx = startPos.xDistanceTo(stopPos);
		double dy = startPos.yDistanceTo(stopPos);
		
		if (dx < 0.001 && dy < 0.001)
			return null;

		double tx = estimateDuration(dx,startVelocity.longitudinalVelocity, stopVelociy.longitudinalVelocity, config.maxV, config.accel  );
		double ty = estimateDuration(dy,startVelocity.transversalVelocity, stopVelociy.transversalVelocity, config.maxV, config.accel  );
		
		double t = Math.max(tx, ty);
		
		return new FourMecanumJerkMinTrajectory(config, startPos.xyPosition(), stopPos.xyPosition(), startVelocity.xyVelocity(), stopVelociy.xyVelocity(), t);	
		
	}
	
	public static FourMecanumJerkMinTrajectory orientationTrajectory(FourMecanumKinematicConfiguration config, CartesianPosition startPos, CartesianPosition stopPos) {

		double d = startPos.orientationDistanceTo(stopPos);
		
		if (d <  0.017)
			return null;
		
		// Both start and stop velocity are zero
		double t = estimateDuration(d, 0, 0, config.maxRotV, config.accelRot);


		return new FourMecanumJerkMinTrajectory(config, startPos.orientationPosition(), stopPos.orientationPosition(), t);	

		
	}
	
	public double getT() {
		return T;
	}
	
	/*
	 * */
	private static double estimateDuration(double displacement, double vBegin, double vEnd, double vMax, double accel) {
		
		
		// t1 is the accelerating. 
		// s1 is the displacement in accelerating phase
		// t2 is the cruise speed 
		// t3 is the de-accelerating
		// ..
		// 
		// ..
		double t1_max, s1_max, t3_max, s3_max;
		
		assert vMax > vBegin && vMax > vEnd;
		
		t1_max = (vMax - vBegin) / accel;
		s1_max = vBegin * t1_max + accel * t1_max * t1_max / 2;
		t3_max = (vMax - vEnd) / accel;
		s3_max = vEnd * t3_max + accel * t3_max * t3_max / 2; 
		
		
		// only two high level scenarios:
		if (s1_max+s3_max > displacement) {
			// without cruise phase
			// math shows the following. 
			// s = s1 + s3 =  (Vb * t1 + a*t1^2 / 2) + (Ve * t3 + 1*t3^2 /2)
			// t1 = (V - Vb )/ a
			// t3 = (V - Ve) / a
			// => v = sqrt (a * s+ (Vb^2 + Ve^2 ) / 2)
			double V = Math.sqrt(accel * displacement + (vBegin*vBegin + vEnd * vEnd )/2);
			double ret = (V-vBegin) / accel + (V - vEnd) / accel;
			
			System.out.println("no cruise phase. max V = " + V + " t= " + ret);
			
			return ret;
			
		}
		else {
			// with cruise phase
			
			double t2 = (displacement - s1_max - s3_max) / vMax;
			
			System.out.println("Cruise phase in. " + t1_max+ "s, "+ t2+ "s, "+ t3_max+ "s ");
			
			return t1_max + t3_max + t2;
		}


	}

}
 