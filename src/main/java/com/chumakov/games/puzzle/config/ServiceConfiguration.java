package com.chumakov.games.puzzle.config;

import com.chumakov.games.puzzle.model.BoardModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
  @Bean
  public BoardModel getBoardModel() {
    return new BoardModel();
  }
}
