package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.service.SubjectService;
import szitt.model.Subject;

import java.util.Optional;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService service;
    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<Subject> getAllSubjects() {
        return this.service.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Optional<Subject> getSubject(@PathVariable Long id) {
        return this.service.getSubject(id);
    }

    @PostMapping("/")
    public void createSubject(@RequestParam String name) {
        this.service.createSubject(name);
    }

    @PutMapping("/{id}")
    public void updateSubject(@PathVariable Long id, @RequestParam String name) {
        this.service.updateSubject(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        this.service.deleteSubject(id);
    }

}
