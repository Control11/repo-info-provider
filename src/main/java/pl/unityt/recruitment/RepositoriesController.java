package pl.unityt.recruitment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepositoriesController {

    @GetMapping("/repositories")
    public void repositories() {
    }
}