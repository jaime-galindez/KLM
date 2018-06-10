package nl.klm.fares.airports;

import nl.klm.fares.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/airport")
public class AirportsResource {

    @Value("${klm.config.endPoints.airports}")
    private String airportsEndPoint;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @GetMapping(value = "/find/{query}")
    public List<Location> searchAirports(@PathVariable("query") String query) {


        final ResponseEntity<PagedResources<Resource<Location>>> airportsResponse =
                restTemplate
                        .exchange(airportsEndPoint + "?page=1&size=10&lang=en&term=" + query, HttpMethod.GET, null,
                                new TypeReferences.PagedResourcesType<Resource<Location>>() {
                                });//restTemplate.getForEntity("http://localhost:8080/airports?size=10&page=1&lang=en&term=" + query, PagedResources.class);
        return airportsResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());
    }
}
