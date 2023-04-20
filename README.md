# Agendador de Emails JAX-RS (Alura Cursos)

## Projeto
Um sisteminha que simula o agendamento de e-mails, enviando-os para o mail-trap.

* O projeto utiliza a especificação JAX-RS do JavaEE para receber e tratar as requsições Http, para agendar e listar os emails.
* Para a camada de negócios utilizamos os <code>EJB</code>s, e como servidor de aplicação, o WildFly da JBoss.

## Persistência
* Por estarmos rodando em um servidor de aplicação, e usando <code>EJB</code>s, temos acesso ao JTA (Java Transaction API), nos livrando da resposabilidade de abrir, comitar e fazer os rollbacks das transações com o banco, caso alguma exceção seja lançada.

### 1. Agendando um Email
* Temos um Controller que recebe essas requisições, que criam o objeto <code>AgendamentoEmail</code> (que representará o email).
* Este agendamento, é passado para a camada de negócio, representada pela classe de <code>Business</code>, que por sua vez é enviado para a classe <code>Dao</code> para ser persistido.
* O email receberá o status de não enviado, e será persistido.
* <strong>OBS: </strong> Não é possível realizar outro agendamento para o email, caso o anterior ainda não tenha sido enviado.

### 2. Agendador e Fila JMS (Java Message Service)
 * Utilizamos um método anotado com <code>@Schedule</code> em um <code>EJB</code> do tipo <code>Singleton</code>, para que os verifique a cada 10 segundos os e-mails agendados, e os envie para a fila JMS, fila essa que é armazenada no <code>servidor WildFly</code>.
 
 ### 3. Enviando de fato os e-mails com um Message Driven Bean <code>MDB</code>
 * Criamos um <code>MDB</code> que ficará "escutando" a nossa fila <code>JMS</code>, que por sua vez, pegará os e-mails não enviados e chamará o método enviarEmail() da classe <code>Business</code>.
