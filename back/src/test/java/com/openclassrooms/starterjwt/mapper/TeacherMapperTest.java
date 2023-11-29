package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeacherMapperTest {

    private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    private TeacherDto teacherDto;
    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        // Arrange
        teacherDto = new TeacherDto();
        teacherDto.setFirstName("Helene");

        teacher = new Teacher();
        teacher.setFirstName("Helene");
    }


    @Test
    public void testToEntity() {
        // Act
        Teacher teacherEntity = teacherMapper.toEntity(teacherDto);

        // Assert
        assertEquals(teacher, teacherEntity);
    }

    @Test
    public void testToDto() {
        // Act
        TeacherDto teacherDtoEntity = teacherMapper.toDto(teacher);

        // Assert
        assertEquals(teacherDto, teacherDtoEntity);
    }

    @Test
    public void testToDtoList() {
        // Arrange
        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        teacherDtoList.add(teacherDto);

        // Act
        List<TeacherDto> teacherDtoListMapped = teacherMapper.toDto(teacherList);

        // Assert
        assertEquals(teacherDtoList, teacherDtoListMapped);
    }

    @Test
    public void testToEntityList() {
        // Arrange
        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        teacherDtoList.add(teacherDto);

        // Act
        List<Teacher> teacherListMapped = teacherMapper.toEntity(teacherDtoList);

        // Assert
        assertEquals(teacherList, teacherListMapped);
    }
}

