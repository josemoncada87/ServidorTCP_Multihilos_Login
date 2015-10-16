import processing.core.PApplet;
import processing.data.XML;

public class ControlXMLUsuarios {

	private XML usuarios;
	private PApplet app;

	public ControlXMLUsuarios(PApplet app) {
		this.app = app;
		try {
			usuarios = app.loadXML("../data/BD_usuarios.xml");
		} catch (Exception e) {
			usuarios = app.parseXML("<usuarios></usuarios>");
		}
	}
	
	public boolean agregarUsuario(String usuario, String contrasena) {
		boolean existe = false;
		boolean agregado = false;
		XML[] hijos = usuarios.getChildren("usuario");
		for (int i = 0; i < hijos.length; i++) {
			if (hijos[i].getString("usuario").equals(usuario)) {
				existe = true; // el usuario existe
			}
		}
		if (!existe) {
			XML hijo = app.parseXML("<usuario usuario=\"" + usuario
					+ "\" contrasena=\"" + contrasena + "\"></usuario>");
			usuarios.addChild(hijo);
			app.saveXML(usuarios, "../data/BD_usuarios.xml");
			agregado = true;
		}
		return agregado;
	}

	public int validarUsuario(String usuario, String contrasena) {
		int estadoUsuario = 0; // el usuario no existe
		XML[] hijos = usuarios.getChildren("usuario");
		for (int i = 0; i < hijos.length; i++) {
			if (hijos[i].getString("usuario").equals(usuario)) {				
				if (hijos[i].getString("contrasena").equals(contrasena)) {
					estadoUsuario = 1; // existe y la contrasena correcta
				} else {
					estadoUsuario = 2; // existe pero la contrasena es no correcta
				}
			}
		}
		return estadoUsuario;
	}
}
