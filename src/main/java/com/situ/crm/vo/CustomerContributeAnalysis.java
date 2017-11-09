package com.situ.crm.vo;

import java.io.Serializable;

public class CustomerContributeAnalysis implements Serializable {

	//客户名称
	private String name;
	//客户订单总金额
	private Float sum;
	
	public CustomerContributeAnalysis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerContributeAnalysis(String name, Float sum) {
		super();
		this.name = name;
		this.sum = sum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sum
	 */
	public Float getSum() {
		return sum;
	}

	/**
	 * @param sum the sum to set
	 */
	public void setSum(Float sum) {
		this.sum = sum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerContributeAnalysis [name=" + name + ", sum=" + sum + "]";
	}
	
	
}
