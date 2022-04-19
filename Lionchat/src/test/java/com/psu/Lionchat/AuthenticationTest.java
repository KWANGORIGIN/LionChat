package com.psu.Lionchat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void needsAuthenticationTest() {
		String admin = "http://localhost:" + port + "/administrative";
		try {
			var result = restTemplate.getForObject(admin + "/total-questions-asked", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/questions-asked", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/number-questions-per-topic", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/average-ratings", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/questions-asked", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/number-misclassifications-per-topic", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/number-inappropriate-queries", Object.class);
			assert (false);
		} catch (Exception e) {

		}
		try {
			var result = restTemplate.getForObject(admin + "/inappropriate-queries", Object.class);
			assert (false);
		} catch (Exception e) {

		}
	}
}
