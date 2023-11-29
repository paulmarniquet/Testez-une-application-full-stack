package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeacherServiceTest {

    @Mock
    TeacherRepository teacherRepository;

    @Test
    public void findAll() {
        // Arrange
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher());
        teachers.add(new Teacher());
        teachers.add(new Teacher());
        when(teacherRepository.findAll()).thenReturn(teachers);

        // Act
        TeacherService teacherService = new TeacherService(teacherRepository);
        List<Teacher> foundTeachers = teacherService.findAll();

        // Assert
        assertEquals(3, foundTeachers.size());
    }

    @Test
    public void findById() {
        // Arrange
        Long id = 2L;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        when(teacherRepository.findById(id)).thenReturn(java.util.Optional.of(teacher));

        // Act
        TeacherService teacherService = new TeacherService(teacherRepository);
        Teacher foundTeacher = teacherService.findById(id);

        // Assert
        assertEquals(teacher.getId(), foundTeacher.getId());
    }

}