package readingmeter.validators;


import readingmeter.Connection;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ConnectionValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Connection.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Connection) {
            validateFields((Connection) target, errors);
        }
    }

    private void validateFields(Connection connection, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"id","id.empty");
        ValidationUtils.rejectIfEmpty(errors,"meterReading","meterReading.empty");
    }
}
