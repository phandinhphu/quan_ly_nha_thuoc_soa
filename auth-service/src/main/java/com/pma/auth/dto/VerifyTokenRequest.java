package com.pma.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTokenRequest {

	@NotBlank(message = "Token không được để trống")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
