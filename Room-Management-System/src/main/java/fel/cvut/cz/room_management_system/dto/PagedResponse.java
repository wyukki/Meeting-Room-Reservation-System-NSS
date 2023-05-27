package fel.cvut.cz.room_management_system.dto;

import java.util.List;

public class PagedResponse<T> {

    private List<T> objectList;

    private Integer currentPageNumber;

    private long totalPageCount;

    public void setObjectList(List<T> objectList) {
        this.objectList = objectList;
    }

    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public void setTotalPageCount(long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List<T> getObjectList() {
        return objectList;
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public long getTotalPageCount() {
        return totalPageCount;
    }
}
