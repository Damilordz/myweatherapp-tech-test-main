# MyWeather App Tech Test

## Overview

MyWeather App is a Java-based weather application built with **Spring Boot**. It fetches real-time weather data using the **Visual Crossing Weather API** and provides insights on **daylight duration comparison** and **rain status** between two cities.

This project was completed as part of a tech test, implementing the required features along with exception handling and unit testing.

---

## 🚀 **Features Implemented**
1. **Daylight Hours Comparison**
   - Compares the daylight duration (sunrise to sunset) of two cities and determines which city has a longer day.

2. **Rain Check**
   - Checks which city is currently experiencing rain.

3. **Exception Handling**
   - Ensures proper error handling for invalid cities, API failures, and incorrect inputs.

4. **Unit Testing**
   - `WeatherServiceTest.java` includes tests to validate the accuracy of daylight comparison and rain check functionality.

---

## 📂 **Project Structure**
```
myweatherapp-tech-test-main/
│── src/
│   ├── main/java/com/weatherapp/myweatherapp/
│   │   ├── controller/       # API Endpoints (WeatherController.java)
│   │   ├── service/          # Business logic (WeatherService.java)
│   │   ├── model/            # Data models (CityInfo.java)
│   │   ├── repository/       # API connection (VisualcrossingRepository.java)
│   ├── test/java/com/weatherapp/myweatherapp/
│   │   ├── service/          # Unit tests (WeatherServiceTest.java)
│── pom.xml                   # Maven dependencies
│── README.md                 # Documentation
│── application.properties     # API key configuration
```

---

## 🛠 **Installation & Setup**
### **Prerequisites**
Ensure you have the following installed:
- [Java SDK 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven 3.6.3+](https://maven.apache.org/install.html)
- [Visual Crossing Weather API Key](https://www.visualcrossing.com/weather-data-editions)

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/Damilordz/myweatherapp-tech-test-main.git
cd myweatherapp-tech-test
```

### **2️⃣ Checkout to the Branch**
```sh
git checkout feature/weather-comparison
```

### **3️⃣ Add API Key**
- Register for an API key at **Visual Crossing Weather API**.
- Add it to `src/main/resources/application.properties`:
  ```
  weather.visualcrossing.key=YOUR_API_KEY_HERE
  ```

### **4️⃣ Build the Project**
```sh
mvn clean install
```

### **5️⃣ Run the Application**
```sh
mvn spring-boot:run
```
The app should now be running on **http://localhost:8080**.

---

## 🔗 **API Endpoints**
### **1️⃣ Get Weather Forecast for a City**
- **URL:** `GET /forecast/{city}`
- **Example Request:**
  ```
  GET http://localhost:8080/forecast/London
  ```
- **Response:**
  ```json
  {
    "address": "London",
    "description": "Similar temperatures continuing with a chance of rain tomorrow.",
    "currentConditions": {
      "temp": "38.8",
      "sunrise": "07:23:25",
      "sunset": "17:06:55",
      "feelslike": "35.2",
      "humidity": "87.9",
      "conditions": "Overcast"
    }
  }
  ```

---

### **2️⃣ Compare Daylight Hours Between Two Cities**
- **URL:** `GET /compare-daylight/{city1}/{city2}`
- **Example Request:**
  ```
  GET http://localhost:8080/compare-daylight/London/Paris
  ```
- **Response:**
  ```json
  "Paris has longer daylight hours (720 minutes)"
  ```

---

### **3️⃣ Check Which City is Raining**
- **URL:** `GET /check-rain/{city1}/{city2}`
- **Example Request:**
  ```
  GET http://localhost:8080/check-rain/London/NewYork
  ```
- **Response:**
  ```json
  "It is currently raining in New York"
  ```

---

## ✅ **Unit Testing**
This project includes **JUnit tests** to validate the logic in `WeatherService.java`.

### **Run Tests**
```sh
mvn test
```
### **Example Test Case (`WeatherServiceTest.java`):**
```java
@Test
void testCompareDaylightHours() {
    CityInfo city1 = new CityInfo();
    CityInfo.CurrentConditions conditions1 = new CityInfo.CurrentConditions();
    conditions1.setSunrise("06:00");
    conditions1.setSunset("17:00");
    city1.setCurrentConditions(conditions1);

    CityInfo city2 = new CityInfo();
    CityInfo.CurrentConditions conditions2 = new CityInfo.CurrentConditions();
    conditions2.setSunrise("07:00");
    conditions2.setSunset("19:00");
    city2.setCurrentConditions(conditions2);

    when(mockRepository.getByCity("City1")).thenReturn(city1);
    when(mockRepository.getByCity("City2")).thenReturn(city2);

    String result = weatherService.compareDaylightHours("City1", "City2");
    assertEquals("City2 has longer daylight hours (720 minutes)", result);
}
```

---


### ✅ **Error Handling**
Implemented error handling for:
- **Invalid city names** (API response failure)
- **Incorrect user inputs**
- **Network issues with API calls**

