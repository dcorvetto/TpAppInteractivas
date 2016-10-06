package negocio;

import java.util.Collection;

public class Usuario {
	
	private String nombre;
	private String apellido;
	private Collection <EnumRoles> roles; 	
	private int codigo;
	private String usuario;
	private String clave;
	
	public Usuario(String nombre, String apellido, int codigo, String usuario, String clave) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.codigo = codigo;
		this.usuario = usuario;
		this.clave = clave;
	}
	

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Collection<EnumRoles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<EnumRoles> roles) {
		this.roles = roles;
	}	


}
