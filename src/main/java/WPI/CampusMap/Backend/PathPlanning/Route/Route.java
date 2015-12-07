package WPI.CampusMap.Backend.PathPlanning.Route;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Route {
	private LinkedList<Instruction> route;

	public Route(LinkedList<Instruction> route) {
		this.route = route;
	}

	public Route() {
		this.route = new LinkedList<Instruction>();
	}

	public Route(Path mp) {
		this.route = new LinkedList<Instruction>();
		parse(mp);
	}

	public LinkedList<Instruction> getRoute() {
		return route;
	}

	public void setRoute(LinkedList<Instruction> route) {
		this.route = route;
	}

	public void append(LinkedList<Instruction> newPart) {
		for (int i = 0; i < newPart.size(); i++) {
			this.route.add(newPart.get(i));
		}
	}

	public void parse(Path mp) {
		int offset = 0;

		for (Section current: mp) {;
			this.append(Route.parse(current, offset));
			offset += current.size();
		}
	}

	public static LinkedList<Instruction> parse(Section current, int offset) {
		ArrayList<IPoint> p = current.getTurns();
		LinkedList<Instruction> list = new LinkedList<Instruction>();
		Instruction latest = null;
		
		String route = "";
		route += "Start: " + p.get(0).getCoord().toString() + "\n";
		route += "Face " + p.get(1).getId() + " and walk "
				+  new DecimalFormat("#.").format(p.get(0).distance(p.get(1))) + " feet.\n";
		latest = new Instruction(route, 0, p.get(0), offset);
		list.add(latest);
		route = "";
		float totalDist = 0;

		for (int i = 1; i < p.size() - 1; i++) {
			String turn = "";
			String direction = "";
			float dist = (float) p.get(i).distance(p.get(i - 1));

			float angleBefore = p.getAngle(p.get(i - 1), p.get(i));
			float angleAfter = p.getAngle(p.get(i), p.get(i + 1));
			// System.out.printf("Angle Before: %f, Angle After: %f \n",
			// angleBefore, angleAfter);

//			route += p.get(i).getPoint().getCoord().toString() + " to "
//					+ p.get(i+1).getPoint().getCoord().toString() + "";

			int quad1 = (int) (((angleBefore < 0 ? 360 : 0) + angleBefore) / 90 + 1);
			int quad2 = (int) (((angleAfter < 0 ? 360 : 0) + angleAfter) / 90 + 1);
//			 System.out.printf("Quad Before: %d, Quad After: %d \n", quad1, quad2);
			if (quad1 == quad2)
				if (angleAfter > angleBefore)
					turn = "left";
				else
					turn = "right";
			else if( quad2%4 == (quad1 + 2) % 4){
				float after2 = (angleAfter+180);
				after2 = after2 > 180 ? after2 - 360 : after2;
				//int quad3 = (int) (((after2 < 0 ? 360 : 0) + after2) / 90 + 1);
//				System.out.printf("Angle Before: %f, Angle After2: %f \n", angleBefore, after2);
				if (after2 > angleBefore)
					turn = "right";
				else
					turn = "left";
				}
			else if (quad2%4 == (quad1 + 1) % 4)
				turn = "left";
			else
				turn = "right";
			if (Math.abs(angleBefore - angleAfter) < 45) {
				direction = "slightly ";
			} else {
				direction = "";
			}
			route += "Turn " + direction + turn + " and walk " +  new DecimalFormat("#.").format(dist) + " feet.\n";

			latest = new Instruction(route, dist, p.get(0), offset + i);
			list.add(latest);
			totalDist += dist;
			route = "";
		}
		
		float walkingSpeed = (float) 4.11; //feet per sec
		float seconds = (totalDist / walkingSpeed);
		String time = "ETA: " + (int)(seconds/60) + " minutes and " + new DecimalFormat("#.").format(seconds%60) +" seconds."; 

		latest = new Instruction(time, 0, p.get(p.size()-1), p.size()-1);
		list.add(latest);

		return list;
	}
	
	public String toString() {
		String answer = "";
		
		for (Instruction i : this.route) {
			answer += i.toString();
			answer += "\n";
		}
		
		return answer;
	}

}
