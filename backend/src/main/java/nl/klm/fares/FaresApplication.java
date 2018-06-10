package nl.klm.fares;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableOAuth2Client
@EnableAsync
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class FaresApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaresApplication.class, args);
    }

    @Value("${klm.config.oauth.clientId}")
    private String clientId;

    @Value("${klm.config.oauth.clientSecret}")
    private String clientSecret;

    @Value("${klm.config.oauth.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${klm.config.oauth.grantType}")
    private String grantType;

    @Value("#{'${klm.config.oauth..scopes}'.split(',')}")
    private List<String> scopes;

    @Value("${klm.config.async.corePoolSize}")
    private Integer corePoolSize;

    @Value("${klm.config.async.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${klm.config.async.queueCapacity}")
    private Integer queueCapacity;

    @Value("${klm.config.async.threadNamePrefix}")
    private String threadNamePrefix;

    @Bean
    public WebClient webClient() {
        WebClient client = WebClient.create();
        return client;
    }

    @Bean
    public RestTemplate restTemplate() {
        // ClientCredentials
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(accessTokenUrl);
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setGrantType(grantType);
        resourceDetails.setScope(scopes);

        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        // end of oauth


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.registerModule(new Jackson2HalModule());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);


        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.add(0, converter);
        restTemplate.setMessageConverters(converters);

        return restTemplate;
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
