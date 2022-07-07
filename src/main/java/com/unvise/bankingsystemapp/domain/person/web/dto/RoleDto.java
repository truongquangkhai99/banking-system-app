package com.unvise.bankingsystemapp.domain.person.web.dto;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.person.role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    @Null(message = "id must be null", groups = {View.New.class, View.Update.class})
    private Long id;

    @NotNull(message = "role must be not null and have valid type", groups = {View.New.class, View.Update.class})
    private RoleType role;

}
