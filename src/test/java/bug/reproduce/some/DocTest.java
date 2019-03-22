package bug.reproduce.some;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;

@ExtendWith(SpringExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class DocTest {

	@Autowired
	ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeEach
	void setup(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
			.alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
				Preprocessors.preprocessRequest(),
				Preprocessors.preprocessResponse(
					ResponseModifyingPreprocessors.replaceBinaryContent(),
					Preprocessors.prettyPrint())))
			.apply(documentationConfiguration(restDocumentation)
				.uris()
				.withScheme("http")
				.withHost("some.host.eu")
				.withPort(80)
				.and().snippets()
				.withDefaults(CliDocumentation.curlRequest(),
					HttpDocumentation.httpRequest(),
					HttpDocumentation.httpResponse(),
					AutoDocumentation.requestFields().failOnUndocumentedFields(true),
					AutoDocumentation.responseFields().failOnUndocumentedFields(true),
					AutoDocumentation.pathParameters(),
					AutoDocumentation.requestParameters(),
					AutoDocumentation.description(),
					AutoDocumentation.methodAndPath(),
					AutoDocumentation.section()))
			.build();
	}

	@Test
	void hello() throws Exception {
		mockMvc.perform(
			get("/hello")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.someMessage", equalTo("Hello Bug")))
		;
	}
}
