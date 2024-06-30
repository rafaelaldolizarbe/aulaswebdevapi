# 🏬 Aula 1 - Introdução a serviços REST API (Entities & Controllers)

### O que é REST?
REST é um acrônimo para Representational State Transfer, que é um estilo de arquitetura de software que define um conjunto de restrições para a criação de serviços web. O REST foi definido por Roy Fielding em sua tese de doutorado em 2000.

### Características de um serviço REST

- **Cliente-Servidor**: O cliente e o servidor são separados, o que permite que eles evoluam independentemente.
- **Stateless**: Cada requisição do cliente para o servidor deve conter todas as informações necessárias para que o servidor entenda e responda a requisição. O servidor não deve manter estado de sessão entre requisições.
- **Cache**: As respostas do servidor devem ser marcadas como cacheáveis ou não-cacheáveis. Isso permite que o cliente armazene respostas em cache para melhorar a performance.
- **Interface Uniforme**: A interface entre o cliente e o servidor deve ser uniforme, o que significa que todas as requisições devem seguir o mesmo padrão.
- **Sistema em camadas**: O servidor pode ser composto por várias camadas, como servidores de cache, servidores de aplicação, servidores de banco de dados, etc.
- **Código sob demanda (opcional)**: O servidor pode enviar código executável para o cliente, que pode ser executado no cliente.
- **Transferência de estado**: O cliente pode transferir o estado de uma representação para o servidor, o que permite que o servidor entenda o estado do cliente.

### Métodos HTTP

Os métodos HTTP são usados para indicar a ação que deve ser realizada em um recurso. Os métodos mais comuns são:
- **GET**: Recupera um recurso.
- **POST**: Cria um novo recurso.
- **PUT**: Atualiza um recurso.
- **DELETE**: Remove um recurso.

Nesta parte do curso, vamos aprender a criar um serviço REST API usando Spring Boot porém sem conexão com o banco de dados, com o objetivo de abstrair a idéia e garantir uma evolução constante.

A seguir um exemplo do que poderia ser definido como controller é usado como exemplo:

Considere que para simular as requisições é necessário usar um cliente HTTP completo assim como o Postman ou Insomnia.

É necessário também que haja a compreencão de que o endereço http local é:
`http://localhost:8080`
e que os paths, que se encontram abaixo e correspondem aos parametros da anotação @RequestMapping, devem complementar o endereço acima.

As classes controladoras são responsáveis por receber as requisições HTTP e retornar as respostas. Elas são anotadas com `@RestController` e os métodos são anotados com `@GetMapping`, `@PostMapping`, `@PutMapping` e `@DeleteMapping` para indicar o método HTTP que deve ser usado.

### Controller comentada
Abaixo alguns comentários que explicam o código em sí, foram introduzidos para fins didáticos.
```java
/* Logo abaixo temos anotações que indicam que a
 classe é um controlador REST. */
@RestController
/* Aqui temos a anotação que indica o path que 
será usado para acessar os métodos da classe. */
@RequestMapping("/estoque")
public class EstoqueController {
/* Note que é necessário especificar o tipo de
 objeto que será retornado e que neste caso a 
 entidade Estoque é importada e o tipo de objeto
 é uma lista e não um ResponseEntity que 
 posteriormente será utilizado como objeto padrão 
 utilizado nas seguintes aulas. */
    private List<Estoque> listaEstoque = new ArrayList<>();
// Método Get que retorna a lista de produtos.
    @GetMapping("/produtos")
    public List<Estoque> listar() {
        if (listaEstoque.size() <= 0) {
            return null;
        }
        return listaEstoque;
    }
// Método Get que retorna um produto específico.
    @GetMapping("/produtos/{indice}")
    public Estoque recuperar(@PathVariable int indice) {
// Função ternária que verifica se o índice é válido.
        return isIndiceValido(indice) ? listaEstoque.get(indice) : null;

    }
/* Método Post que cadastra um produto, insere
 na lista e retorna o produto cadastrado. */
    @PostMapping("/produtos")

    public Estoque cadastrar(@RequestBody Estoque estoque) {

        listaEstoque.add(estoque);
        return estoque;
    }
/* Método Put que atualiza um produto e retorna 
o produto atualizado.*/
    @PutMapping("/produtos/{indice}")

    public Estoque atualizar(@PathVariable int indice, @RequestBody Estoque estoqueAtualizado) {

        if (indice >= 0 && indice < listaEstoque.size()) {

            listaEstoque.set(indice, estoqueAtualizado);
            return estoqueAtualizado;
        }
        return null;
    }
/* Método Delete que remove um produto e retorna uma 
mensagem de sucesso.*/
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
/* O método abaixo verifica se o índice é válido. Porém
 é comumente implementado em outra classe, a classe de 
 serviço. */
    private boolean isIndiceValido(int indice) {
        return indice >= 0 && indice < listaEstoque.size();
    }

}

````
### Exemplo de entidade

```java

/* Apesar de ser um Objeto toda Entity deverá ter 
as suas devidas anotações, isso será tratado em 
outros contextos, no entanto, para fins didáticos,
devemos considerar que a entidade sempre possuirá um 
construtor vazio e um construtor com todas os seus 
atributos, os seus 'getters' e ‘setters’ e um método 
toString. */
public class Estoque {
    private String nome;
    private double preco;
    private int qtdEstoque;
/* Método construtor vazio */
    public Estoque() {
    }
/* Método construtor com todos os atributos */
    public Estoque(String nome, double preco, int qtdEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
    }
/* Métodos Get e Set */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
    public double getValorTotalEstoque(){
        return this.preco*this.qtdEstoque;

    }
/* Anotação Override que sobrescreve o método 
toString padrão da classe Object built in do Java. */
    @Override
    public String toString() {
        return "Estoque{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", qtdEstoque=" + qtdEstoque +
                '}';
    }
}
```
As duas classes acima são exemplos de como poderia ser implementado um serviço REST API sem conexão com o banco de dados. O objetivo é abstrair a idéia e garantir uma evolução constante.