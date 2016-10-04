package negocio;

import java.util.Collection;

public class Usuario {
	
	private String apellido;
	private int codigo;
	private Collection <EnumRoles> roles; 	
	private String clave;
	private String nombre;
	private String usuario;
	
	public Usuario(int codigo, String apellido, String clave, String nombre, String usuario){
		this.codigo=codigo;
		this.apellido=apellido;
		this.clave=clave;
		this.nombre=nombre;
		this.usuario=usuario;
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
