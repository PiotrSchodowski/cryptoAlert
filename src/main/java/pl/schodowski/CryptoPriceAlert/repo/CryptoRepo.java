package pl.schodowski.CryptoPriceAlert.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CryptoRepo extends ReactiveMongoRepository<Crypto, String> {

    Mono<Crypto> findByName(String name);
    Flux<Crypto> findAll();
}
