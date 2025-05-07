import java.util.*;
import javax.swing.*;
import java.awt.*;

public class CatConverter {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CatConverter::createAndShowGUI);
    }

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

    private static String toCatMorse(char c) {
        return CAT_MORSE_MAP.getOrDefault(Character.toLowerCase(c), "?");
    }

    public static String toEnglish(String catStr) {
        StringBuilder result = new StringBuilder();
        String[] tokens = catStr.trim().split("\\s+");
        boolean capitalizeNext = false;
        ArrayList<String> buffer = new ArrayList<>();

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

    private static char toEnglishWord(String catMorse) {
        return ENGLISH_CAT_MAP.getOrDefault(catMorse, '?');
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ฅ^•ﻌ•^ฅ  Cat Morse Translator  🐾");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(255, 245, 250)); // pastel pink bg

        // Font and Color
        Font font = new Font("Comic Sans MS", Font.PLAIN, 16);
        Color pastelPink = new Color(255, 230, 250);
        Color pastelPurple = new Color(240, 220, 255);
        Color white = Color.WHITE;

        // Title label
        JLabel title = new JLabel("(=^-ω-^=) Welcome to the Cat Morse Translator! (=^-ω-^=)", JLabel.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        title.setForeground(new Color(180, 100, 200));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Text Areas
        JTextArea inputArea = new JTextArea(7, 40);
        JTextArea outputArea = new JTextArea(7, 40);
        inputArea.setFont(font);
        outputArea.setFont(font);
        inputArea.setBackground(pastelPink);
        outputArea.setBackground(pastelPurple);
        outputArea.setEditable(false);
        inputArea.setLineWrap(true);
        outputArea.setLineWrap(true);

        // Scroll panes with borders
        JScrollPane inputScroll = new JScrollPane(inputArea);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        inputScroll.setBorder(BorderFactory.createTitledBorder("🐾 Type English or Cat Morse"));
        outputScroll.setBorder(BorderFactory.createTitledBorder("🐱 Translation Output"));

        // Buttons
        JButton toCatBtn = new JButton("Meowify! (English -> Cat)");
        JButton toEnglishBtn = new JButton("Translate to Hooman :0 (Cat -> English)");
        JButton clearBtn = new JButton("Clean the Litter Box!");

        toCatBtn.setFont(font);
        toEnglishBtn.setFont(font);
        clearBtn.setFont(font);
        toCatBtn.setBackground(white);
        toEnglishBtn.setBackground(white);
        clearBtn.setBackground(white);

        // Button actions
        toCatBtn.addActionListener(e -> {
            String input = inputArea.getText().trim();
            if (!input.isEmpty()) {
                outputArea.setText(toCat(input));
            }
        });

        toEnglishBtn.addActionListener(e -> {
            String input = inputArea.getText().trim();
            if (!input.isEmpty()) {
                outputArea.setText(toEnglish(input));
            }
        });

        clearBtn.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
        });

        // Layout panels
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(inputScroll);
        centerPanel.add(outputScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(toCatBtn);
        buttonPanel.add(toEnglishBtn);
        buttonPanel.add(clearBtn);

        // Add everything to frame
        frame.add(title, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}