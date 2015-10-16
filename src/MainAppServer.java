import processing.core.PApplet;


public class MainAppServer extends PApplet{	
	Servidor server;	
	@Override
	public void setup() {		
		server = new Servidor(this);
		server.start();
	}	
}
