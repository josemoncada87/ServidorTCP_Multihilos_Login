import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Servidor extends Thread implements Observer {

	private ServerSocket ss;
	private ArrayList<ControlCliente> clientes;
	private ControlXMLUsuarios cxml;

	public Servidor(PApplet app) {
		cxml = new ControlXMLUsuarios(app);
		clientes = new ArrayList<ControlCliente>();
		try {
			ss = new ServerSocket(5000);
			System.out.println("[ SERVIDOR INICIADO ]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("[ ESPERANDO CLIENTE ]");
				clientes.add(new ControlCliente(ss.accept(), this));
				System.out.println("[ NUEVO CLIENTE ES: " + clientes.size()
						+ " ]");
				sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable observado, Object mensajeString) {
		String notificacion = (String) mensajeString;
		if (notificacion.contains("login_req:")) {
			String[] partes = notificacion.split(":");			
			int resultadoLogin = cxml.validarUsuario(partes[1], partes[2]);
			((ControlCliente)observado).enviarMensaje("login_resp:"+resultadoLogin);			
		}
		if (notificacion.contains("signup_req:")) {
			String[] partes = notificacion.split(":");			
			boolean resultadoAgregar = cxml.agregarUsuario(partes[1], partes[2]);			
			((ControlCliente)observado).enviarMensaje("signup_resp:"+(resultadoAgregar==true?1:0));			
		}
	}
}
