package org.codemash.runnerz.ui.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Run {
    private Integer id;
    private String title;
    private LocalDateTime startedOn = LocalDateTime.now();
    private LocalDateTime completedOn = LocalDateTime.now();
    private Integer miles;
    private Location location;
}
