package com.translation.assessment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "translations", indexes = { @Index(name = "idx_locale", columnList = "locale"),
		@Index(name = "idx_translation_key", columnList = "translationKey") })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 5)
	private String locale;

	@Column(name = "translation_key", nullable = false)
	private String key;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "translation_tags", joinColumns = @JoinColumn(name = "translation_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	@CreationTimestamp
	private Instant createdAt;

	@UpdateTimestamp
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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
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
	
	public static TranslationBuilder builder() {
	    return new TranslationBuilder();
	}

	public static class TranslationBuilder {
	    private Long id;
	    private String locale;
	    private String key;
	    private String content;
	    private Set<Tag> tags;
	    private Instant createdAt;
	    private Instant updatedAt;
	    
	    public TranslationBuilder id(Long id) { this.id = id; return this; }
	    public TranslationBuilder locale(String locale) { this.locale = locale; return this; }
	    public TranslationBuilder key(String key) { this.key = key; return this; }
	    public TranslationBuilder content(String content) { this.content = content; return this; }
	    public TranslationBuilder tags(Set<Tag> tags) { this.tags = tags; return this; }
	    public TranslationBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
	    public TranslationBuilder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }
	    
	    public Translation build() {
	        Translation translation = new Translation();
	        translation.setId(id);
	        translation.setLocale(locale);
	        translation.setKey(key);
	        translation.setContent(content);
	        translation.setTags(tags);
	        translation.setCreatedAt(createdAt);
	        translation.setUpdatedAt(updatedAt);
	        return translation;
	    }
	}

}


