package project.springBoot.contentcalender.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.springBoot.contentcalender.model.Content;
import project.springBoot.contentcalender.repository.ContentCollectionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
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

    @ResponseStatus(HttpStatus.CREATED) //used to show that something created
    @PostMapping("")
    public void createContent(@Valid @RequestBody Content content){
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Content content, @PathVariable Integer id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "content not found");
        }
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        repository.delete(id);
    }
}
