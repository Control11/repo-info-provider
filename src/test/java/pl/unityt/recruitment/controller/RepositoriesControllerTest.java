package pl.unityt.recruitment.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.unityt.recruitment.dto.RepositoryDTO;
import pl.unityt.recruitment.exception.RepositoryNotFoundException;
import pl.unityt.recruitment.service.RepositoryProviderService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoriesController.class)
class RepositoriesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RepositoryProviderService repositoryProviderService;
    private static final String owner = "owner";
    private static final String repositoryName = "repositoryName";
    private static final ZonedDateTime createdAt = ZonedDateTime.of(
            LocalDateTime.of(2023, 8, 17, 12, 1, 2),
            ZoneId.of("+0")
    );

    @Test
    void shouldMakeCorrectRequest() throws Exception {
        RepositoryDTO respond = new RepositoryDTO("fullName", "description", "http://cloneUrl", 0, createdAt);

        when(repositoryProviderService.getRepository(eq(owner), eq(repositoryName))).thenReturn(respond);

        mockMvc.perform(get("http://localhost/api/repositories/{owner}/{repositoryName}", owner, repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.fullName").value("fullName"),
                        jsonPath("$.description").value("description"),
                        jsonPath("$.cloneUrl").value("http://cloneUrl"),
                        jsonPath("$.stars").value(0),
                        jsonPath("$.createdAt").value(createdAt.toString()),
                        status().isOk()
                );
    }

    @Test
    void shouldMakeCorrectRequestWithEmptyDescription() throws Exception {
        RepositoryDTO respond = new RepositoryDTO("fullName", null, "http://cloneUrl", 0, createdAt);

        when(repositoryProviderService.getRepository(eq(owner), eq(repositoryName))).thenReturn(respond);

        mockMvc.perform(get("http://localhost/api/repositories/{owner}/{repositoryName}", owner, repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.fullName").value("fullName"),
                        jsonPath("$.description").isEmpty(),
                        jsonPath("$.cloneUrl").value("http://cloneUrl"),
                        jsonPath("$.stars").value(0),
                        jsonPath("$.createdAt").value(createdAt.toString()),
                        status().isOk()
                );
    }

    @Test
    void shouldReturn404ErrorAndThrowRepositoryNotFoundException() throws Exception {
        when(repositoryProviderService.getRepository(any(), any())).thenThrow(new RepositoryNotFoundException("Requested repository could not be found"));

        mockMvc.perform(get("http://localhost/api/repositories/{owner}/{repositoryName}", owner, repositoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()),
                        jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()),
                        jsonPath("$.message").value("Requested repository could not be found"),
                        jsonPath("$.timestamp").exists(),
                        status().isOk()
                );
    }


}