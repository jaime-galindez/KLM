package nl.klm.fares.fares;

import nl.klm.fares.model.AggregatedFares;
import nl.klm.fares.model.Fare;
import nl.klm.fares.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/fares")
public class FaresResource {

    @Value("${klm.config.endPoints.fares}")
    private String faresEndPoint;

    @Value("${klm.config.endPoints.airports}")
    private String airportsEndPoint;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/{originAirport}/{destinationAirport}")
    public AggregatedFares getFares(@PathVariable("originAirport") String originAirportCode, @PathVariable("destinationAirport") String destinationAirportCode) {


        CompletableFuture<Fare> fareCompletableFuture = getFare(originAirportCode, destinationAirportCode);

        CompletableFuture<Location> originAirportCompletableFuture = getLocation(originAirportCode);

        CompletableFuture<Location> destinationAirportCompletableFuture = getLocation(destinationAirportCode);

        CompletableFuture<Void> allDone
                = CompletableFuture.allOf(fareCompletableFuture, originAirportCompletableFuture, destinationAirportCompletableFuture);


        AggregatedFares result = new AggregatedFares();
        try {
            allDone.get();
            Fare fare = fareCompletableFuture.get();
            Location originAirport = originAirportCompletableFuture.get();
            Location destinationAirport = destinationAirportCompletableFuture.get();
            result.setAmount(fare.getAmount());
            result.setCurrency(fare.getCurrency());
            result.setOriginAirport(originAirport);
            result.setDestinationAirport(destinationAirport);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        return result;

    }

    @Async
    protected CompletableFuture<Fare> getFare(String originAirportCode, String destinationAirportCode) {
        return CompletableFuture.supplyAsync(() -> {
            ResponseEntity<Fare> fareResponse = restTemplate.getForEntity(faresEndPoint + originAirportCode + "/" + destinationAirportCode, Fare.class);
            return fareResponse.getBody();
        });
    }

    @Async
    protected CompletableFuture<Location> getLocation(String locationCode) {
        return CompletableFuture.supplyAsync(() -> {
            ResponseEntity<Location> locationResponse = restTemplate.getForEntity(airportsEndPoint + locationCode, Location.class);
            return locationResponse.getBody();
        });
    }

}
