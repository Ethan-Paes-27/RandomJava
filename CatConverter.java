import java.util.*;

public class CatConverter {

    private static final Map<Character, String> CAT_MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> ENGLISH_CAT_MAP = new HashMap<>();

    static {
        CAT_MORSE_MAP.put('a', "meow purr");
        CAT_MORSE_MAP.put('b', "purr meow meow meow");
        CAT_MORSE_MAP.put('c', "purr meow purr meow");
        CAT_MORSE_MAP.put('d', "purr meow meow");
        CAT_MORSE_MAP.put('e', "meow");
        CAT_MORSE_MAP.put('f', "meow meow purr meow");
        CAT_MORSE_MAP.put('g', "purr purr meow");
        CAT_MORSE_MAP.put('h', "meow meow meow meow");
        CAT_MORSE_MAP.put('i', "meow meow");
        CAT_MORSE_MAP.put('j', "meow purr purr purr");
        CAT_MORSE_MAP.put('k', "purr meow purr");
        CAT_MORSE_MAP.put('l', "meow purr meow meow");
        CAT_MORSE_MAP.put('m', "purr purr");
        CAT_MORSE_MAP.put('n', "purr meow");
        CAT_MORSE_MAP.put('o', "purr purr purr");
        CAT_MORSE_MAP.put('p', "meow purr purr meow");
        CAT_MORSE_MAP.put('q', "purr purr meow purr");
        CAT_MORSE_MAP.put('r', "meow purr meow");
        CAT_MORSE_MAP.put('s', "meow meow meow");
        CAT_MORSE_MAP.put('t', "purr");
        CAT_MORSE_MAP.put('u', "meow meow purr");
        CAT_MORSE_MAP.put('v', "meow meow meow purr");
        CAT_MORSE_MAP.put('w', "meow purr purr");
        CAT_MORSE_MAP.put('x', "purr meow meow purr");
        CAT_MORSE_MAP.put('y', "purr meow purr purr");
        CAT_MORSE_MAP.put('z', "purr purr meow meow");

        for (Map.Entry<Character, String> entry : CAT_MORSE_MAP.entrySet()) {
            ENGLISH_CAT_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    private static String toCatMorse(char c) {
        return CAT_MORSE_MAP.getOrDefault(Character.toLowerCase(c), "?");
    }

    private static char toEnglishWord(String catMorse) {
        return ENGLISH_CAT_MAP.getOrDefault(catMorse, '?');
    }

    public static String toCat(String str) {
        StringBuilder catString = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c == ' ') {
                catString.append(" nya");
                continue;
            }

            if (!Character.isLetterOrDigit(c)) {
                catString.append(" ").append(c);
                continue;
            }

            if (Character.isDigit(c)) {
                String extra = "";
                if (i != 0 && !Character.isDigit(str.charAt(i - 1))) {
                    extra = " ";
                }
                catString.append(extra + c);
                continue;
            }

            if (Character.isUpperCase(c)) {
                catString.append(" mew");
            }

            catString.append(" " + toCatMorse(c) + " wi");
        }

        return catString.toString().trim();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Cat Converter!");
        System.out.println("Type 1 for Cat to English, 2 for English to Cat, or 0 to exit.");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        while (choice != 0) {
            if (choice == 1) {
                System.out.println("Enter the Cat string:");
                scanner.nextLine(); // consume the newline
                String catStr = scanner.nextLine();
                String englishStr = toEnglish(catStr);
                System.out.println(englishStr);
                System.out.println();
            } else if (choice == 2) {
                System.out.println("Enter the English string:");
                scanner.nextLine(); // consume the newline
                String englishStr = scanner.nextLine();
                String catStr = toCat(englishStr);
                System.out.println(catStr);
                System.out.println();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("Type 1 for Cat to English, 2 for English to Cat, or 0 to exit.");
            choice = scanner.nextInt();
        }
    }

    public static String toEnglish(String catStr) {
        StringBuilder result = new StringBuilder();
        String[] tokens = catStr.trim().split("\\s+");
        boolean capitalizeNext = false;
        List<String> buffer = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (token.equals("nya")) {
                result.append(" ");
                continue;
            }

            if (token.equals("mew")) {
                capitalizeNext = true;
                continue;
            }

            if (token.equals("wi")) {
                if (!buffer.isEmpty()) {
                    String catMorse = String.join(" ", buffer);
                    buffer.clear();

                    char letter = toEnglishWord(catMorse);
                    if (capitalizeNext) {
                        result.append(Character.toUpperCase(letter));
                        capitalizeNext = false;
                    } else {
                        result.append(letter);
                    }
                }
                continue;
            }

            if (token.length() == 1 && !Character.isLetterOrDigit(token.charAt(0))) {
                result.append(token);
                continue;
            }

            if (Character.isDigit(token.charAt(0))) {
                result.append(token);
                continue;
            }

            buffer.add(token);
        }

        if (!buffer.isEmpty()) {
            String catMorse = String.join(" ", buffer);
            char letter = toEnglishWord(catMorse);
            if (capitalizeNext) {
                result.append(Character.toUpperCase(letter));
            } else {
                result.append(letter);
            }
        }

        return result.toString();
    }

}