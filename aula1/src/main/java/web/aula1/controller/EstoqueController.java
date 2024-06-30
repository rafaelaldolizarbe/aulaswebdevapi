package web.aula1.controller;

import org.springframework.web.bind.annotation.*;
import web.aula1.entity.Estoque;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    private List<Estoque> listaEstoque = new ArrayList<>();

    @GetMapping("/produtos")
    public List<Estoque> listar() {
        if (listaEstoque.size() <= 0) {
            return null;
        }
        return listaEstoque;
    }

    @GetMapping("/produtos/{indice}")
    public Estoque recuperar(@PathVariable int indice) {

        return isIndiceValido(indice) ? listaEstoque.get(indice) : null;

    }

    @PostMapping("/produtos")

    public Estoque cadastrar(@RequestBody Estoque estoque) {

        listaEstoque.add(estoque);
        return estoque;
    }

    @PutMapping("/produtos/{indice}")

    public Estoque atualizar(@PathVariable int indice, @RequestBody Estoque estoqueAtualizado) {

        if (indice >= 0 && indice < listaEstoque.size()) {

            listaEstoque.set(indice, estoqueAtualizado);
            return estoqueAtualizado;
        }
        return null;
    }

    @DeleteMapping("/produtos/{indice}")
    public String remover(@PathVariable int indice) {
        if (indice >= 0 && indice < listaEstoque.size()) {
            listaEstoque.remove(indice);
            return "Removido com sucesso";
        }
        return "Não encontrado";
    }

    @GetMapping("/produtos/estoque/{minEstoque}")
    public List<Estoque> listar(@PathVariable int minEstoque) {
        List<Estoque> listapercorrer = new ArrayList<>();
        if (listaEstoque.size() <= 0) {
            return null;
        } else {
            for (Estoque e : listaEstoque) {
                if (e.getQtdEstoque() >= minEstoque) {
                    listapercorrer.add(e);
                }
            }
        }
        if (listapercorrer.size() <= 0) {
            return null;
        } else {
            return listapercorrer;
        }
    }

    private boolean isIndiceValido(int indice) {
        return indice >= 0 && indice < listaEstoque.size();
    }

}
