package WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Route;

import java.text.DecimalFormat;
import java.util.LinkedList;

import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;
import WPI.CampusMap.Backend.PathPlanning.Path;

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

	public Route(MultiPath mp) {
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

	public void parse(MultiPath mp) {
		LinkedList<Path> allPaths = mp.getMp();
		Path current = null;
		int offset = 0;

		for (int j = 0; j < allPaths.size(); j++) {
			current = allPaths.get(j);
			this.append(Route.parse(current, offset));
			offset += current.getPath().size();
		}
	}

	public static LinkedList<Instruction> parse(Path origP, int offset) {
		Path p = origP.getTurns();
		LinkedList<Instruction> list = new LinkedList<Instruction>();
		Instruction latest = null;
		
		String route = "";
		route += "Start: " + p.getPath().get(0).getPoint().getCoord().toString() + "\n";
		route += "Face " + p.getPath().get(1).getPoint().getId() + " and walk "
				+  new DecimalFormat("#.").format(p.getPath().get(0).getPoint().distance(p.getPath().get(1).getPoint())) + " feet.\n";
		latest = new Instruction(route, 0, p.getPath().get(0), offset);
		list.add(latest);
		route = "";
		float totalDist = 0;

		for (int i = 1; i < p.getPath().size() - 1; i++) {
			String turn = "";
			String direction = "";
			float dist = (float) p.getPath().get(i).getPoint().distance(p.getPath().get(i - 1).getPoint());

			float angleBefore = p.getAngle(p.getPath().get(i - 1).getPoint(), p.getPath().get(i).getPoint());
			float angleAfter = p.getAngle(p.getPath().get(i).getPoint(), p.getPath().get(i + 1).getPoint());
			// System.out.printf("Angle Before: %f, Angle After: %f \n",
			// angleBefore, angleAfter);

//			route += p.getPath().get(i).getPoint().getCoord().toString() + " to "
//					+ p.getPath().get(i+1).getPoint().getCoord().toString() + "";

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

			latest = new Instruction(route, dist, p.getPath().get(0), offset + i);
			list.add(latest);
			totalDist += dist;
			route = "";
		}
		
		float walkingSpeed = (float) 4.11; //feet per sec
		float seconds = (totalDist / walkingSpeed);
		String time = "ETA: " + (int)(seconds/60) + " minutes and " + new DecimalFormat("#.").format(seconds%60) +" seconds."; 

		latest = new Instruction(time, 0, p.getPath().get(p.getPath().size()-1), p.getPath().size()-1);
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
