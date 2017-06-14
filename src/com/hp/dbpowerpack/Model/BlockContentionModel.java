package com.hp.dbpowerpack.Model;


/**
 * The Class BlockContentionModel.
 */
public class BlockContentionModel {

	/** The wait class. */
	private String waitClass;

	/** The total waits. */
	private Double totalWaits;

	/** The total time. */
	private Double totalTime;

	/**
	 * Gets the wait class.
	 * 
	 * @return the wait class
	 */
	public String getWaitClass() {
		return waitClass;
	}

	/**
	 * Sets the wait class.
	 * 
	 * @param waitClass
	 *            the new wait class
	 */
	public void setWaitClass(String waitClass) {
		this.waitClass = waitClass;
	}

	/**
	 * Gets the total waits.
	 * 
	 * @return the total waits
	 */
	public Double getTotalWaits() {
		return totalWaits;
	}

	/**
	 * Sets the total waits.
	 * 
	 * @param totalWaits
	 *            the new total waits
	 */
	public void setTotalWaits(Double totalWaits) {
		this.totalWaits = totalWaits;
	}

	/**
	 * Gets the total time.
	 * 
	 * @return the total time
	 */
	public Double getTotalTime() {
		return totalTime;
	}

	/**
	 * Sets the total time.
	 * 
	 * @param totalTime
	 *            the new total time
	 */
	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}

}
