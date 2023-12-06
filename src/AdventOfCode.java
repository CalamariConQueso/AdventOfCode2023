import day1.CalibrationParser;
import day2.CubeGame;
import day3.PartNumberScanner;
import day4.ScratchCardParser;
import day5.Gardener;
import day6.BoatRace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCode {
    public static void main(String[] args) {
        // day1();
        // day2();
        // day3();
        // day4();
        // day5();
        day6();
    }

    private static void day6() {
        List<String> raceStatistics = getFileLines("day6.txt");

        BoatRace boatRace = new BoatRace();
        boatRace.parse(raceStatistics);
    }

    private static void day5() {
        List<String> instructions = getFileLines("day5.txt");

        Gardener gardener = new Gardener();
        gardener.parse(instructions);
    }

    private static void day4() {
        List<String> scratchCards = getFileLines("day4.txt");

        ScratchCardParser lottoMachine = new ScratchCardParser();
        lottoMachine.parse(scratchCards);
    }

    private static void day3() {
        List<String> schematicLines = getFileLines("day3.txt");

        PartNumberScanner scanner = new PartNumberScanner();
        scanner.parse(schematicLines);
    }

    private static void day2() {
        List<String> gameRecords = getFileLines("day2.txt");

        CubeGame cubeGame = new CubeGame(12, 13, 14);
        cubeGame.parse(gameRecords);
    }

    private static void day1() {
        List<String> calibrationLines = getFileLines("day1.txt");

        CalibrationParser parser = new CalibrationParser();
        parser.parse(calibrationLines);
    }


    private static List<String> getFileLines(String fileName) {
        BufferedReader reader;
        List<String> fileLines = new ArrayList<>();

        String baseDir = "resources\\";
        try {
            reader = new BufferedReader(new FileReader(baseDir + fileName));
            String line = reader.readLine();
            while (line != null) {
                fileLines.add(line);
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

}
