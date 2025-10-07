package com.foh.usermgmt.dto;

public record UserSummaryDto(
    Long   userId,
    String username,
    Long   companyId,
    String companyName,
    Long   roleId,
    String roleName
) {}
