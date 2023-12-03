import day1.CalibrationParser;
import day2.CubeGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCode {
    public static void main(String[] args) {
//        day1();
        day2();
    }

    private static void day1() {
        List<String> calibrationLines = getFileLines("day1.txt");

        CalibrationParser parser = new CalibrationParser();
        parser.parse(calibrationLines);
    }

    private static void day2() {
        List<String> gameRecords = getFileLines("day2.txt");

        CubeGame cubeGame = new CubeGame(12, 13, 14);
        cubeGame.parse(gameRecords);
    }



    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }


    public static List<String> getFileLines(String fileName) {
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
