package com.translation.assessment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static TagBuilder builder() {
		return new TagBuilder();
	}

	public static class TagBuilder {
		private Long id;
		private String name;

		public TagBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public TagBuilder name(String name) {
			this.name = name;
			return this;
		}

		public Tag build() {
			Tag tag = new Tag();
			tag.setId(id);
			tag.setName(name);
			return tag;
		}
	}

}
