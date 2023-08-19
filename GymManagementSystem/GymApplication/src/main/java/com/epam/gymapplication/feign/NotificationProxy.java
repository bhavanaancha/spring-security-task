//package com.epam.gymapplication.feign;
//
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.epam.dto.MailRequestDTO;
//
//
//
//@FeignClient(name="NotificationService")
//@LoadBalancerClient(name="NotificationService")
//public interface NotificationProxy {
//	
//	@PostMapping("notifications/register")
//	void register(@RequestBody   MailRequestDTO user);
//	
//	@PostMapping("notifications/update")
//	void update(@RequestBody  MailRequestDTO user);
//	
//	@PostMapping("notifications/addTraining")
//	void addTraining(@RequestBody  MailRequestDTO training);
//
//}
