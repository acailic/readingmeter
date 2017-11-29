package readingmeter.rest;

import readingmeter.Consumption;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ConsumptionResource extends ResourceSupport {

    private final Consumption consumption;

    public ConsumptionResource(Consumption consumption) {
        String username = consumption.getConnection().getProfile().getAccount().getUsername();
        this.consumption = consumption;
        this.add(new Link(consumption.uri, "consumption-uri"));
        this.add(linkTo(ConsumptionRestController.class, consumption.getMonth()).withRel("consumptions"));
        this.add(linkTo(
                methodOn(ConsumptionRestController.class, username).readConsumption(null,
                        consumption.getMonth().name(),consumption.getConnection().getId())).withSelfRel());
    }

    public Consumption getConsumption() {
        return consumption;
    }
}