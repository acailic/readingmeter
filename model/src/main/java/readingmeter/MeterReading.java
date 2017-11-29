package readingmeter;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class MeterReading {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Month month;

    @JsonIgnore
    @ManyToOne
    private Connection connection;

    @NotNull
    private int meterReading;

    MeterReading( ) { //jpa only
    }

    public MeterReading(Month month, int meterReading) {
        this.month = month;
        this.meterReading = meterReading;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(int meterReading) {
        this.meterReading = meterReading;
    }
}
