package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.info.BuildProperties;

import net.mpqdata.app.mpqdataapi.test.junit.ClassNameDisplayNameGenerator;
import net.mpqdata.app.mpqdataapi.web.api.rest.RestApiVersionHolder;

@DisplayNameGeneration(ClassNameDisplayNameGenerator.class)
@ExtendWith(MockitoExtension.class)
class HelloRestControllerTests {

	private static final String GROUP_ID = "group.id";
	private static final String ARTIFACT_ID = "artifactId";
	private static final String VERSION = "1.0.0";

	@Mock
	private BuildProperties buildProperties;

	@Mock
	private HttpServletRequest request ;


	@Nested
	class Hello {

		@Test
		void returnsMapWithBuildInfoAndDate() {
			Date now = new Date();
			doReturn(GROUP_ID).when(buildProperties).getGroup();
			doReturn(ARTIFACT_ID).when(buildProperties).getArtifact();
			doReturn(VERSION).when(buildProperties).getVersion();
			RestApiVersionHolder.setRestApiVersion("1");

			HelloRestController controller = new HelloRestController();
			controller.setBuildProperties(buildProperties);

			Map<String, ?> result = controller.hello(request);

			assertAll(
				"Invalid Map Returned",
				() -> assertNotNull(result, "map is null"),
				() -> assertFalse(result.isEmpty(), "map is empty"),
				() -> assertEquals(GROUP_ID, result.get("group"), "Mismatched group"),
				() -> assertEquals(ARTIFACT_ID, result.get("artifact"), "Mismatched artifact"),
				() -> assertEquals(VERSION, result.get("version"), "Mismatched version"),
				() -> assertEquals("1", result.get("apiVersion"), "Mismatched API versions"),
				() -> assertTrue( now.before( (Date) result.get("now") ), "Unexpected date")
			);
		}

	}

}
