import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ControlCliente extends Observable implements Runnable {

	private Socket s;
	private Observer jefe;

	public ControlCliente(Socket s, Observer jefe) {
		this.s = s;
		this.jefe = jefe;
		Thread t =  new Thread(this); 
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				recibirMensajes();
				Thread.sleep(33);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void recibirMensajes() throws IOException {
		DataInputStream dis = new DataInputStream(s.getInputStream());
		String mensaje = dis.readUTF();
		System.out.println("[MENSAJE A RECIBIDO: "+mensaje+"]" );
		jefe.update(this, mensaje);
	}

	public void enviarMensaje(String mensaje) {
		try {
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(mensaje);
			System.out.println("[MENSAJE A ENVIADO: "+mensaje+"]" );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
