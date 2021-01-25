package org.xc.utils.spring.data;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.xc.utils.commons.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    private final int pageNum;
    private final int pageSize;
    private final List<String> availableColumn;
    private final List<Sort.Order> defaultOrder;
    private final List<Sort.Order> orders;
    private boolean tolerateFaultColumn = true;

    private PageUtils(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.availableColumn = new ArrayList<>();
        this.defaultOrder = new ArrayList<>();
        this.orders = new ArrayList<>();
    }


    public static PageUtils create(int pageNum, int pageSize) {
        return new PageUtils(pageNum, pageSize);
    }

    public PageUtils availableColumn(Class clazz) {
        List<String> fieldName = ReflectionUtils.getAllFieldName(clazz);
        for (String name : fieldName) {
            this.availableColumn.add(name);
        }
        return this;
    }

    public PageUtils availableColumn(String... columns) {
        if (columns != null) {
            for (String column : columns) {
                this.availableColumn.add(column);
            }
        }
        return this;
    }

    public PageUtils availableEntityColumn(Object entity) {
        List<String> fieldName = ReflectionUtils.getAllFieldName(entity);
        for (String name : fieldName) {
            this.availableColumn.add(name);
        }
        return this;
    }

    public PageUtils defaultOrder(Sort.Order... orders) {
        if (orders != null) {
            for (Sort.Order order : orders) {
                this.defaultOrder.add(order);
            }
        }
        return this;
    }

    public PageUtils order(Sort.Order... orders) {
        if (orders != null) {
            for (Sort.Order order : orders) {
                this.orders.add(order);
            }
        }
        return this;
    }

    public PageUtils tolerateFaultColumn(boolean tolerateFaultColumn) {
        this.tolerateFaultColumn = tolerateFaultColumn;
        return this;
    }

    public Pageable buildPageable() {
        Sort sort = sort();
        return PageRequest.of(this.pageNum, this.pageSize, sort);
    }

    private Sort sort() {
        List<Sort.Order> useOrders = new ArrayList<>();
        if (!this.availableColumn.isEmpty() && !this.orders.isEmpty()) {
            for (Sort.Order order : this.orders) {
                if (this.availableColumn.contains(order.getProperty())) {
                    useOrders.add(order);
                    continue;
                }
                if (!this.tolerateFaultColumn) {
                    throw new IllegalArgumentException("incorrect order column : " + order.getProperty());
                }
            }
        } else if (this.availableColumn.isEmpty() && !this.orders.isEmpty()) {
            useOrders.addAll(this.orders);
        }

        if (useOrders.isEmpty()) {
            useOrders.addAll(this.defaultOrder);
        }

        return Sort.by(useOrders);
    }

    private void verifyOrders() {
        if (!this.availableColumn.isEmpty() && !this.orders.isEmpty()) {
            this.orders.forEach(order -> {
                if (!this.availableColumn.contains(order.getProperty())) {
                    throw new IllegalArgumentException("incorrect order column : " + order.getProperty());
                }
            });
        }
    }


}
