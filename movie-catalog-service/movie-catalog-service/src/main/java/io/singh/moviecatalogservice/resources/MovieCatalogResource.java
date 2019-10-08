package io.singh.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.catalog.Catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.singh.moviecatalogservice.models.CatalogItem;
import io.singh.moviecatalogservice.models.Movie;
import io.singh.moviecatalogservice.models.Rating;
import io.singh.moviecatalogservice.models.UserRating;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplate; // We now gonna use WebClient instead of this
	@Autowired
	WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId){
		//We dont need new instance everytime so we gonna move this from here
		//WebClient.Builder builder = WebClient.builder();
		
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		
		return ratings.getUserRating().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+ rating.getMovieId(), Movie.class); //Replaced by web client
			
			/*
			Movie movie = webClientBuilder.build()
			.get() // GET / PUT / POST methods
			.uri("http://movie-info-service/movies/"+ rating.getMovieId()) // URL
			.retrieve()
			.bodyToMono(Movie.class) // it actually saying Body to Movie class but Mono means promise something you get in future. (Asynchronous)
			.block(); // Blocking execution till Mono is fulfilled. It blocks execution until get response back.
			*/
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
			
		}).collect(Collectors.toList());
				
	}
}
