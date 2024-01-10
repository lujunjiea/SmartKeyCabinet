package com.example.smartkeycabinet.model;

import java.io.Serializable;
import java.util.List;


public class CarListBean implements Serializable {


    /**
     * total : 16
     * list : [{"id":"1","parkingArea":"A区","parkingNo":"101","parkingType":"小车位","inTime":"2023-12-19 21:54:00","parkingStatus":"有车","plateNumber":"鲁B123456","images":"937765154664259585","isDel":false,"createTime":"2023-11-17 17:59:45","updateTime":"2023-12-19 21:54:00"},{"id":"937761844410490881","parkingArea":"A区","parkingNo":"1112","parkingType":"大车位","inTime":"2023-12-05 00:00:00","outTime":"2023-12-05 00:00:00","parkingStatus":"有车","plateNumber":"111","isDel":false,"createTime":"2023-12-02 17:30:46","updateTime":"2023-12-02 17:39:04"},{"id":"937765159894556672","parkingArea":"A区","parkingNo":"11111111111","parkingType":"大车位","inTime":"2023-12-02 00:00:00","outTime":"2023-11-30 00:00:00","parkingStatus":"有车","plateNumber":"11111","images":"937765154664259585","isDel":false,"createTime":"2023-12-02 17:43:56","updateTime":"2023-12-03 21:55:34"},{"id":"934577541258190848","parkingArea":"A区","parkingNo":"111","parkingType":"大车位","inTime":"2023-11-06 00:00:00","outTime":"2023-11-03 00:00:00","parkingStatus":"有车","plateNumber":"111111","isDel":false,"createTime":"2023-11-23 22:37:29","updateTime":"2023-11-25 16:31:21"},{"id":"935210045283540992","parkingArea":"B区","parkingNo":"111","parkingType":"大车位","inTime":"2023-11-04 00:00:00","outTime":"2023-11-02 00:00:00","parkingStatus":"有车","plateNumber":"111","isDel":false,"createTime":"2023-11-25 16:30:50","updateTime":"2023-11-25 16:30:50"},{"id":"935515835710083072","parkingArea":"C区","parkingType":"大车位","inTime":"2023-11-03 00:00:00","outTime":"2023-11-13 00:00:00","parkingStatus":"有车","plateNumber":"1111111","createTime":"2023-11-26 12:45:56","updateTime":"2023-11-26 12:45:56"},{"id":"935517835323547648","parkingArea":"C区123","parkingType":"大车位","inTime":"2023-11-03 00:00:00","outTime":"2023-11-13 00:00:00","parkingStatus":"有车","plateNumber":"1111111","createTime":"2023-11-26 12:53:53","updateTime":"2023-11-26 12:53:53"},{"id":"935515726570098689","parkingArea":"C区","parkingType":"大车位","inTime":"2023-11-03 00:00:00","outTime":"2023-11-13 00:00:00","parkingStatus":"有车","plateNumber":"1111111","createTime":"2023-11-26 12:45:30","updateTime":"2023-11-26 12:45:30"},{"id":"935519391158669313","parkingArea":"C区12322","parkingNo":"1111","parkingType":"大车位","inTime":"2023-11-03 00:00:00","outTime":"2023-11-13 00:00:00","parkingStatus":"有车","plateNumber":"1111111","isDel":false,"createTime":"2023-11-26 13:00:04","updateTime":"2023-11-26 13:00:04"},{"id":"935517579995291649","parkingArea":"C区","parkingType":"大车位","inTime":"2023-11-03 00:00:00","outTime":"2023-11-13 00:00:00","parkingStatus":"有车","plateNumber":"1111111","createTime":"2023-11-26 12:52:52","updateTime":"2023-11-26 12:52:52"},{"id":"933425924555214848","parkingArea":"A04","parkingType":"大车位","inTime":"2023-02-12 12:32:32","outTime":"2023-02-12 12:32:32","parkingStatus":"有车","plateNumber":"鲁C4321","createTime":"2023-11-20 18:21:21","updateTime":"2023-11-20 18:21:21"},{"id":"933425924135784449","parkingArea":"A03","parkingType":"大车位","inTime":"2023-02-12 12:32:32","outTime":"2023-02-12 12:32:32","parkingStatus":"有车","plateNumber":"鲁C432","createTime":"2023-11-20 18:21:20","updateTime":"2023-11-20 18:21:20"},{"id":"938186671604670465","isDel":false,"createTime":"2023-12-03 21:38:53","updateTime":"2023-12-03 21:38:53"},{"id":"935554264846082049","isDel":false,"createTime":"2023-11-26 15:18:38","updateTime":"2023-11-26 15:18:38"},{"id":"940755106855428097","isDel":false,"createTime":"2023-12-10 23:44:55","updateTime":"2023-12-10 23:44:55"},{"id":"935568391052238849","isDel":false,"createTime":"2023-11-26 16:14:46","updateTime":"2023-11-26 16:14:46"}]
     * pageNum : 1
     * pageSize : 20
     * size : 16
     * startRow : 1
     * endRow : 16
     * pages : 1
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     */

    private String total;
    private int pageNum;
    private int pageSize;
    private int size;
    private String startRow;
    private String endRow;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private List<ListBean> list;
    private List<Integer> navigatepageNums;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 1
         * parkingArea : A区
         * parkingNo : 101
         * parkingType : 小车位
         * inTime : 2023-12-19 21:54:00
         * parkingStatus : 有车
         * plateNumber : 鲁B123456
         * images : 937765154664259585
         * isDel : false
         * createTime : 2023-11-17 17:59:45
         * updateTime : 2023-12-19 21:54:00
         * outTime : 2023-12-05 00:00:00
         */

        private String id;
        private String parkingArea;
        private String parkingNo;
        private String parkingType;
        private String inTime;
        private String parkingStatus;
        private String plateNumber;
        private String images;
        private boolean isDel;
        private String createTime;
        private String updateTime;
        private String outTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParkingArea() {
            return parkingArea;
        }

        public void setParkingArea(String parkingArea) {
            this.parkingArea = parkingArea;
        }

        public String getParkingNo() {
            return parkingNo;
        }

        public void setParkingNo(String parkingNo) {
            this.parkingNo = parkingNo;
        }

        public String getParkingType() {
            return parkingType;
        }

        public void setParkingType(String parkingType) {
            this.parkingType = parkingType;
        }

        public String getInTime() {
            return inTime;
        }

        public void setInTime(String inTime) {
            this.inTime = inTime;
        }

        public String getParkingStatus() {
            return parkingStatus;
        }

        public void setParkingStatus(String parkingStatus) {
            this.parkingStatus = parkingStatus;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public boolean isDel() {
            return isDel;
        }

        public void setDel(boolean del) {
            isDel = del;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }
    }


}
