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
