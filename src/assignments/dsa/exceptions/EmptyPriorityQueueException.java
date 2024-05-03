/**
 * 
 */
package assignments.dsa.exceptions;

/**
 * @author nikhilbhardwaj01
 *
 */
public class EmptyPriorityQueueException extends Exception {
	public EmptyPriorityQueueException() {
		super("The Queue is empty.");
	}
}
