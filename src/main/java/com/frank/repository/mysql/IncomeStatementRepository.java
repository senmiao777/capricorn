package com.frank.repository.mysql;

import com.frank.entity.mysql.IncomeStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.\
 * JpaRepository
 * CrudRepository
 */

@Repository
public interface IncomeStatementRepository extends JpaRepository<IncomeStatement, Long>, JpaSpecificationExecutor<IncomeStatement> {

    @Query(value = "select * from income_statement where ticker= ?1", nativeQuery = true)
    List<IncomeStatement> findByStockCode(String stockCode);
}
