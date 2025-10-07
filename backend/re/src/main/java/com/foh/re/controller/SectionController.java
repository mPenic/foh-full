package com.foh.re.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.foh.re.services.SectionService;
import com.foh.re.vo.SectionVO;

@Controller
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @MessageMapping("/section/create")
    @SendTo("/topic/section")
    public SectionVO createSection(SectionVO sectionVO) {
        return sectionService.createSection(sectionVO, null);
    }

    @MessageMapping("/section/update")
    @SendTo("/topic/section")
    public SectionVO updateSection(Long sectionId, SectionVO sectionVO) {
        return sectionService.updateSection(sectionId, sectionVO);
    }

    @MessageMapping("/section/delete")
    @SendTo("/topic/section")
    public String deleteSection(Long sectionId) {
        sectionService.deleteSection(sectionId);
        return "Section deleted: " + sectionId;
    }
}
