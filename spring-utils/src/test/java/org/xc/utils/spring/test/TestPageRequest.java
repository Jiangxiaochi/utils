package org.xc.utils.spring.test;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.xc.utils.spring.data.PageUtils;

public class TestPageRequest {

    public static void main(String[] args) {
        Pageable pageable = PageUtils.create(0, 100)
                .tolerateFaultColumn(false)
                .availableEntityColumn(new Tree())
                .order(Sort.Order.asc("hahaha"), Sort.Order.asc("name"), Sort.Order.asc("pid"))
                .defaultOrder(Sort.Order.desc("name"), Sort.Order.desc("pid"))
                .buildPageable();
        System.out.println(pageable);
    }

}
