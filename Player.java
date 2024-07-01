
/*
 * Creates player objects to use in the game. 
 */
public class Player {
    int points;
    String name;

    /*
     * Creates player with name and zero points initially. 
     */
    public Player(String name) {
        this.name = name;
        this.points = 2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        if (points > 0) {
            this.points = points;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.points;
    }

    public void changePoints(int points) {
        this.points += points;
    }
}
