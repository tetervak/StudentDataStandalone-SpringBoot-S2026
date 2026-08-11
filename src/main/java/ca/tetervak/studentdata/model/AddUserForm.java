package ca.tetervak.studentdata.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddUserForm {

    @NotBlank
    private String username = "";

    @NotBlank
    private String password = "";

    @NotBlank
    private String firstName = "";

    @NotBlank
    private String lastName = "";

    @NotNull
    private Boolean sysAdmin = false;

    @NotNull
    private Boolean dataAdmin = false;

    @NotNull
    private Boolean dataUser = false;
}
