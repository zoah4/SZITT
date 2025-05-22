package szitt.controller;

import org.springframework.web.bind.annotation.*;
import szitt.service.InstructorSubjectService;
import szitt.model.InstructorSubject;

@RestController
@RequestMapping("/api/instructor-subject")
public class InstructorSubjectController {
    private final InstructorSubjectService service;
    public InstructorSubjectController(InstructorSubjectService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Iterable<InstructorSubject> getAllInstructorSubjects() {
        return this.service.getAllInstructorSubjects();
    }

    @GetMapping("/instructor/{id}")
    public Iterable<InstructorSubject> getInstructorsSubject(@PathVariable Long id) {
        return this.service.getInstructorsSubject(id);
    }

    @GetMapping("/subject/{id}")
    public Iterable<InstructorSubject> getSubjectsInstructor(@PathVariable Long id) {
        return this.service.getSubjectsInstructor(id);
    }

    @GetMapping("/instructor/{instructorId}/subject/{subjectId}")
    public Iterable<InstructorSubject> getInstructorAndSubject(@PathVariable Long instructorId, @PathVariable Long subjectId) {
        return this.service.getInstructorAndSubject(instructorId, subjectId);
    }

    @PostMapping("/instructor/{instructorId}/subject/{subjectId}")
    public void assignSubjectToInstructor(@PathVariable Long instructorId, @PathVariable Long subjectId) {
        this.service.assignSubjectToInstructor(instructorId, subjectId);
    }

    @DeleteMapping("/instructor/{instructorId}/subject/{subjectId}")
    public void removeSubjectFromInstructor(@PathVariable Long instructorId, @PathVariable Long subjectId) {
        this.service.removeSubjectFromInstructor(instructorId, subjectId);
    }
}
