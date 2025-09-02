public class GameServerTestDebugger {
    public static void main(String[] args) {
        System.out.println("Testing GameServer error message consistency...");

        // Test 1: Invalid position (too high)
        System.out.println("\n=== Test 1: Position 10 (too high) ===");
        testInput("1\n10\nexit");

        // Test 2: Invalid position (negative)
        System.out.println("\n=== Test 2: Position 0 (too low) ===");
        testInput("1\n0\nexit");

        // Test 3: Non-numeric input
        System.out.println("\n=== Test 3: Non-numeric input ===");
        testInput("1\nabc\nexit");

        // Test 4: Duplicate position
        System.out.println("\n=== Test 4: Duplicate position ===");
        testInput("1\n1\n1\nexit");

        // Test 5: Just exit
        System.out.println("\n=== Test 5: Exit command ===");
        testInput("exit");

        System.out.println("\n=== Expected Messages ===");
        System.out.println("INVALID_INPUT: '" + GameServer.Messages.INVALID_INPUT + "'");
        System.out.println("SHUTDOWN: '" + GameServer.Messages.SHUTDOWN + "'");
    }

    private static void testInput(String input) {
        System.out.println("Input: " + input.replace("\n", "\\n"));

        try {
            // Create a test console with the input
            java.io.ByteArrayInputStream testIn = new java.io.ByteArrayInputStream(input.getBytes());
            java.io.ByteArrayOutputStream testOut = new java.io.ByteArrayOutputStream();
            java.io.PrintStream testPrint = new java.io.PrintStream(testOut);

            Console console = new Console(testIn, testPrint);

            // Run the game server
            GameServer.launchMainWithConsole(console);

            // Show the output
            String output = testOut.toString();
            System.out.println("Output contains INVALID_INPUT: " + output.contains(GameServer.Messages.INVALID_INPUT));
            System.out.println("Output contains SHUTDOWN: " + output.contains(GameServer.Messages.SHUTDOWN));
            System.out.println("Full output:");
            System.out.println(output);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

