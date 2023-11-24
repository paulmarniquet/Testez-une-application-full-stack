package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import org.junit.jupiter.api.Test;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeacherControllerTest {

    @Mock
    TeacherMapper teacherMapper;

    @Mock
    TeacherService teacherService;


    @Test
    public void getById() {
        // Arrange
        Long id = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(id);
        when(teacherService.findById(id)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        // Act
        TeacherController teacherController = new TeacherController(teacherService, teacherMapper);
        ResponseEntity<?> response = teacherController.findById(id.toString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), teacherDto);
    }

    @Test
    public void getAll() {
        // Arrange
        List<Teacher> teacher = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("TeacherUno");
        teacher.add(teacher1);

        List<TeacherDto> teacherDto = new ArrayList<>();
        TeacherDto teacherDto1 = new TeacherDto();
        teacherDto1.setFirstName("TeacherUno");
        teacherDto.add(teacherDto1);

        when(teacherService.findAll()).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        // Act
        TeacherController teacherController = new TeacherController(teacherService, teacherMapper);
        ResponseEntity<?> response = teacherController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), teacherDto);
    }
}