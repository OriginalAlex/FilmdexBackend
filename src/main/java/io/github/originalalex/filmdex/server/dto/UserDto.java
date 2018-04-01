package io.github.originalalex.filmdex.server.dto;

import io.github.originalalex.filmdex.server.annotations.ValidEmail;
import io.github.originalalex.filmdex.server.annotations.ValidPassword;
import io.github.originalalex.filmdex.server.annotations.ValidUsername;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    @NotEmpty
    @ValidUsername
    private String username;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }
}
