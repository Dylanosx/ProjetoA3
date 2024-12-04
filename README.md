<div align="center">
  <img src="https://i.imgur.com/g6K8N0n.png" alt="easyparking logo" style="border-radius: 70%; width: 200px; height: 200px;" />
</div>

# Easy Parking 

## Membros
- Camila Cordeiro - 12523173817
- Ellen Cristine do Vale Cruz - 12522179952 
- Dylan Renato de Andrade - 12523179440
- Pietro da Silva Sanches -1252328165

## Índice

- [Descrição do projeto](#descrição-do-projeto)
- [Funcionalidades e demonstração da aplicação](#funcionalidades-e-demonstração-da-aplicação)
- [Acesso ao projeto](#acesso-ao-projeto)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Estrutura da API](#estrutura-da-api)

## Descrição do projeto

**Easy Parking** é um sistema interno de uma empresa de estacionamento que carrega o mesmo nome da solução, foi projetado para oferecer autonomia e praticidade aos clientes, desde o momento de entrada até a saída. A aplicação permite que o cliente registre seu veículo, consulte valores e realize pagamentos sem precisar de assistência, tudo diretamente no navegador, sem necessidade de instalar um aplicativo.

O objetivo principal é proporcionar uma experiência fluida e rápida para o cliente, com opções de pagamento via Pix ou cartão e uma interface simplificada que facilita a consulta e o pagamento.

## Funcionalidades e demonstração da Aplicação

- **Registro de entrada**: Cliente insere a placa e o modelo do carro, e o sistema registra automaticamente a entrada, atribuindo um `ticket` e armazenando o horário de entrada.
- **Consulta e saída**: Na saída, o cliente insere a placa para visualizar as informações de horário de entrada, saída e o valor calculado.
- **Pagamento**: Cliente pode pagar via Pix (geração de QR Code) ou cartão de crédito/débito inserindo os dados no sistema.
- **Consulta de valores**: Opção para o cliente consultar as tarifas, exibida em um pop-up informativo.

### Funcionamento
<img src="https://i.ibb.co/QCFxCs0/animacaofuncional1.gif"/>


## Acesso ao projeto

Este projeto é acessível diretamente pelo navegador, sem necessidade de download ou instalação de aplicativos.
- URL: [Link para a aplicação](https://exemplo.com)
- Usuários de teste:
  - **Placa**: TESTE123
  - **Modelo de Carro**: Fusca

## Tecnologias utilizadas

- **Front-end**: JavaScript, HTML5 e CSS3 integrados com bootstrap
- **Back-end**: Java com Spring Boot para criação da API REST
- **Banco de Dados**: MySQL hospedado na Azure para armazenamento de dados de entrada e saída de veículos
- **Hospedagem**: Azure para a implantação da API Spring Boot e do banco de dados MySQL

## Estrutura da API

### Endpoints

### Consulta de tabela de preços(opcional)
   - **Rota**: `GET /api/valores`
   - **Descrição**: Retorna a tabela de preços atual para que o cliente visualize os valores do estacionamento.
   - **Método**: `GET`

- **Saída:**
    ```json
    {
      "tarifas": [
        {"tempo": "Até 1 hora", "valor": R$5.00},
        {"tempo": "1 a 2 horas", "valor": R$10.00},
        {"tempo": "2 a 3 horas", "valor": R$15.00},
        {"tempo": "Diária", "valor": R$25.00}
        {"tempo": "Acima de 24 horas", "valor": + R$25.00}
      ]
    }

### 1. Criação de ticket (entrada do veículo)
   - **Rota**: `POST /api/entrada`
   - **Descrição**: Registra a entrada do veículo, captura automaticamente o horário de entrada e cria um ticket associado à placa.
   - **Método**: `POST`

#### Parâmetros do body:
- **Entrada**:
  ```json
  {
    "placa": "ABC1234",
    "modelo_carro": "Fusca"
  }

- **Saída**:
  ```json
  {
   "placa": "ABC1234"
   }

### 2. Consulta de placa e valor (saída do veículo)
- **Rota**: `GET /api/saida`
- **Descrição:** Recupera o ticket associado à placa, registra o horário de saída, e calcula o valor a ser pago com base no tempo de permanência.
- **Método:** POST

- **Entrada:**
  ```json
  {
   "placa": "ABC1234"
   }
  
- **Saída:** 
  ```json
      {
        "ticketId": "123456",
        "horarioEntrada": "2024-11-06T09:45:00Z",
        "horarioSaida": "2024-11-06T13:45:00Z",
        "valor": 15.00,
        "mensagem": "Consulta realizada com sucesso!"
      }

### 3. Confirmação de pagamento
- **Rota:** `POST /api/pagamento`
- **Descrição:** Processa a confirmação de pagamento do ticket via PIX ou cartão. Para o método PIX, é gerado um QR code para o pagamento. No caso do cartão, simula o processamento do pagamento.
- **Método:** POST

- **Entrada(pagamento com pix):**
  ```json
  {
  "ticketId": "123456",
  "metodo": "PIX"
  }
  
- **Saída(pagamento com pix):**
  ```json
  {
  "ticketId": "123456",
  "valor": 15.00,
  "statusPagamento": "Aguardando confirmação",
  "qrCodePix": "link_para_qrcode",
  "mensagem": "QR Code gerado para pagamento via PIX."
  }

- **Entrada(pagamento com cartão):**
  ```json
  {
  "ticketId": "123456",
  "metodo": "Cartão"
  }
  
- **Saída(aguardando pagamento com cartão):**
  ```json
  {
  "ticketId": "123456",
  "valor": 15.00,
  "statusPagamento": "Aguardando pagamento",
  "mensagem": "Preencha os dados do cartão"
  }
  
- **Saída(após pagamento com cartão):**
  ```json
  {
  "valor": 15.00,
  "statusPagamento": "Pago",
  "mensagem": "Pagamento confirmado com sucesso. Obrigado!",
  "dataPagamento": "2024-11-06T14:35:00Z"
  }
  

### Fluxo de funcionamento
1. **Entrada**: O cliente insere os dados iniciais do veículo, e o sistema gera automaticamente um ticket associado, capturando o horário de entrada.
2. **Saída**: Ao retornar, o cliente insere a placa, e o sistema recupera o ticket, calcula o valor baseado no tempo de permanência e exibe as informações finais.
3. **Pagamento**: O cliente escolhe o método de pagamento (Pix ou cartão), e o sistema processa o pagamento, liberando a saída ao ser confirmado.
