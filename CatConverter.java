import java.util.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.datatransfer.*;

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
        // --- Frame Setup ---
        JFrame frame = new JFrame("🐾 Purrfect Cat Morse Translator 🐱");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // --- Fonts ---
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 22);
        Font textFont  = new Font("Comic Sans MS", Font.PLAIN, 16);

        // --- Themes ---
        class Theme {
            Color frameBg, inputBg, outputBg, border, text, btnBg, btnFg;
            Theme(Color frameBg, Color inputBg, Color outputBg, Color border,
                  Color text, Color btnBg, Color btnFg) {
                this.frameBg = frameBg; this.inputBg = inputBg; this.outputBg = outputBg;
                this.border  = border;  this.text    = text;
                this.btnBg   = btnBg;   this.btnFg   = btnFg;
            }
        }
        final Theme light = new Theme(
            new Color(255,245,250), new Color(255,230,250), new Color(240,220,255),
            new Color(200,150,200), Color.BLACK,      Color.WHITE,      new Color(180,100,200)
        );
        final Theme dark  = new Theme(
            new Color( 40, 30, 50), new Color( 70, 50, 90), new Color( 80, 60,100),
            new Color(150,100,170), new Color(255,200,255), new Color(255,240,255), new Color(150, 70,170)
        );
        final Theme[] current = { light };

        // --- Components ---
        JLabel title = new JLabel("(=^-ω-^=) Welcome to the Cat Morse Translator! (=^-ω-^=)", SwingConstants.CENTER);
        title.setFont(titleFont);

        JTextArea inputArea  = new JTextArea(7, 40);
        JTextArea outputArea = new JTextArea(7, 40);

        JScrollPane inScroll  = new JScrollPane(inputArea);
        JScrollPane outScroll = new JScrollPane(outputArea);

        JButton btnToCat     = new JButton("Meowify! (English → Cat)");
        JButton btnToEnglish = new JButton("Translate to Hooman :0 (Cat → English)");
        JButton btnPaste     = new JButton("📥 Paste");
        JButton btnCopy      = new JButton("📋 Copy");
        JButton btnClear     = new JButton("🧼 Clean the Litter Box!");
        JButton btnToggle    = new JButton("🌙 Toggle Night Light");

        // Pack buttons in order
        JButton[] buttons = { btnToCat, btnToEnglish, btnPaste, btnCopy, btnClear, btnToggle };

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton b : buttons) buttonPanel.add(b);

        JPanel centerPanel = new JPanel(new GridLayout(2,1,10,10));
        centerPanel.add(inScroll);
        centerPanel.add(outScroll);

        // --- Theming Routine ---
        Runnable applyTheme = () -> {
            Theme t = current[0];
            frame.getContentPane().setBackground(t.frameBg);

            // Title color
            title.setForeground(t.btnFg);

            // Input area
            inputArea.setBackground(t.inputBg);
            inputArea.setForeground(t.text);
            inputArea.setFont(textFont);
            inScroll.getViewport().setBackground(t.inputBg);

            // Output area
            outputArea.setBackground(t.outputBg);
            outputArea.setForeground(t.text);
            outputArea.setFont(textFont);
            outputArea.setEditable(false);
            outScroll.getViewport().setBackground(t.outputBg);

            // Borders
            inScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(t.border),
                "🐾 Type English or Cat Morse",
                TitledBorder.LEFT, TitledBorder.TOP,
                textFont, t.text));
            outScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(t.border),
                "🐱 Translation Output",
                TitledBorder.LEFT, TitledBorder.TOP,
                textFont, t.text));

            // Buttons
            for (JButton b : buttons) {
                b.setFont(textFont);
                b.setBackground(t.btnBg);
                b.setForeground(t.btnFg);
            }
            buttonPanel.setBackground(t.frameBg);
            centerPanel.setBackground(t.frameBg);
        };

        // --- Actions ---
        btnToCat.addActionListener(e -> {
            String in = inputArea.getText();
            if (!in.isEmpty()) outputArea.setText(toCat(in));
        });
        btnToEnglish.addActionListener(e -> {
            String in = inputArea.getText();
            if (!in.isEmpty()) outputArea.setText(toEnglish(in));
        });
        btnPaste.addActionListener(e -> {
            try {
                String clip = (String) Toolkit.getDefaultToolkit()
                                  .getSystemClipboard()
                                  .getData(DataFlavor.stringFlavor);
                inputArea.setText(clip);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Couldn't paste from clipboard 🐾");
            }
        });
        btnCopy.addActionListener(e -> {
            String txt = outputArea.getText();
            if (!txt.isEmpty()) {
                StringSelection sel = new StringSelection(txt);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
            }
        });
        btnClear.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
        });
        btnToggle.addActionListener(e -> {
            current[0] = (current[0] == light ? dark : light);
            btnToggle.setText(current[0] == light ? "🌙 Toggle Night Light" : "☀️ Toggle Daylight");
            applyTheme.run();
        });

        // --- Layout & Show ---
        frame.add(title, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        applyTheme.run();
        frame.setVisible(true);
    }
}