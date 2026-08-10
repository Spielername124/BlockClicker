package me.Spielername124.blockClicker.RewardManagement.Rewards.RewardsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedList<E> {
    private final List<E> listElements;
    private final List<Double> weights;
    private double totalWeight = 0.0;

    //creates a List from already existing Lists
    public WeightedList(List<E> listElements, List<Double> weights) {
        if (listElements.size() != weights.size()) {
            throw new IllegalArgumentException("both List must have the same number of elements");
        }
        this.listElements = new ArrayList<>(listElements);
        this.weights = new ArrayList<>(weights);

        //adds all weights to the totalWeight of the list
        for (double weight : weights) {
            totalWeight += weight;
        }
    }

    //creates an empty list
    public WeightedList() {
        this.listElements = new ArrayList<>();
        this.weights= new ArrayList<>();
    }

    public void addElement(E element, double weight) {
        this.listElements.add(element);
        this.weights.add(weight);
        //update the total weight
        totalWeight += weight;
    }

    public E getRandomElement( ) {
        //roll a weight
        double rolledWeight = ThreadLocalRandom.current().nextDouble(totalWeight);
        return listElements.get(getIndexForWeight(rolledWeight));
    }


    private E pollRandomElement( ) {
        double rolledWeight = ThreadLocalRandom.current().nextDouble(totalWeight);
        int index = getIndexForWeight(rolledWeight);
        E polledElement = listElements.get(index);

        //removes the element from the structure
        totalWeight -= weights.get(index);
        weights.remove(index);
        listElements.remove(index);
        return polledElement;
    }

    public List<E> getXRandomElements(int x) {
        WeightedList<E> temp = new WeightedList<>(listElements, weights);
        ArrayList<E> output = new ArrayList<>();

        //if there are x or less than x elements in the list, just return the listElement list.
        if(temp.size() <= x) {
            return temp.listElements;
        }

        //poll times weighted elements
        for(int i = 0; i < x; i++) {
            output.add(temp.pollRandomElement());
        }

        return output;
    }

    //removes a element in O(1) since we don't care about the ordering in the lists, except that both Lists are ordered the same
    public void remove(int index) {
        int lastElement = this.size()-1;

        //Puts the last Element in the List at in place of the one to remove
        listElements.set(index, listElements.get(lastElement));
        //removes the now duplicated element in O(1)
        listElements.remove(lastElement);

        weights.set(index, weights.get(lastElement));
        weights.remove(lastElement);

        //removes the weight since the total weight is now lower
        totalWeight -= weights.get(lastElement);

    }


    //return the element associated with the weight
    public int getIndexForWeight( double index ) {
        double currentWeight = 0.0;

        //iterates through the pool until it finds the one that was rolled
        for (int i = 0; i < listElements.size(); i++) {
            currentWeight += weights.get(i);
            if (currentWeight >= index) {
                return i;

            }
        }
        return weights.size() - 1;
    }

    public boolean isEmpty() {
        return listElements.isEmpty();
    }

    public int size() {
        return listElements.size();
    }
}
