package WPI.CampusMap.Backend.PathPlanning.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
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

	public LinkedList<Instruction> getRoute(Section section) {
		return routeTable.get(section);
	}

	/**
	 * Sets this to the given route.
	 * @param route
	 */
	public void setRoute(LinkedList<Instruction> route) {
		this.route = route;
	}

	/**
	 * Appends new instructions and sections to the route.
	 * @param newPart
	 * @param section
	 */
	public void append(LinkedList<Instruction> newPart, Section section) {
		LinkedList<Instruction> sectionList = new LinkedList<>();
		for (int i = 0; i < newPart.size(); i++) {
			this.route.add(newPart.get(i));
			sectionList.add(newPart.get(i));
		}

		routeTable.put(section, sectionList);
	}

	/**
	 * Parses a path and generates a route.
	 * @param mp
	 */
	public void parse(Path mp) {
		Section last = null;
		Iterator<Section> iterator = mp.iterator();
		Section current  = iterator.next();
		while(current != null) {
			this.append(Route.parse(current), current);
			
			if(last == null){
				routeTable.get(current).addFirst(new Instruction(current.getPoints().getFirst(), true));
			}else if(!last.getMap().equals(current.getMap())){
				IPoint lastP = last.getPoints().getLast();
				IPoint currentP = current.getPoints().getFirst();
				routeTable.get(last).add(new Instruction(lastP.getType(), lastP, currentP));
			}
			
			if(iterator.hasNext()){
				last = current;
				current = iterator.next();
			}else{
				routeTable.get(current).add(new Instruction(current.getPoints().getLast(), false));
				routeTable.get(current).add(eta(mp));
				break;
			}
		}
	}

	/**
	 * Parses a section and returns a List of Instructions.
	 * @param current
	 * @return
	 */
	private static LinkedList<Instruction> parse(Section current) {
		ArrayList<IPoint> p = GetTurns.getTurns(current);
		LinkedList<Instruction> list = new LinkedList<Instruction>();
		Instruction latest = null;

//		latest = new Instruction(p.get(0), true);
//		list.add(latest);
		latest = new Instruction(p.get(0).distance(p.get(1)), p.get(0), p.get(1));
		list.add(latest);

		for (int i = 1; i < p.size() - 1; i++) {
			String turn = "";
			String direction = "";
			float dist = (float) p.get(i).distance(p.get(i + 1));

			float angleBefore = GetTurns.getAngle(p.get(i - 1), p.get(i));
			float angleAfter = GetTurns.getAngle(p.get(i), p.get(i + 1));
			// System.out.printf("Angle Before: %f, Angle After: %f \n",
			// angleBefore, angleAfter);

			// route += p.get(i).getPoint().getCoord().toString() + " to "
			// + p.get(i+1).getPoint().getCoord().toString() + "";

			int quad1 = (int) (((angleBefore < 0 ? 360 : 0) + angleBefore) / 90 + 1);
			int quad2 = (int) (((angleAfter < 0 ? 360 : 0) + angleAfter) / 90 + 1);
			// System.out.printf("Quad Before: %d, Quad After: %d \n", quad1,
			// quad2);
			if (quad1 == quad2)
				if (angleAfter > angleBefore)
					turn = "left";
				else
					turn = "right";
			else if (quad2 % 4 == (quad1 + 2) % 4) {
				float after2 = (angleAfter + 180);
				after2 = after2 > 180 ? after2 - 360 : after2;
				// int quad3 = (int) (((after2 < 0 ? 360 : 0) + after2) / 90 +
				// 1);
				// System.out.printf("Angle Before: %f, Angle After2: %f \n",
				// angleBefore, after2);
				if (after2 > angleBefore)
					turn = "right";
				else
					turn = "left";
			} else if (quad2 % 4 == (quad1 + 1) % 4)
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
		}

		return list;
	}
	
	/**
	 * Generates an ETA Instruction for the given path.
	 * @param mp
	 * @return
	 */
	private static Instruction eta(Path mp){
		float distance = 0;
		IPoint previous = null;
		for(Section section: mp){
			for(IPoint point : section){
				if(previous != null && !point.getMap().equals(previous.getMap())){
					if(point.getType().equals(RealPoint.STAIRS) && previous.getType().equals(RealPoint.STAIRS)){
						distance += 20;
					}
					if(point.getType().equals(RealPoint.ELEVATOR) && previous.getType().equals(RealPoint.ELEVATOR)){
						//average time for elevator = 1 min
						distance += 180;
					}
					if(point.getType().equals(RealPoint.OUT_DOOR) && previous.getType().equals(RealPoint.OUT_DOOR)){
						distance += 0;
					}
				}else if(previous != null){
					distance += point.distance(previous);
				}
				
				previous = point;
			}
		}
		// http://www.bellaonline.com/articles/art20257.asp
		float walkingSpeed = (float) 2.93; // feet per sec
		
		return new Instruction(distance/walkingSpeed,previous);
	}

	/**
	 * Converts the route to a String.
	 */
	public String toString() {
		String answer = "";

		for (Instruction i : this.route) {
			answer += i.toString();
			answer += "\n";
		}

		return answer;
	}

}
