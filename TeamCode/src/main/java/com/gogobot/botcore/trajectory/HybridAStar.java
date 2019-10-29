package com.gogobot.botcore.trajectory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;

public abstract class HybridAStar {

	private final int numThetaCells;

	private final double maxTurnRadian;
	
	public HybridAStar() {
		super();
		
		numThetaCells = 90;
		maxTurnRadian = 35 * Math.PI / 180; // 35deg -> radians 
	}
	/**
	 * 
	 * @param numThetaCells
	 * @param speed Only used for bicycle model
	 * @param length
	 * @param grid_sz
	 */
	public HybridAStar(int numThetaCells, double max_turn_radian) {
		super();
		this.numThetaCells = numThetaCells;
		this.maxTurnRadian = max_turn_radian;
	}



	public double getMaxTurnRadian() {
		return maxTurnRadian;
	}

	public int getNumThetaCells() {
		return numThetaCells;
	}


	
	public class MazeS {
		public int g; // iteration
		public int f;
		public double x;
		public double y;
		public double theta;
	}

	public class MazePath {
		public int[][][] closed;
		public MazeS[][][] came_from;
		public MazeS finals_ms;
	}
	
	public class InflectionMazeS extends MazeS {
		
		public InflectionMazeS(MazeS s) {
			this.g = s.g;
			this.f = s.f;
			this.x = s.x;
			this.y = s.y;
			this.theta = s.theta;
			xInflection = yInflection = false;
		}
		public boolean xInflection;
		public boolean yInflection;
	}

	class MazeSComparator implements Comparator<MazeS> {

		@Override
		public int compare(MazeS lhs, MazeS rhs) {
			return lhs.f - rhs.f;
		}

	}

	public int thetaToStackNumber(double theta) {

		// Takes an angle (in radians) and returns which "stack" in the 3D
		// configuration space this angle corresponds to. Angles near 0 go in the
		// lower stacks while angles near 2 * pi go in the higher stacks.
		double new_theta = (theta + 2 * Math.PI) % (2 * Math.PI);
		int stack_number = (int) (Math.round(new_theta * numThetaCells / (2 * Math.PI))) % numThetaCells;

		return stack_number;

	}

	public int idx(double float_num) {
		// Returns the index into the grid for continuous position. So if x is 3.621,
		// then this would return 3 to indicate that 3.621 corresponds to array
		// index 3.
		return (int) Math.floor(float_num);
	}

	double heuristic(double x, double y, List<Integer> goal) {
		return Math.abs(y - goal.get(0)) + Math.abs(x - goal.get(1)); // return grid distance to goal
	}

	abstract List<MazeS> expand(MazeS state, List<Integer> goal); 

	public List<MazeS> reconstructPath(MazeS[][][] came_from, List<Double> start, MazeS final_ms) {
		List<MazeS> path = new ArrayList<MazeS>();
		path.add(final_ms);

		int stack = thetaToStackNumber(final_ms.theta);

		MazeS current = came_from[stack][idx(final_ms.x)][idx(final_ms.y)];

		stack = thetaToStackNumber(current.theta);

		double x = current.x;
		double y = current.y;

		while (x != start.get(0) || y != start.get(1)) {
			path.add(current);
			current = came_from[stack][idx(x)][idx(y)];
			x = current.x;
			y = current.y;
			stack = thetaToStackNumber(current.theta);
		}

		return path;

	}

	/**
	 * 
	 * @param grid
	 * @param start - a vector of x position, y position and moving-towards direction. 
	 *       note: for the mecanum type of body, the moving-towards direction is different
	 *       from the orientation of the bot 
	 * @param goal  - goal in grid coordinates
	 * @return
	 */
	public MazePath search(int[][] grid, List<Double> start, List<Integer> goal) {
		
		if( start.size() < 3 || goal.size() < 2)
			throw new IllegalArgumentException("start must have 3 values, including theta, and goal must have 2 values");

		int[][][] closed = new int[numThetaCells][grid[0].length][grid.length];

		MazeS[][][] came_from = new MazeS[numThetaCells][grid[0].length][grid.length];

		double theta = start.get(2);
		int stack = thetaToStackNumber(theta);
		int g = 0;

		MazeS state = new MazeS();
		state.g = g;
		state.x = start.get(0);
		state.y = start.get(1);
		state.f = g + (int) heuristic(state.x, state.y, goal);
		state.theta = theta;

		closed[stack][idx(state.x)][idx(state.y)] = 1;
		came_from[stack][idx(state.x)][idx(state.y)] = state;
		int total_closed = 1;
		List<MazeS> opened = new ArrayList<MazeS>();
		opened.add(state);
		//boolean finished = false;
		while (!opened.isEmpty()) {

			Collections.sort(opened, new MazeSComparator());
			MazeS current = opened.get(0); // grab first element
			opened.remove(0); // pop first element

			int x = (int) current.x;
			int y = (int) current.y;

			if (idx(x) == goal.get(0) && idx(y) == goal.get(1)) {
				System.out.println("found path to goal in " + total_closed + " expansions");

				MazePath path = new MazePath();
				path.came_from = came_from;
				path.closed = closed;
				path.finals_ms = current;

				return path;

			}

			List<MazeS> next_state = expand(current, goal);

			for (int i = 0; i < next_state.size(); ++i) {
				int g2 = next_state.get(i).g;
				double x2 = next_state.get(i).x;
				double y2 = next_state.get(i).y;
				double theta2 = next_state.get(i).theta;

				if ((x2 < 0 || x2 >= grid.length) || (y2 < 0 || y2 >= grid[0].length)) {
					// invalid cell
					continue;
				}

				int stack2 = thetaToStackNumber(theta2);

				if (closed[stack2][idx(x2)][idx(y2)] == 0 && grid[idx(x2)][idx(y2)] == 0) {
					opened.add(next_state.get(i));
					closed[stack2][idx(x2)][idx(y2)] = 1;
					came_from[stack2][idx(x2)][idx(y2)] = current;
					++total_closed;
				}
			}
		}

		System.out.println("no valid path.");
		MazePath path = new MazePath();
		path.came_from = came_from;
		path.closed = closed;
		path.finals_ms = state;

		return path;

	}
	
