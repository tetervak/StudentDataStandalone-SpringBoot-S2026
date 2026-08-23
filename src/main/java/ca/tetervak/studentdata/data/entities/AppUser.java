package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 4, max = 15)
    private String username = "";

    @Column(name = "password_hash", nullable = false)
    @Size(min = 8, max = 128)
    private String passwordHash = "";

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 30)
    private String firstName = "";

    @Column(name = "last_name")
    @Size(min = 2, max = 30)
    private String lastName = "";

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="app_user_role",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private Set<AppRole> roles = new HashSet<>(2);

    public List<String> getPrettyRoleNames() {
        return roles.stream().map(AppRole::toPrettyString).toList();
    }
}
