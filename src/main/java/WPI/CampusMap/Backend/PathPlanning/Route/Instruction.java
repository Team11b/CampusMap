package WPI.CampusMap.Backend.PathPlanning.Route;

import java.text.DecimalFormat;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Instruction {

	private String instruction;
	private double distance;
	private IPoint start, end;
	private String map;
	private InstructionType type;

	private static boolean metric = false;
	private static double ftToM = 0.3048;

	public Instruction(IPoint point, boolean start) {
		if (start) {
			if (point.getId().length() > 15) {
				this.instruction = "Start at " + point.getMapDisplayName() + ".\n";
				System.out.println("here");
			} else {
				this.instruction = "Start at " + point.getId() + ".\n";
			}
			this.start = point;
			this.type = InstructionType.start;
			this.map = this.start.getMap();

		} else {
			if (point.getId().length() > 15) {
				this.instruction = "You have arrived at your destination" + ".\n";
			} else {
				this.instruction = "You have arrived at " + point.getDisplayName() + ".\n";
			}
			this.end = point;
			this.type = InstructionType.end;
			this.map = this.end.getMap();

		}
	}

	public Instruction(String turn, IPoint start) {
		this.instruction = "Turn " + turn + ".\n";
		this.start = start;
		this.type = InstructionType.turn;
		this.map = this.start.getMap();
	}

	public Instruction(double distance, IPoint start, IPoint end) {
		if (Instruction.metric) {
			this.instruction = "Walk " + new DecimalFormat("#").format((distance * Instruction.ftToM))
					+ " meters.\n";
		} else {
			this.instruction = "Walk " + new DecimalFormat("#").format(distance) + " feet.\n";
		}

		this.distance = distance;
		this.start = start;
		this.end = end;
		this.type = InstructionType.walk;
		this.map = this.start.getMap();
	}
	
	public Instruction(String type, IPoint start, IPoint end) {
		System.out.println(type);
		switch(type){
		case RealPoint.ELEVATOR:
			this.instruction = "Take the elevator to floor " + end.getMapDisplayName().split("-")[1];
			break;
		case RealPoint.OUT_DOOR:
			if(end.getMap() == "Campus_Map"){
				this.instruction = "Go outside";
			}else{
				this.instruction = "Go into " + end.getMapDisplayName().split("/")[0].replace("_", " ");
			}
			break;
		case RealPoint.STAIRS:
			this.instruction = "Take the stairs to floor "+ end.getMapDisplayName().split("-")[1];
			break;
		default:
			this.instruction = "";
			break;
		}
		this.start = start;
		this.end = end;
		this.type = InstructionType.walk;
		this.map = this.start.getMap();
	}

	public Instruction(float seconds, IPoint end) {
		int min = (int) seconds / 60;
		seconds = seconds % 60;
		this.instruction = "ETA: " + min + " minutes and " + new DecimalFormat("#").format(seconds) + " seconds.\n";
		this.type = InstructionType.time;
		this.end = end;
		this.map = this.end.getMap();
	}

	public String getInstruction() {
		return instruction;
	}

	public double getDistance() {
		return distance;
	}

	public InstructionType getType() {
		return type;
	}

	public IPoint getStart() {
		return start;
	}

	public IPoint getEnd() {
		return end;
	}

	public String getMap() {
		return map;
	}

	/**
	 * @return the metric
	 */
	public static boolean isMetric() {
		return metric;
	}

	/**
	 * @param metric
	 *            the metric to set
	 */
	public static void setMetric(boolean metric) {
		Instruction.metric = metric;
	}

	public String toString() {
		return this.instruction + " @ " + this.map;
	}

}
