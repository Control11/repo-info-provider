package pl.unityt.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDTO {
    @JsonProperty("fullName")
    @JsonAlias("full_name")
    private String fullName;
    @JsonProperty("description")
    @JsonAlias("description")
    private String description;
    @JsonProperty("cloneUrL")
    @JsonAlias("clone_url")
    private String cloneUrL;
    @JsonProperty("stars")
    @JsonAlias("stargazers_count")
    private int stars;
    @JsonProperty("createdAt")
    @JsonAlias("created_at")
    private ZonedDateTime createdAt;
}
