package myProject.service.cookie.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "COOKIE_TABLE")
public class CookieEntity {

    /**
     * cookie unique Id 값
     */
    @Id
    @Column(name = "cookie_value")
    private String cookieValue;

    /**
     * cookie 기간 만료시 체크 하기 위한 부분
     */
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    /**
     * 사용자가 선택한 target
     */
    @Column(name = "target")
    private String target;

}