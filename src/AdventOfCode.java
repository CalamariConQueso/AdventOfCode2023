import aoc2024.day1.ListCalculator;
import aoc2023.day1.CalibrationParser;
import aoc2023.day10.PipeLoop;
import aoc2023.day11.GalaxyMap;
import aoc2023.day12.SpringRecord;
import aoc2023.day14.RollingRocks;
import aoc2023.day16.LightGrid;
import aoc2023.day2.CubeGame;
import aoc2023.day23.HikingMap;
import aoc2023.day3.PartNumberScanner;
import aoc2023.day4.ScratchCardParser;
import aoc2023.day5.Gardener;
import aoc2023.day6.BoatRace;
import aoc2023.day7.CamelCards;
import aoc2023.day8.DesertMap;
import aoc2023.day9.OasisParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCode {
    public static void main(String[] args) {
        // aoc2023.day1();
        // aoc2023.day2();
        // aoc2023.day3();
        // aoc2023.day4();
        // aoc2023.day5();
        // aoc2023.day6();
        // aoc2023.day7();
        // aoc2023.day8();
        // aoc2023.day9();
        // todo: finish aoc2023.day10 part 2
        // aoc2023.day10();
        // aoc2023.day11(); // todo: do this
        // aoc2023.day12(); // todo: do this
        // day13(); // todo: do this
        // aoc2023.day14();
//        day15(); // todo: yeah..
        // aoc2023.day16();
//        aoc2023.day23();
        day1_2024();
    }

    public static void day1_2024() {
        List<String> inputList = getFileLines("2024/day1.txt");

        ListCalculator calc = new ListCalculator(inputList);
    }

    public static void day23() {
        List<String> inputList = getFileLines("2023/day23.txt");

        HikingMap trails = new HikingMap();
        trails.parse(inputList);
    }

    public static void day16() {
        List<String> inputList = getFileLines("2023/day16.txt");

        LightGrid grid = new LightGrid();
        grid.parse(inputList);
    }

    public static void day15() {

    }

    public static void day14() {
        List<String> inputList = getFileLines("2023/day14.txt");

        RollingRocks rollingRocks = new RollingRocks();
        rollingRocks.parse(inputList);
    }

    public static void day13() {

    }

    public static void day12() {
        List<String> inputList = getFileLines("2023/day12.txt");

        SpringRecord record = new SpringRecord();
        record.parse(inputList);
    }

    public static void day11() {
        List<String> inputList = getFileLines("2023/day11.txt");

        GalaxyMap map = new GalaxyMap();
        map.parse(inputList);

    }

    public static void day10() {
        List<String> pipeList = getFileLines("2023/day10.txt");

        PipeLoop pipeLoop = new PipeLoop();
        pipeLoop.parse(pipeList);
    }

    public static void day9() {
        List<String> readings = getFileLines("2023/day9.txt");

        OasisParser parser = new OasisParser();
        parser.parse(readings);
    }

    public static void day8() {
        List<String> directions = getFileLines("2023/day8.txt");

        DesertMap map = new DesertMap();
        map.parse(directions);
    }

    private static void day7() {
        List<String> cardGames = getFileLines("2023/day7.txt");

        CamelCards camelCards = new CamelCards();
        camelCards.play(cardGames);
    }

    private static void day6() {
        List<String> raceStatistics = getFileLines("2023/day6.txt");

        BoatRace boatRace = new BoatRace();
        boatRace.parse(raceStatistics);
    }

    private static void day5() {
        List<String> instructions = getFileLines("2023/day5.txt");

        Gardener gardener = new Gardener();
        gardener.parse(instructions);
    }

    private static void day4() {
        List<String> scratchCards = getFileLines("2023/day4.txt");

        ScratchCardParser lottoMachine = new ScratchCardParser();
        lottoMachine.parse(scratchCards);
    }

    private static void day3() {
        List<String> schematicLines = getFileLines("2023/day3.txt");

        PartNumberScanner scanner = new PartNumberScanner();
        scanner.parse(schematicLines);
    }

    private static void day2() {
        List<String> gameRecords = getFileLines("2023/day2.txt");

        CubeGame cubeGame = new CubeGame(12, 13, 14);
        cubeGame.parse(gameRecords);
    }

    private static void day1() {
        List<String> calibrationLines = getFileLines("2023/day1.txt");

        CalibrationParser parser = new CalibrationParser();
        parser.parse(calibrationLines);
    }


    private static List<String> getFileLines(String fileName) {
        BufferedReader reader;
        List<String> fileLines = new ArrayList<>();

        String baseDir = "resources/";
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
