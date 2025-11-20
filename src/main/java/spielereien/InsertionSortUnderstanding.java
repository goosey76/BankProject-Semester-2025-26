package spielereien;

import java.util.Arrays;

/**
 * InsertionsSortUnderstanding wie es eben Funktioniert und populiert.
 */
public class InsertionSortUnderstanding {

    /**
     * Obligatorisch
     */
    public InsertionSortUnderstanding() {
        // Kein Konstruktor Aufruf
    }
    /**
     * Die Main
     * @param args mit Args
     */
    public static void main(String[] args) {

        // Insertion Sort Beispiel

        int[] intArray = new int[] {1, 4, 2,};
        System.out.println("initial: " + Arrays.toString(intArray));
        insertionSortVisual(intArray);
        System.out.println("\nsorted: " + Arrays.toString(intArray));
    }

    /**
     * Hier ist i au der rechten Seite und j auf der Linken
     * @param intArray das IntArrays
     */
    static void insertionSortVisual(int[] intArray) {
        for (int i = 1; i < intArray.length; i++) {
            int key = intArray[i];
            int j = i - 1;

            System.out.println("\ni = " + i + ", key = " + key);
            // Schiebt die Elemente nach link bis der korrekte Platz des keys gefunden ist
            while (j >= 0 && intArray[j] > key) {
                intArray[j + 1] = intArray[j];
                System.out.println(" verschoben: verschieb element von index " + j + " to " + (j + 1)
                        + " -> " + Arrays.toString(intArray));
                j--;
            }

            intArray[j + 1] = key;
            System.out.println(" insert: placed key at index " + (j + 1)
                    + " -> " + Arrays.toString(intArray));
        }
    }
}


