package ru.romchela.kafkastreams.crypto.configuration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.romchela.kafkastreams.crypto.dto.SourceWeight;

@ConfigurationProperties
public class SourcesProperties {

    private List<SourceWeight> sources;

    public List<SourceWeight> getSources() {
        return sources;
    }

    public void setSources(List<SourceWeight> sources) {
        this.sources = sources;
    }
}
