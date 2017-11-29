package readingmeter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Consumption {


    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Month month;

    @NotNull
    private int consumption;

    @JsonIgnore
    @OneToOne
    private Connection connection;

    public String uri;

    public Consumption(Month month, int consumption, Connection connection, String uri) {
        this.month = month;
        this.consumption = consumption;
        this.connection = connection;
        this.uri = uri;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
