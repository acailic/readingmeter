package readingmeter;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Connection {

    @NotNull
    @Id
    private Long id;

    @JsonIgnoreProperties(value ={ "id","fractions","uri"})
    @ManyToOne
    private Profile profile;


    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)
    private Set<MeterReading> meterReading;


    public String uri;


    public Connection(Long id, Profile profile, Set<MeterReading> meterReading, String uri) {
        this.id = id;
        this.profile = profile;
        this.meterReading = meterReading;
        this.uri = uri;
    }

    public Connection( ) { //jpa only and tests
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MeterReading> getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(Set<MeterReading> meterReading) {
        this.meterReading = meterReading;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}
