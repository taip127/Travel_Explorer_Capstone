package com.tai.travel2.service;

import com.tai.travel2.model.Attraction;
import com.tai.travel2.repository.AttractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class AttractionServiceTest {

    @Mock
    private AttractionRepository attractionRepository;

    @InjectMocks
    private AttractionService attractionService;

    private Attraction attraction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attraction = new Attraction();
        attraction.setId(1L);
        attraction.setName("Austin Zoo");
        attraction.setDescription("A fun zoo in Austin");
        attraction.setPrice(15.99);
        attraction.setLocation("Austin, TX");
        attraction.setAvailableTickets(100);
        attraction.setImgUrl("/image/austin-zoo.jpg");
        attraction.setLinkUrl("https://austinzoo.org");
    }

    @Test
    void getAllAttractions_ReturnsListOfAttractions() {
        // Arrange
        List<Attraction> attractions = Arrays.asList(attraction);
        when(attractionRepository.findAll()).thenReturn(attractions);

        // Act
        List<Attraction> result = attractionService.getAllAttractions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(attraction.getName(), result.get(0).getName());
        verify(attractionRepository, times(1)).findAll();
    }

    @Test
    void getAttractionById_ValidId_ReturnsAttraction() {
        // Arrange
        when(attractionRepository.findById(1L)).thenReturn(Optional.of(attraction));

        // Act
        Optional<Attraction> result = attractionService.getAttractionById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(attraction.getId(), result.get().getId());
        verify(attractionRepository, times(1)).findById(1L);
    }

    @Test
    void getAttractionById_InvalidId_ReturnsEmptyOptional() {
        // Arrange
        when(attractionRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Optional<Attraction> result = attractionService.getAttractionById(2L);

        // Assert
        assertFalse(result.isPresent());
        verify(attractionRepository, times(1)).findById(2L);
    }

    @Test
    void createAttraction_SavesAndReturnsAttraction() {
        // Arrange
        when(attractionRepository.save(attraction)).thenReturn(attraction);

        // Act
        Attraction result = attractionService.createAttraction(attraction);

        // Assert
        assertNotNull(result);
        assertEquals(attraction.getName(), result.getName());
        assertEquals(attraction.getImgUrl(), result.getImgUrl());
        assertEquals(attraction.getLinkUrl(), result.getLinkUrl());
        verify(attractionRepository, times(1)).save(attraction);
    }

    @Test
    void updateAttraction_InvalidId_ThrowsException() {
        // Arrange
        Attraction updatedAttraction = new Attraction();
        when(attractionRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> attractionService.updateAttraction(2L, updatedAttraction));
        verify(attractionRepository, times(1)).findById(2L);
        verify(attractionRepository, never()).save(any(Attraction.class));
    }

    @Test
    void deleteAttraction_ValidId_DeletesAttraction() {
        // Act
        attractionService.deleteAttraction(1L);

        // Assert
        verify(attractionRepository, times(1)).deleteById(1L);
    }
}