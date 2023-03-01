package com.conarflib.util;

import java.util.ArrayList;
import java.util.List;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.PropertyChange;

/**
 * Class with methods to compares objects of the same domain model.
 * 
 * @author Alciran Franco
 */
public class CompareObjects {

    private static Javers javersBuild() {
        return JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).build();
    }

    /**
     * <h2>Deep Compare using JaVers</h2>
     * 
     * To get a Diff just provide two versions of the
     * same domain object.
     * 
     * 
     * @param oldVersion Old Version of domain object
     * @param newVersion New version of domain object
     * @return Diff Object.
     */
    public static Diff getDiff(Object oldVersion, Object newVersion) {
        return javersBuild().compare(oldVersion, newVersion);

    }

    private static String prettyDifference(PropertyChange<?> propertyChange, String classNameFull) {
        String changePrettyDescription = "[" + propertyChange.getPropertyNameWithPath() + "] ";

        if (propertyChange.getRight() == null ||
                ObjectUtils.isEmpty(propertyChange.getRight())
                        && propertyChange.getLeft() != null && !propertyChange.getLeft().equals(""))
            changePrettyDescription += "REMOVE value " +
                    propertyChange.getLeft().toString().replace(classNameFull, "");
        else if (propertyChange.getLeft() == null ||
                ObjectUtils.isEmpty(propertyChange.getRight()))
            changePrettyDescription += "ADD value: "
                    + propertyChange.getRight().toString().replace(classNameFull, "");
        else
            changePrettyDescription += "CHANGE from: " +
                    propertyChange.getLeft().toString().replace(classNameFull, "") + " to: "
                    + propertyChange.getRight().toString().replace(classNameFull, "");

        return changePrettyDescription;
    }

    /**
     * <h2>Get Pretty List of Differences</h2>
     * 
     * This method use a JaVers Library to return a List of String, for
     * differences between two object of the same domain model.
     * 
     * To get a pretty List, just provide two versions of
     * same domain objects.
     * 
     * @param oldVersion Old version of domain object.
     * @param newVersion New version of domain object.
     * @return An Array of String, with differences.
     */
    public static List<String> getDifferencesPrettyList(Object oldVersion, Object newVersion) {
        List<String> changes = new ArrayList<>();
        Diff diff = getDiff(oldVersion, newVersion);

        diff.groupByObject().forEach(changesByObject -> {
            String className = oldVersion.getClass().getSimpleName();
            String classNameFull = oldVersion.getClass().getName();
            changesByObject.getPropertyChanges()
                    .forEach(
                            propertyChange -> changes.add(className + prettyDifference(propertyChange, classNameFull)));
        });

        return changes;
    }

}
