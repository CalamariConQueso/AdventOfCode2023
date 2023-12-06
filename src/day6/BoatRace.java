package day6;

import java.util.ArrayList;
import java.util.List;

public class BoatRace {

    List<Long> raceTimes = new ArrayList<>();
    List<Long> recordDistances = new ArrayList<>();
    List<RaceSimulation> races = new ArrayList();
    public void parse(List<String> raceStatistics) {
        for (String line : raceStatistics) {
            String[] lineParts = line.split("\\s+");
            if (lineParts[0].startsWith("Time:")) {
                for(int i = 1; i < lineParts.length; i++) {
                    raceTimes.add(Long.parseLong(lineParts[i]));
                }
            } else {
                for(int i = 1; i < lineParts.length; i++) {
                    recordDistances.add(Long.parseLong(lineParts[i]));
                }
            }
        }
        for (int i = 0; i < raceTimes.size(); i++) {
            RaceSimulation simulation = new RaceSimulation(raceTimes.get(i), recordDistances.get(i));
            races.add(simulation);
        }

        Integer part1Answer = 0;
        for (RaceSimulation sim : races) {
            if (part1Answer == 0) {
                part1Answer = sim.getWinningChargeTimes().size();
            } else {
                part1Answer *= sim.getWinningChargeTimes().size();
            }
        }
        System.out.println(part1Answer);

        // Part 2
        String part2RaceTime = "";
        String part2RecordDistance = "";
        for (int i = 0; i < raceTimes.size(); i++) {
            part2RaceTime += Long.toString(raceTimes.get(i));
            part2RecordDistance += Long.toString(recordDistances.get(i));
        }
        Long part2Time = Long.parseLong(part2RaceTime);
        Long part2Distance = Long.parseLong(part2RecordDistance);

        RaceSimulation part2RaceSim = new RaceSimulation(part2Time, part2Distance);

        System.out.println(part2RaceSim.getWinningChargeTimes().size());

    }

    class RaceSimulation {
        private final Long raceTime;
        private final Long recordDistance;
        public RaceSimulation(Long raceTime, Long distance) {
            this.raceTime = raceTime;
            this.recordDistance = distance;
        }

        public List<Long> getWinningChargeTimes() {
            List<Long> winningChargeTimes = new ArrayList();
            // this excludes 0 and raceTime, neither of which can win.
            for (long i = 1; i < raceTime; i++) {
                if (willChargeTimeWin(i)) {
                    winningChargeTimes.add(i);
                }
            }
            return winningChargeTimes;
        }

        private boolean willChargeTimeWin(Long chargeTime) {
            // chargeTime is the Boat's speed per millisecond.
            Long remainingRaceTime = raceTime - chargeTime;
            return (remainingRaceTime * chargeTime) > recordDistance;
        }
    }
}
