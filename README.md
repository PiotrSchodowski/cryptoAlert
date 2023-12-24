# CryptoPriceAlert
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [How it works](#how-it-works)
## General info
`CryptoPriceAlert` is an application designed for investors
who want to be informed about the major changes price and volume
of cryptocurrencies. Application is based on `Reactive Programming` 
and `Webflux` technology.


## Technologies
* Java 17
* Spring Boot 3.14
* ReactiveMongoDB
* Webflux
* Gradle
* Lombok

## Setup
To Deploy Application on your computer, you need Java 17+ and Gradle:

1. **Pull the project from the main branch**
2. **Create shema `crypto` in your MongoDB**
3. **Set up in `application.properties` file:**
   - MongoDB connection

   -  Data from `Twilio.com` (first you have to create account, there may be a free version)

   -  `values` - set change that interests you expressed as a percentage(default 0.5%)

   -  `interval` - set interval in milliseconds(default 10 minutes)

4. **Run application**
   -  **Command Line:**  `gradle clean build`
-  **Run .jar file with command:**  `java -jar patch\file_name.jar`


## How it works

When u run application, it will start to download data from `CoinGecko.com` 
and save it to database (default 10 cryptocurrencies largest in terms of capitalization).
Then it will start to check if the price or volume
of the cryptocurrency has changed by the value you set and if so, it will send you a message.


