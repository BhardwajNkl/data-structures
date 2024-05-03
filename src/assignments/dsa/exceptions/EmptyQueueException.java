/**
 * 
 */
package assignments.dsa.exceptions;

/**
 * @author nikhilbhardwaj01
 *
 */
public class EmptyQueueException extends Exception {
	public EmptyQueueException() {
		super("The Queue is empty.");
	}
}
