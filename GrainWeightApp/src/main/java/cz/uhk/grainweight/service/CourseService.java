package cz.uhk.grainweight.service;

import cz.uhk.grainweight.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    List<Course> getAllCourses();
    void saveCourse(Course course);
    Course getCourse(long id);
    void deleteCourse(long id);
}
