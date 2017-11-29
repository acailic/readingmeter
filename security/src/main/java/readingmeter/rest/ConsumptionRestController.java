package readingmeter.rest;

import readingmeter.Connection;
import readingmeter.Consumption;
import readingmeter.MeterReading;
import readingmeter.Month;
import readingmeter.exceptions.EntityNotFoundException;
import readingmeter.exceptions.UserNotFoundException;
import readingmeter.repositories.AccountRepository;
import readingmeter.repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consumptions/{consumptionMonth}")
public class ConsumptionRestController {

    private final AccountRepository accountRepository;

    private final ConnectionRepository connectionRepository;

    @Autowired
    ConsumptionRestController(ConnectionRepository connectionRepository,
                             AccountRepository accountRepository) {
        this.connectionRepository = connectionRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<ConsumptionResource> readConsumptions(Principal principal, @PathVariable String consumptionMonth) {
        this.validateUser(principal);
        Month month = getMonth(consumptionMonth);
        List<Connection> connections =  this.connectionRepository.findByProfileAccountUsername(principal.getName()).stream().collect(Collectors.toList());

        List<ConsumptionResource> consumptionResourceList = connections.stream().
                map(connection ->  new ConsumptionResource(
                new Consumption(month, getMeterReadingForMonth(connection, month).getMeterReading(),connection,
                        connection.getUri()+"/consumptions/"+month.name())))
                .collect(Collectors.toList());
     return new Resources<>(consumptionResourceList);
    }



    @RequestMapping(method = RequestMethod.GET, value = "/{connectionId}") //connectionId
    ConsumptionResource readConsumption(Principal principal, @PathVariable String consumptionMonth, @PathVariable Long connectionId) {
        this.validateUser(principal);
        this.validateEntity(connectionId);
        Connection connection = this.connectionRepository.findOne(connectionId);
        Month month = getMonth(consumptionMonth);
        int retriveConsumption = this.retriveConsumption(month, connection);
        return new ConsumptionResource(
                new Consumption(month, retriveConsumption,connection, connection.getUri()+"/consumptions/"+month.name() ));
    }

    private Month getMonth(String consumptionMonth) throws EntityNotFoundException {
        Month month;
        try {
            month = Month.valueOf(consumptionMonth);
        } catch (IllegalArgumentException ilegalArgumentExcept) {
            throw new EntityNotFoundException("Invalid month.");
        }
        return month;
    }



    private void validateEntity(Long connectionId) {
        if(!connectionRepository
                .exists(connectionId)){
            throw new EntityNotFoundException(connectionId.toString());
        }
    }

    private int retriveConsumption(Month month, Connection connection) {
        if (month.equals(Month.JAN)) {
            return getMeterReadingForMonth(connection, Month.JAN).getMeterReading();
        } else {
            final int meterReadingCurrentMonth = getMeterReadingForMonth(connection, month).getMeterReading();
            final int meterReadingPreviousMonth = getMeterReadingForMonth(connection, Month.of(month.getValue() - 1)).getMeterReading();
            return meterReadingCurrentMonth - meterReadingPreviousMonth;
        }
    }

    private MeterReading getMeterReadingForMonth(Connection connection, Month month) {
        return connection.getMeterReading().stream()
                .filter(meterReading1 -> meterReading1.getMonth().equals(month)).findFirst().orElse(null);
        //assume it will find it
    }

    private void validateUser(Principal principal) {
        String userId = principal.getName();
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId));
    }
}
