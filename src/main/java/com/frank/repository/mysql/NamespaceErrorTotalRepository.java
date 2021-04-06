package com.frank.repository.mysql;

import com.frank.entity.mysql.NamespaceErrorTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NamespaceErrorTotalRepository extends JpaRepository<NamespaceErrorTotal, Long>, JpaSpecificationExecutor<NamespaceErrorTotal> {

    @Query(value = "select * from namespace_error_total where dt= ?1", nativeQuery = true)
    List<NamespaceErrorTotal> findByDate(int date);

}
