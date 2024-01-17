
public interface SerpentInterface {
    public boolean getgameOver();
    public void play();
    public void move();
    public void verifPomme();
    public void reinitialise();
    public boolean isOnSnake(int x, int y);
    public void augmenteVitesse();
    public void initialiseVitesse();
    public void ajoutPomme();
    public void collision();
}
