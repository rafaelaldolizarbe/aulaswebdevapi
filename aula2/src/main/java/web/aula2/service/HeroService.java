package web.aula2.service;
import java.util.List;

import web.aula2.entity.Hero;

public class HeroService {
    public boolean isIndiceValido(int indice, List<Hero> listaherois) { return indice >= 0 && indice < listaherois.size(); }
    public boolean isValidBlank(String validavel) {
        return validavel.length()>3 && !validavel.isBlank();
    }
    public boolean maiorQuZer(int idade ){
        return idade>3;
    }
    public boolean maiorMenorQ100(int idade){
        return idade>3 && idade<100;
    }
}
