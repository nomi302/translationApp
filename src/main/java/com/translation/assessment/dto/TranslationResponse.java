package com.translation.assessment.dto;

import java.time.Instant;
import java.util.Set;

import lombok.Data;

@Data
public class TranslationResponse {
	
	private Long id;
	private String locale;
	private String key;
	private String content;
	private Set<String> tags;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
	
	 public TranslationResponse(Long id, String locale, String key, String content, Set<String> tags) {
		this.id = id;
		this.locale = locale;
		this.key = key;
		this.content = content;
		this.tags = tags;
	}



	public TranslationResponse(Long id, String locale, String key, String content, Set<String> tags, Instant createdAt,
			Instant updatedAt) {
		super();
		this.id = id;
		this.locale = locale;
		this.key = key;
		this.content = content;
		this.tags = tags;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public TranslationResponse() {
		// TODO Auto-generated constructor stub
	}

	// Builder implementation
    public static TranslationResponseBuilder builder() {
        return new TranslationResponseBuilder();
    }

    public static class TranslationResponseBuilder {
        private Long id;
        private String locale;
        private String key;
        private String content;
        private Set<String> tags;
        private Instant createdAt;
        private Instant updatedAt;

        public TranslationResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TranslationResponseBuilder locale(String locale) {
            this.locale = locale;
            return this;
        }

        public TranslationResponseBuilder key(String key) {
            this.key = key;
            return this;
        }

        public TranslationResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public TranslationResponseBuilder tags(Set<String> tags) {
            this.tags = tags;
            return this;
        }

        public TranslationResponseBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TranslationResponseBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public TranslationResponse build() {
            TranslationResponse response = new TranslationResponse();
            response.setId(id);
            response.setLocale(locale);
            response.setKey(key);
            response.setContent(content);
            response.setTags(tags);
            response.setCreatedAt(createdAt);
            response.setUpdatedAt(updatedAt);
            return response;
        }
    }

}