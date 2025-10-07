package com.foh.re.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.foh.re.services.TableService;
import com.foh.re.vo.TableVO;

@Controller
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @MessageMapping("/table/create")
    @SendTo("/topic/table")
    public TableVO createTable(TableVO tableVO) {
        return tableService.createTable(tableVO, null);
    }

    @MessageMapping("/table/update")
    @SendTo("/topic/table")
    public TableVO updateTable(Long tableId, TableVO tableVO) {
        return tableService.updateTable(tableId, tableVO);
    }

    @MessageMapping("/table/delete")
    @SendTo("/topic/table")
    public String deleteTable(Long tableId) {
        tableService.deleteTable(tableId);
        return "Table deleted: " + tableId;
    }
}
