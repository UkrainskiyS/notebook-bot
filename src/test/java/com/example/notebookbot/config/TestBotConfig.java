package com.example.notebookbot.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class TestBotConfig {
	@Autowired
	private BotConfig config;

	@Test
	public void testCommands() {
		String patternToken = "[0-9]{10}:[\\w_-]{35}";
		var commands = Set.of(
				"/newnote",     "/newnote@"     + config.getName(),
				"/deletenote",  "/deletenote@"  + config.getName(),
				"/showall",     "/showall@"     + config.getName(),
				"/getnote",     "/getnote@"     + config.getName(),
				"/start",       "/start@"       + config.getName(),
				"/help",        "/help@"        + config.getName(),
				"/getfile",     "/getfile@"     + config.getName(),
				"/editnote",    "/editnote@"     + config.getName()
		);
		assertEquals(config.getCommands(), commands);
		assertTrue(config.getToken().matches(patternToken));
		assertTrue(config.getName().endsWith("bot"));

		log.info("Test config [BotConfig] class completed successfully!");
	}
}
