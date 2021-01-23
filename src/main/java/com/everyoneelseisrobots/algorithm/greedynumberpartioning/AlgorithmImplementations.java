package com.everyoneelseisrobots.algorithm.greedynumberpartioning;

import java.util.*;

import static java.util.Collections.reverseOrder;

public class AlgorithmImplementations {

    private final PartitionComparator partitionComparator;

    AlgorithmImplementations() {
        this.partitionComparator = new PartitionComparator();
    }

    Collection<Partition> improvedPartition(Collection<Integer> numbers, int partitionCount) {
        List<Integer> numbersCopy = new ArrayList<>(numbers);

        Collections.sort(numbersCopy, reverseOrder());
        Queue<Integer> numberQueue = new ArrayDeque<>(numbersCopy);

        PriorityQueue<Partition> partitionPriorityQueue =
                new PriorityQueue<>(partitionCount, partitionComparator);
        for (int i = 0; i < partitionCount; i++) {
            partitionPriorityQueue.add(new Partition());
        }

        while (!numberQueue.isEmpty()) {
            Integer number = numberQueue.poll();
            Partition lowestSumPartition = partitionPriorityQueue.poll();
            lowestSumPartition.increaseSum(number);
            partitionPriorityQueue.add(lowestSumPartition);
        }
        return partitionPriorityQueue;
    }

    Collection<Partition> simplePartition(Collection<Integer> numbers, int partitionCount) {
        Queue<Integer> numberQueue = new ArrayDeque<>(numbers);
        List<Partition> partitionsList = new ArrayList<>();
        for (int i = 0; i < partitionCount; i++) {
            partitionsList.add(new Partition());
        }

        while (!numberQueue.isEmpty()) {
            Integer number = numberQueue.poll();
            Partition lowestSumPartition = getLowestSumPartition(partitionsList, partitionComparator);
            lowestSumPartition.increaseSum(number);
        }
        return partitionsList;
    }

    Partition getLowestSumPartition(Collection<Partition> partitions, PartitionComparator partitionComparator) {
        return partitions.stream().min(partitionComparator).get();
    }

    class Partition {

        private int sum;

        Partition() {
            this.sum = 0;
        }

        public void increaseSum(int amount) {
            this.sum += amount;
        }

        public int getSum() {
            return this.sum;
        }
    }

    /**
     * Sorts partitions by their sums in descending order.
     */
    class PartitionComparator implements Comparator<Partition> {
        @Override
        public int compare(Partition partitionA, Partition partitionB) {
            return Integer.compare(partitionA.getSum(), partitionB.getSum());
        }
    }
}
