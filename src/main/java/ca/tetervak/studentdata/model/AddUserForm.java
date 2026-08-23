package ca.tetervak.studentdata.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AddUserForm {

    @Size(min = 4, max = 15)
    private String username = "";

    @Size(min = 6)
    private String password = "";

    @NotBlank
    @Size(max = 30)
    private String firstName = "";

    @Size(min = 2, max = 30)
    private String lastName = "";

    @NotNull
    private Boolean userAdmin = false;

    @NotNull
    private Boolean dataAdmin = false;

    @NotNull
    private Boolean dataUser = false;
}
