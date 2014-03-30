package br.com.sistema.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username = "";
	private String nome = "";
	private String senha;
	private Date dtAlter;
	private int status;
	private String email;
	private Date dtAlterSenha;
	private String flProibaltSenha;
	private String idioma;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "GrupoUsuario",
			  joinColumns = @JoinColumn(name = "usuario_id"),
			  inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos;

	public Usuario() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDtAlter() {
		return dtAlter;
	}

	public void setDtAlter(Date dtAlter) {
		this.dtAlter = dtAlter;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDtAlterSenha() {
		return dtAlterSenha;
	}

	public void setDtAlterSenha(Date dtAlterSenha) {
		this.dtAlterSenha = dtAlterSenha;
	}

	public String getFlProibaltSenha() {
		return flProibaltSenha;
	}

	public void setFlProibaltSenha(String flProibaltSenha) {
		this.flProibaltSenha = flProibaltSenha;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public List<Grupo> getGrupos() {
		if (grupos == null) {
			grupos = new ArrayList<Grupo>();
		}
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
