package com.devsu.hackerearth.backend.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

	private Long id;
	private String name;
	private String gender;
	private int age;
	private String address;
	private String phone;
	private boolean isActive;
}
