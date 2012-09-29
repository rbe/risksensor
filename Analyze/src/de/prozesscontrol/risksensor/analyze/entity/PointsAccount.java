package de.prozesscontrol.risksensor.analyze.entity;

import javax.persistence.Embeddable;

@Embeddable
public class PointsAccount {

    private int numberOfSpeedUpEventsSecond;

    private double speedUpPointsSecond;

    private int numberOfBrakeEventsSecond;

    private double brakePointsSecond;

    private int numberOfLeftCurveEventsSecond;

    private double leftCurvePointsSecond;

    private int numberOfRightCurveEventsSecond;

    private double rightCurvePointsSecond;

    private int numberOfBumpEventsSecond;

    private double bumpPointsSecond;

    private int numberOfPotHoleEventsSecond;

    private double potHolePointsSecond;

    private int numberOfEventsSecond;

    private double pointsSecond;

    private int numberOfSpeedUpEventsSum;

    private double speedUpPointsSum;

    private int numberOfBrakeEventsSum;

    private double brakePointsSum;

    private int numberOfLeftCurveEventsSum;

    private double leftCurvePointsSum;

    private int numberOfRightCurveEventsSum;

    private double rightCurvePointsSum;

    private int numberOfBumpEventsSum;

    private double bumpPointsSum;

    private int numberOfPotHoleEventsSum;

    private double potHolePointsSum;

    private int numberOfEventsSum;

    private double pointsSum;

    private int numberEventsCredited;

    private double pointsCredited;

    public PointsAccount() {
    }

    public void addSpeedupPointsSecond(double speedUpPointsSecond) {
        this.speedUpPointsSecond = speedUpPointsSecond;
        numberOfSpeedUpEventsSecond++;
        numberOfSpeedUpEventsSum++;
        numberOfEventsSum++;
        pointsSecond += speedUpPointsSecond;
        speedUpPointsSum += speedUpPointsSecond;
        pointsSum += speedUpPointsSecond;
    }

    public double getSpeedUpPointsSecond() {
        return speedUpPointsSecond;
    }

    public void addBrakePointsSecond(double brakePointsSecond) {
        this.brakePointsSecond = brakePointsSecond;
        numberOfBrakeEventsSecond++;
        numberOfBrakeEventsSum++;
        numberOfEventsSum++;
        pointsSecond += brakePointsSecond;
        brakePointsSum += brakePointsSecond;
        pointsSum += brakePointsSecond;
    }

    public double getBrakePointsSecond() {
        return brakePointsSecond;
    }

    public void addLeftCurvePointsSecond(double leftCurvePointsSecond) {
        this.leftCurvePointsSecond = leftCurvePointsSecond;
        numberOfLeftCurveEventsSecond++;
        numberOfLeftCurveEventsSum++;
        numberOfEventsSum++;
        pointsSecond += leftCurvePointsSecond;
        leftCurvePointsSum += leftCurvePointsSecond;
        pointsSum += leftCurvePointsSecond;
    }

    public double getLeftCurvePointsSecond() {
        return leftCurvePointsSecond;
    }

    public void addRightCurvePointsSecond(double rightCurvePointsSecond) {
        this.rightCurvePointsSecond = rightCurvePointsSecond;
        numberOfRightCurveEventsSecond++;
        numberOfRightCurveEventsSum++;
        numberOfEventsSum++;
        pointsSecond += rightCurvePointsSecond;
        rightCurvePointsSum += rightCurvePointsSecond;
        pointsSum += rightCurvePointsSecond;
    }

    public double getRightCurvePointsSecond() {
        return rightCurvePointsSecond;
    }

    public void addBumpPointsSecond(double bumpPointsSecond) {
        this.bumpPointsSecond = bumpPointsSecond;
        numberOfBrakeEventsSecond++;
        numberOfBrakeEventsSum++;
        numberOfEventsSum++;
        pointsSecond += bumpPointsSecond;
        bumpPointsSum += bumpPointsSecond;
        pointsSum += bumpPointsSecond;
    }

    public double getBumpPointsSecond() {
        return bumpPointsSecond;
    }

    public void addPotholePointsSecond(double potholePointsSecond) {
        this.potHolePointsSecond = potholePointsSecond;
        numberOfPotHoleEventsSecond++;
        numberOfPotHoleEventsSum++;
        numberOfEventsSum++;
        pointsSecond += potholePointsSecond;
        potHolePointsSum += potholePointsSecond;
        pointsSum += potholePointsSecond;
    }

    public double getPotHolePointsSecond() {
        return potHolePointsSecond;
    }

    public double getSpeedUpPointsSum() {
        return speedUpPointsSum;
    }

    public double getBrakePointsSum() {
        return brakePointsSum;
    }

    public double getLeftCurvePointsSum() {
        return leftCurvePointsSum;
    }

    public double getRightCurvePointsSum() {
        return rightCurvePointsSum;
    }

    public double getBumpPointsSum() {
        return bumpPointsSum;
    }

    public double getPotHolePointsSum() {
        return potHolePointsSum;
    }

    public void addPointsSecond(double pointsSecond) {
        this.pointsSecond = pointsSecond;
    }

    public double getPointsSecond() {
        return pointsSecond;
    }

    public void addPointsSum(double pointsSum) {
        this.pointsSum = pointsSum;
    }

    public double getPointsSum() {
        return pointsSum;
    }

    public void addNumberOfSpeedUpEventsSecond(int numberOfSpeedUpEventsSecond) {
        this.numberOfSpeedUpEventsSecond = numberOfSpeedUpEventsSecond;
    }

    public int getNumberOfSpeedUpEventsSecond() {
        return numberOfSpeedUpEventsSecond;
    }

