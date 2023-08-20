package pl.unityt.recruitment.service;

import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.unityt.recruitment.dto.RepositoryDTO;
import pl.unityt.recruitment.exception.RepositoryNotFoundException;
import reactor.core.publisher.Mono;

@Service
@Setter
public class RepositoryProviderService {
    private String apiUrl;
    private final WebClient webClient;

    public RepositoryProviderService() {
        this.webClient = WebClient.create();
        this.apiUrl = "https://api.github.com";
    }

    public RepositoryDTO getRepository(String owner, String repositoryName) {
        String url = apiUrl + "/repos/" + owner + "/" + repositoryName;
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new RepositoryNotFoundException("Requested repository could not be found")))
                .bodyToMono(RepositoryDTO.class)
                .block();
    }
}
