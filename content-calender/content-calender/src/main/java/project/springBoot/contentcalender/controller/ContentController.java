package project.springBoot.contentcalender.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import project.springBoot.contentcalender.model.Content;
import project.springBoot.contentcalender.repository.ContentCollectionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    private final ContentCollectionRepository repository;

    //for one contructor there is no need of @Autowired annotaion, it will do it by itself.
    public ContentController(ContentCollectionRepository repository) {
        this.repository = repository;
    }

    //make a request and find all the pices of the content.
    @GetMapping("")
    public List<Content> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Content findById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContentNotFound"));
    }

}
