package org.example;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.BotInfo;
import dev.robocode.tankroyale.botapi.Color;
import dev.robocode.tankroyale.botapi.events.BotDeathEvent;
import dev.robocode.tankroyale.botapi.events.RoundEndedEvent;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;

import java.util.Random;

import org.example.Vector2;

public class BTreeBot extends Bot {
    private Random random;

    private ScannedBotEvent lastScannedBot;

    public BTreeBot() {
        super(BotInfo.fromFile("BTreeBot.json"));
        random = new Random();
        random.setSeed(91);
    }

    // Helper methods
    private Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    private boolean canFire() {
        return getGunHeat() < 1e-5;
    }

    private double getNextFirePower() {
        // TODO something better here. We try to fire with energy two if possible,
        // or we give up on firing and hope our opponent runs out of energy
        return getEnergy() > 2.0 ? 2.0 : 0.0;
    }

    // TODO: Wire this up
    // Figure out where a bot would go in the next tick, based on current
    private Vector2 getTargetLocation(ScannedBotEvent trackedEnemyEvent) {
        // TODO: Run this to fixed point? bulletTime should be derived from predictedPosition,
        // not targetPos
        Vector2 targetPos = new Vector2(trackedEnemyEvent.getX(), trackedEnemyEvent.getY());
        Vector2 targetDir = Vector2.fromAngleDegrees(trackedEnemyEvent.getDirection());
        double targetSpeed = trackedEnemyEvent.getSpeed();
        double bulletSpeed = calcBulletSpeed(getNextFirePower());
        double bulletTime = getPosition().subtract(targetPos).magnitude() / bulletSpeed;
        // TODO: Augment this with historical steering information somehow?
        Vector2 predictedPosition = targetPos.add(targetDir.scale(targetSpeed * bulletTime));
        return predictedPosition;
    }

    private void aim() {
        // TODO: Independently move camera and radar
        if (lastScannedBot == null) {
            setTurnGunRight(-getMaxGunTurnRate());
            return;
        }
        Vector2 targetLocation = new Vector2(lastScannedBot.getX(), lastScannedBot.getY());
        // System.out.println("targetLocation: " + targetLocation.toString());
        double currentGunBearing = getGunDirection();
        // System.out.println("currentGunBearing: " + Double.toString(currentGunBearing));
        double targetBearing = targetLocation.subtract(getPosition()).toBearing();
        // System.out.println("targetBearing: " + Double.toString(targetBearing));
        double rightTurnDegrees = -calcDeltaAngle(targetBearing, currentGunBearing);
        // System.out.println("rightTurnDegrees: " + Double.toString(rightTurnDegrees));
        setTurnGunRight(rightTurnDegrees);
        // FIXME: This can be improved, See https://robowiki.net/wiki/Robocode/Game_Physics#Firing_pitfall
        if (canFire() && Math.abs(rightTurnDegrees) < 10) {
            setFire(getNextFirePower());
        }
    }

    private void move() {
        setTargetSpeed(getMaxSpeed() / 2);
        // Steer by randomly walking the neighbourhood of nearby turnrates.
        double nextTurnRate = getTurnRate() + (random.nextGaussian() * 0.05) * getMaxTurnRate();
        setTurnRate(nextTurnRate);
    }

    private void reset() {
        lastScannedBot = null;
        random.setSeed(91);
        // Simplifies turret positioning logic. You can turn this off if you want to try something trickier.
        setAdjustGunForBodyTurn(true);
    }

    @Override
    public void run() {
        reset();
        setBodyColor(Color.ORANGE);
        setGunColor(Color.WHITE);
        while (isRunning()) {
            int turnNumber = getTurnNumber();
            // Hold on to the scanned event for a few turns to adjust the turret
            if (lastScannedBot != null && lastScannedBot.getTurnNumber() + 2 < turnNumber) {
                lastScannedBot = null;
            }
            move();
            aim();
            // Submit moves for current turn, and wait for next turn to start
            go();
        }
    }

    @Override
    public void onScannedBot(ScannedBotEvent event) {
        lastScannedBot = event;
    }

    @Override
    public void onBotDeath(BotDeathEvent event) {
        System.out.println("Bot " + event.getVictimId() + " is out of commission");
    }

    public static void main(String[] args) {
        new BTreeBot().start();
    }
}
