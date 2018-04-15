package org.vijayt.n26.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties
@Component
@Getter
public class ApplicationProperties {
    @Value("${secondsToUseForStats:60}")
    private long secondsToUseForStats;
}
