//package com.epam.gymapplication.feign;
//
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.epam.gymapplication.dtos.ReportRequestDTO;
//import com.epam.gymapplication.dtos.ReportResponseDTO;
//
//
//
//@FeignClient(name="ReportService")
//@LoadBalancerClient(name="ReportService")
//public interface ReportProxy {
//	
//	@PostMapping("/report")
//	void reportTime(@RequestBody ReportRequestDTO mapToReportRequestDTO);
//	@GetMapping("/report")
//	ReportResponseDTO getReport(@RequestParam String username);
//}
