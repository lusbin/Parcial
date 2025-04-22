package co.edu.udes.activity.backend.demo.controllers;

import co.edu.udes.activity.backend.demo.dto.CourseDTO;
import co.edu.udes.activity.backend.demo.models.Course;
import co.edu.udes.activity.backend.demo.services.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses()
                .stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(course -> ResponseEntity.ok(modelMapper.map(course, CourseDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CourseDTO createCourse(@RequestBody CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        Course saved = courseService.saveCourse(course);
        return modelMapper.map(saved, CourseDTO.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        Course updatedCourse = modelMapper.map(courseDTO, Course.class);
        Course updated = courseService.updateCourse(id, updatedCourse);
        return updated != null ?
                ResponseEntity.ok(modelMapper.map(updated, CourseDTO.class)) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteCourse(id);
        return deleted ?
                ResponseEntity.ok("Course deleted successfully") :
                ResponseEntity.status(404).body("Course not found with id: " + id);
    }
}
