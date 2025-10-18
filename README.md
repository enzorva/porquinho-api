# 🐖 Porquinho API

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Oracle DB](https://img.shields.io/badge/Oracle-DB-red?style=for-the-badge&logo=oracle)
![Status](https://img.shields.io/badge/status-Em%20Desenvolvimento-yellow?style=for-the-badge)

---

## 💡 Sobre o Projeto

O **Porquinho** é um aplicativo de **controle financeiro pessoal** que ajuda o usuário a **entender, planejar e otimizar seus hábitos de consumo**.  
Com ele, é possível registrar **receitas e despesas**, **gerenciar categorias, contas e carteiras**, **definir orçamentos** e **gerar relatórios claros** sobre a vida financeira.

🔮 **Diferenciais e Futuro:**
- Análises inteligentes com IA para recomendações personalizadas.  
- Integração com **APIs financeiras** e automação de lançamentos.  
- Foco em **simplicidade, clareza e usabilidade**.

---

## 🛠️ Tecnologias Utilizadas

- ☕ **Java 17**
- 🌱 **Spring Boot 3.x**
- 🧠 **Lombok**
- 💾 **Oracle Database**
- 🧰 **H2 Database (para testes)**
- 🚀 **Spring Boot DevTools**

---

## 📌 Endpoints Principais

### 🏦 **Carteiras**
| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/wallets/{id}` | Retorna uma carteira |
| `GET` | `/wallets` | Lista todas as carteiras |
| `POST` | `/wallets` | Cria uma nova carteira |
| `PUT` | `/wallets/{id}` | Atualiza uma carteira |
| `DELETE` | `/wallets/{id}` | Exclui uma carteira |

---

### 💳 **Contas**
| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/accounts/{id}` | Retorna uma conta |
| `GET` | `/accounts` | Lista todas as contas |
| `POST` | `/accounts` | Cria uma nova conta |
| `PUT` | `/accounts/{id}` | Atualiza uma conta |
| `DELETE` | `/accounts/{id}` | Exclui uma conta |

---

### 👤 **Usuários**
| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/users/{id}` | Retorna um usuário |
| `GET` | `/users` | Lista todos os usuários |
| `POST` | `/users` | Cria um novo usuário |
| `PUT` | `/users/{id}` | Atualiza um usuário |
| `DELETE` | `/users/{id}` | Exclui um usuário |

---

### 🏷️ **Categorias**
| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/categories/{id}` | Retorna uma categoria |
| `GET` | `/categories` | Lista todas as categorias |
| `POST` | `/categories` | Cria uma nova categoria |
| `PUT` | `/categories/{id}` | Atualiza uma categoria |
| `DELETE` | `/categories/{id}` | Exclui uma categoria |

---

### 💸 **Transações**
| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/transactions/{id}` | Retorna uma transação |
| `GET` | `/transactions` | Lista todas as transações |
| `POST` | `/transactions` | Cria uma nova transação |
| `PUT` | `/transactions/{id}` | Atualiza uma transação |
| `DELETE` | `/transactions/{id}` | Exclui uma transação |

---

### 🏛️ **Bancos e Tipos de Conta**
| Método | Endpoint | Descrição |
|--------|-----------|-----------|
| `GET` | `/banks` | Lista todos os bancos |
| `GET` | `/banks/{id}` | Retorna um banco |
| `GET` | `/account-types` | Lista todos os tipos de conta |
| `GET` | `/account-types/{id}` | Retorna um tipo de conta |

---

## 🚀 Como Executar Localmente

### **Pré-requisitos**
- ☕ Java 17+
- 🧱 Maven
- 💡 IDE (IntelliJ, VS Code, Eclipse)
- 🧩 Git

### **Clonar o Repositório**
```bash
git clone https://github.com/enzorva/porquinho-api.git
```

### **Acesse a Pasta do Projeto**
```bash
cd porquinho-api/
```

### **Instale as Dependências**
```bash
mvn install
```

### **Executar o Projeto**
```bash
mvn spring-boot:run
```

Acesse a documentação da aplicação em:  
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 📐 Especificações Técnicas

- Arquitetura em camadas (Controller → Service → Repository)
- Entidades JPA mapeadas com relacionamentos
- Controle transacional e validação de dados
- Integração com banco de dados Oracle e H2

---

## 🧩 Diagrama de Classes

O diagrama abaixo representa a estrutura principal das entidades e seus relacionamentos na aplicação **Porquinho API**.

![Diagrama de Classes](https://raw.githubusercontent.com/enzorva/porquinho-api/main/docs/classes-uml-diagram.png)

---

## 🧩 DER

O diagrama abaixo representa a estrutura principal da nossa base de dados e seus relacionamentos.

![Diagrama Entidade Relacionamento](https://raw.githubusercontent.com/enzorva/porquinho-api/main/docs/der.png)


---

## 📅 Cronograma de Desenvolvimento

| Fase | Entregas Principais |
|------|---------------------|
| **Sprint 01** | Definição do escopo, modelagem do banco e criação das entidades | 
| **Sprint 02** | Implementação dos endpoints principais (Users, Wallets, Accounts) | 
| **Sprint 03** | Relatórios, integração com IA e validações de negócio |
| **Sprint 04** | Integração com API financeira e deploy na nuvem |
---

## 🧪 Requisições de Teste

O projeto inclui um script `requests.sh` com exemplos de requisições via `curl`.

---

## 🎥 Pitch do Projeto

🎬 [Assista ao nosso pitch no YouTube](https://youtu.be/uYpMm3QMLng)

---

## 👥 Contribuidores

| Nome | Função |
|------|---------|
| **Antonio de Luca Ribeiro Silva** | IoT |
| **Enzo Ribeiro Vilela de Azevedo** | Backend (Front-Office) / Banco de Dados |
| **Paulo Sérgio França Barbosa** | Backend (Back-Office) / Frontend |