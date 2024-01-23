package myProject.service.scraper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WEATHER_TABLE")
public class Weather {

    @Id
    @Column(name = "WeatherRegion")
    private String Region;

    @Column(name = "WeatherTimestamp")
    private LocalDateTime Timestamp;

    @Column(name = "WeatherState")
    private String WeatherStateNow;

    @Column(name = "Temperature")
    private long Temperature;

    @Column(name = "Country")
    private String Country;

    @Column(name = "WeatherStamp")
    private String WeatherImg;

}
