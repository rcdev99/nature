package br.com.fatec.les.nature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.fatec.les.nature.dto.Foto;
import br.com.fatec.les.nature.service.ProdutoService;
import br.com.fatec.les.nature.storage.FotoReader;

@RestController
@RequestMapping("/fotos")
public class FotoController {
	
	@Autowired
	private ProdutoService pService;
	
	@Autowired
	private FotoReader fotoReader;

	@RequestMapping(value = "/{produto.id}",method = RequestMethod.POST)
	public Foto upload(@PathVariable("produto.id") Long id, @RequestParam("files[]") MultipartFile[] files) {
		
		String url = pService.salvarFoto(id, files[0]);
		return new Foto(url);
	}
	
	@RequestMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fotoReader.recuperar(nome);
	}
	
}
