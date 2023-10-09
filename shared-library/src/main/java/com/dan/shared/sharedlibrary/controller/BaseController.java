package com.dan.shared.sharedlibrary.controller;

import com.dan.shared.sharedlibrary.model.request.SearchRequest;
import com.dan.shared.sharedlibrary.model.request.SpecificationRequest;
import com.dan.shared.sharedlibrary.util.CommonConstants;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseController {

    protected Pageable buildPageableFromRequest(SearchRequest input){
        String sortBy = StringUtils.isEmpty(input.getSortBy()) ? "id" : input.getSortBy();
        Sort.Direction sortOrder = StringUtils.isNotEmpty(input.getSortOrder()) && input.getSortOrder().equals("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(input.getPage(), input.getSize(), Sort.by(sortOrder, sortBy));
    }

    protected Specification buildSearchSpecification(List<String> columns, SpecificationRequest input){
        doValidateColumns(columns);
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(String column : columns){
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(column),input.getTextSearch())));
            }
            if(ObjectUtils.isNotEmpty(input.isShowDeleted()) && input.isShowDeleted()){
                doValidateShowDeleted(input);
                Path column = root.get(input.getDeletedColumn());
                Object value = transformDeletedValue(input.getDeletedValue());
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(column, value)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Object transformDeletedValue(Object deletedValue){
        if(deletedValue instanceof Integer){
            return Integer.valueOf((Integer) deletedValue);
        }
        if(deletedValue instanceof Boolean){
            return Boolean.valueOf((Boolean) deletedValue);
        }
        return deletedValue;
    }

    private void doValidateColumns(List<String> columns){
        if(ObjectUtils.isEmpty(columns)){
            log.error(CommonConstants.ERR_MSG_SPECIFICATION_COLUMNS_EMPTY);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
    }

    private void doValidateShowDeleted(SpecificationRequest input){
        if(ObjectUtils.isEmpty(input.getDeletedColumn())){
            log.error(CommonConstants.ERR_MSG_SPECIFICATION_DELETED_COLUMN_EMPTY);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
        if(ObjectUtils.isEmpty(input.getDeletedValue())){
            log.error(CommonConstants.ERR_MSG_SPECIFICATION_DELETED_VALUE_EMPTY);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, CommonConstants.ERR_MSG_EXPECTATION_FAILED);
        }
    }

}
