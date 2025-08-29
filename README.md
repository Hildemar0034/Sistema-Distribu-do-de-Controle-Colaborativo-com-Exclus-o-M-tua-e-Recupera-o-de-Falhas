# Sistema Distribuído de Controle Colaborativo

Este projeto, **SAJ-ADS10 - Sistema Distribuído de Controle Colaborativo com Exclusão Mútua e Recuperação de Falhas**, tem como objetivo criar um ambiente de colaboração distribuído onde múltiplos processos clientes acessam e modificam um recurso crítico compartilhado. A implementação simula o acesso concorrente a um recurso, empregando algoritmos de exclusão mútua distribuída, técnicas de replicação de dados com consistência eventual e recuperação de falhas com *checkpoints* e *rollback*.

---

### **Requisitos do Sistema**

O sistema implementado deve atender aos seguintes requisitos:

* **Modelo Arquitetural:** Composto por um mínimo de quatro processos/nós, o sistema utiliza uma estrutura híbrida com um servidor coordenador e nós replicados. A comunicação entre os componentes é realizada via Sockets TCP.
* **Exclusão Mútua Distribuída:** Apenas um processo por vez pode acessar ou modificar o recurso crítico. Para garantir isso, o sistema implementa um dos seguintes algoritmos de exclusão mútua: Ricart-Agrawala ou Centralizado.
* **Replicação e Consistência:** Cada nó mantém uma cópia do recurso compartilhado, e as alterações são propagadas para os demais nós de forma assíncrona.
* **Recuperação de Falhas:** O sistema faz *checkpoints* periódicos do estado do recurso. Em caso de falha de um processo, o estado pode ser restaurado a partir do último *checkpoint*. O sistema também simula um *rollback* se uma falha ocorrer durante uma operação crítica.
* **Controle de Concorrência:** As requisições para o recurso crítico são organizadas em uma fila. Para ordenar essas solicitações, o sistema utiliza relógios lógicos de Lamport.

---

### **Video de demonstração**

1. https://drive.google.com/file/d/11jsqXHa4t_YR4bq0XS-8cwORC9MHKGad/view?usp=drive_link

### **Como Executar (Windows)**

Para executar o projeto, siga as instruções abaixo:

1.  **Compile o projeto:** Após abrir a pasta no seu editor, como o VSCode, compile o projeto.
2.  **Inicie o Coordenador:** Abra um terminal e execute o comando:
    ```
    java app.Main coordinator -port 5000
    ```
3.  **Inicie os Nós:** Abra três novos terminais e, em cada um deles, execute o comando a seguir, variando o ID para cada nó:
    ```
    java app.Node --id 1 --coord-port 5000
    java app.Node --id 2 --coord-port 5000
    java app.Node --id 3 --coord-port 5000
    ```
4.  **Comandos de Operação:** Com os nós em execução, você pode usar os seguintes comandos em qualquer um dos terminais dos nós:
    * **inc:** O nó atual incrementa o contador. A mensagem "Node X recebeu: Node X incrementou" aparecerá nos nós.
    * **status:** Exibe o estado atual do nó, mostrando todas as operações executadas, por exemplo: "Node 2 v1 log=[Node x incrementou]".
    * **snapshot:** Sincroniza o nó com o último *checkpoint*.
    * **quit:** Encerra a execução do nó atual.

### **Observação**
* **Projeto:**  O projeto so vai rodar quando voce abrir no VsCode a pasta (saj-ads10-java-final) que está dentro dessa pasta do zip (Sistema-Distribu-do-de-Controle-Colaborativo-com-Exclus-o-M-tua-e-Recupera-o-de-Falhas-main)
