package br.com.ifpb.gpsback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpb.gpsback.model.Task;
import br.com.ifpb.gpsback.model.Usuario;
import br.com.ifpb.gpsback.repository.TaskRepository;
import br.com.ifpb.gpsback.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TaskRepository taskRepository;

	// CREATE
	@PostMapping("criarusuario")
	public void novo(@RequestBody Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	// READ ALL
	@GetMapping("listarusuarios")
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	// READ
	@GetMapping(path = { "/{id}" })
	public ResponseEntity findById(@PathVariable long id) {
		return usuarioRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody Usuario usuario) {

		Usuario usuarioDb = usuarioRepository.findById(id).get();

		usuarioDb.setEmail(usuario.getEmail());
		usuarioDb.setName(usuario.getName());
		usuarioDb.setPassword(usuario.getPassword());
		Usuario usuarioAtualizado = usuarioRepository.save(usuarioDb);
		return ResponseEntity.ok().body(usuarioAtualizado);

	}

	// DELETE
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return usuarioRepository.findById(id).map(record -> {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("adicionartask/{id}")
	public String adicionarTaskEmUsuario(@PathVariable long id, @RequestBody Task task) {
		Usuario usuarioDb = usuarioRepository.findById(id).get();
		task.setUsuario(usuarioDb);
		usuarioDb.adicionarTask(task);
		taskRepository.save(task);
		usuarioRepository.save(usuarioDb);
		return "task adicionada com sucesso!";
	}

}
