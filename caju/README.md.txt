# Desafio técnico CAJU - Transações

## Sobre o Projeto:

https://caju.notion.site/Desafio-T-cnico-para-fazer-em-casa-218d49808fe14a4189c3ca664857de72
&nbsp;

Para suprir as necessidades do enunciado acima foram criadas 3 rotas: 
- /api/transactions/merchants
- /api/transactions/fallbacks
- /api/transactions/simples

Retornando os códigos descritos e uma mensagem informando o que cada código quer dizer conforme descrito no link acima. 
Optei por dividir em 3 rotas e 3 casos de uso separados para facilitar a leitura consciente que poderia ter reaproveitado melhor algumas funções e inclusive ter pensado em um código com perfomance e legibilidade aprimoradas, porém dado a simplicidade do caso de uso apresentado optei pela simplicidade na execução. Utilizei a arquitetura Onion para dividir os domínios e melhorar o desacoplamento entre as camadas presentes na estrutura do código como forma de demonstração mantendo a baixa complexidade do código. 

Para testar basta rodar o projeto e chamar as rotas descritas enviando o payload exatamente como descrito no enunciado do teste. O saldo das bases falsas podem ser manipuladas através do arquivo:"\caju\caju\fakeDatabase" onde repliquei a grosso modo os dados sugeridos pelo teste. 

Esse projeto é extremamente simples e apenas uma forma rápida e organizada de resolver o problema. Sei que um contexto que exige esse tipo de transação tem de ser muito mais complexo e organizado, porém dado o tempo que eu tinha (por falha minha em ver o e-mail com o teste) acabei seguindo o caminho mais simples. Gostaria de lembrar também que Java não é minha stack com mais experiência, tenho uma expertise muito maior em .NET e com certeza poderia ter feito um teste mais vistoso em outro linguagem. Java e C# são linguagens bem próximas, acredito que possa performar em alto nível nas duas mesmo que em Java eu precise estudar um pouco mais, portanto não entendo isso como um impeditivo na minha canditadura e espero que possamos seguir adiante.

## L4. Questão aberta
Dado um ambiente cloud onde é possível um banco de dados conter múltiplicas réplicas eu optaria pela concorrência otimista. Em resumo poderíamos estruturar uma coluna contendo um timestamp que representa a última vez que determinada linha foi atualizada e a partir daí gerenciar o versionamento dos dados. Por exemplo:
Requisição 1: Chegou a solicitaçao de consumir 10R$ do saldo e atualizou o saldo do usuário para 0 no timestamp 1721871808000
Requisição 2: Foi gerada solicitando o consumo de 10R$ do saldo no timestamp 17218718080001

Nesse cenário o dado atualizado na base vai conter 1721871808000 na coluna timestamp e no milesgundo seguinte a requisição dois será negada. Essa gestão pode ser feita utilizando alguns ORMs tanto em Java quanto em c#. 

Outra abordagem seria utilizar a cláusula lock na query e travar a linha específica referente ao saldo do cliente, porém essa abordagem depende de um estudo minucioso da modelagem do banco de dados e a validação se há duas ou mais réplicas de um mesmo banco no ambiente. 
 
## Tecnologias Utilizadas

- Java
- Spring Boot

