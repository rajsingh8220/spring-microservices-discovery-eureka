package io.singh.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.singh.ratingsdataservice.models.Rating;
import io.singh.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {
	
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getUsersRating(@PathVariable("userId") String userId) {
		List<Rating> ratings =  Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 3)
			);
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		
		return userRating;
		
	}
}
