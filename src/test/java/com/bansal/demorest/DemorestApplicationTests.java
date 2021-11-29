package com.bansal.demorest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bansal.demorest.domain.Product;
import com.bansal.demorest.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
class DemorestApplicationTests {

	private MockMvc mockMvc;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),                        
			Charset.forName("utf8")                     
			);
	
	@Autowired
	private ProductService productServiceMock;

	@Test
	void testGetProducts() throws Exception{

		Product honey = new Product();
		honey.setId(1);
		honey.setName("Honey");

		Product almond = new Product();
		almond.setId(2);
		almond.setName("Almond");

		when(productServiceMock.getProducts(1,1)).thenReturn(Arrays.asList(honey));

		mockMvc.perform(get("/getAllProducts"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].name", is("Honey")))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].name", is("Almond")));

		verify(productServiceMock, times(1)).getProducts(1,1);
		verifyNoMoreInteractions(productServiceMock);
	}


}
