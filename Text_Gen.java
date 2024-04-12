import java.util.*;
import java.nio.file.*;

class Text_Gen {
    // build frequency table of the characters that come after the string chunks
    static HashMap<String, HashMap<Character, Integer>> buildFrequencyTables(String sourceText, int windowLength) {
        HashMap<String, HashMap<Character, Integer>> frequencyTable = new HashMap<>();
        
        // text wraps around
        sourceText += sourceText.substring(0, windowLength);
        
        for (int sourceTextIndex = 0; sourceTextIndex < sourceText.length() - windowLength; sourceTextIndex++) {
            Character nextChar = sourceText.charAt(windowLength + sourceTextIndex);
            String stringChunk = sourceText.substring(sourceTextIndex, windowLength + sourceTextIndex);
            
            if (!frequencyTable.containsKey(stringChunk)) { // frequency table didn't contain the string chunk
                frequencyTable.put(stringChunk, new HashMap<Character, Integer>());
            } 
            
            HashMap<Character, Integer> miniTable = frequencyTable.get(stringChunk);
            
            if (!miniTable.containsKey(nextChar)) { // mini table did't contain the character
                miniTable.put(nextChar, 1);
            } else { // updates the character count
                miniTable.put(nextChar, miniTable.get(nextChar) + 1); 
            }
        }
        
        return frequencyTable;
    }
    
    static char sampleFrequencyTable(HashMap<Character, Integer> frequencyTable) {
        assert !frequencyTable.isEmpty(); // empty frequency table
        
        int totalNumberofOccurences = 0;
        for (int frequency : frequencyTable.values()) { // get total num of frequencies
            totalNumberofOccurences += frequency;
        }
        
        int i = getRandomInt(totalNumberofOccurences); // randomly generate a num
        int cutoff = 0;
        
        for (char key : frequencyTable.keySet()) {
            cutoff += frequencyTable.get(key); // gets the frequency of each character
            if (i < cutoff) { // 
                return key;
            }
        }
        
        assert false;
        return 'X';
    }
    
    static String generateTextFromSeed(int windowLength, HashMap<String, HashMap<Character, Integer>> frequencyTables, String seed, int numberOfCharactersToGenerate) {
        String result = seed;
        
        if (result.length() < windowLength) { // given string is shorter than the window; just return it
            return result;
        }

        for (int x = 0; x < numberOfCharactersToGenerate; x++) {
            String stringChunk = result.substring(result.length() - windowLength); // last n char of the string
            HashMap<Character, Integer> miniresult = frequencyTables.get(stringChunk);
            char word = sampleFrequencyTable(miniresult);
            result += word;
        }
            
        return result;
    }
    
    
    public static void main(String[] arguments) {
        // String sourceText = "aaaabbbb";
        // String sourceText = "bananas";
        String sourceText = "red leather yellow leather ";
        // String sourceText = readFileIntoString("Text_Gen.java", false);
        
        int windowLength = 6;
        int numberOfCharactersToGenerate = 1000;
        String seed = sourceText.substring(0, windowLength);
        HashMap<String, HashMap<Character, Integer>> frequencyTables = buildFrequencyTables(sourceText, windowLength);
        String generatedText = generateTextFromSeed(windowLength, frequencyTables, seed, numberOfCharactersToGenerate);
        System.out.println(generatedText);
        // System.out.println(frequencyTables);
    }
    
    
    // return a random int in range [0, upperBoundExclusive)
    // e.g., getRandomInt(10) returns a random number from { 0, 1, 2, ..., 9 }
    static int getRandomInt(int upperBoundExclusive) {
        return _random.nextInt(upperBoundExclusive);
    }
    
    // As written, the behavior of getRandomInt(...)
    // will be the same each time the program runs.
    // To make it different each time...
    // ...change "new Random(0)" -> "new Random()" below
    static Random _random = new Random(0);
    
    
    // Reads the contens of a file into a String and returns it.
    // removeExcessWhiteSpace=true condenses multiple spaces and newlines into a single space
    static String readFileIntoString(String fileName, boolean removeExcessWhiteSpace) {
        String result = null;
        try { result = new String(Files.readAllBytes(Paths.get(fileName))); }
        catch (Exception exception) { System.out.println(exception); assert false; }
        if (removeExcessWhiteSpace) { result = result.replaceAll("[ \n]+", " "); }
        return result;
    }
}