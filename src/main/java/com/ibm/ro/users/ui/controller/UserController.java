package com.ibm.ro.users.ui.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.ro.users.service.JWTUtil;
import com.ibm.ro.users.service.UserService;
import com.ibm.ro.users.ui.dto.UserDTO;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	JWTUtil jwtUtil;

	@DeleteMapping("/{email}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or principal == #email")
	public ResponseEntity deleteUser(@PathVariable("email") String email) {
		userService.deleteUser(email);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping(path = "/{email}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDTO> getUserByName(@PathVariable("email") String email) {

		UserDTO userByName = userService.getUserByEmail(email);
		return new ResponseEntity<UserDTO>(userByName, HttpStatus.OK);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

		return new ResponseEntity<UserDTO>(userService.createUser(userDTO), HttpStatus.OK);
	}

	@PostMapping(path = "/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws Exception {
		Object claims = request.getAttribute("claims");
		Map<String, String> outMap = jwtUtil.doGenerateRefreshToken(claims);
		httpServletResponse.addHeader("token", outMap.get("token"));
		httpServletResponse.addHeader("userId", outMap.get("userId"));
		return new ResponseEntity(HttpStatus.OK);
	}
}
