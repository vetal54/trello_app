package spd.trello.annotation;

import spd.trello.domian.Reminder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReminderValidator implements ConstraintValidator<ReminderValidation, Reminder> {

    @Override
    public boolean isValid(Reminder reminder, ConstraintValidatorContext constraintValidatorContext) {
        return !reminder.getStart().isAfter(reminder.getRemindOn()) &&
                !reminder.getRemindOn().isAfter(reminder.getEnd()) &&
                !reminder.getStart().isAfter(reminder.getEnd());
    }
}
