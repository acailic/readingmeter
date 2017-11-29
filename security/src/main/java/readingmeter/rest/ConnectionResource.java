package readingmeter.rest;

import readingmeter.Connection;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ConnectionResource extends ResourceSupport {

    private final Connection connection;

    public ConnectionResource(Connection connection) {
        String username = connection.getProfile().getAccount().getUsername();
        this.connection = connection;
        this.add(new Link(connection.uri, "connection-uri"));
        this.add(linkTo(ConnectionRestController.class, username).withRel("connections"));
        this.add(linkTo(
                methodOn(ConnectionRestController.class, username).readConnection(null,
                        connection.getId())).withSelfRel());
    }

    public Connection getConnection() {
        return connection;
    }
}