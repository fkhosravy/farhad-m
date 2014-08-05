package com.vas.util;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Author: M.Mohseni Email:mohseni.mehdi@gmail.com
 * Date: 1/19/13 1:28 AM
 */
public class RandomGenerator {
    private static Logger logger = Logger.getLogger(RandomGenerator.class);

    Random _randomGenerator = new Random();

    static double VARIANCE = 5.0f;

    public int getRandomTO(int upperLimit) {
        return _randomGenerator.nextInt(upperLimit) + 1;
    }

    public boolean isPercentOccurred(int percent) {
        int roll = _randomGenerator.nextInt(100) + 1;
        return roll <= percent;
    }

    private double getGaussian(double aMean, double aVariance) {
        return aMean + _randomGenerator.nextGaussian() * aVariance;
    }

    public static void main(String[] args) {
        int trueCount = 0;
        int falseCount = 0;

        RandomGenerator r = new RandomGenerator();
        for (int i = 0; i < 100000; i++) {
            //System.out.println("r.getGaussian(0,.5) = " + r.getGaussian(1, VARIANCE));
//            System.out.println("r.getGaussian(0,.5) = " + r._randomGenerator.nextGaussian());
//            System.out.println("r.getGaussian(0,.5) = " + r._randomGenerator.nextInt());
            //System.out.println("r.getGaussian(0,.5) = " + r.getRandomTO(10));


            System.out.println("r.getRandomTO(10) = " + r.getRandomTO(999));

            if (r.isPercentOccurred(70))
                trueCount++;
            else
                falseCount++;

        }

        System.out.println("trueCount = " + trueCount);
        System.out.println("falseCount = " + falseCount);
    }
}
