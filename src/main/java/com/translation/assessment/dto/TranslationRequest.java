package com.translation.assessment.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TranslationRequest {
	@NotBlank
	@Size(min = 2, max = 5)
	private String locale;

	@NotBlank
	@Size(min = 2, max = 100)
	private String key;

	@NotBlank
	@Size(min = 1, max = 1000)
	private String content;

	@NotEmpty
	private Set<@Size(min = 2, max = 20) String> tags;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public TranslationRequest(@NotBlank @Size(min = 2, max = 5) String locale,
			@NotBlank @Size(min = 2, max = 100) String key, @NotBlank @Size(min = 1, max = 1000) String content,
			@NotEmpty Set<@Size(min = 2, max = 20) String> tags) {
		super();
		this.locale = locale;
		this.key = key;
		this.content = content;
		this.tags = tags;
	}
	
	

}
