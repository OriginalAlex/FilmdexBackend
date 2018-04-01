package io.github.originalalex.filmdex.server.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SignIn {

    @NotEmpty
    @NotNull
    private String username;

    @NotEmpty
    @NotNull
    private String password;

    public String getUsername() { return username; }

    public String getPassword() { return password; }

}
