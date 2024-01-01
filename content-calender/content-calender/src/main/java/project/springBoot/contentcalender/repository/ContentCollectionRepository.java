package project.springBoot.contentcalender.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import project.springBoot.contentcalender.model.Content;
import project.springBoot.contentcalender.model.Status;
import project.springBoot.contentcalender.model.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentCollectionRepository {
    private final List<Content> content = new ArrayList<>();

    public ContentCollectionRepository(){

    }

    public List<Content> findAll(){
        return content;
    }

    public Optional<Content> findById(int id){
        return content.stream().filter(c -> c.id().equals(id)).findFirst();
    }

    @PostConstruct
    private void init(){
        Content c = new Content(
                1,
                "My first post",
                "First creation",
                Status.IDEA,
                Type.ARTICLE,
                LocalDateTime.now(),
                null,
                ""
        );
        content.add(c);
    }
}
