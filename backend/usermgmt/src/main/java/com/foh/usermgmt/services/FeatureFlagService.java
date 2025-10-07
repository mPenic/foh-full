package com.foh.usermgmt.services;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.repository.usermgmt.CompanyDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureFlagService {

    @Autowired
    private CompanyDictionaryRepository companyDictionaryRepository;

    public boolean isChatEnabled(Long companyId) {
        return getFlagOrDefault(companyId, "chat");
    }

    public boolean isKanbanEnabled(Long companyId) {
        return getFlagOrDefault(companyId, "kanban");
    }

    public boolean isReservationsEnabled(Long companyId) {
        return getFlagOrDefault(companyId, "reservations");
    }

    /**
     * Generic helper to retrieve the correct flag.
     * 
     * @param companyId the ID of the company
     * @param feature   "chat", "kanban", or "reservations"
     * @return boolean: whether the feature is enabled
     */
    private boolean getFlagOrDefault(Long companyId, String feature) {
        Optional<CompanyDictionaryEntity> maybeCompany = companyDictionaryRepository.findById(companyId);
        if (maybeCompany.isEmpty()) {
            return false; // or throw an exception if the company doesn't exist
        }
        CompanyDictionaryEntity company = maybeCompany.get();

        switch (feature.toLowerCase()) {
            case "chat":
                return company.isFeatCh();
            case "kanban":
                return company.isFeatKb();
            case "reservations":
                return company.isFeatRe();
            default:
                return false;
        }
    }
}
