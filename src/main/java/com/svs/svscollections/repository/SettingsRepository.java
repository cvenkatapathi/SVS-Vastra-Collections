package com.svs.svscollections.repository;

import com.svs.svscollections.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Settings findFirstByOrderByIdAsc();
}