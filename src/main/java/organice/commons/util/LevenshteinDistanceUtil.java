package organice.commons.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

/**
 * Helper functions for calculating Levenshtein Distance (edit distance). Edit distance is the minimum number of
 * character changes required to make two strings equal.
 */
public class LevenshteinDistanceUtil {

    /**
     * Returns the minimum Levenshtein Distance of every {@code stringFromList} and {@code otherString} pair. If
     * {@code stringFromList} is a single-word string, {@code otherString} is split according to spaces (if possible)
     * and the minimum of the minimum distances of every possible pair is returned.
     */
    public static int findMinLevenshteinDistance(List<String> stringList, String otherString) {
        // If keyword is one word long, split the personName and find minLD.
        // Else just do as we are normally doing in original finalMinLD
        BiFunction<String, String, Integer> findDistanceSplitIfMultiWord = (stringFromList, oString) ->
                stringFromList.split(" ").length == 1 ? Arrays.stream(oString.split(" "))
                        .reduce(Integer.MAX_VALUE, (minDistance, nextWord) -> Integer.min(minDistance,
                                calculateLevenshteinDistance(stringFromList.toLowerCase(), nextWord.toLowerCase())),
                                Integer::min)
                        : calculateLevenshteinDistance(stringFromList.toLowerCase(), oString.toLowerCase());

        return stringList.stream().reduce(Integer.MAX_VALUE, (minDistance, nextKeyword) ->
                        Integer.min(minDistance, findDistanceSplitIfMultiWord.apply(nextKeyword, otherString)),
                Integer::min);
    }

    // Algorithm taken from https://semanti.ca/blog/?an-introduction-into-approximate-string-matching.
    // Java code is original work.
    /**
     * Calculates the Levenshtein Distance (edit distance) between the given {@code String firstString} and
     * {@code String secondString}. Levenshtein Distance is the number of character edits required to morph one
     * string into another.
     */
    public static int calculateLevenshteinDistance(String firstString, String secondString) {
        requireNonNull(firstString);
        requireNonNull(secondString);

        int firstStringLength = firstString.length();
        int secondStringLength = secondString.length();

        checkArgument(firstStringLength != 0 && secondStringLength != 0);

        char[] pkChars = firstString.toCharArray();
        char[] paChars = secondString.toCharArray();

        int[][] costMatrix = new int[firstStringLength][secondStringLength];
        for (int i = 0; i < firstStringLength; i++) {
            for (int j = 0; j < secondStringLength; j++) {
                costMatrix[i][j] = pkChars[i] == paChars[j] ? 0 : 1;
            }
        }

        int[][] levenshteinMatrix = new int [firstStringLength + 1][secondStringLength + 1];
        // Initialise first row and col to be in range 0..length
        for (int r = 0; r < firstStringLength + 1; r++) {
            levenshteinMatrix[r][0] = r;
        }
        for (int c = 0; c < secondStringLength + 1; c++) {
            levenshteinMatrix[0][c] = c;
        }

        // Setting the distance
        for (int r = 1; r < firstStringLength + 1; r++) {
            for (int c = 1; c < secondStringLength + 1; c++) {
                int cellAbovePlusOne = levenshteinMatrix[r - 1][c] + 1;
                int cellLeftPlusOne = levenshteinMatrix[r][c - 1] + 1;
                int cellLeftDiagPlusCost = levenshteinMatrix[r - 1][c - 1] + costMatrix[r - 1][c - 1];
                levenshteinMatrix[r][c] =
                        Integer.min(cellAbovePlusOne, Integer.min(cellLeftPlusOne, cellLeftDiagPlusCost));
            }
        }
        return levenshteinMatrix[firstStringLength][secondStringLength];
    }
}
