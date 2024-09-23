# Teste Magalu - App de Consulta de Repositórios e Pull Requests

Este projeto é um aplicativo Android desenvolvido como parte de um desafio técnico, com o objetivo de listar repositórios do GitHub com base na linguagem `Kotlin` e exibir suas Pull Requests. Ele foi desenvolvido utilizando diversas abordagens modernas de desenvolvimento Android, seguindo boas práticas e princípios de arquitetura limpa.

## Arquitetura

O projeto utiliza a arquitetura **Model-View-Intent (MVI)** para gerenciamento de estado e comunicação entre as camadas. A estrutura de pastas foi organizada para facilitar o desenvolvimento, manutenção e escalabilidade da aplicação.

### Principais Abordagens Utilizadas

- **MVI (Model-View-Intent)**: Utilizado para garantir um fluxo de dados unidirecional e previsível, com separação clara entre a lógica de negócios (ViewModel) e a interface do usuário (Fragmentos).
- **Dagger 2 para Injeção de Dependências**: Injeção de dependências para criar componentes modulares, facilitando o uso de mocks em testes e a manutenção do código.
- **ReactiveX (RxJava 3)**: Utilizado para lidar com operações assíncronas e reativas, como chamadas de API, garantindo uma experiência fluida ao usuário.
- **Retrofit**: Biblioteca para consumo da API REST do GitHub, integrada com RxJava para chamadas assíncronas.
- **View Binding**: Facilita o acesso a views sem a necessidade de `findViewById`, melhorando a segurança de tipo e reduzindo boilerplate.
- **RecyclerView**: Utilizado para exibir listas de repositórios e pull requests de forma eficiente.
- **ConstraintLayout**: Layout principal utilizado para organização dos componentes da interface de usuário.

### Estrutura de Pastas

```bash
.
├── data               # Interface e implementação do DataSource
├── di                 # Módulos de injeção de dependências
├── model              # Entidades e modelos de dados
│   ├── pullrequest    # Modelos de Pull Requests
│   └── repository     # Modelos de Repositórios
├── network            # Classes de rede e API
├── ui                 # Pacote de interface do usuário
│   ├── pullrequest    # Fragments e Adapters para Pull Requests
│   └── repository     # Fragments e Adapters para Repositórios
├── viewmodel          # ViewModels para gerenciamento de estado
└── utils              # Utilitários e extensões
```

## Como Rodar o Projeto

### Pré-requisitos

- Android Studio instalado (recomendado versão mais recente).
- Configuração do ambiente com o SDK mínimo conforme definido no projeto (API 24).
- Conta no GitHub (não é necessário utilizar **Personal Access Token** para este projeto).

### Configurando o Projeto

1. **Clone o repositório:**

```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
```

Abra o projeto no Android Studio:

Vá em **File > Open** e selecione a pasta do projeto.

### Configuração do Token de Acesso do GitHub (opcional) 


- Anteriormente, utilizávamos um Personal Access Token para acessar a API do GitHub, mas o fluxo atual não exige mais essa configuração. Caso tenha interesse, você pode seguir as instruções em: https://github.com/settings/tokens

### Executando o App

- Conecte um dispositivo Android ou utilize um emulador configurado.
- Clique no botão **"Run"** ```(Shift + F10)``` no Android Studio ou selecione **"Run > Run 'app'"**.
O aplicativo será instalado e iniciado no dispositivo.

### Testes
O projeto utiliza o ```JUnit``` e Mockito para testes unitários e de integração. Para rodar os testes:

### Executar os testes no Android Studio:

Vá para o painel **Run > Run...** e selecione **Test in 'app'**.
Estrutura de Testes:

- ViewModel Tests: Garantem que a lógica de negócio e o fluxo de dados estejam corretos.

### Referências
- Aqui estão algumas das principais documentações e recursos utilizados no desenvolvimento deste projeto:

- Documentação do Dagger Android:
https://developer.android.com/training/dependency-injection/dagger-android?hl=pt-br#dagger-modules

- Configuração da API do GitHub:
https://docs.github.com/pt/rest/search/search?apiVersion=2022-11-28#search-repositories

- Injeção de ViewModel com Dagger:
https://stackoverflow.com/questions/71675474/viewmodel-injection-failing-on-dagger-kotlin-android

- Testes com Mockito:
https://site.mockito.org/

### Considerações Finais
O projeto foi desenvolvido com o objetivo de aplicar boas práticas de desenvolvimento Android, seguindo os padrões de arquitetura modernos para garantir uma aplicação escalável e de fácil manutenção. Qualquer sugestão ou contribuição é bem-vinda!
