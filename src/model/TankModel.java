package model;

import java.util.ArrayList;
import java.util.List;

public class TankModel {
    private List<Tank> players;
    private List<TankBot> tankBots;
    private List<Bullet> bullets;
    private List<ScoreObserver> scoreObservers;
    private List<LiveObserver> liveObservers;

    public TankModel() {
        bullets = new ArrayList<>();
        players = new ArrayList<>();
        tankBots = new ArrayList<>();
        scoreObservers = new ArrayList<>();
        liveObservers = new ArrayList<>();
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void addPlayer(Tank player) {
        players.add(player);
    }

    public void addTankBot(TankBot tankBot) {
        tankBots.add(tankBot);
    }

    public List<Tank> getPlayers() {
        return players;
    }

    public void setPlayers(List<Tank> players) {
        this.players = players;
    }

    public List<TankBot> getTankBots() {
        return tankBots;
    }

    public void setTankBots(List<TankBot> tankBots) {
        this.tankBots = tankBots;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void registerScoreObserver(ScoreObserver observer) {
        scoreObservers.add(observer);
    }

    public void removeScoreObserver(ScoreObserver observer) {
        scoreObservers.remove(observer);
    }

    public void registerLiveObserver(LiveObserver observer) {
        liveObservers.add(observer);
    }

    public void removeLiveObserver(LiveObserver observer) {
        liveObservers.remove(observer);
    }

    public void notifyScoreObservers(String playerName, int score) {
        for (ScoreObserver observer : scoreObservers) {
            observer.updateScore(playerName, score);
        }
    }

    public void notifyLiveObservers(String playerName, int lives) {
        for (LiveObserver observer : liveObservers) {
            observer.updateLives(playerName, lives);
        }
    }
}