package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.parser.CliSyntax.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import organice.commons.core.Messages;
import organice.logic.parser.ArgumentMultimap;
import organice.model.Model;
import organice.model.person.Person;
import organice.model.person.PersonContainsExactPrefixesPredicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Filter;

/**
 * Finds and lists all persons in address book whose prefixes match any of the argument prefix-keyword pairs.
 * Performs fuzzy searching based on Levenshtein Distance.
 * Keyword matching is case insensitive.
 */
public class FuzzyFindCommand extends Command {

    public static final String COMMAND_WORD = "fuzfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose prefixes are similar to any of"
            + " the specified prefix-keywords pairs (case-insensitive) and displays them as a list with index numbers."
            + "\nList of Prefixes: n/, ic/, p/, a/, t/, pr/, b/, d/, tt/, exp/, o/"
            + "Parameters: PREFIX/KEYWORD [MORE_PREFIX-KEYWORD_PAIRS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice t/doctor";

    private final ArgumentMultimap argMultimap;

    public FuzzyFindCommand(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    private FilteredList<Person> fuzzyMatch(ArgumentMultimap argMultimap, FilteredList<Person> inputList) {
        // Take math_min of Levenshtein Distance within one prefix, and accumulate them.

        // Calculate LD for each pair of keyword, attribute within one prefix. Take math_min of all.
        // sum all LD. That is the LD of the Person
        // Sort the inputlist according to the LDs
        // Over to you #execute

        // Fuzzy Match does not take into account the following prefixes due to these reasons:
        // Age, Priority, BloodType, TissueType:
        // Typos in these fields have a very small Levenshtein Distance (LD) as field length is very small
        // Current Implementation of fuzzy search only supports the main 4 person attributes

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> nricKeywords = argMultimap.getAllValues(PREFIX_NRIC);
        List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> typeKeywords = argMultimap.getAllValues(PREFIX_TYPE);

        // Array containing combined Levenshtein Distance of persons in inputList
        int[] distanceArr = new int[inputList.size()];

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

            distanceArr[i] = combinedLevenshteinDistance;
        }

        ArrayList<Person> sortedPersons = new ArrayList<>(inputList);
        Collections.sort(sortedPersons, Comparator.comparingInt(left -> distanceArr[inputList.indexOf(left)]));

        return new FilteredList<>(FXCollections.observableArrayList(sortedPersons));
    }

    private int findMinLevenshteinDistance(List<String> prefixKeywords, String personAttribute) {
        return prefixKeywords.stream().reduce(0, (minDistance, nextKeyword) -> Integer.min(
                minDistance, calculateLevenshteinDistance(nextKeyword.toLowerCase(), personAttribute.toLowerCase())),
                Integer::min);
    }

    // Algorithm taken from https://semanti.ca/blog/?an-introduction-into-approximate-string-matching.
    // Java code is original work.
    private int calculateLevenshteinDistance(String prefixKeyword, String personAttribute) {
        int prefixKeywordLength = prefixKeyword.length();
        int personAttributeLength = personAttribute.length();

        if (prefixKeywordLength == 0 || prefixKeywordLength == 0) {
            // Placeholder, should not occur.
            return 0;
        }

        char[] pkChars = prefixKeyword.toCharArray();
        char[] paChars = personAttribute.toCharArray();

        int[][] costMatrix = new int[prefixKeywordLength][personAttributeLength];
        for (int i = 0; i < prefixKeywordLength; i++) {
            for (int j = 0; j < personAttributeLength; j++) {
                costMatrix[i][j] = pkChars[i] == paChars[j] ? 0 : 1;
            }
        }

        int[][] levenshteinMatrix = new int [prefixKeywordLength + 1][personAttributeLength + 1];
        // Initialise first row and col to be in range 0..length
        for (int r = 0; r < prefixKeywordLength + 1; r++) {
            levenshteinMatrix[r][0] = r;
        }
        for (int c = 0; c < personAttributeLength + 1; c++) {
            levenshteinMatrix[0][c] = c;
        }

        // Setting the distance
        for (int r = 1; r < prefixKeywordLength + 1; r++) {
            for (int c = 1; c < personAttributeLength + 1; c++) {
                int cellAbovePlusOne = levenshteinMatrix[r - 1][c] + 1;
                int cellLeftPlusOne = levenshteinMatrix[r][c - 1] + 1;
                int cellLeftDiagPlusCost = levenshteinMatrix[r - 1][c - 1] + costMatrix[r - 1][c - 1];
                levenshteinMatrix[r][c] =
                        Integer.min(cellAbovePlusOne, Integer.min(cellLeftPlusOne, cellLeftDiagPlusCost));
            }
        }
        return levenshteinMatrix[prefixKeywordLength][personAttributeLength];
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Find exact matches - A
        // Find all except exact matches - B
        // Perform fuzzy matching on B, to sort it
        // Append B to A - searchResults
        // Replace FilteredPersonList with searchResults


        ObservableList<Person> allPersons = model.getFilteredPersonList();

        FilteredList<Person> exactMatches = new FilteredList<>(allPersons);
        exactMatches.setPredicate(new PersonContainsExactPrefixesPredicate(argMultimap));

        FilteredList<Person> allExceptExactMatches = new FilteredList<>(allPersons);
        allExceptExactMatches.removeAll(exactMatches);
        allExceptExactMatches = fuzzyMatch(argMultimap, allExceptExactMatches);

        ArrayList<Person> finalArrayList = new ArrayList<>(exactMatches);
        finalArrayList.addAll(allExceptExactMatches);

        FilteredList<Person> searchResults = new FilteredList<>(FXCollections.observableArrayList(finalArrayList));

        model.overrideFilteredPersonList(searchResults);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FuzzyFindCommand // instanceof handles nulls
                && argMultimap.equals(((FuzzyFindCommand) other).argMultimap)); // state check
    }
}
