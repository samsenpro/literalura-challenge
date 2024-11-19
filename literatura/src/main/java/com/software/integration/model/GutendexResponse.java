package com.software.integration.model;

import java.util.List;

public class GutendexResponse {

    private List<BookResponse> results;

    public List<BookResponse> getResults() {
        return results;
    }

    public void setResults(List<BookResponse> results) {
        this.results = results;
    }

    public static class BookResponse {
        private String title;
        private boolean copyright;
        private List<String> languages;
        private Integer downloadCount;
        private List<AuthorResponse> authors;

        // Getters y setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCopyright() {
            return copyright;
        }

        public void setCopyright(boolean copyright) {
            this.copyright = copyright;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public Integer getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(Integer downloadCount) {
            this.downloadCount = downloadCount;
        }

        public List<AuthorResponse> getAuthors() {
            return authors;
        }

        public void setAuthors(List<AuthorResponse> authors) {
            this.authors = authors;
        }
    }

    public static class AuthorResponse {
        private String name;
        private Integer birthYear;
        private Integer deathYear;

        // Getters y setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }

        public Integer getDeathYear() {
            return deathYear;
        }

        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }
    }
}