	// this is a convenient method so the 
	public static int[] cartesianToGrid(double[] xytheta, double grid_size) {
		return cartesianToGrid(xytheta[0], xytheta[1], grid_size);
	}
	public static int[] cartesianToGrid(double x, double y, double grid_size) {
		int[]  r ={(int) Math.ceil(x/grid_size), (int) Math.ceil(y/grid_size)}; 
		return  r ;
	}

	public static double[] cartesianToGridDouble(double[] xytheta, double grid_size) {
		return  cartesianToGridDouble(xytheta[0], xytheta[1], xytheta[2], grid_size) ;
	}
	public static double[] cartesianToGridDouble(double x, double y, double theta, double grid_size) {
		double[]  r ={x/grid_size, y/grid_size, theta}; 
		return  r ;
	}
	
	/**
	 * 
	 * This is to calculate gridspace speed 
	 * 
	 * @param cartesianSpeed 
	 * @param grid_size
	 * @param timePerStep  time needed for every step. this is related to processing frequency
	 * @return
	 */
	public static double cartesianSpeedToGridStepDistance(double cartesianSpeed, double timePerStep, double grid_size ) {
		return cartesianSpeed * timePerStep / grid_size ;
	}
	
	/**
	 * Partition the path to several segments. .
	 * Start and stop position of the path are zero on both dimension; interim position has zero on one dimension
	 * 
	 * @param path
	 * @return
	 */
	public List<InflectionMazeS> findPathWithReflectionPoints(int[][] grid, List<Double> start, List<Integer> goal) {
		
		MazePath get_path = search(grid, start, goal);

		List<MazeS> path = reconstructPath(get_path.came_from, start, get_path.finals_ms);

		
		List<InflectionMazeS> s = new ArrayList<InflectionMazeS>();
		
		
		/**
		 * define quarter per theta, I (1), II (2), III (3), IV (4).
		 * 0 (unknown)
		 * I = [0, 90)
		 * II = [90, 180)
		 * III = [180, 270)
		 * IV = [270, 360)
		 * Q = (int)theta / (Math.PI/2) + 1
		 * 
		 *        |
		 *        |
		 *    III |   II
		 *  ----------------> (y)
		 *    IV  |   I
		 *        |
		 *        |
		 *        V
		 *       (x)
		 */

		// integer key encoding - 10s means from Q; singles means to Q. "12" means from Q1 to Q2
		// Integer value encoding - 0 (means x-false, y false), 1 means (x-false, y-true), 
		//                          2 means (x-true, y-false), 3 means ( x true, y-true)

		int[][] mapping = {
				
				{12, 2},
				{23, 1},
				{34, 2},
				{41, 1},
				{21, 2},
				{32, 1},
				{43, 2},
				{14, 1},
				
				
		};
		
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		
		for (int i=0; i < mapping.length; i++ ) {
			m.put(mapping[i][0], mapping[i][1]);
		}
		
		int prevQ = 0;
		
		
		for (int i = path.size() - 1; i >= 0; --i) {
			HybridAStarBicycle.MazeS step = path.get(i);
			
			InflectionMazeS ifs = new InflectionMazeS(step);
			s.add(ifs);
			
			int Q = (int)(step.theta / (Math.PI/2) + 1);
			
			// set inflection point 
			
			Integer key = Integer.valueOf(prevQ * 10 + Q);
			
			if (m.containsKey(key)) {
				int v = m.get(key).intValue();
				
				if (v == 2) {
					ifs.xInflection = true;
				}
				else if (v == 1) {
					ifs.yInflection = true;
				}
				
				else {
					System.out.println("wrong value in the mapping");
				}
				
			}
			
			prevQ = Q;
			
		}
		
		return s;
	}
	
}
