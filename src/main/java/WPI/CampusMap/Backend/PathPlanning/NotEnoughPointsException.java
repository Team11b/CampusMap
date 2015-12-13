/**
 * 
 */
package WPI.CampusMap.Backend.PathPlanning;

/**
 * @author Jacob Zizmor
 *
 */
public class NotEnoughPointsException extends Exception {

	private static final long serialVersionUID = -5855413996739567879L;

	public NotEnoughPointsException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public NotEnoughPointsException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public NotEnoughPointsException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NotEnoughPointsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public NotEnoughPointsException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
