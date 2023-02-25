package byow.Core;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over.
 *  An integer has to be provided in the argument interface, which will be
 *  used as a seed for random number generation in the byow.Core.Engine class.
 */

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Please provide a seed number");
        }

        int seed = Integer.parseInt(args[0]);
        Engine engine = new Engine(seed);
        engine.interactWithKeyboard();
    }
}
