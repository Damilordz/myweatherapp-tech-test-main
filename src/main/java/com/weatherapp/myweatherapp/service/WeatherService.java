package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public String compareDaylightHours(String city1, String city2) {
    try {
      CityInfo city1Info = weatherRepo.getByCity(city1);
      CityInfo city2Info = weatherRepo.getByCity(city2);

      if (city1Info == null || city2Info == null) {
        return "Error: One or both cities were not found.";
      }

      if (city1Info.getCurrentConditions() == null || city2Info.getCurrentConditions() == null) {
        return "Error: Weather data is missing for one or both cities.";
      }

      // Parse sunrise and sunset times
      LocalTime city1Sunrise = LocalTime.parse(city1Info.getCurrentConditions().getSunrise());
      LocalTime city1Sunset = LocalTime.parse(city1Info.getCurrentConditions().getSunset());
      long city1Minutes = ChronoUnit.MINUTES.between(city1Sunrise, city1Sunset);

      LocalTime city2Sunrise = LocalTime.parse(city2Info.getCurrentConditions().getSunrise());
      LocalTime city2Sunset = LocalTime.parse(city2Info.getCurrentConditions().getSunset());
      long city2Minutes = ChronoUnit.MINUTES.between(city2Sunrise, city2Sunset);

      if (city1Minutes > city2Minutes) {
        return city1 + " has longer daylight hours (" + city1Minutes + " minutes)";
      } else if (city2Minutes > city1Minutes) {
        return city2 + " has longer daylight hours (" + city2Minutes + " minutes)";
      } else {
        return "Both cities have the same daylight hours (" + city1Minutes + " minutes)";
      }
    } catch (Exception e) {
      return "Error: Unable to compare daylight hours due to " + e.getMessage();
    }
  }

  public String checkRainingCities(String city1, String city2) {
    try {
      CityInfo city1Info = weatherRepo.getByCity(city1);
      CityInfo city2Info = weatherRepo.getByCity(city2);

      if (city1Info == null || city2Info == null) {
        return "Error: One or both cities were not found.";
      }

      if (city1Info.getCurrentConditions() == null || city2Info.getCurrentConditions() == null) {
        return "Error: Weather data is missing for one or both cities.";
      }

      String conditions1 = city1Info.getCurrentConditions().getConditions();
      String conditions2 = city2Info.getCurrentConditions().getConditions();

      if (conditions1 == null || conditions2 == null) {
        return "Error: Weather conditions are missing for one or both cities.";
      }

      conditions1 = conditions1.toLowerCase();
      conditions2 = conditions2.toLowerCase();

      boolean isRainingCity1 = conditions1.contains("rain");
      boolean isRainingCity2 = conditions2.contains("rain");

      if (isRainingCity1 && isRainingCity2) {
        return "It is currently raining in both " + city1 + " and " + city2;
      } else if (isRainingCity1) {
        return "It is currently raining in " + city1;
      } else if (isRainingCity2) {
        return "It is currently raining in " + city2;
      } else {
        return "It is not raining in either " + city1 + " or " + city2;
      }
    } catch (Exception e) {
      return "Error: Unable to check rain status due to " + e.getMessage();
    }
  }
}
