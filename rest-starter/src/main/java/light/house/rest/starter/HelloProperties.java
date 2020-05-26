package light.house.rest.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "welcome")
public class HelloProperties {
    private static final String DEFAULT_NAME = "xiecf";
    private static final String DEFAULT_LANGUAGE = "java";

    private String name = DEFAULT_NAME;
    private String language = DEFAULT_LANGUAGE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
