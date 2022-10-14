package spd.trello.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ReminderValidator.class)
@Documented
public @interface ReminderValidation {

    String message() default "Reminder time not validity";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