    public void addNumberOfBrakeEventsSecond(int numberOfBrakeEventsSecond) {
        this.numberOfBrakeEventsSecond = numberOfBrakeEventsSecond;
    }

    public int getNumberOfBrakeEventsSecond() {
        return numberOfBrakeEventsSecond;
    }

    public void addNumberOfLeftCurveEventsSecond(int numberOfLeftCurveEventsSecond) {
        this.numberOfLeftCurveEventsSecond = numberOfLeftCurveEventsSecond;
    }

    public int getNumberOfLeftCurveEventsSecond() {
        return numberOfLeftCurveEventsSecond;
    }

    public void addNumberOfRightCurveEventSeconds(int numberOfRightCurveEventSeconds) {
        this.numberOfRightCurveEventsSecond = numberOfRightCurveEventSeconds;
    }

    public int getNumberOfRightCurveEventsSecond() {
        return numberOfRightCurveEventsSecond;
    }

    public void addNumberOfBumpEventsSecond(int numberOfBumpEventsSecond) {
        this.numberOfBumpEventsSecond = numberOfBumpEventsSecond;
    }

    public int getNumberOfBumpEventsSecond() {
        return numberOfBumpEventsSecond;
    }

    public void addNumberOfPotholeEventsSecond(int numberOfPotholeEventsSecond) {
        this.numberOfPotHoleEventsSecond = numberOfPotholeEventsSecond;
    }

    public int getNumberOfPotHoleEventsSecond() {
        return numberOfPotHoleEventsSecond;
    }

    public void addNumberOfEventsSecond(int numberOfEventsSecond) {
        this.numberOfEventsSecond = numberOfEventsSecond;
    }

    public int getNumberOfEventsSecond() {
        return numberOfEventsSecond;
    }

    public void addNumberOfSpeedUpEventsSum(int numberOfSpeedUpEventsSum) {
        this.numberOfSpeedUpEventsSum = numberOfSpeedUpEventsSum;
    }

    public int getNumberOfSpeedUpEventsSum() {
        return numberOfSpeedUpEventsSum;
    }

    public void addNumberOfBrakeEventsSum(int numberOfBrakeEventsSum) {
        this.numberOfBrakeEventsSum = numberOfBrakeEventsSum;
    }

    public int getNumberOfBrakeEventsSum() {
        return numberOfBrakeEventsSum;
    }

    public void addNumberOfLeftCurveEventsSum(int numberOfLeftCurveEventsSum) {
        this.numberOfLeftCurveEventsSum = numberOfLeftCurveEventsSum;
    }

    public int getNumberOfLeftCurveEventsSum() {
        return numberOfLeftCurveEventsSum;
    }

    public void addNumberOfRightCurveEventsSum(int numberOfRightCurveEventsSum) {
        this.numberOfRightCurveEventsSum = numberOfRightCurveEventsSum;
    }

    public int getNumberOfRightCurveEventsSum() {
        return numberOfRightCurveEventsSum;
    }

    public void addNumberOfBumpEventsSum(int numberOfBumpEventsSum) {
        this.numberOfBumpEventsSum = numberOfBumpEventsSum;
    }

    public int getNumberOfBumpEventsSum() {
        return numberOfBumpEventsSum;
    }

    public void addNumberOfPotholeEventsSum(int numberOfPotholeEventsSum) {
        this.numberOfPotHoleEventsSum = numberOfPotholeEventsSum;
    }

    public int getNumberOfPotHoleEventsSum() {
        return numberOfPotHoleEventsSum;
    }

    public void addNumberOfEventsSum(int numberOfEventsSum) {
        this.numberOfEventsSum = numberOfEventsSum;
    }

    public int getNumberOfEventsSum() {
        return numberOfEventsSum;
    }

    public int getNumberEventsCredited() {
        return numberEventsCredited;
    }

    public double getPointsCredited() {
        return pointsCredited;
    }

    public void resetNumberOfEventsSecond() {
        // NumberOf...
        numberOfSpeedUpEventsSecond = 0;
        numberOfBrakeEventsSecond = 0;
        numberOfLeftCurveEventsSecond = 0;
        numberOfRightCurveEventsSecond = 0;
        numberOfBumpEventsSecond = 0;
        numberOfPotHoleEventsSecond = 0;
    }

    public void resetPointsSecond() {
        // ...PointsSecond
        speedUpPointsSecond = 0;
        brakePointsSecond = 0;
        leftCurvePointsSecond = 0;
        rightCurvePointsSecond = 0;
        bumpPointsSecond = 0;
        potHolePointsSecond = 0;
        pointsSecond = 0;
    }

    public void resetSecond() {
        resetNumberOfEventsSecond();
        resetPointsSecond();
    }

    public void subtract(double points) {
        speedUpPointsSum -= points;
        if (speedUpPointsSum < 0) {
            speedUpPointsSum = 0;
        }
        brakePointsSum -= points;
        if (brakePointsSum < 0) {
            brakePointsSum = 0;
        }
        leftCurvePointsSum -= points;
        if (leftCurvePointsSum < 0) {
            leftCurvePointsSum = 0;
        }
        rightCurvePointsSum = -points;
        if (rightCurvePointsSum < 0) {
            rightCurvePointsSum = 0;
        }
        bumpPointsSum -= points;
        if (bumpPointsSum < 0) {
            bumpPointsSum = 0;
        }
        potHolePointsSum -= points;
        if (potHolePointsSum < 0) {
            potHolePointsSum = 0;
        }
        pointsSum -= points;
        if (pointsSum < 0) {
            pointsSum = 0;
        } else {
            //
            numberEventsCredited++;
            pointsCredited += points;
        }
    }

}
