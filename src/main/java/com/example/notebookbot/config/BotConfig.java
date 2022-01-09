package com.example.notebookbot.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
    private Set<String> commands;
}
