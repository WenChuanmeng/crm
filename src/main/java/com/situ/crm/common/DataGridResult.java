package com.situ.crm.common;

import java.util.List;

/**
 * 
 *作为查询DataGrid结果的封装
 */
public class DataGridResult {

	//数据的数量
	private Integer total;
	//数据的集合
	private List<?> rows;
	
	public DataGridResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataGridResult(Integer total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public List<?> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataGridResult [total=" + total + ", rows=" + rows + "]";
	}
	
	
}
