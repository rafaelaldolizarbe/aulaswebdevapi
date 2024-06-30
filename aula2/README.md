# 🦸🏽‍♂️ Aula 2 - Introdução a serviços REST API (Services & Status HTTP)

No contexto de desenvolvimento REST API, é importante entender que o protocolo HTTP é a base para a comunicação entre cliente e servidor. O protocolo HTTP é um protocolo de comunicação que permite a transferência de informações na web. Ele é a base para a comunicação de dados na web e é um protocolo cliente-servidor, o que significa que as solicitações são iniciadas pelo destinatário, geralmente um navegador da web.

### Http Status

No entanto existe uma regulamentação nas respostas possíveis que uma API pode retornar, e isso é feito através de códigos de status HTTP. Os códigos de status HTTP são padrões de três dígitos que indicam o resultado de uma solicitação HTTP. Eles são enviados como parte do cabeçalho da resposta HTTP para informar o cliente sobre o resultado da solicitação feita.

A seguir temos elencado os códigos que correspondem aos status:

- **1xx (Informational)**: Indica que a solicitação foi recebida e o processo está em andamento.
- **2xx (Success)**: Indica que a ação foi concluída com sucesso.
- **3xx (Redirection)**: Indica que o cliente precisa realizar mais ações para concluir a solicitação.
- **4xx (Client Error)**: Indica que ocorreu um erro do lado do cliente.
- **5xx (Server Error)**: Indica que ocorreu um erro do lado do servidor.
- **Transferência de estado**: O cliente pode transferir o estado de uma representação para o servidor, o que permite que o servidor entenda o estado do cliente.

No contexto atual vamos detalhar as famílias de status 2xx e 4xx:
- 201 Created: Indica que a solicitação foi bem-sucedida e um novo recurso foi criado como resultado.
- 204 No Content: Indica que a solicitação foi bem-sucedida, mas não há conteúdo para enviar no corpo da resposta.
- 400 Bad Request: Indica que a solicitação do cliente não pôde ser atendida devido a um erro de sintaxe.
- 401 Unauthorized: Indica que o cliente deve se autenticar para obter a resposta solicitada.
- 403 Forbidden: Indica que o servidor entende a solicitação do cliente, mas se recusa a atendê-la.
- 404 Not Found: Indica que o servidor não pôde encontrar o recurso solicitado.
- 405 Method Not Allowed: Indica que o método de solicitação é conhecido pelo servidor, mas foi desativado e não pode ser usado.
- 409 Conflict: Indica que a solicitação não pôde ser concluída devido a um conflito com o estado atual do recurso.

### Service
Outra parte que também será incluída é a abstração de Services responsáveis por implementar a lógica de negócio da aplicação. Eles são anotados com `@Service` e são injetados nas classes controladoras usando a anotação `@Autowired`. No entanto, como utilizamos um exemplo simples, a lógica de negócio será implementada sem anotações chamando a classe service diretamente e atribuindo a uma variável.

### Response Entity Annotation

A anotação `@ResponseEntity` é usada para retornar uma resposta HTTP personalizada. Ela permite que você defina o status da resposta, o corpo da resposta e os cabeçalhos da resposta. A anotação `@ResponseEntity` é usada em conjunto com os métodos de controlador REST para retornar uma resposta HTTP personalizada.

### Controller comentada
Como exemplo o exercício foi realizado utilizando Herois como tema.
````java
@RestController
@RequestMapping("/herois")
public class HeroController {
    // Instância da classe HeroService
    private HeroService service = new HeroService();
    private List<Hero> listaherois = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Hero>> listar() {
        /* O método isEmpty() verifica se a lista 
        está vazia, ele é um método da classe 
        List.*/
        if (listaherois.isEmpty()) {
            /* Retorna 204 No Content, indica que 
            houve sucesso na requisição e que o 
            conteúdo é vazio */
            return ResponseEntity.status(204).build();
        }
        /* Retorna 200 OK e a lista de herois, 
        pois isso é uma prática recomendada. O 
        responsável pelo front end precisará 
        reutilizar o Body quando a página for 
        atualizada, portanto, retornar a lista é 
        recomendado. */
        return ResponseEntity.status(200).body(listaherois);
    }

    @GetMapping("/{index}")
    public ResponseEntity<Hero> recuperarHeroi(@PathVariable int index) {
        if (listaherois.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else if (service.isIndiceValido(index,listaherois)) {
            return ResponseEntity.status(200).body(listaherois.get(index));
        }
        /* Retorna 404 Not Found, padrão para 
        recurso não encontrado. Recurso que não 
        se encontra presente no banco de dados. */
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
        /* Retorna 400 Bad Request, indica que o 
        indice é inválido, pois irá gerar um 
        erro de sintaxe na requisição.
         */
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
````
### Service comentada

A service abaixo é responsável por implementar a lógica de negócio da aplicação. Note que ela não possuí a anotação `@Service` e é chamada diretamente na classe controladora a partir de uma declaração de variável. Baseado no princípio SOLID, a classe service é responsável por implementar a lógica de negócio da aplicação, enquanto a classe controladora é responsável por receber as requisições HTTP e retornar as respostas.
````java
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
````