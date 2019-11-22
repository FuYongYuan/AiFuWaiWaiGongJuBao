package page;

/**
 * 页标签实体类
 */
public class PageEntity {
    /**
     * 创建页标签实体类
     *
     * @param pageShow      页标签名称
     * @param pageNumber    实际对应的页数
     * @param pageUrl       页标签地址
     * @param isShow        是否要显示 1显示 2不显示
     * @param isCurrentPage 是否当前页 1是 2否
     */
    public PageEntity(String pageShow, long pageNumber, String pageUrl, boolean isShow, boolean isCurrentPage) {
        this.pageShow = pageShow;
        this.pageNumber = pageNumber;
        this.pageUrl = pageUrl.replaceFirst("\\$", String.valueOf(pageNumber));
        this.isShow = isShow;
        this.isCurrentPage = isCurrentPage;
    }

    /**
     * 页标签名称
     */
    private String pageShow;
    /**
     * 实际页数
     */
    private long pageNumber;
    /**
     * 页标签地址
     */
    private String pageUrl;
    /**
     * 是否显示 true显示 false不显示
     */
    private boolean isShow;
    /**
     * 是否当前页 true是 false否
     */
    private boolean isCurrentPage;

    /**
     * 页标签名称
     */
    public String getPageShow() {
        return pageShow;
    }

    /**
     * 页标签名称
     */
    public void setPageShow(String pageShow) {
        this.pageShow = pageShow;
    }

    /**
     * 实际页数
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * 实际页数
     */
    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 是否显示 true显示 false不显示
     */
    public boolean getIsShow() {
        return isShow;
    }

    /**
     * 是否显示 true显示 false不显示
     */
    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * 页标签地址
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * 页标签地址
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /**
     * 是否当前页 true是 false否
     */
    public boolean getIsCurrentPage() {
        return isCurrentPage;
    }

    /**
     * 是否当前页 true是 false否
     */
    public void setIsCurrentPage(boolean isCurrentPage) {
        this.isCurrentPage = isCurrentPage;
    }
}
