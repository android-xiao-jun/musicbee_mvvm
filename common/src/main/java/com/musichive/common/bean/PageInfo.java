package com.musichive.common.bean;


import java.util.List;

/**
 *
 * 分页基础类.
 *
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-10-29 : 下午3:45
 */
public class PageInfo<T>  {

    private int pageNum;

    private int pageSize;

    private boolean isHasNextPage;

    private List<T> list;

    private double totalMoney;

    private int groupCount;

    private int totalPage;

    private int totalRecord;

    private int pageFlag;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNextPage() {
        return isHasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        isHasNextPage = hasNextPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(int pageFlag) {
        this.pageFlag = pageFlag;
    }
}