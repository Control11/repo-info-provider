package pl.unityt.recruitment.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import pl.unityt.recruitment.dto.RepositoryDTO;
import pl.unityt.recruitment.exception.RepositoryNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RepositoryProviderServiceTest {
    private static final String owner = "owner";
    private static final String repositoryName = "repositoryName";
    private static final ZonedDateTime createdAt = ZonedDateTime.of(
            LocalDateTime.of(2023, 8, 17, 12, 1, 2),
            ZoneId.of("+0")
    );
    private final RepositoryProviderService repositoryProviderService = new RepositoryProviderService();
    private MockWebServer mockWebServer;


    @BeforeEach
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();
        String apiUrlMock = mockWebServer.url("/").url().toString();
        repositoryProviderService.setApiUrl(apiUrlMock);
    }

    @Test
    void shouldMakeCorrectRequestToGitHubApi() throws InterruptedException {
        String responseJsonFromExternalApi = """
                {
                    "id": 1296269,
                    "node_id": "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                    "fullName": "fullName",
                    "description": "description",
                    "cloneUrl": "http://cloneUrl",
                    "stars": 0,
                    "createdAt": "2023-08-17T12:01:02Z"
                }
                """;
        RepositoryDTO resultResponseExpected = new RepositoryDTO("fullName", "description", "http://cloneUrl", 0, createdAt);

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(responseJsonFromExternalApi)
        );

        RepositoryDTO resultResponse = repositoryProviderService.getRepository(owner, repositoryName);
        RecordedRequest request = mockWebServer.takeRequest();

        assertEquals(request.getMethod(), "GET");
        assertEquals(request.getPath(), String.format("/repos/%s/%s", owner, repositoryName));
        assertEquals(resultResponse.toString(), resultResponseExpected.toString());
    }

    @Test
    void shouldReturn404ErrorAndThrowRepositoryNotFoundException() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        assertThrows(RepositoryNotFoundException.class,
                () -> repositoryProviderService.getRepository(owner, repositoryName));
    }

}