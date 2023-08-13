package pl.unityt.recruitment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.unityt.recruitment.dto.RepositoryDTO;
import pl.unityt.recruitment.exception.RepositoryNotFoundException;

@Service
public class RepositoryProviderService {
    private static final String API_URL = "https://api.github.com";
    private final WebClient webClient;

    public RepositoryProviderService() {
        this.webClient = WebClient.builder().baseUrl(API_URL).build();
    }

    public RepositoryDTO getRepository(String owner, String repositoryName) {
        String url = API_URL + "/repos/" + owner + "/" + repositoryName;
        try {
            return webClient.get().uri(url).retrieve().bodyToMono(RepositoryDTO.class).block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RepositoryNotFoundException("Requested repository could not be found", e);
        }
    }
}
