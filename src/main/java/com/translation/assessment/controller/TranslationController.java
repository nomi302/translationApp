package com.translation.assessment.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.translation.assessment.dto.TranslationRequest;
import com.translation.assessment.dto.TranslationResponse;
import com.translation.assessment.service.TranslationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

	private final TranslationService translationService;

	public TranslationController(TranslationService translationService) {
		this.translationService = translationService;
	}

	// Create Translation
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public TranslationResponse createTranslation(@Valid @RequestBody TranslationRequest request) {
		return translationService.createTranslation(request);
	}

	// Get Translation
	@GetMapping("/{id}")
	public TranslationResponse getTranslation(@PathVariable Long id) {
		return translationService.getTranslation(id);
	}

	// Update Translation
	@PutMapping("/{id}")
	public TranslationResponse updateTranslation(@PathVariable Long id,
			@Valid @RequestBody TranslationRequest request) {
		return translationService.updateTranslation(id, request);
	}

	// Delete Translation
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTranslation(@PathVariable Long id) {
		translationService.deleteTranslation(id);
	}

	// Search Translations
	@GetMapping("/search")
	public Page<TranslationResponse> searchTranslations(@RequestParam(required = false) Set<String> locales,
			@RequestParam(required = false) Set<String> tags, @RequestParam(required = false) String key,
			@RequestParam(required = false) String content, Pageable pageable) {
		return translationService.searchTranslations(locales, tags, key, content, pageable);
	}

	// Export Translations
	@GetMapping("/export")
	public Map<String, Map<String, String>> exportTranslations() {
		return translationService.exportTranslations();
	}
}
