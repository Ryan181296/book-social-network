package com.socialnetwork.profile;

import com.socialnetwork.profile.dto.UniversityResponseDTO;
import com.socialnetwork.profile.mapper.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ImplementAPITest {
    private static final String URL = "https://jsonmock.hackerrank.com/api/universities?page=%s";

    @Test
    void testCase1() {
        var firstCity = "Singapore";
        var secondCity = "New York City";

        var result = highestInternationalStudents(firstCity, secondCity);
        log.info("case 1: {}", result);
    }

    @Test
    void testCase2() {
        var firstCity = "Sample Input For Custom Testing Pune";
        var secondCity = "New Delhi";

        var result = highestInternationalStudents(firstCity, secondCity);
        log.info("case 2: {}", result);
    }

    private String highestInternationalStudents(String firstCity, String lastCity) {
        var universities = fetchUniversities();

        var universityOfFirstCity = getUniversity(universities, firstCity);
        if (StringUtils.hasText(universityOfFirstCity)) {
            return universityOfFirstCity;
        }
        return getUniversity(universities, lastCity);
    }

    private String getUniversity(List<UniversityResponseDTO.University> universities, String city) {
        return universities.stream()
                .filter(university -> university.getLocation().getCity().equals(city))
                .max(Comparator.comparingInt(university -> Integer.parseInt(university.getInternationalStudents().replace(",", ""))))
                .map(UniversityResponseDTO.University::getUniversity)
                .orElse(null);
    }

    private List<UniversityResponseDTO.University> fetchUniversities() {
        int page = 1;
        int totalPages = 1;

        List<UniversityResponseDTO.University> universities = new ArrayList<>();
        while (page <= totalPages) {
            var requestUri = String.format(URL, page);
            var responseData = getApiResponse(requestUri);

            if (Objects.nonNull(responseData)) {
                totalPages = responseData.getTotalPages();
                var universitiesOfCurrentPage = responseData.getData();
                if (!CollectionUtils.isEmpty(universitiesOfCurrentPage)) {
                    universities.addAll(universitiesOfCurrentPage);
                }
            }
            page++;
        }
        return universities;
    }

    private UniversityResponseDTO getApiResponse(String requestUri) {
        try {
            var url = new URL(requestUri);
            var connection = (HttpURLConnection) url.openConnection();
            var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            var stringBuilder = new StringBuilder();
            String lineData;
            while ((lineData = reader.readLine()) != null) {
                stringBuilder.append(lineData);
            }

            return JsonMapper.map(stringBuilder.toString(), UniversityResponseDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
