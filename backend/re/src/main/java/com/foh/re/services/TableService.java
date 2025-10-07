package com.foh.re.services;
import org.springframework.stereotype.Service;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.repository.usermgmt.CompanyDictionaryRepository;
import com.foh.security.CurrUserDetails;
import com.foh.data.entities.re.SectionEntity;
import com.foh.data.entities.re.TableEntity;
import com.foh.data.repository.re.SectionRepository;
import com.foh.data.repository.re.TableRepository;
import com.foh.re.vo.TableVO;

@Service
public class TableService {
    private final TableRepository tableRepository;
    private final SectionRepository sectionRepository;
    private final CompanyDictionaryRepository companyDictionaryRepository;

    public TableService(TableRepository tableRepository,
                        SectionRepository sectionRepository,
                        CompanyDictionaryRepository companyDictionaryRepository) {
        this.tableRepository = tableRepository;
        this.sectionRepository = sectionRepository;
        this.companyDictionaryRepository = companyDictionaryRepository;
    }

    public TableVO createTable(TableVO tableVO, CurrUserDetails currentUser) {
        if (tableVO.getSectionId() == null) {
            throw new IllegalArgumentException("Section is required for creating a table.");
        }

        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableNumber(tableVO.getTableNumber());

        SectionEntity section = sectionRepository.findById(tableVO.getSectionId())
                .orElseThrow(() -> new IllegalArgumentException("Section not found"));
        tableEntity.setSection(section);

        CompanyDictionaryEntity company = companyDictionaryRepository.findById(currentUser.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        tableEntity.setCompany(company);

        return mapToVO(tableRepository.save(tableEntity));
    }

    public TableVO updateTable(Long tableId, TableVO tableVO) {
        TableEntity table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found"));
        table.setTableNumber(tableVO.getTableNumber());

        if (tableVO.getSectionId() != null) {
            SectionEntity section = sectionRepository.findById(tableVO.getSectionId())
                    .orElseThrow(() -> new IllegalArgumentException("Section not found"));
            table.setSection(section);
        }

        return mapToVO(tableRepository.save(table));
    }

    public void deleteTable(Long tableId) {
        if (!tableRepository.existsById(tableId)) {
            throw new IllegalArgumentException("Table not found");
        }
        tableRepository.deleteById(tableId);
    }

    private TableVO mapToVO(TableEntity table) {
        return new TableVO(
                table.getTableId(),
                table.getTableNumber(),
                table.getSection() != null ? table.getSection().getSectionId() : null
        );
    }
}
