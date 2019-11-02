package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.LevenshteinDistanceUtil.findMinLevenshteinDistance;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;

import organice.logic.parser.ArgumentMultimap;
import organice.model.Model;
import organice.model.person.Person;
import organice.model.person.PersonContainsPrefixesPredicate;


/**
 * Finds and lists all persons in address book whose prefixes match any of the argument prefix-keyword pairs.
 * Performs fuzzy searching based on Levenshtein Distance.
 * Keyword matching is case insensitive.
 */
public class FuzzyFindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose prefixes are similar to any of"
            + " the specified prefix-keywords pairs (case-insensitive) and displays them as a list with index numbers."
            + "\nList of Prefixes: n/, ic/, p/, a/, t/, pr/, b/, d/, tt/, exp/, o/"
            + "Parameters: PREFIX/KEYWORD (MORE_PREFIX-KEYWORD_PAIRS...)\n"
            + "Example: " + COMMAND_WORD + " n/alice t/doctor";

    // Maximum Levenshtein Distance tolerated for a fuzzy match
    private static final int FUZZY_THRESHOLD = 5;

    private final ArgumentMultimap argMultimap;

    public FuzzyFindCommand(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    /**
     * Returns a copy of the {@code inputList} which is filtered according to maximum tolerable Levenshtein Distance
     * (edit distance) then sorted according to ascending level of distance.
     */
    private List<Person> fuzzyMatch(ArgumentMultimap argMultimap, List<Person> inputList) {
        // Fuzzy Match by Levenshtein Distance is not implemented for following prefixes:
        // Age, Priority, BloodType, TissueType
        // Typos in these fields have a very small Levenshtein Distance (LD) as typical field length is very small

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> nricKeywords = argMultimap.getAllValues(PREFIX_NRIC);
        List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> typeKeywords = argMultimap.getAllValues(PREFIX_TYPE);

        // List containing combined Levenshtein Distance of persons in inputList
        ArrayList<Integer> distanceList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            Person currentPerson = inputList.get(i);
            int combinedLevenshteinDistance = 0;
            combinedLevenshteinDistance += nameKeywords.isEmpty() ? 0
                    : findMinLevenshteinDistance(nameKeywords, currentPerson.getName().toString());
            combinedLevenshteinDistance += nricKeywords.isEmpty() ? 0
                    : findMinLevenshteinDistance(nricKeywords, currentPerson.getNric().toString());
            combinedLevenshteinDistance += phoneKeywords.isEmpty() ? 0
                    : findMinLevenshteinDistance(phoneKeywords, currentPerson.getPhone().toString());
            combinedLevenshteinDistance += typeKeywords.isEmpty() ? 0
                    : findMinLevenshteinDistance(typeKeywords, currentPerson.getType().toString());

            distanceList.add(i, combinedLevenshteinDistance);
        }

        // Keep Persons whose Levenshtein Distance is within tolerable range
        ArrayList<Integer> distancesOfTolerablePersons = new ArrayList<>();
        ArrayList<Person> tolerablePersons = new ArrayList<>(inputList.size());
        for (int i = 0; i < inputList.size(); i++) {
            int levenshteinDistance = distanceList.get(i);
            if (levenshteinDistance <= FUZZY_THRESHOLD) {
                distancesOfTolerablePersons.add(levenshteinDistance);
                tolerablePersons.add(inputList.get(i));
            }
        }

        ArrayList<Person> sortedPersons = new ArrayList<>(tolerablePersons);
        sortedPersons.sort(Comparator.comparingInt(left ->
                distancesOfTolerablePersons.get(tolerablePersons.indexOf(left))));

        return sortedPersons;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Person> allPersons = Arrays.asList(model.getFilteredPersonList().toArray(Person[]::new));

        FilteredList<Person> exactMatches = new FilteredList<>(FXCollections.observableList(allPersons));
        exactMatches.setPredicate(new PersonContainsPrefixesPredicate(argMultimap));

        List<Person> allExceptExactMatches = new ArrayList<>(allPersons);
        allExceptExactMatches.removeAll(Arrays.asList(exactMatches.toArray(Person[]::new)));
        allExceptExactMatches = fuzzyMatch(argMultimap, allExceptExactMatches);

        ArrayList<Person> finalArrayList = new ArrayList<>(exactMatches);
        finalArrayList.addAll(allExceptExactMatches);

        model.setDisplayedPersonList(finalArrayList);
        return new CommandResult("Found " + exactMatches.size() + " exact matches and "
                + allExceptExactMatches.size() + " possible matches!");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FuzzyFindCommand // instanceof handles nulls
                && argMultimap.equals(((FuzzyFindCommand) other).argMultimap)); // state check
    }
}
