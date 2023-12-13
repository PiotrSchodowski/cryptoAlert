package pl.schodowski.CryptoPriceAlert;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CryptoRepo extends ReactiveMongoRepository<Crypto, String> {

    Mono<Crypto> findBySymbol(String symbol);
}
