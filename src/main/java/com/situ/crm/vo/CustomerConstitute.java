package com.situ.crm.vo;

import java.io.Serializable;

public class CustomerConstitute implements Serializable {

	//客户等级
	private String levelName;
	//等级数量
	private Integer levelNum;
	
	public CustomerConstitute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerConstitute(String levelName, Integer levelNum) {
		super();
		this.levelName = levelName;
		this.levelNum = levelNum;
	}

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return the levelNum
	 */
	public Integer getLevelNum() {
		return levelNum;
	}

	/**
	 * @param levelNum the levelNum to set
	 */
	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerConstitute [levelName=" + levelName + ", levelNum=" + levelNum + "]";
	}
	
	
	
}
