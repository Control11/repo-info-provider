package pl.unityt.recruitment.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.unityt.recruitment.dto.RepositoryDTO;
import pl.unityt.recruitment.exception.RepositoryNotFoundException;
import reactor.core.publisher.Mono;

@Service
public class RepositoryProviderService {
    private static final String API_URL = "https://api.github.com";
    private final WebClient webClient;

    public RepositoryProviderService() {
        this.webClient = WebClient.create(API_URL);
    }

    public RepositoryDTO getRepository(String owner, String repositoryName) {
        String url = API_URL + "/repos/" + owner + "/" + repositoryName;
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new RepositoryNotFoundException("Requested repository could not be found")))
                .bodyToMono(RepositoryDTO.class)
                .block();
    }
}
