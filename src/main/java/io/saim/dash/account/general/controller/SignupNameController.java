package io.saim.dash.account.general.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.saim.dash.account.general.dto.SignupNameRequestDTO;
import io.saim.dash.account.general.model.SignupName;
import io.saim.dash.account.general.service.SignupNameService;

@RestController
@RequestMapping("/signup")
public class SignupNameController {

	@Autowired
	private SignupNameService signupNameService;

	@PostMapping("/name")
	public ResponseEntity<?> registerUser(@RequestBody SignupNameRequestDTO requestDTO){
		String name = requestDTO.getGeneralName();

		SignupName user = signupNameService.registerUser(name);

		return ResponseEntity.ok(Map.of(
			"status", "success",
			"message", "이름이 저장되었습니다.",
			"data", Map.of(
				"general_id", user.getGeneralId()
			)
		));
	}
}
