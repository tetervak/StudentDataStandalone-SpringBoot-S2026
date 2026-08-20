package ca.tetervak.studentdata.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

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
    @NotBlank
    @Size(max = 30)
    private String lastName = "";

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
}
