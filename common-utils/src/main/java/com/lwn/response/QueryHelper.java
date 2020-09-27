package com.lwn.response;

import com.github.pagehelper.PageHelper;
import com.lwn.common.BeanUtil;
import com.lwn.enumeration.OrderType;
import com.lwn.request.PageCondition;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Deprecated
public class QueryHelper {

    public static String createFullLikeFilterText(String filterText) {
        String validFilter = null;

        if (filterText != null) {
            validFilter = "%" + StringUtils.trim(filterText) + "%";
        }

        return validFilter;
    }

    public static void setupPageCondition(Map<String, String> mapping, PageCondition pageCondition) {
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());

        String sortString = getSortString(mapping, pageCondition);
        if (StringUtils.isNotBlank(sortString)) {
            PageHelper.orderBy(sortString);
        }
    }

    public static void setupPageCondition(PageCondition pageCondition, String sort, OrderType orderType) {
        if (StringUtils.isBlank(sort) || orderType == null) {

            throw new RuntimeException("order error");
        }
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());
        String sortString = String.format("%s %s", sort, orderType.getValue());
        PageHelper.orderBy(sortString);
    }

    public static void setupPageCondition(PageCondition pageCondition) {
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());
    }

    public static void setupOrder(String sort, OrderType orderType) {
        String sortString = String.format("%s %s", sort, orderType.getValue());
        PageHelper.orderBy(sortString);
    }

    public static <I, R> Paging<R> getPaging(List<I> data, Class<R> clazz) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanUtil.target(clazz).acceptList(data));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    public static <I, R> Paging<R> getPaging(List<I> data, Class<R> clazz, BiConsumer<R, I> consumer) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanUtil.target(clazz).acceptListDefault(data, consumer));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    public static <I> Paging<I> getPaging(List<I> data) {
        com.github.pagehelper.PageInfo<I> pageInfo = new com.github.pagehelper.PageInfo<>(data);

        return new Paging<>(pageInfo);
    }

    private static String getSortString(Map<String, String> mapping, PageCondition pageCondition) {
        if (pageCondition.getSort() != null) {
            String orderBy = mapping.get(pageCondition.getSort());
            if (orderBy == null) {

            } else {
                return String.format("%s %s", orderBy, pageCondition.getOrderType().getValue());
            }
        }

        return "";
    }

}
