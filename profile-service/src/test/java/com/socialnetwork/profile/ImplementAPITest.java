package com.socialnetwork.profile;

import com.socialnetwork.profile.dto.UniversityResponseDTO;
import com.socialnetwork.profile.mapper.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private String highestInternationalStudents(String firstCity, String secondCity) {
        var universities = fetchUniversities();

        var universityOfFirstCity = getUniversityWithMaxStudents(universities, firstCity);
        if (StringUtils.hasText(universityOfFirstCity)) {
            return universityOfFirstCity;
        } else {
            return getUniversityWithMaxStudents(universities, secondCity);
        }
    }

    private String getUniversityWithMaxStudents(List<UniversityResponseDTO.University> universities, String city) {
        return universities.stream()
                .filter(university -> university.getLocation().getCity().equals(city))
                .max(Comparator.comparingInt(university -> Integer.parseInt(university.getInternationalStudents().replace(",",""))))
                .map(UniversityResponseDTO.University::getUniversity)
                .orElse(null);
    }

    private List<UniversityResponseDTO.University> fetchUniversities() {
        var urlStr = "https://jsonmock.hackerrank.com/api/universities?page=%s";
        int page = 1;
        int totalPages = 1;

        List<UniversityResponseDTO.University> universities = new ArrayList<>();
        try {
            while (page <= totalPages) {
                var requestUri = String.format(urlStr, page);
                var responseJson = getApiResponse(requestUri);
                var responseData = JsonMapper.map(responseJson, UniversityResponseDTO.class);

                if (Objects.nonNull(responseData)) {
                    totalPages = responseData.getTotalPages();
                    var universitiesOfCurrentPage = responseData.getData();

                    if (!CollectionUtils.isEmpty(universitiesOfCurrentPage)) {
                        universities.addAll(universitiesOfCurrentPage);
                    }
                }
                page++;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return universities;
    }

    private String getApiResponse(String requestUri) throws Exception {
        var url = new URL(requestUri);
        var connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(RequestMethod.GET.name());

        var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        var builder = new StringBuilder();

        String dateLine;
        while ((dateLine = reader.readLine()) != null) {
            builder.append(dateLine);
        }
        return builder.toString();
    }
}
