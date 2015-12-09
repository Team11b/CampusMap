package WPI.CampusMap.Backend.PathPlanning.Route;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<Section, LinkedList<Instruction>> routeTable = new HashMap<>();

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
	
	public LinkedList<Instruction> getRoute(Section section)
	{
		return routeTable.get(section);
	}

	public void setRoute(LinkedList<Instruction> route) {
		this.route = route;
	}

	public void append(LinkedList<Instruction> newPart, Section section) {
		LinkedList<Instruction> sectionList = new LinkedList<>();
		for (int i = 0; i < newPart.size(); i++) {
			this.route.add(newPart.get(i));
			sectionList.add(newPart.get(i));
		}
		
		routeTable.put(section, sectionList);
	}

	public void parse(Path mp) {

		for (Section current: mp) {;
			this.append(Route.parse(current), current);
		}
	}

	private static LinkedList<Instruction> parse(Section current) {
		ArrayList<IPoint> p = GetTurns.getTurns(current);
		LinkedList<Instruction> list = new LinkedList<Instruction>();
		Instruction latest = null;
		
		latest = new Instruction(p.get(0),true);
		list.add(latest);
		latest = new Instruction(p.get(0).distance(p.get(1)), p.get(0), p.get(1));
		list.add(latest);
		float totalDist = (float) p.get(0).distance(p.get(1));

		for (int i = 1; i < p.size() - 1; i++) {
			String turn = "";
			String direction = "";
			float dist = (float) p.get(i).distance(p.get(i + 1));

			float angleBefore = GetTurns.getAngle(p.get(i - 1), p.get(i));
			float angleAfter = GetTurns.getAngle(p.get(i), p.get(i + 1));
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

			latest = new Instruction(direction + turn, p.get(i));
			list.add(latest);
			latest = new Instruction(dist, p.get(i), p.get(i + 1));
			list.add(latest);
			totalDist += dist;
		}
		
		float walkingSpeed = (float) 4.11; //feet per sec
		float seconds = (totalDist / walkingSpeed);

		latest = new Instruction(p.get(p.size()-1), false);
		list.add(latest);
		latest = new Instruction(seconds, p.get(p.size() - 1));
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
