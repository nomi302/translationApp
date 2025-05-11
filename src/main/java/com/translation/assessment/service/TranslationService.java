package com.translation.assessment.service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.translation.assessment.dto.TranslationRequest;
import com.translation.assessment.dto.TranslationResponse;
import com.translation.assessment.entity.Tag;
import com.translation.assessment.entity.Translation;
import com.translation.assessment.exception.ResourceNotFoundException;
import com.translation.assessment.repository.TranslationRepository;
import com.translation.assessment.repository.TranslationRepository.TranslationProjection;

import jakarta.transaction.Transactional;

@Service
public class TranslationService {

	private final TranslationRepository translationRepository;
	private final TagService tagService;

	public TranslationService(TranslationRepository translationRepository, TagService tagService) {
		this.translationRepository = translationRepository;
		this.tagService = tagService;
	}

	public TranslationResponse createTranslation(TranslationRequest request) {
		Set<Tag> tags = resolveTags(request.getTags());

		Translation translation = Translation.builder().locale(request.getLocale().toLowerCase()).key(request.getKey())
				.content(request.getContent()).tags(tags).build();

		return mapToResponse(translationRepository.save(translation));
	}

	@Transactional
	public TranslationResponse getTranslation(Long id) {
		return mapToResponse(findById(id));
	}

	public TranslationResponse updateTranslation(Long id, TranslationRequest request) {
		Translation translation = findById(id);

		translation.setLocale(request.getLocale().toLowerCase());
		translation.setKey(request.getKey());
		translation.setContent(request.getContent());
		translation.setTags(resolveTags(request.getTags()));

		return mapToResponse(translationRepository.save(translation));
	}

	public void deleteTranslation(Long id) {
		translationRepository.delete(findById(id));
	}

	@Transactional
	public Page<TranslationResponse> searchTranslations(Set<String> locales, Set<String> tags, String key,
			String content, Pageable pageable) {
		return translationRepository
				.search(locales != null ? locales.stream().map(String::toLowerCase).collect(Collectors.toSet()) : null,
						tags != null ? tags.stream().map(String::toLowerCase).collect(Collectors.toSet()) : null, key,
						content, pageable)
				.map(this::mapToResponse);
	}

	@Transactional
	public Map<String, Map<String, String>> exportTranslations() {
	    return translationRepository.findAllForExport().stream()
	        .collect(Collectors.groupingBy(
	            TranslationProjection::getLocale,
	            Collectors.toMap(
	                TranslationProjection::getKey,  // Key
	                TranslationProjection::getContent,  // Value
	                (existing, replacement) -> existing  // Keep the first occurrence
	            )
	        ));
	}

	private Translation findById(Long id) {
		return translationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Translation not found with id: " + id));
	}

	private Set<Tag> resolveTags(Set<String> tagNames) {
		return tagNames.stream().map(String::toLowerCase).map(tagService::findOrCreate).collect(Collectors.toSet());
	}

	private TranslationResponse mapToResponse(Translation translation) {
		return TranslationResponse.builder().id(translation.getId()).locale(translation.getLocale())
				.key(translation.getKey()).content(translation.getContent())
				.tags(translation.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
				.createdAt(translation.getCreatedAt()).updatedAt(translation.getUpdatedAt()).build();
	}
	
}