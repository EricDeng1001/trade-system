package ai.techfin.tradesystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties specific to Trade System.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String author;

    private String authorAs;

    public String getAuthor() {
        return author;
    }

    public String getAuthorAs() {
        return authorAs;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorAs(String authorAs) {
        this.authorAs = authorAs;
    }

}
