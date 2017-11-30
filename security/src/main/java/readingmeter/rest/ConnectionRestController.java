package readingmeter.rest;


import readingmeter.Connection;
import readingmeter.Fraction;
import readingmeter.MeterReading;
import readingmeter.Profile;
import readingmeter.exceptions.EntityNotFoundException;
import readingmeter.exceptions.UserNotFoundException;
import readingmeter.repositories.AccountRepository;
import readingmeter.repositories.ConnectionRepository;
import readingmeter.repositories.ProfileRepository;
import readingmeter.validators.ConnectionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/connections")
public class ConnectionRestController {

    final  static double TOLERANCE = 0.25;

    private final AccountRepository accountRepository;

    private final ConnectionRepository connectionRepository;

    private final ProfileRepository profileRepository;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
            binder.setValidator(new ConnectionValidator());
    }

    @Autowired
    public ConnectionRestController(ConnectionRepository connectionRepository,
                             AccountRepository accountRepository,
                             ProfileRepository profileRepository) {
        this.connectionRepository = connectionRepository;
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Resources<ConnectionResource> readConnections(Principal principal) {
        this.validateUser(principal);
        List<ConnectionResource> connectionResourceList = this.connectionRepository
                .findByProfileAccountUsername(principal.getName()).stream()
                .map(ConnectionResource::new)
                .collect(Collectors.toList());
        return new Resources<>(connectionResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(Principal principal, @Valid @RequestBody Connection inputConnection) {
        this.validateUser(principal);
        this.validateConnection(inputConnection,principal.getName());
        return accountRepository
                .findByUsername(principal.getName())
                .map(account -> {
                    Connection connection = connectionRepository.save(
                          new Connection(//account,
                                  inputConnection.getId(),
                                  getByNameAndAccountUsername(inputConnection.getProfile().getName(),account.getUsername()),
                                  inputConnection.getMeterReading(),
                                  inputConnection.getUri()));

                    Link forOneConnection = new ConnectionResource(connection).getLink(Link.REL_SELF);

                    return ResponseEntity.created(URI
                            .create(forOneConnection.getHref()))
                            .build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{connectionId}")
    public ResponseEntity<?> put(Principal principal, @Valid @RequestBody Connection inputConnection,@PathVariable Long connectionId) {
        this.validateUser(principal);
        this.validateEntity(connectionId);
        this.validateUpdateId(inputConnection, connectionId);
        this.validateConnection(inputConnection,principal.getName());
        return accountRepository
                .findByUsername(principal.getName())
                .map(account -> {
                    Connection connection = connectionRepository.save(
                            new Connection(//account,
                                    connectionId,
                                    getByNameAndAccountUsername(inputConnection.getProfile().getName(),account.getUsername()),
                                    inputConnection.getMeterReading(),
                                    inputConnection.getUri()));

                    Link forOneConnection = new ConnectionResource(connection).getLink(Link.REL_SELF);

                    return ResponseEntity.ok(URI
                            .create(forOneConnection.getHref()));
                })
                .orElse(ResponseEntity.noContent().build());
    }

    private void validateUpdateId(Connection inputConnection, Long connectionId) {
        if(connectionId.equals(inputConnection.getId())){
            throw new ValidationException("Id can't be changed.");
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{connectionId}")
    ConnectionResource readConnection(Principal principal, @PathVariable Long connectionId) {
        this.validateEntity(connectionId);
        this.validateUser(principal);
        return new ConnectionResource(
                this.connectionRepository.findOne(connectionId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{connectionId}")
    public ResponseEntity<?> deleteConnection(Principal principal, @PathVariable Long connectionId) {
        this.validateEntity(connectionId);
        this.validateUser(principal);
        this.connectionRepository.delete(connectionId);
        return ResponseEntity.ok().build();
    }

    private void validateUser(Principal principal) throws UserNotFoundException {
        String userId = principal.getName();
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId));
    }

    private void validateEntity(Long connectionId)throws EntityNotFoundException {
        if(!this.connectionRepository
                .exists(connectionId)){
            throw new EntityNotFoundException(connectionId.toString());
        }
    }


    private void validateConnection(Connection connection, String username) throws ValidationException {
        if (connection == null) {
            throw new ValidationException("empty content");
        }
        //meter readings
        if (connection.getMeterReading() == null || connection.getMeterReading().size() != 12) {
            throw new ValidationException("Size of fractions for meter readings need to be 12 for connection");
        }
        if (connection.getProfile().getName() == null) {
            throw new ValidationException("Not found profile name.");
        }
        Profile profile = getByNameAndAccountUsername(connection.getProfile().getName(), username);
        if (profile == null) {
            throw new ValidationException("Not found profile with required name for specific user.");
        }
        validConsumption(connection,profile);
    }

    private void validConsumption(Connection connection, Profile profile) throws ValidationException {
        final int totalReading = connection.getMeterReading().stream().mapToInt(MeterReading::getMeterReading).sum();
        connection.getMeterReading().forEach(meterReading -> {
            Fraction fractionPerMonth = profile.getFractions().stream().filter(fraction -> fraction.getMonth().equals(meterReading.getMonth())).findAny().get();
            int fractionReading = (int) (totalReading * fractionPerMonth.getFraction());
            int allowedRange = (int) TOLERANCE * fractionReading;
            int readingPerMonth = meterReading.getMeterReading();
            if (readingPerMonth < fractionReading - allowedRange || readingPerMonth > fractionReading + allowedRange) {
                throw new ValidationException("Invalid consumption for profile:" + profile.getName() + "  in the month of:" + meterReading.getMonth() + " and value of fraction is:" +
                        +fractionPerMonth.getFraction() + " .");
            }
        });
    }

    private Profile getByNameAndAccountUsername(String name, String username) {
        return this.profileRepository.findByNameAndAccountUsername(name,username);
    }
}
