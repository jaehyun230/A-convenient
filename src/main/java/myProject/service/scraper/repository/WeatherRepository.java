package myProject.service.scraper.repository;

import myProject.service.scraper.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface WeatherRepository extends JpaRepository<Weather, Timestamp> {
}
