package com.situ.crm.vo;

import java.io.Serializable;

public class CustomerServiceAnalysis implements Serializable {

	//服务类型
	private String serviceTypeName;
	//服务类型的次数
	private Integer serviceTypeTimes;
	
	public CustomerServiceAnalysis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerServiceAnalysis(String serviceTypeName, Integer serviceTypeTimes) {
		super();
		this.serviceTypeName = serviceTypeName;
		this.serviceTypeTimes = serviceTypeTimes;
	}

	/**
	 * @return the serviceTypeName
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}

	/**
	 * @param serviceTypeName the serviceTypeName to set
	 */
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	/**
	 * @return the serviceTypeTimes
	 */
	public Integer getServiceTypeTimes() {
		return serviceTypeTimes;
	}

	/**
	 * @param serviceTypeTimes the serviceTypeTimes to set
	 */
	public void setServiceTypeTimes(Integer serviceTypeTimes) {
		this.serviceTypeTimes = serviceTypeTimes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerServiceAnalysis [serviceTypeName=" + serviceTypeName + ", serviceTypeTimes=" + serviceTypeTimes
				+ "]";
	}
	
	
	
}
