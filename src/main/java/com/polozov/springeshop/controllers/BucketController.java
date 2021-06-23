package com.polozov.springeshop.controllers;

import com.polozov.springeshop.dto.BucketDto;
import com.polozov.springeshop.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BucketController {

	private final BucketService bucketService;

	public BucketController(BucketService bucketService) {
		this.bucketService = bucketService;
	}

	@GetMapping("/bucket")
	public String aboutBucket(Model model, Principal principal){
		if(principal == null){
			model.addAttribute("bucket", new BucketDto());
		}
		else {
			BucketDto bucketDto = bucketService.getBucketByUser(principal.getName());
			model.addAttribute("bucket", bucketDto);
		}

		return "bucket";
	}

	@PostMapping("/bucket")
	public String commitBucket(Principal principal){
		if(principal != null){
			bucketService.commitBucketToOrder(principal.getName());
		}
		return "redirect:/bucket";
	}

}

