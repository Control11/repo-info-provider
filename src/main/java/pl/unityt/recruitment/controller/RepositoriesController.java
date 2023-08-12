package pl.unityt.recruitment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.unityt.recruitment.dto.RepositoryDto;
import pl.unityt.recruitment.service.RepositoryProviderService;

@RestController
public class RepositoriesController {
    private final RepositoryProviderService repositoryProviderService;

    public RepositoriesController(final RepositoryProviderService repositoryProviderService) {
        this.repositoryProviderService = repositoryProviderService;
    }

    @GetMapping("/repositories/{owner}/{repositoryName}")
    public RepositoryDto repositories(@PathVariable final String owner, @PathVariable final String repositoryName) {
       return repositoryProviderService.getRepository(owner, repositoryName);
    }
}