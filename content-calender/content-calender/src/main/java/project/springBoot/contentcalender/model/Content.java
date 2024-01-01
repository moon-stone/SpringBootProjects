package project.springBoot.contentcalender.model;

import java.time.LocalDateTime;

public record Content(
        Integer id,
        String title,
        String desc,
        Status status,
        Type typeContent,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        String url
) {
}
