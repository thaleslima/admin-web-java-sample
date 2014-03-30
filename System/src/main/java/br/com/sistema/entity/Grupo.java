package br.com.sistema.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	private int teste;
	
	public int getTeste() {
		return teste;
	}

	public void setTeste(int teste) {
		this.teste = teste;
	}

	@ManyToMany
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(name = "GrupoUsuario",
	  joinColumns = @JoinColumn(name = "grupo_id"),
	  inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private List<Usuario> usuarios;
	
	public Grupo()
	{
		
	}
	
	public Grupo(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarios() {
		if (usuarios == null )
		{
			usuarios = new ArrayList<Usuario>();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
