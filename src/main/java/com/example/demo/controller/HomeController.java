package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.models.LocationStats;
import com.example.demo.services.CoronaVirusDataService;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model ) {
		List<LocationStats> allStats = coronaVirusDataService.getAllStats();
		int totalreportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalReport()).sum();
		int totalreportedCasesToday = allStats.stream().mapToInt(stat -> stat.getDifferenceFromPrevDay()).sum();
		model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
		model.addAttribute("totalreportedCases", totalreportedCases);
		model.addAttribute("totalreportedCasesToday", totalreportedCasesToday);
		return "home";
	}

}
