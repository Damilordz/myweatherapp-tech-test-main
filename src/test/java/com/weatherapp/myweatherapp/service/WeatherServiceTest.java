package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// import java.time.LocalTime;
// import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

  private WeatherService weatherService;
  private VisualcrossingRepository mockRepository;

  @BeforeEach
  void setUp() {
    mockRepository = Mockito.mock(VisualcrossingRepository.class);
    weatherService = new WeatherService();
    weatherService.weatherRepo = mockRepository; // Inject mock repository
  }

  @Test
  void testCompareDaylightHours() {
    // Mock data for city1 (Shorter daylight)
    CityInfo city1 = new CityInfo();
    CityInfo.CurrentConditions conditions1 = new CityInfo.CurrentConditions();
    conditions1.setSunrise("06:00");
    conditions1.setSunset("17:00"); // Sunset is at 17:00 (earlier sunset)
    city1.setCurrentConditions(conditions1);

    // Mock data for city2 (Longer daylight)
    CityInfo city2 = new CityInfo();
    CityInfo.CurrentConditions conditions2 = new CityInfo.CurrentConditions();
    conditions2.setSunrise("07:00");
    conditions2.setSunset("19:00"); // Sunset is at 19:00 (later sunset)
    city2.setCurrentConditions(conditions2);

    // Mock repository behavior
    when(mockRepository.getByCity("City1")).thenReturn(city1);
    when(mockRepository.getByCity("City2")).thenReturn(city2);

    // Call the method
    String result = weatherService.compareDaylightHours("City1", "City2");

    // Verify the result
    assertEquals("City2 has longer daylight hours (720 minutes)", result);
  }

  @Test
  void testCheckRainingCities() {
    // Mock city1 with rain
    CityInfo city1 = new CityInfo();
    CityInfo.CurrentConditions conditions1 = new CityInfo.CurrentConditions();
    conditions1.setConditions("Rain");
    city1.setCurrentConditions(conditions1);

    // Mock city2 with clear weather
    CityInfo city2 = new CityInfo();
    CityInfo.CurrentConditions conditions2 = new CityInfo.CurrentConditions();
    conditions2.setConditions("Clear");
    city2.setCurrentConditions(conditions2);

    // Define behavior of the mock repository
    when(mockRepository.getByCity("City1")).thenReturn(city1);
    when(mockRepository.getByCity("City2")).thenReturn(city2);

    // Call the method
    String result = weatherService.checkRainingCities("City1", "City2");

    // Verify the result
    assertEquals("It is currently raining in City1", result);
  }
}
