package com.partyrock.comm;

import java.util.ArrayList;

/**
 * The basics for a class that communicates over some protocol with I/O, such as
 * serial. This keeps track of CommListeners, so we can notify another object
 * when a message is received
 * @author Matthew
 */
public abstract class Communicator {

	/**
	 * The list of all listeners this communicator should notify
	 */
	private ArrayList<CommListener> listeners;

	public Communicator() {
		listeners = new ArrayList<CommListener>();
	}

	public void addListener(CommListener listener) {
		listeners.add(listener);
	}

	/**
	 * Optional method for the communicator to connect to its given
	 * communication method. This is here so disconnect() can exist too.
	 * @return Returns true on success
	 */
	public boolean connect() {
		return true;
	}

	/**
	 * Optional method that can be implemented by a child to disconnect from a
	 * communication method
	 */
	public void disconnect() {

	}
}