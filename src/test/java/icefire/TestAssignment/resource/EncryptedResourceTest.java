package icefire.TestAssignment.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.repository.EncryptedRepository;
import icefire.TestAssignment.repository.UserRepository;
import icefire.TestAssignment.service.EncryptedService;
import icefire.TestAssignment.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EncryptedResourceTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private EncryptedService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EncryptedRepository repository;
	
	@Autowired
	private ObjectMapper om;
	
	private String encryptText(String text) throws Exception{
		Encrypted e = new Encrypted(text);
		MvcResult result = mvc.perform(post("/encrypt")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(e)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.encrypted",not(is(text)))).andReturn();
		return result.getResponse().getContentAsString();
	}
	
	@Before
	public void insertUser() {
		userService.doesUserExist("user1");
	}
	
	@Test
	public void test_encryptionSuccessful() throws Exception{
		int before = service.getAllValues().size();
		encryptText("Hello World!");
		int after = service.getAllValues().size();
		assertThat(after-before).isEqualTo(1);
	}
	
	@Test
	public void test_encryptionDifferentByUser() throws Exception {
		String enc1 = encryptText("Hello World!");
		userService.doesUserExist("user2");
		String enc2 = encryptText("Hello World!");
		assertThat(enc1).isNotEqualTo(enc2);
		userRepository.deleteById("user2");
	}
	
	@Test
	public void test_encryptionFailure() throws Exception {
		int before = service.getAllValues().size();
		Encrypted e = new Encrypted("  ");
		mvc.perform(post("/encrypt")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(e)))
				.andExpect(status().isBadRequest());
		int after = service.getAllValues().size();
		assertThat(after-before).isEqualTo(0);
	}
	
	@Test
	public void test_decryptionSuccessful() throws Exception {
		String response = encryptText("Hello World!");
		Encrypted e = om.readValue(response,Encrypted.class);
		String encrypted = e.getEncrypted();
		mvc.perform(get("/decrypt").param("text", encrypted))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.encrypted",is("Hello World!")));
	}
	
	@Test
	public void test_decryptionFailure() throws Exception {
		mvc.perform(get("/decrypt").param("text", "  "))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_getAllValuesSuccessful() throws Exception {
		repository.deleteAll();
		String response = encryptText("Hello World!");
		Encrypted e = om.readValue(response,Encrypted.class);
		encryptText("Hello World!");
		
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].encrypted",hasSize(2)))
			.andExpect(jsonPath("$.[*].encrypted",contains(e.getEncrypted(), e.getEncrypted())));
	
		userService.doesUserExist("user2");
		mvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[*].encrypted",hasSize(0)));

	}
}
