package com.everyoneelseisrobots.algorithm.greedynumberpartioning;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MoteCarloSimulation {

    public static void main(String[] args) {
        int numbersLength = 350; // The size of the random list of numbers to generate in each Monte Carlo run.
        int partitionCount = 20; // The number of partitions to be created in every Monte Carlo run.
        int simulationRuns = 1000; // The number of Monte Carlo iterations to simulate.
        boolean useSimpleImplementation = false; // If true use the simple implementation otherwise use the improved one
        AlgorithmImplementations algorithmImplementations = new AlgorithmImplementations();

        Random random = new Random(System.currentTimeMillis());
        for (int simulationRun = 0; simulationRun < simulationRuns; simulationRun++) {
            List<Integer> numbers = createListOfRandomNumbers(numbersLength, random);

            Collection<AlgorithmImplementations.Partition> partitions =
                    useSimpleImplementation
                            ? algorithmImplementations.simplePartition(numbers, partitionCount)
                            : algorithmImplementations.improvedPartition(numbers, partitionCount);

            Integer sum = sumPartitionSums(partitions);
            Integer min = getMinSum(partitions);
            Integer max = getMaxSum(partitions);
            double average = sum / partitionCount;
            int minMaxDifference = max - min;

            double standardDeviationOfPartitionSums =
                    standardDeviationOfPartitionSums(partitionCount, partitions, average);

            System.out.println(
                    Joiner.on(',').join(sum, min, max, average, minMaxDifference, standardDeviationOfPartitionSums));
        }
    }

    private static double standardDeviationOfPartitionSums(
            int partitionCount, Collection<AlgorithmImplementations.Partition> partitions, double average) {
        return partitions.stream()
                .map(AlgorithmImplementations.Partition::getSum)
                .map(i -> Math.pow(i - average, 2))
                .reduce(0.0, Double::sum)/ partitionCount;
    }

    private static Integer getMaxSum(Collection<AlgorithmImplementations.Partition> partitions) {
        return partitions.stream().map(AlgorithmImplementations.Partition::getSum).max(Integer::compareTo).get();
    }

    private static Integer getMinSum(Collection<AlgorithmImplementations.Partition> partitions) {
        return partitions.stream().map(AlgorithmImplementations.Partition::getSum).min(Integer::compareTo).get();
    }

    private static Integer sumPartitionSums(Collection<AlgorithmImplementations.Partition> partitions) {
        return partitions.stream().map(AlgorithmImplementations.Partition::getSum).reduce(0, Integer::sum);
    }

    private static List<Integer> createListOfRandomNumbers(int numbersLength, Random random) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < numbersLength; i++) {
            int randomInt = random.nextInt(100);
            numbers.add(randomInt);
        }
        return numbers;
    }
}
