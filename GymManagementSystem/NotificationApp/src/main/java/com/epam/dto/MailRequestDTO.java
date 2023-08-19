 package com.epam.dto;

import java.util.List;
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
public class MailRequestDTO {
//	private String subject;
//	private String body;
	private List<String> toMails;
	private List<String> ccMails;
	Map<String,String> details;
}
