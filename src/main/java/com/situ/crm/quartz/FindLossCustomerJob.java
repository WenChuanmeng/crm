package com.situ.crm.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.situ.crm.service.ICustomerService;

/**
 * 查找用户流失，定时任务
 * @author wen
 *
 */
@Component
public class FindLossCustomerJob {

	@Autowired
	private ICustomerService customerService;//用户Service
	
	/**
	 * 设置执行的时间
	 */
	//@Scheduled(cron="0 0 2 * * ?")//每天凌晨2点执行
	@Scheduled(cron="0/10 * * * * ?")
	public void work() {
		//System.out.println("FindLossCustomerJob.work()");
		//customerService.checkCustomerLoss();
	}
}
