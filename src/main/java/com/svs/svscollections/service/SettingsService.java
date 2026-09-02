package com.svs.svscollections.service;

import com.svs.svscollections.model.Settings;
import com.svs.svscollections.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    // Get existing settings
    public Settings getSettings() {
        return settingsRepository.findFirstByOrderByIdAsc();
    }

    // Save or update settings
    public Settings saveSettings(Settings newSettings) {

        Settings existingSettings = getSettings();

        // If no settings record exists, create one
        if (existingSettings == null) {
            return settingsRepository.save(newSettings);
        }

        // Update existing record
        existingSettings.setStoreName(newSettings.getStoreName());
        existingSettings.setEmail(newSettings.getEmail());
        existingSettings.setPhone(newSettings.getPhone());
        existingSettings.setAddress(newSettings.getAddress());
        existingSettings.setAdminPassword(newSettings.getAdminPassword());
        existingSettings.setLogo(newSettings.getLogo());

        return settingsRepository.save(existingSettings);
    }
}