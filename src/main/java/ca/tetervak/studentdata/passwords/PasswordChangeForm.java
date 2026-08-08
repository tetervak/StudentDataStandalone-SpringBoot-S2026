package ca.tetervak.studentdata.passwords;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PasswordChangeForm implements Serializable {
    private String currentPassword = "";
    private String newPassword1 = "";
    private String newPassword2 = "";

}
