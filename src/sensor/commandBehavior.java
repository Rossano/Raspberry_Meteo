package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * 
 * <h1>commandBehavior Interface<h1>
 * <p>Generic interface for the commandBehavior object.
 * This interface is meant to act as a generic interface to start the sensor.<p>
 *
 */
public interface commandBehavior {

		/**
		 * <h3>start Method<h3>
		 * <p>This method is implementing a standard interface to start the sensor.<p>
		 * 
		 * @param none
		 * @return boolean, status of the starting, true is OK, false is FAILED.
		 */
		public boolean start();
		
}
