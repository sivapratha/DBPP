package com.hp.dbpowerpack.Model;


/**
 * The Class TableSpaceModel.
 */
public class TableSpaceModel {

	/** The table space name. */
	private String tableSpaceName;

	/** The size mb. */
	private Double sizeMb;

	/** The free mb. */
	private Double freeMb;

	/** The free perc. */
	private Double freePerc;

	/** The used perc. */
	private Double usedPerc;

	/**
	 * Gets the table space name.
	 * 
	 * @return the table space name
	 */
	public String getTableSpaceName() {
		return tableSpaceName;
	}

	/**
	 * Sets the table space name.
	 * 
	 * @param tableSpaceName
	 *            the new table space name
	 */
	public void setTableSpaceName(String tableSpaceName) {
		this.tableSpaceName = tableSpaceName;
	}

	/**
	 * Gets the size mb.
	 * 
	 * @return the size mb
	 */
	public Double getSizeMb() {
		return sizeMb;
	}

	/**
	 * Sets the size mb.
	 * 
	 * @param sizeMb
	 *            the new size mb
	 */
	public void setSizeMb(Double sizeMb) {
		this.sizeMb = sizeMb;
	}

	/**
	 * Gets the free mb.
	 * 
	 * @return the free mb
	 */
	public Double getFreeMb() {
		return freeMb;
	}

	/**
	 * Sets the free mb.
	 * 
	 * @param freeMb
	 *            the new free mb
	 */
	public void setFreeMb(Double freeMb) {
		this.freeMb = freeMb;
	}

	/**
	 * Gets the free perc.
	 * 
	 * @return the free perc
	 */
	public Double getFreePerc() {
		return freePerc;
	}

	/**
	 * Sets the free perc.
	 * 
	 * @param freePerc
	 *            the new free perc
	 */
	public void setFreePerc(Double freePerc) {
		this.freePerc = freePerc;
	}

	/**
	 * Gets the used perc.
	 * 
	 * @return the used perc
	 */
	public Double getUsedPerc() {
		return usedPerc;
	}

	/**
	 * Sets the used perc.
	 * 
	 * @param usedPerc
	 *            the new used perc
	 */
	public void setUsedPerc(Double usedPerc) {
		this.usedPerc = usedPerc;
	}

}
