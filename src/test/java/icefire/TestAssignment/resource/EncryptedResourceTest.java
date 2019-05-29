package icefire.TestAssignment.resource;


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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import icefire.TestAssignment.domain.Encrypted;
import icefire.TestAssignment.repository.EncryptedRepository;
import icefire.TestAssignment.service.EncryptedService;

/**
 * 
 * @author denizalp@ut.ee
 * <p>API tests</p>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EncryptedResourceTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private EncryptedService service;
	
	@Autowired
	private EncryptedRepository repository;
	
	@Autowired
	private ObjectMapper om;
	
	/**
	 * Encryption with calling API
	 * <ul>
	 * <li>Status must be 200</li>
	 * <li>Response json must have a value for key 'encrypted'
	 * and it is not equal to input text.
	 * </li>
	 * </ul>
	 * @param text going to be encrypted
	 * @return encryption result
	 * @throws Exception
	 */
	private String encryptText(String text) throws Exception{
		Encrypted e = new Encrypted(text);
		MvcResult result = mvc.perform(post("/encrypt")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(e)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.encrypted",not(is(text)))).andReturn();
		return result.getResponse().getContentAsString();
	}
	
	/**
	 * Check if encryption succeeds
	 * <ul>
	 * <li>DB Table size must be increased by 1</li>
	 * </ul>
	 * @throws Exception
	 */
	@Test
	public void test_encryptionSuccessful() throws Exception{
		int before = service.getAllValues().size();
		encryptText("Hello World!");
		int after = service.getAllValues().size();
		assertThat(after-before).isEqualTo(1);
	}
	
	/**
	 * Check if encryption fails because the input is blank
	 * <ul>
	 * <li>Request must return 400</li>
	 * <li>DB Table size must not be changed.</li>
	 * </ul>
	 * @throws Exception
	 */
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
	
	/**
	 * Check if decryption succeeds
	 * <ul>
	 * <li>Request must return 200</li>
	 * <li>Response json must have a value for key 'encrypted'
	 * and it must be equal to initial value that is encrypted for the test.</li>
	 * </ul>
	 * @throws Exception
	 */
	@Test
	public void test_decryptionSuccessful() throws Exception {
		String response = encryptText("Hello World!");
		Encrypted e = om.readValue(response,Encrypted.class);
		String encrypted = e.getEncrypted();
		mvc.perform(get("/decrypt").param("text", encrypted))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.encrypted",is("Hello World!")));
	}
	
	/**
	 * Check if decryption fails because the input text is blank
	 * <ul>
	 * <li>Request must return 400</li>
	 * </ul>
	 * @throws Exception
	 */
	@Test
	public void test_decryptionFailure() throws Exception {
		mvc.perform(get("/decrypt").param("text", "  "))
			.andExpect(status().isBadRequest());
	}
	
	/**
	 * Check if all encrypted values are obtained with the request<br>
	 * Initially 2 encryptions made for testing
	 * <ul>
	 * <li>Request must return 200</li>
	 * <li>Response json array's size must be equal to 2.</li>
	 * <li>Response json array must include the encryption results.</li>
	 * </ul>
	 */
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

	}
}
