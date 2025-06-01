package com.bank.util;

import com.bank.entity.vo.PageInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具类
 */
public class PageUtils {
    public static final int DEAULT_PAGE_NUM = 1;
    public static final int DEAULT_PAGE_SIZE = 20;

    /**
     * 将实体类列表转换成分页列表
     * param:
     * arrayList---查询结果list
     * pageNum---分页参数，页码，从1开始
     * pageSize---分页参数，每页大小
     * total---分页查询总数
     */
    public static <T> PageInfo<T> list2PageInfo(List<T> arrayList, Integer pageNum, Integer pageSize, long total) {
        if (pageNum < DEAULT_PAGE_NUM) {
            pageNum = DEAULT_PAGE_NUM;
        }
        //实现list分页
        long offset = (pageNum - 1) * pageSize;
        List<T> limitList = arrayList.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
        PageInfo<T> pageInfo = new PageInfo<T>(limitList);
        //设置总条数
        pageInfo.setTotal(total);

        boolean hasPreviousPage = pageNum == DEAULT_PAGE_NUM ? false : true;
        int pages = total % pageSize == 0 ? (int) total / pageSize : (int) (total / pageSize) + 1;
        boolean isFirstPage = pageNum == DEAULT_PAGE_NUM;
        boolean isLastPage = pageNum == pages || pages == 0;
        pageInfo.setHasPreviousPage(hasPreviousPage);
        pageInfo.setFirstPage(isFirstPage);
        pageInfo.setLastPage(isLastPage);
        boolean hasNextPage = pageNum < pages;
        pageInfo.setHasNextPage(hasNextPage);
        pageInfo.setNavigateLastPage(pages);

        int[] navigatePageNums = calcNavigatepageNums(pages, pageNum, PageInfo.DEFAULT_NAVIGATE_PAGES);
        pageInfo.setNavigatepageNums(navigatePageNums);
        int nextPage = pageNum < pages ? pageNum + 1 : 0;
        pageInfo.setNextPage(nextPage);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPages(pages);

        pageInfo.setSize(pageInfo.getList().size());
        int prePage = 0;
        if (pageNum > DEAULT_PAGE_NUM) {
            prePage = pageNum - 1;
        }
        pageInfo.setPrePage(prePage);

        int starRow = 0;
        long endRow = 0;
        if (!CollectionUtils.isEmpty(limitList)) {
            starRow = pageNum > 0 ? (pageNum - 1) * pageSize + 1 : 1;
            //当前页面最后一个元素在数据库中的行号，计算实际的endRow（最后一页的时候特殊）
            endRow = starRow - 1 + limitList.size();
        }
        pageInfo.setStartRow(starRow);
        pageInfo.setEndRow(endRow);
        return pageInfo;
    }


    /**
     * 计算导航页
     */
    private static int[] calcNavigatepageNums(int pages, int pageNum, int navigatePages) {
        int[] navigatepageNums = null;
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }

        return navigatepageNums;
    }
}
