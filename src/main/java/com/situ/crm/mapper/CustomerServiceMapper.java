package com.situ.crm.mapper;

import com.situ.crm.pojo.CustomerService;
import com.situ.crm.pojo.CustomerServiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerServiceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int countByExample(CustomerServiceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int deleteByExample(CustomerServiceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int insert(CustomerService record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int insertSelective(CustomerService record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    List<CustomerService> selectByExample(CustomerServiceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    CustomerService selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int updateByExampleSelective(@Param("record") CustomerService record, @Param("example") CustomerServiceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int updateByExample(@Param("record") CustomerService record, @Param("example") CustomerServiceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int updateByPrimaryKeySelective(CustomerService record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service
     *
     * @mbggenerated Mon Oct 30 13:55:56 CST 2017
     */
    int updateByPrimaryKey(CustomerService record);
}