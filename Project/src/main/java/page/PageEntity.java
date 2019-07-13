package page;

/**
 * 页标签实体类
 *
 * @author FYY
 */
public class PageEntity {
    /**
     * 创建页标签实体类
     *
     * @param pageName      页标签名称
     * @param pageNumber    实际对应的页数
     * @param pageUrl       页标签地址
     * @param isShow        是否要显示
     * @param isCurrentPage 是否当前页 1是 2否
     */
    public PageEntity(String pageName, Integer pageNumber, String pageUrl, int isShow, int isCurrentPage) {
        this.pageName = pageName;
        this.pageNumber = pageNumber;
        this.pageUrl = pageUrl.replaceFirst("\\$", pageNumber.toString());
        this.isShow = isShow;
        this.isCurrentPage = isCurrentPage;
    }

    /**
     * 页标签名称
     */
    private String pageName;
    /**
     * 实际页数
     */
    private int pageNumber;
    /**
     * 页标签地址
     */
    private String pageUrl;
    /**
     * 是否显示 1显示 2不显示
     */
    private int isShow;
    /**
     * 是否当前页 1是 2否
     */
    private int isCurrentPage;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getIsCurrentPage() {
        return isCurrentPage;
    }

    public void setIsCurrentPage(int isCurrentPage) {
        this.isCurrentPage = isCurrentPage;
    }
}
