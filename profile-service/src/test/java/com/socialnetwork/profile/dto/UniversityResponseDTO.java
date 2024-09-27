package com.socialnetwork.profile.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityResponseDTO {
    int page;

    @SerializedName("per_page")
    int perPage;
    int total;

    @SerializedName("total_pages")
    int totalPages;
    List<University> data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class University {
        String university;

        @SerializedName("rank_display")
        String rankDisplay;
        Double score;
        String type;

        @SerializedName("student_faculty_ratio")
        Integer studentFacultyRatio;

        @SerializedName("international_students")
        String internationalStudents;

        @SerializedName("faculty_count")
        String facultyCount;
        Location location;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class Location {
            String city;
            String country;
            String region;
        }
    }
}
