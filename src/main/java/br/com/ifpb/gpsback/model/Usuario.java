package br.com.ifpb.gpsback.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String password;

	private String email;

	@Column(name = "task")
	@OneToMany(mappedBy = "usuario", cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private List<Task> tasks = new ArrayList<>();

	@Override
	public String toString() {
		return "Usuario [id= " + id + "," + " name= " + name + "," + " password= " + password + "," + " email= " + email
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void adicionarTask(Task task) {
		tasks.add(task);
	}

	public void removerTask(Task task) {
		tasks.remove(task);
	}

}