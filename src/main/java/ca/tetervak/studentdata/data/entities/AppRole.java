package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_role")
@NoArgsConstructor
@Getter
@Setter
public class AppRole {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName = "";

    public String getAuthority() {
        return "ROLE_" + roleName;
    }

    // roles will be compared in the set by their names
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppRole)) return false;
        return Objects.equals(roleName, ((AppRole) o).roleName);
    }

    // roles be stored in the set based on their names
    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    public String toPrettyString() {
        String[] words = roleName.toLowerCase().split("_");
        String[] capitalizedWords = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            capitalizedWords[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
        }
        return String.join(" ", capitalizedWords);
    }
}
