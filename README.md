Projeto SAJ-ADS10 - Sistema Distribuído (Java puro)

Ordem de execução (Windows):
1) Logo após abrir a pasta do projeto no VScode, compile o projeto;
2)Nos terminais do proprio Vscode rode o trecho de linha: 

java app.Main coordinator -port 5000

3)Logo após abra 3 terminais (Nó) no Vscode e digite em cada um o trecho de codigo:

java app.Node --id 1 --coord-port 5000
java app.Node --id 2 --coord-port 5000
java app.Node --id 3 --coord-port 5000


4) Comandos dentro de cada nó: inc | status | snapshot | quit e voce verá se comunicarem em cada nó

O que aparecerá em cada um comando desse:

Inc: Node X recebeu: Node x incrementou
status: Node 2 v1 log=[Node x incrementou] -- aqui é tudo que foi executado em todos os nós
snapshot: Node X sincronizado com snapshot. 
quit: O que faz: encerra o nó atual

