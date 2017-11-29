package readingmeter;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "id"}))
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Fraction> fractions;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Connection> connections;

    @JsonIgnore
    @ManyToOne
    private Account account;

    public String uri;

    public Profile(Account account, String name, Set<Fraction> fractions, String uri) {
        this.name = name;
        this.fractions = fractions;
        this.uri = uri;
        this.account=account;
    }


   public Profile(){} //jpa only


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(Set<Fraction> fractions) {
        this.fractions = fractions;
    }

    public Long getId() {
        return id;
    }

    public Set<Connection> getConnections() {
        return connections;
    }

    public Account getAccount() {
        return account;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (id != null ? !id.equals(profile.id) : profile.id != null) return false;
        if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
        if (fractions != null ? !fractions.equals(profile.fractions) : profile.fractions != null) return false;
        if (connections != null ? !connections.equals(profile.connections) : profile.connections != null) return false;
        if (account != null ? !account.equals(profile.account) : profile.account != null) return false;
        return uri != null ? uri.equals(profile.uri) : profile.uri == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fractions != null ? fractions.hashCode() : 0);
        result = 31 * result + (connections != null ? connections.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
