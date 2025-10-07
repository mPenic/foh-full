package com.foh.re.services;

import org.springframework.stereotype.Service;

import com.foh.core.entities.CompanyDictionaryEntity;
import com.foh.core.repository.CompanyDictionaryRepository;
import com.foh.core.security.CurrUserDetails;
import com.foh.re.entities.SectionEntity;
import com.foh.re.repository.SectionRepository;
import com.foh.re.vo.SectionVO;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final CompanyDictionaryRepository companyDictionaryRepository;

    public SectionService(SectionRepository sectionRepository, CompanyDictionaryRepository companyDictionaryRepository) {
        this.sectionRepository = sectionRepository;
        this.companyDictionaryRepository = companyDictionaryRepository;
    }

    public SectionVO createSection(SectionVO sectionVO, CurrUserDetails currentUser) {
        SectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setSectionNumber(sectionVO.getSectionNumber());
        sectionEntity.setIsSpecial(sectionVO.getIsSpecial());

        CompanyDictionaryEntity company = companyDictionaryRepository.findById(currentUser.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        sectionEntity.setCompany(company);

        return mapToVO(sectionRepository.save(sectionEntity));
    }

    public SectionVO updateSection(Long sectionId, SectionVO sectionVO) {
        SectionEntity section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section not found"));
        section.setSectionNumber(sectionVO.getSectionNumber());
        section.setIsSpecial(sectionVO.getIsSpecial());
        return mapToVO(sectionRepository.save(section));
    }

    public void deleteSection(Long sectionId) {
        if (!sectionRepository.existsById(sectionId)) {
            throw new IllegalArgumentException("Section not found");
        }
        sectionRepository.deleteById(sectionId);
    }

    private SectionVO mapToVO(SectionEntity section) {
        return new SectionVO(section.getSectionId(), section.getSectionNumber(), section.getIsSpecial());
    }
}
