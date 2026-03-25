# CurrencyConverter

Para executar o projeto, é obrigatório configurar uma chave de API do serviço Currency Converter do RapidAPI.

Obtenha uma chave de API válida para o [currency-converter5](https://rapidapi.com/natkapral/api/currency-converter5) no RapidAPI.

Crie um arquivo chamado `local.properties` no diretório raiz do projeto.

Insira a seguinte linha no arquivo, substituindo o valor pela sua chave:
```
API_KEY="SUA_CHAVE_DE_API_AQUI"
```
O script `build.gradle.kts` no modulo app, lê o valor de API_KEY e gera o campo BuildConfig.API_KEY, utilizado para autenticar as requisições HTTP na interface do Retrofit.
