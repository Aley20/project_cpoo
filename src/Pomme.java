public class Pomme implements InterfaceSerpent {
    private int pomme; // nombre de pomme mangés

    // Coordonnés de la pomme
    private int pommeX;
    private int pommeY;

    // Coordonnés de la pomme empoisonné
    private int poisonX;
    private int poisonY;
    
    public Pomme(){ }
    
    public int getPommeX(){
        return this.pommeX;
    }
    public int getPommeY(){
        return this.pommeY;
    }
    public int getPoisonX(){
        return this.poisonX;
    }
    public int getPoisonY(){
        return this.poisonY;
    }
    public void setPommeX(int i){
        this.pommeX=i;
    }
    public void setPommeY(int i){
        this.pommeY=i;
    }
    public void setPoisonX(int i){
        this.poisonX=i;
    }
    public void setPoisonY(int i){
        this.poisonY=i;
    }
    public int getPomme(){
        return this.pomme;
    }
    public void ajoutPomme(){
        this.pomme++;
    }
    public void reinitialisePomme(){
        this.pomme=0;
    }
}
