package pl.unityt.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RepositoryDTO {
    @JsonProperty("fullName")
    @JsonAlias("full_name")
    @NotNull
    private String fullName;

    @JsonProperty("description")
    @JsonAlias("description")
    private String description;

    @JsonProperty("cloneUrl")
    @JsonAlias("clone_url")
    @NotNull
    @URL
    private String cloneUrl;

    @JsonProperty("stars")
    @JsonAlias("stargazers_count")
    @NotNull
    private int stars;

    @JsonProperty("createdAt")
    @JsonAlias("created_at")
    @NotNull
    private ZonedDateTime createdAt;
}
