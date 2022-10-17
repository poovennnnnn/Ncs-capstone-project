package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String firstName;
	private String lastName;
	private String city;
	private String actType;
	private Integer phone;
	private Double balance;
	private Integer actNumber;
	
	
}
