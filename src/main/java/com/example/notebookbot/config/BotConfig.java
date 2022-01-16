package com.example.notebookbot.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfig {
    private String name;
    private String token;
    private Set<String> commands;
    private String host;

    public Set<String> getCommands() {
        Set<String> set = new HashSet<>(commands);
        set.addAll(commands.stream()
                        .map(s -> s + '@' + name)
                        .collect(Collectors.toSet())
        );
        return set;
    }
}
