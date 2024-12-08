package aoc2023.day2;

import java.util.List;

public class CubeGame {
    final int maxRed;
    final int maxGreen;
    final int maxBlue;
    public CubeGame(int red, int green, int blue) {
        maxRed = red;
        maxGreen = green;
        maxBlue = blue;
    }

    public void parse(List<String> fileLines) {
        int total = 0;
        int totalPower = 0;

        for (String line : fileLines) {
            // Strip off "Game "
            line = line.substring(5);

            String[] parts = line.split(": ");
            int gameNo = Integer.parseInt(parts[0]);

            totalPower += getPowerOfMinCubes(gameNo, parts[1]);

            if (isGamePossible(gameNo, parts[1])) {
                total += gameNo;
            }
        }
        System.out.println("Total: " + total);
        System.out.println("Total Power: " + totalPower);
    }

    private int getPowerOfMinCubes(int gameNo, String gameInfo) {
        System.out.println(gameNo + ": " + gameInfo);

        int neededRed = 0;
        int neededGreen = 0;
        int neededBlue = 0;

        String[] handFullsOfCubes = gameInfo.split("; ");

        for (String handFull : handFullsOfCubes) {
            String [] colors = handFull.split(", ");

            for (String color : colors) {
                String[] colorParts = color.split(" ");

                switch (colorParts[1]) {
                    case "red" -> {
                        if (neededRed < Integer.parseInt(colorParts[0])) {
                            neededRed = Integer.parseInt(colorParts[0]);
                        }
                    }
                    case "blue" -> {
                        if (neededBlue < Integer.parseInt(colorParts[0])) {
                            neededBlue = Integer.parseInt(colorParts[0]);
                        }
                    }
                    case "green" -> {
                        if (neededGreen < Integer.parseInt(colorParts[0])) {
                            neededGreen = Integer.parseInt(colorParts[0]);
                        }
                    }
                };
            }
        }

        // Now we have the required numbers of cubes needed of each color for this game to be possible.
        // Return the power!
        int power = neededRed * neededGreen * neededBlue;
        System.out.println("Power: " + power);
        return power;
    }


    private boolean isGamePossible(int gameNo, String gameInfo) {
        System.out.println(gameNo + ": " + gameInfo);

        String[] handFullsOfCubes = gameInfo.split("; ");

        for (String handFull : handFullsOfCubes) {
            String [] colors = handFull.split(", ");

            for (String color : colors) {
                String[] colorParts = color.split(" ");

                int maxToCompare = switch (colorParts[1]) {
                    case "red" -> maxRed;
                    case "blue" -> maxBlue;
                    case "green" -> maxGreen;
                    default -> 0;
                };

                if (Integer.parseInt(colorParts[0]) > maxToCompare) {
                    System.out.println("Game " + gameNo + " is impossible.");
                    return false;
                }
            }
        }
        System.out.println("Game " + gameNo + " is possible.");
        return true;
    }

}
