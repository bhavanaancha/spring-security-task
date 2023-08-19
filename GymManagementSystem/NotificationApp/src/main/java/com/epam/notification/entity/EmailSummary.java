package com.epam.notification.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection="EmailsSummary")
public class EmailSummary {
	@Id
	private String id;
	private String fromEmail;
	private List<String> toEmails;
	private List<String> ccEmails;
	private String status;
	private String remarks;


}
