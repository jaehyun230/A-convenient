package myProject.service.Scraper.repository;

import myProject.service.Scraper.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface WeatherRepository extends JpaRepository<Weather, Timestamp> {
}
