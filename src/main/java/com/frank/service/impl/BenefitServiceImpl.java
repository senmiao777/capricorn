package com.frank.service.impl;

import com.frank.entity.mysql.Benefit;
import com.frank.repository.mysql.BenefitRepository;
import com.frank.service.IBenefitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/15 0015 下午 12:53
 */
@Service
@Slf4j
public class BenefitServiceImpl implements IBenefitService {
    @Autowired
    private BenefitRepository benefitRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public Benefit save(Benefit benefit) {
        return benefitRepository.save(benefit);
    }
}
