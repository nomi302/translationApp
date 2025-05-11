package com.translation.assessment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.translation.assessment.entity.Translation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

	@Query("SELECT t FROM Translation t " + "LEFT JOIN t.tags tag "
			+ "WHERE (:locales IS NULL OR t.locale IN :locales) " + "AND (:tags IS NULL OR tag.name IN :tags) "
			+ "AND (:key IS NULL OR t.key = :key) "
			+ "AND (:content IS NULL OR LOWER(t.content) LIKE LOWER(CONCAT('%', :content, '%')))")
	Page<Translation> search(@Param("locales") Set<String> locales, @Param("tags") Set<String> tags,
			@Param("key") String key, @Param("content") String content, Pageable pageable);

	@Query("SELECT t.locale as locale, t.key as key, t.content as content FROM Translation t")
	List<TranslationProjection> findAllForExport();

	public interface TranslationProjection {
		String getLocale();

		String getKey();

		String getContent();
		
		@EntityGraph(attributePaths = {"tags"})
		Optional<Translation> findById(Long id);
	}
}
