package myProject.service.cookie.repository;

import myProject.service.cookie.entity.CookieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CookieRepository 인터페이스는 CookieEntity 관리를 위해 JpaRepository를 확장합니다.
 */
public interface CookieRepository extends JpaRepository<CookieEntity, String> {

    /**
     * findByTimestampBefore(LocalDateTime timestamp):
     * 지정된 시간 이전에 생성된 모든 CookieEntity 인스턴스를 찾아 리스트로 반환합니다.
     */
    List<CookieEntity> findByTimestampBefore(LocalDateTime timestamp);

    /**
     * deleteByTimestampBefore(LocalDateTime expiryDate)}:
     * 지정된 만료 날짜 이전의 CookieEntity 인스턴스를 삭제하고, 삭제된 인스턴스 수를 반환합니다.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM CookieEntity c WHERE c.timestamp < :expiryDate")
    int deleteByTimestampBefore(LocalDateTime expiryDate);

}
