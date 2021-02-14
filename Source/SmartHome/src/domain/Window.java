package domain;

public class Window extends Object {

	private Shader shader;
	/*
	 * ricordiamoci che probabilmente shader e' un oggetto
	 */

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

}