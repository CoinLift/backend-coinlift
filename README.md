# backend-coin-lift
## Requirements:
- PostgreSQL database for correct migration

## Getting Started

1. Clone the repository to your local machine.
2. Open the `application.yaml` file located in `src/main/resources` and update the datasource configuration with your own database details:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://<DATABASE_HOST>:<DATABASE_PORT>/<DATABASE_NAME>
    username: <DATABASE_USERNAME>
    password: <DATABASE_PASSWORD>
```
3. Obtain the API keys from CoinMarketCap and Coindesk **_(instructions below)_**.
4. Add the API keys to the Spring Boot project by updating the application.yaml file:
```yaml
coinmarketcap:
  api:
    key: <YOUR_API_KEY>

rapid:
  coindesk:
    api:
      key: <YOUR_API_KEY>
```
5. Save the application.yaml file and restart the Spring Boot application.
6. Build the project by running `mvn clean package`.
7. Run the project by running `java -jar target/backend-0.0.1-SNAPSHOT.jar`.


## Instruction for obtaining API Keys from CoinMarketCap and Coindesk

## Obtaining a CoinmarketCap API Key
1. Go to the [CoinMarketCap](https://coinmarketcap.com) website and sign up for an account, or log in if you already have one.
2. Once you are logged in, navigate to the [API page](https://coinmarketcap.com/api/) by clicking on the "API" link in the top menu.
3. Choose a plan that suits your needs and click the "Subscribe" button to proceed to the next step _(For this tutorial, we will be using a free plan)_.
4. Enter your billing information and click the "Continue" button to complete the subscription process. Note that some plans require payment, while others are free.
5. Once you have subscribed to a plan, you will receive an API key, which you can use to access the CoinMarketCap API.

### Adding the API Key to a Spring Boot Project

1. Open the application.yaml file of this Spring Boot project, located in the src/main/resources directory.
2. Add the following line to the file, replacing <YOUR_API_KEY> with the API key you obtained from CoinMarketCap:
```agsl
coinmarketcap:
    api:
       key: <YOUR_API_KEY>
```

## Obtaining a Coindesk API Key

1. Go to the [RAPID API](https://rapidapi.com/hub) page and sign up for an account, or log in if you already have one.
2. Once you are logged in, navigate to the [Cryptocurrency News API](https://rapidapi.com/topapi-topapi-default/api/cryptocurrency-news2/) page by searching on website or by clicking this [LINK](https://rapidapi.com/topapi-topapi-default/api/cryptocurrency-news2/).
3. Choose a plan that suits your needs and click the "Subscribe" button to proceed to the next step _(For this tutorial, we will be using a free plan)_.
4. Fill in the required information and click the "Submit" button to complete the subscription process. Note that some plans require payment, while others are free.
5. Once you have subscribed to a plan, you will receive an API key, which you can use to access the Coindesk API.

### Adding the API Key to a Spring Boot Project

1. Open the `application.yaml` file of this Spring Boot project, located in the `src/main/resources` directory.
2. Add the following lines to the file, replacing `<YOUR_API_KEY>` with the API key you obtained from Coindesk:

```yaml
rapid:
  coindesk:
    api:
      key: <YOUR_API_KEY>
```

