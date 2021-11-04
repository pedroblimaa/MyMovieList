package com.myMovieList.config.validation;

import java.util.Arrays;

import com.myMovieList.config.exception.HandledException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ValidateQueryParamsService {

    public void validatePagination(Pageable pagination, Object modelClass) throws HandledException {

        this.validateSize(pagination.getPageSize());
        this.validateSort(pagination.getSort().toString(), modelClass);
    }

    private void validateSize(Integer size) throws HandledException {

        if (size > 100) {
            throw new HandledException("Max page size is 100", 400);
        }
    }

    private void validateSort(String sort, Object modelClass) throws HandledException {
        if (sort.equals("UNSORTED")) {
            return;
        }

        String[] sortSplit = sort.split(":");
        String sortParam = sortSplit[0];
        String sortDirection = sortSplit[1];

        if (Arrays.stream(modelClass.getClass().getDeclaredFields()).noneMatch(f -> f.getName().equals(sortParam))
                || !sortDirection.equals(" DESC") && !sortDirection.equals(" ASC")) {
            throw new HandledException("Sort param is incorrect", 400);
        }
    }
}
