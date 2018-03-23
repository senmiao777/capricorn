package com.frank.repository.mysql;

import com.frank.entity.mysql.BlindDateComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018年3月20日20:15:53
 */
@Repository
public interface BlindDateCommentRepository extends JpaRepository<BlindDateComment, Long> {

    @Query(value = "SELECT * from blind_date_comment where area != ?1", nativeQuery = true)
    List<BlindDateComment> findByAreaNot(String area);

    @Query(value = "SELECT * from blind_date_comment where requirement = 'requirement'", nativeQuery = true)
    List<BlindDateComment> modifyEdu();

}
