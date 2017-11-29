package readingmeter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"month", "profile_id"}))
public class Fraction {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Month month;

    @NotNull
    private Double fraction;

    @JsonIgnore
    @OneToOne
    private Profile profile;

    public Fraction(Month month, Double fraction ) {
        this.month = month;
        this.fraction = fraction;
    }

      Fraction( ) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Double getFraction() {
        return fraction;
    }

    public void setFraction(Double fraction) {
        this.fraction = fraction;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }



}
