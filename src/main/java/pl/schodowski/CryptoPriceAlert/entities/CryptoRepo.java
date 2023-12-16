package pl.schodowski.CryptoPriceAlert.entities;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pl.schodowski.CryptoPriceAlert.entities.Crypto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CryptoRepo extends ReactiveMongoRepository<Crypto, String> {

    Mono<Crypto> findBySymbol(String symbol);
    Mono<Crypto> findByName(String name);
    Flux<Crypto> findAll();
}
