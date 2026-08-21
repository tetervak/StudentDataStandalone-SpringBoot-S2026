package ca.tetervak.studentdata.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditUserForm {

    private Integer id = null;

    @NotBlank
    @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters")
    private String username = "";

    @NotBlank
    @Size(max = 30, message = "First name must be no more than 30 characters")
    private String firstName = "";

    @NotBlank
    @Size(max = 30, message = "Last name must be no more than 30 characters")
    private String lastName = "";

    @NotNull
    private Boolean userAdmin = false;

    @NotNull
    private Boolean dataAdmin = false;

    @NotNull
    private Boolean dataUser = false;
}
