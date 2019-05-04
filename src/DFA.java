import javax.swing.*;

public class DFA {

    /**
     * Switch-based implementation of a DFA.
     * The DFA accepts the language L = {a} + {a b a^n b | n >= 0}
     * (see example 7.8 of the lecture slides)
     */
    public static boolean accept1(String word) {
        int pos = 0;
        int state = 0;

        while (pos < word.length()) {
            char ch = word.charAt(pos);
            pos++;

            switch (state) {
                case 0:
                    switch (ch) {
                        case 'a':
                            state = 1;
                            break;
                        default:
                            System.out.println("Error at position " + pos + ": unexpected character 'a'");
                            return false;
                    }
                    break;
                case 1:
                    switch (ch) {
                        case 'b':
                            state = 2;
                            break;
                        default:
                            System.out.println("Error at position " + pos + ": unexpected character 'b'");
                            return false;
                    }

                    break;
                case 2:
                    switch (ch) {
                        case 'a':
                            state = 2;
                            break;
                        case 'b':
                            state = 3;
                            break;
                        default:
                            System.out.println("Error at position " + pos + ": expected 'a' or 'b'");
                            return false;
                    }

                    break;
                case 3:
                    switch (ch) {
                        default:
                            System.out.println("Error at position " + pos + ": end expected");
                            return false;
                    }
            }
        }
        if (isAcceptState(state)) {
            return true;
        } else {
            System.out.println("Error: no accept state reached");
            return false;
        }
    }


    /**
     * Table-based implementation of a DFA
     * The DFA accepts the language L = {a} + {a b a^n b | n >= 0}
     * (see example 7.8 of the lecture slides)
     */
    private final static int ERROR = -1;

    //	         Input       a      b
    private static int[][] delta =
            {
                    {1, ERROR},             // state 0
                    {ERROR, 2},             // state 1
                    {2, 3},                 // state 2
                    {ERROR, ERROR}          // state 3
            };

    private static boolean isAcceptState(int q) {
        return q == 1 || q == 3;
    }

    private static boolean accept2(String word) {
        int pos = 0;
        int state = 0;

        while (pos < word.length()) {
            char ch = word.charAt(pos);
            pos++;

            if (!('a' <= ch && ch <= 'b')) {
                return false; //illegal character
            }

            //look up successor state in the transition table
            state = delta[state][ch - 'a'];

            if (state == ERROR) {
                return false;   //character is not accepted
            }
        }
        return isAcceptState(state); //word is accepted, if accept state is reached at the end
    }


    public static void main(String[] args) {
        String input;

        do {
            input = JOptionPane.showInputDialog("Input: ");

            if (input != null) {
                //if (accept1(input)) {
                if (accept2(input)) {
                    JOptionPane.showMessageDialog(null, "Input '" + input + "' is ACCEPTED");
                } else {
                    JOptionPane.showMessageDialog(null, "Input '" + input + "' is NOT accepted");
                }
            }
        } while (input != null);

    }

}
