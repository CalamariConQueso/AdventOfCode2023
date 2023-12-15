package day14;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RollingRocks {
    // list of columns
//    List<List<Character>> columns = new ArrayList<>();
    List<String> columnStrings = new ArrayList<>();
//    List<String> tiltedColumns = new ArrayList<>();
    List<String> rowStrings = new ArrayList<>();
//    List<String> tiltedRows = new ArrayList<>();
    public void parse(List<String> inputList) {
        for (int y = 0; y < inputList.size(); y++) {
            char[] row = inputList.get(y).toCharArray();
            for (int x = 0; x < row.length; x++) {
                if (y == 0) {
//                    columns.add(new ArrayList<>());
                    columnStrings.add("");
                }
//                columns.get(x).add(row[x]);
                String c = columnStrings.get(x);
                columnStrings.remove(x);
                columnStrings.add(x, c + Character.toString(row[x]));
            }
        }



        rowStrings = convertColumnsToRows(columnStrings);

        long start = Instant.now().toEpochMilli();

        for (int i = 0; i < 500; i++) {
            cycle();
        }
        List<String> savedCycleState = new ArrayList<String>(rowStrings);
        long l = 0;
        for (l = 0; l < 999999500; l++) {
            cycle();
            if (rowStringsMatch(savedCycleState)) {
                System.out.println("Found loop at index: " + l);
                break;
            }
        }

        int r = 999999499 % 21;
        System.out.println("remaining: " + r);
        for (int i = 0; i < r; i++) {
            cycle();
        }

        // number of cycles = (500 + l+1)


        for (String row : rowStrings) {
            System.out.println(row);
        }


        int load = 0;
        for (String column : columnStrings) {
            load += getColumnLoad(column);
        }
        System.out.println("Load: " + load);

        System.out.println("Finished in " + ((Instant.now().toEpochMilli() - start) / 1000) + " seconds.");
    }

    private boolean rowStringsMatch(List<String> oneCycleState) {
        for (int i = 0; i < rowStrings.size(); i++) {
            if (!rowStrings.get(i).equals(oneCycleState.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void cycle() {
        tiltNorthOrWest(true);
        tiltNorthOrWest(false);
        tiltSouthOrEast(true);
        tiltSouthOrEast(false);
    }

    private int getColumnLoad(String column) {
        int currentLoadForSpace = column.length();
        int load = 0;
        char[] chars = column.toCharArray();
        for (char c : chars) {
            switch(c) {
                case 'O' -> {
                    load += currentLoadForSpace;
                }
            }
            currentLoadForSpace--;
        }
        return load;
    }

    private void tiltNorthOrWest(boolean north) {
        if (north) {
            List<String> tiltedColumns = new ArrayList<>();
            for (String column : columnStrings) {
                tiltedColumns.add(rollNorthOrWest(column));
            }
            columnStrings = new ArrayList<String>(tiltedColumns);
            rowStrings = convertColumnsToRows(columnStrings);
        } else {
            // west
            List<String> tiltedRows = new ArrayList<>();
            for (String row : rowStrings) {
                tiltedRows.add(rollNorthOrWest(row));
            }
            rowStrings = new ArrayList<String>(tiltedRows);
            columnStrings = convertRowsToColumns(rowStrings);
        }
    }
    private String rollNorthOrWest(String column) {
        if (!column.contains(".O")) {
            return column;
        } else {
            return rollNorthOrWest(column.replaceAll("\\.O", "O\\."));
        }
    }
    private void tiltSouthOrEast(boolean south) {
        if (south) {
            List<String> tiltedColumns = new ArrayList<>();
            for (String column : columnStrings) {
                tiltedColumns.add(rollSouthOrEast(column));
            }
            columnStrings = new ArrayList<String>(tiltedColumns);
            rowStrings = convertColumnsToRows(columnStrings);
        } else {
            // east
            List<String> tiltedRows = new ArrayList<>();
            for (String row : rowStrings) {
                tiltedRows.add(rollSouthOrEast(row));
            }
            rowStrings = new ArrayList<String>(tiltedRows);
            columnStrings = convertRowsToColumns(rowStrings);
        }
    }
    private String rollSouthOrEast(String column) {
        if (!column.contains("O.")) {
            return column;
        } else {
            return rollSouthOrEast(column.replaceAll("O\\.", "\\.O"));
        }
    }

    private List<String> convertColumnsToRows(List<String> columns) {
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < columns.get(0).length(); i++) {
            String rowString = "";
            for (String column : columns) {
                rowString += Character.toString(column.charAt(i));
            }
            rows.add(rowString);
        }
        return rows;
    }

    private List<String> convertRowsToColumns(List<String> rows) {
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String columnString = "";
            for (String row : rows) {
                columnString += Character.toString(row.charAt(i));
            }
            columns.add(columnString);
        }
        return columns;
    }

//    private void printGrid() {
//        // print the current grid
//        int rows = columns.get(0).size();
//        System.out.println("Number of Rows: " + rows);
//
//        for (int y = 0; y < rows; y++) {
//            String rowString = "";
//            for (List<Character> column : columns) {
//                rowString += Character.toString(column.get(y));
//            }
//            System.out.println(rowString);
//        }
//    }
}
