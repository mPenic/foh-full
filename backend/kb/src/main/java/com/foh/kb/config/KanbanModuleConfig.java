package com.foh.kb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "kb")
public class KanbanModuleConfig {

    // Define any beans specific to Kanban module here
    // e.g. @Bean public SomeKanbanSpecificBean someKanbanSpecificBean() { ... }

}