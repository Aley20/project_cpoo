
public class Jeu {
	public static void main(String[] args) {
		//GameFrame g=new GameFrame();
		try {
			new PrincipalMenu().frame.setLocationRelativeTo(null);
		} catch (Exception e) {
			System.err.println("EXCEPTION DETECTED");
		}
		
	}

}
