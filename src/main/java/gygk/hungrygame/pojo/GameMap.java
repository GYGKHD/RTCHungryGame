package gygk.hungrygame.pojo;

import java.util.List;

public class GameMap {
    private String name;
    private int status;
    private List<String> location;
    private List<String> chestLocation;
    private List<String> brewingLocation;
    private List<String> player;
    private List<String> playPlayer;
    private List<String> livingPlayer;
    private List<String> diePlayer;
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public GameMap() {
    }

    public List<String> getBrewingLocation() {
        return brewingLocation;
    }

    public void setBrewingLocation(List<String> brewingLocation) {
        this.brewingLocation = brewingLocation;
    }

    public List<String> getPlayPlayer() {
        return playPlayer;
    }

    public void setPlayPlayer(List<String> playPlayer) {
        this.playPlayer = playPlayer;
    }

    public List<String> getChestLocation() {
        return chestLocation;
    }

    public void setChestLocation(List<String> chestLocation) {
        this.chestLocation = chestLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public List<String> getPlayer() {
        return player;
    }

    public void setPlayer(List<String> player) {
        this.player = player;
    }

    public List<String> getLivingPlayer() {
        return livingPlayer;
    }

    public void setLivingPlayer(List<String> livingPlayer) {
        this.livingPlayer = livingPlayer;
    }

    public List<String> getDiePlayer() {
        return diePlayer;
    }

    public void setDiePlayer(List<String> diePlayer) {
        this.diePlayer = diePlayer;
    }
}
