package ch.georgiou.util.aws.ip.common;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final Properties properties;

    @Inject
    public Config() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public String getProperty(@Nonnull String key) {
        return properties.getProperty(key);
    }
}
