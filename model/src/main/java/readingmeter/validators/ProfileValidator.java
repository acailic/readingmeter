package readingmeter.validators;

import readingmeter.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProfileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Profile.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Profile) {
            validateFields((Profile) target, errors);
        }
    }

    private void validateFields(Profile target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"name","name.empty");
        ValidationUtils.rejectIfEmpty(errors,"fractions","fractions.empty");

    }
}
