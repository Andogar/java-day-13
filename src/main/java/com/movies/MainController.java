package com.movies;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    final static String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/now-playing")
    public String nowPlaying(Model model) {
        model.addAttribute("movies", getMovies(URL));

        return "now-playing";
    }

    @RequestMapping("/medium-popular-long-name")
    public String mediumMovies(Model model) {
        List<Movie> semiPopularMovies = getMovies(URL).stream()
                .filter(e -> e.popularity >= 30 && e.popularity <= 80)
                .filter(e -> e.title.length() > 10)
                .collect(Collectors.toList());

        model.addAttribute("movies", semiPopularMovies);
        return "medium-popular-long-name";
    }


    public static List<Movie> getMovies(String route) {
        RestTemplate restTemplate = new RestTemplate();
        ResultsPage movieResults = restTemplate.getForObject(route, ResultsPage.class);

        return movieResults.getResults();
    }
}
