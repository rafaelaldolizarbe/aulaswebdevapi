package web.aula2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.aula2.entity.Hero;
import web.aula2.service.HeroService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/herois")
public class HeroController {
    private HeroService service = new HeroService();
    private List<Hero> listaherois = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Hero>> listar() {
        if (listaherois.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaherois);
    }

    @GetMapping("/{index}")
    public ResponseEntity<Hero> recuperarHeroi(@PathVariable int index) {
        if (listaherois.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else if (service.isIndiceValido(index,listaherois)) {
            return ResponseEntity.status(200).body(listaherois.get(index));
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping()
    public ResponseEntity<Hero> adicionarHeroi(@RequestBody Hero heroi) {
        if (service.isValidBlank(heroi.getNome())&&service.isValidBlank(heroi.getHabilidade())
                &&service.maiorQuZer(heroi.getIdade())&&service.maiorMenorQ100(heroi.getForca())){
            listaherois.add(heroi);
            return ResponseEntity.status(201).body(heroi);
        }
        return ResponseEntity.status(404).build();
    }
    @PutMapping("/{index}")
    public  ResponseEntity<Hero> retificarHeroi(@PathVariable int index,@RequestBody Hero heroi){
        if (service.isValidBlank(heroi.getNome())&&service.isValidBlank(heroi.getHabilidade())
                &&service.maiorQuZer(heroi.getIdade())&&service.maiorMenorQ100(heroi.getForca())){
            listaherois.set(index,heroi);
            return ResponseEntity.status(201).body(heroi);
        }
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Boolean> removerHeroi(@PathVariable int index) {
        if (service.isIndiceValido(index,listaherois)) {
            listaherois.remove(index);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(400).build();
    }


}
