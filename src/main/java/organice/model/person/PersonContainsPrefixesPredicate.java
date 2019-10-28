package organice.model.person;

import java.util.List;
import java.util.function.Predicate;

import organice.commons.util.StringUtil;
import organice.logic.parser.ArgumentMultimap;
import organice.logic.parser.ArgumentTokenizer;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

/**
 * Tests that a {@code Person}'s prefixes matches any of the prefix-keyword pairs given.
 */
public class PersonContainsPrefixesPredicate implements Predicate<Person> {
    private final ArgumentMultimap argMultimap;

    public PersonContainsPrefixesPredicate(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    @Override
    public boolean test(Person person) {
        // Get all prefixes. Test all prefixes. All predicates must pass for person to appear
        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> nricKeywords = argMultimap.getAllValues(PREFIX_NRIC);
        List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> typeKeywords = argMultimap.getAllValues(PREFIX_TYPE);
        List<String> ageKeywords = argMultimap.getAllValues(PREFIX_AGE);
        List<String> priorityKeywords = argMultimap.getAllValues(PREFIX_PRIORITY);
        List<String> bloodTypeKeywords = argMultimap.getAllValues(PREFIX_BLOOD_TYPE);
        List<String> doctorInChargeKeywords = argMultimap.getAllValues(PREFIX_DOCTOR_IN_CHARGE);
        List<String> tissueTypeKeywords = argMultimap.getAllValues(PREFIX_TISSUE_TYPE);
        List<String> organExpiryDateKeywords = argMultimap.getAllValues(PREFIX_ORGAN_EXPIRY_DATE);
        List<String> organKeywords = argMultimap.getAllValues(PREFIX_ORGAN);

        // Early return if argMultimap is empty
        if (argMultimap.equals(ArgumentTokenizer.tokenize("find", PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE,
                PREFIX_TYPE, PREFIX_AGE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                PREFIX_TISSUE_TYPE, PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN))) {
            return false;
        }

        return (nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(person.getName().fullName, keyword)))
                && (nricKeywords.isEmpty() || nricKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(person.getNric().toString(), keyword)))
                && (phoneKeywords.isEmpty() || phoneKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(person.getPhone().toString(), keyword)))
                && (typeKeywords.isEmpty() || typeKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(person.getType().toString(), keyword)))
                // Only Patient and Donor have age. So only test age for those two.
                && (ageKeywords.isEmpty() || (person.getType().isPatient() ? ageKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Patient) person).getAge().toString(), keyword))
                    : !person.getType().isDonor() || ageKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Donor) person).getAge().toString(), keyword))))
                && (priorityKeywords.isEmpty() || !person.getType().isPatient() || priorityKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Patient) person).getPriority().toString(), keyword)))
                && (bloodTypeKeywords.isEmpty() || (person.getType().isPatient() ? bloodTypeKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Patient) person).getBloodType().toString(), keyword))
                    : !person.getType().isDonor() || bloodTypeKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Donor) person).getBloodType().toString(), keyword))))
                && (doctorInChargeKeywords.isEmpty() || !person.getType().isPatient() || doctorInChargeKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Patient) person).getDoctorInCharge().toString(), keyword)))
                && (tissueTypeKeywords.isEmpty() || (person.getType().isPatient() ? tissueTypeKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Patient) person).getTissueType().toString(), keyword))
                    : !person.getType().isDonor() || tissueTypeKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Donor) person).getTissueType().toString(), keyword))))
                && (organExpiryDateKeywords.isEmpty() || !person.getType().isDonor() || organExpiryDateKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Donor) person).getOrganExpiryDate().toString(), keyword)))
                && (organKeywords.isEmpty() || (person.getType().isPatient() ? organKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Patient) person).getOrgan().toString(), keyword))
                    : !person.getType().isDonor() || tissueTypeKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordsIgnoreCase(((Donor) person).getOrgan().toString(), keyword))));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsPrefixesPredicate // instanceof handles nulls
                && argMultimap.equals(((PersonContainsPrefixesPredicate) other).argMultimap)); // state check
    }

}
