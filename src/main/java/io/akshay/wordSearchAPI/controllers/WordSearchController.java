package io.akshay.wordSearchAPI.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.akshay.wordSearchAPI.services.WordGridService;

@RestController
public class WordSearchController {

	@Autowired
	WordGridService wordGridService;

	@GetMapping("/wordgrid")
	public String createWordGrid(@RequestParam int gridSize, @RequestParam String wordList) {
		List<String> words = Arrays.asList(wordList.split(","));
		char[][] contents = wordGridService.generateGrid(gridSize, words);
		String grid = "";
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid += contents[i][j] + " ";
			}
			grid += "\r\n";
		}
		return grid;
	}

	@GetMapping("/akshay")
	public String getName() {
		return "Akshay";
	}
}
