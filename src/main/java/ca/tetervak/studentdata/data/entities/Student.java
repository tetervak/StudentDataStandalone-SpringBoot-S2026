package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class) // automatically maintain creation and modification timestamps
public class Student {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 30)
    private String firstName = "";

    @Column(name = "last_name")
    @Size(min = 2, max = 30)
    private String lastName = "";

    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    @Past
    private LocalDate dateOfBirth = LocalDate.of(2018, 1, 1);

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "international")
    private Boolean international = false;

    @Column(name = "program_year")
    @Min(1)
    @Max(3)
    private Integer programYear = 0;

    @Column(name = "program_coop")
    private Boolean programCoop = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;          // or LocalDateTime / OffsetDateTime

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // returns current age in years
    public int getAge() {
        return getAge(LocalDate.now());
    }

    public int getAge(LocalDate onDate) {
        if (dateOfBirth == null) {
            throw new IllegalStateException("dateOfBirth is null");
        }
        if (onDate.isBefore(dateOfBirth)) {
            throw new IllegalArgumentException("Reference date cannot be before date of birth");
        }
        return Period.between(dateOfBirth, onDate).getYears();
    }
}
