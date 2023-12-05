package aoc23;

import java.util.ArrayList;
import java.util.List;

public class Ex3 {
    record Grid(List<Symbol> symbols, List<Number> numbers) {
    };

    record Position(int row, int columnIndex) {
    };

    record PositionRange(int row, int startIndex, int endIndex) {
        boolean contains(Position position) {
            return position.row() == row && startIndex <= position.columnIndex()
                    && position.columnIndex() <= endIndex;
        }
    };

    record Number(int value, PositionRange position) {
    };

    record Symbol(Character value, Position position) {

        static boolean isSymbol(Character character) {
            return "@#$%^&*\\\\-=+/".contains("" + character);
        }

        List<Position> getAdjacentPositions() {
            return List.of(new Position(position.row(), position.columnIndex + 1),
                    new Position(position.row(), position.columnIndex - 1),
                    new Position(position.row() - 1, position.columnIndex),
                    new Position(position.row() - 1, position.columnIndex - 1),
                    new Position(position.row() - 1, position.columnIndex + 1),
                    new Position(position.row() + 1, position.columnIndex),
                    new Position(position.row() + 1, position.columnIndex - 1),
                    new Position(position.row() + 1, position.columnIndex + 1));
        }
    };

    public int solveA() {
        List<String> inputLines = Utils.readInputLines("ex3.txt");
        Grid grid = parseGrid(inputLines);
        int result = 0;

        for (Symbol symbol : grid.symbols()) {
            for (Number number : grid.numbers()) {
                if (isAdjacent(symbol, number)) {
                    result += number.value();
                }
            }
        }

        return result;
    }

    public int solveB() {
        List<String> inputLines = Utils.readInputLines("ex3.txt");
        Grid grid = parseGrid(inputLines);
        int result = 0;

        List<Symbol> onlyGearSymbols = grid.symbols().stream().filter(symbol -> symbol.value().equals('*')).toList();

        for (Symbol symbol : onlyGearSymbols) {
            List<Number> matchedNumbers = new ArrayList<>();
            for (Number number : grid.numbers()) {
                if (isAdjacent(symbol, number)) {
                    matchedNumbers.add(number);
                }
            }

            if (matchedNumbers.size() == 2) {
                result += matchedNumbers.get(0).value() * matchedNumbers.get(1).value();
            }
        }

        return result;
    }

    private boolean isAdjacent(Symbol symbol, Number number) {
        return symbol.getAdjacentPositions().stream().anyMatch(adjacent -> number.position().contains(adjacent));
    }

    private Grid parseGrid(List<String> inputLines) {
        List<List<Character>> gridRows = inputLines.stream()
                .map(line -> line.chars().mapToObj(c -> (char) c).toList())
                .toList();

        List<Symbol> symbols = new ArrayList<Symbol>();
        List<Number> numbers = new ArrayList<Number>();

        for (int rowIndex = 0; rowIndex < gridRows.size(); rowIndex++) {
            List<Character> row = gridRows.get(rowIndex);
            for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                Character current = row.get(columnIndex);
                if (Symbol.isSymbol(current)) {
                    symbols.add(new Symbol(current, new Position(rowIndex, columnIndex)));
                } else if (Character.isDigit(current)) {
                    String number = "";
                    int start = columnIndex;
                    int end = columnIndex;
                    Character next = current;
                    while (end < row.size() && !next.equals('.') && !Symbol.isSymbol(next)) {
                        number += next;
                        end++;
                        if (end < row.size()) {
                            next = row.get(end);
                        }
                    }

                    end--;
                    numbers.add(new Number(Integer.valueOf(number), new PositionRange(rowIndex, start, end)));
                    columnIndex = end;
                }
            }
        }

        return new Grid(symbols, numbers);
    }
}
