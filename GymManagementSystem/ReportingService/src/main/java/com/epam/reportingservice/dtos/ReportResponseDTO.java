package com.epam.reportingservice.dtos;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReportResponseDTO {
	
	private String firstName;
	private String lastName;
	private String username;
	private boolean isActive;
//	private Map<Long, Map<Long, Map<Long,Map<String, Long>>>> duration;
	private Map<Integer, Map<Integer, Map<Integer,Integer>>> duration;
}
