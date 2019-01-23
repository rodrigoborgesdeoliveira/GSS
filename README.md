# GSS
Sistema de Gerenciamento de Solicitações de Serviços

# Requisitos
* GWT 2.8.2
* JDK 1.8

# Configuração ao importar o projeto 
1. Vá até **Project Structure**.
1. Em **Project Settings**.
    1. Em **Project**, verifique se
        1. **Project SDK** está selecionado.
        1. **Project language level** está configurado com a mesma versão que o **Project SDK**, exemplo Project SDK 
        1.8, Project language level 8.
        1. **Project compiler output** está configurado. Pode ser qualquer caminho, mas é uma boa prática configurá-lo 
        para uma pasta out (ou output) na raiz do projeto.
    1. Em **Modules**
        1. Verifique que o diretório **src** está marcado como **Sources**.
        1. Adicione o GWT module apontando para o caminho GWT e com o **Target Web Facet** configurado para um Web facet 
        (se não tiver nenhum ainda, veja o próximo tópico). Se ele reclamar sobre não ter nenhuma library ou artifact ainda,
         escolha a opção sugerida _Fix/Create_.
    1. Em **war/WEB-INF** verifique se **web.xml** existe, se não existir, crie um. Um exemplo pode ser visto abaixo.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>rpcServlet</servlet-name>
        <servlet-class>com.Sandbox.idea.server.RpcServiceImpl</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>rpcServlet</servlet-name>
        <url-pattern>/idea/rpc</url-pattern>
    </servlet-mapping>
    
    <welcome-file-list>
        <welcome-file>idea.html</welcome-file>
    </welcome-file-list>
</web-app>
```

# Antes de executá-lo
Verifique que há uma configuração de execução. Para verificar se há uma, vá até o menu de execução e veja se há algum ou 
vá até **Run** e depois **Edit Configurations**. Alternativamente, você pode escolher **Edit Configurations** do menu de 
execução.

Em **Edit Configurations**, 
1. escolha **+**
1. **GWT Configuration**
1. Nomeie-o como quiser (o padrão é o nome do projeto)
1. Verifique se o módulo do projeto (Module) está selecionado
1. Marque a caixa **Use Super Dev Mode**
1. Altere **GWT Modules to load** para **All**
1. **VM Options** pode ser o padrão (provavelmente **-Xmx512m**)
1. **Dev Mode parameters**, **Working directory**, **Environment variables** e **Redirect input from** podem ser deixados 
em branco
1. Altere **Server** para **Default**
1. Altere **Start page** para o arquivo html da pasta **war** (pode ser que mostre no menu)
1. Altere **JRE** para **Default**
1. Marque **Open in browser** e selecione seu navegador (funciona melhor com o Google Chrome)
1. Desmarque ambas **with JavaScript debugger** e **Update resources on frame deactivation**
1. Desmarque **Show this page**
1. Desmarque **Activate tool window**

# Criar um banco de dados do RH para simulação
Execute os seguintes comados SQL
```sql
CREATE DATABASE IF NOT EXISTS rh;
CREATE TABLE IF NOT EXISTS setor (id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), nome VARCHAR(50) NULL);
CREATE TABLE funcionario (id INT NOT NULL AUTO_INCREMENT, email VARCHAR(254) NOT NULL, nome VARCHAR(50) NULL, setor_id INT NULL, PRIMARY KEY (id), UNIQUE INDEX email_UNIQUE (email ASC) INVISIBLE, INDEX setor_id_idx (setor_id ASC) VISIBLE, CONSTRAINT setor_id FOREIGN KEY (setor_id) REFERENCES setor (id) ON DELETE SET NULL ON UPDATE NO ACTION);
ALTER TABLE setor ADD (gestor_id INT NULL, FOREIGN KEY(gestor_id) REFERENCES funcionario(id));
```