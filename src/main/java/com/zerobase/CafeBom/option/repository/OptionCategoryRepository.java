package com.zerobase.CafeBom.option.repository;

import com.zerobase.CafeBom.option.domain.entity.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Long> {
}
