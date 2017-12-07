package com.frank.repository.mysql;

import com.frank.entity.mysql.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/4/24 0024.\
 *JpaRepository
 *CrudRepository
 */

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {



}
