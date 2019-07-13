package page;


import java.util.List;

public class PageBean<T> {

    /**
     * 初始化
     *
     * @param currentPage    当前页
     * @param maxNumber      最大数据条数
     * @param eachPageNumber 每页多少条
     * @param frontPage      前方最多有多少页
     * @param rearPage       后方最多有多少页
     * @param pageUrl        带$的跳转地址地址 如 http:localhost:8080/xxx/xxx?页数=$&参数=*&参数=*
     */
    public PageBean(int currentPage, int maxNumber, int eachPageNumber, int frontPage, int rearPage, String pageUrl) {
        this.currentPage = currentPage;
        this.maxNumber = maxNumber;
        this.eachPageNumber = eachPageNumber;
        this.frontPage = frontPage;
        this.rearPage = rearPage;
        this.pageUrl = pageUrl;
        //对所有值进行初始化
        this.homePage = this.getHomePage();
        this.backPage = this.getBackPage();
        this.previousPage = this.getPreviousPage();
        this.nextPage = this.getNextPage();
        this.currentPage = this.getCurrentPage();
        this.maxNumber = this.getMaxNumber();
        this.eachPageNumber = this.getEachPageNumber();
        this.startNumber = this.getStartNumber();
        this.endNumber = this.getEndNumber();
        //计算标签页
        this.totalPage = frontPage + rearPage + 1;
        if (0 > (this.getCurrentPage() - (this.getTotalPage() - this.getRearPage()))) {
            this.rearPage = rearPage + (-(this.getCurrentPage() - (this.getTotalPage() - this.getRearPage())));
        }
        if (this.getBackPage() < (this.getCurrentPage() + (this.getTotalPage() - this.getFrontPage()))) {
            this.frontPage = frontPage + ((this.getCurrentPage() + (this.getTotalPage() - this.getFrontPage())) - (this.getBackPage() + 1));
        }
        this.setPage(pageUrl);
    }

    /**
     * 首页
     */
    private int homePage;

    /**
     * 尾页
     */
    private int backPage;

    /**
     * 首页地址
     */
    private String homePageUrl;

    /**
     * 尾页地址
     */
    private String backPageUrl;

    /**
     * 上一页
     */
    private int previousPage;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 上一页地址
     */
    private String previousPageUrl;

    /**
     * 下一页地址
     */
    private String nextPageUrl;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 最大数据数量
     */
    private int maxNumber;

    /**
     * 每页多少条
     */
    private int eachPageNumber;

    /**
     * 跳转起始数据
     */
    private int startNumber;

    /**
     * 跳转结束数据
     */
    private int endNumber;

    /**
     * 前方最多有多少页
     */
    private int frontPage;

    /**
     * 后方最多有多少页
     */
    private int rearPage;

    /**
     * 总共显示几个标签页
     */
    private int totalPage;

    /**
     * 页标签
     */
    private PageEntity[] page;

    /**
     * 查询
     */
    private List<T> list;

    /**
     * 带$的跳转地址地址
     */
    private String pageUrl;

    /**
     * 是否是图片地址作为页标签名称.1.不是 2.是
     */
    private int isImageUrl;

    /**
     * 首页
     */
    public int getHomePage() {
        return 1;
    }

    /**
     * 尾页
     */
    public int getBackPage() {
        double b = Double.parseDouble(maxNumber + "") / Double.parseDouble(eachPageNumber + "");
        int i = (int) b;
        if (i < b) {
            return i + 1;
        } else if (b < 1) {
            return 1;
        } else {
            return i;
        }
    }

    /**
     * 上一页
     */
    public int getPreviousPage() {
        return currentPage <= 1 ? 1 : currentPage - 1;
    }

    /**
     * 下一页
     */
    public int getNextPage() {
        return currentPage == this.getBackPage() ? this.getBackPage() : currentPage < this.getBackPage() ? currentPage + 1 : this.getBackPage();
    }

    /**
     * 当前页
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 当前页
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 最大数据数量
     */
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * 最大数据数量
     */
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * 每页多少条
     */
    public int getEachPageNumber() {
        return eachPageNumber;
    }

    /**
     * 每页多少条
     */
    public void setEachPageNumber(int eachPageNumber) {
        this.eachPageNumber = eachPageNumber;
    }

    /**
     * 跳转起始数据
     */
    public int getStartNumber() {
        return (currentPage - 1) * eachPageNumber;
    }

    /**
     * 跳转结束数据
     */
    public int getEndNumber() {
        return currentPage * eachPageNumber;
    }

    /**
     * 前方最多有多少页
     *
     * @param frontPage 页数
     */
    public void setFrontPage(int frontPage) {
        this.frontPage = frontPage;
    }

    /**
     * 前方最多有多少页
     */
    public int getFrontPage() {
        return frontPage;
    }

    /**
     * 后方最多有多少页
     *
     * @param rearPage 页数
     */
    public void setRearPage(int rearPage) {
        this.rearPage = rearPage;
    }

    /**
     * 后方最多有多少页
     */
    public int getRearPage() {
        return rearPage;
    }

    /**
     * 总共多少个标签页
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 总共多少个标签页
     *
     * @param totalPage =frontPage+rearPage
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 页标签
     */
    public PageEntity[] getPage() {
        return page;
    }

    /**
     * 页标签
     */
    public void setPage(String pageUrl) {
        this.page = new PageEntity[this.getBackPage()];
        for (int i = 1; i < (this.getBackPage() + 1); i++) {
            String name = i + "";
            int isShow = 0;
            int isCurrentPage = 2;
            if (this.getCurrentPage() > i) {
                if (i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage())) {
                    isShow = 1;
                } else {
                    isShow = 2;
                }
            } else if (this.getCurrentPage() == i) {
                isShow = 1;
                isCurrentPage = 1;
            } else if (this.getCurrentPage() < i) {
                if (i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage())) {
                    isShow = 1;
                } else {
                    isShow = 2;
                }
            }
            page[i - 1] = new PageEntity(name, i, pageUrl, isShow, isCurrentPage);
        }
        this.isImageUrl = 1;
    }

    /**
     * 页标签
     */
    public void setPage(String pageUrl, String pageName) {
        this.page = new PageEntity[this.getBackPage()];
        for (int i = 1; i < (this.getBackPage() + 1); i++) {
            int isShow = 0;
            int isCurrentPage = 2;
            if (this.getCurrentPage() > i) {
                if (i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage())) {
                    isShow = 1;
                } else {
                    isShow = 2;
                }
            } else if (this.getCurrentPage() == i) {
                isShow = 1;
                isCurrentPage = 1;
            } else if (this.getCurrentPage() < i) {
                if (i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage())) {
                    isShow = 1;
                } else {
                    isShow = 2;
                }
            }
            page[i - 1] = new PageEntity(pageName, i, pageUrl, isShow, isCurrentPage);
        }
        this.isImageUrl = 1;
    }

    /**
     * 页标签
     */
    public void setPage(String pageUrl, String[] pageName) {
        this.page = new PageEntity[this.getBackPage()];
        if (pageName.length == page.length) {
            for (int i = 1; i < (this.getBackPage() + 1); i++) {
                int isShow = 0;
                int isCurrentPage = 2;
                if (this.getCurrentPage() > i) {
                    if (i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage())) {
                        isShow = 1;
                    } else {
                        isShow = 2;
                    }
                } else if (this.getCurrentPage() == i) {
                    isShow = 1;
                } else if (this.getCurrentPage() < i) {
                    if (i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage())) {
                        isShow = 1;
                    } else {
                        isShow = 2;
                    }
                }
                page[i - 1] = new PageEntity(pageName[i], i, pageUrl, isShow, isCurrentPage);
            }
        }
        this.isImageUrl = 1;
    }

    /**
     * 查询对象的list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list 查询对象的list
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 下一页地址
     */
    public String getNextPageUrl() {
        return pageUrl.replaceFirst("\\$", nextPage + "");
    }

    /**
     * 上一页地址
     */
    public String getPreviousPageUrl() {
        return pageUrl.replaceFirst("\\$", previousPage + "");
    }

    /**
     * 首页地址
     */
    public String getHomePageUrl() {
        return pageUrl.replaceFirst("\\$", homePage + "");
    }

    /**
     * 尾页地址
     */
    public String getBackPageUrl() {
        return pageUrl.replaceFirst("\\$", backPage + "");
    }

    /**
     * 是否是图片地址作为显示页标签名称
     */
    public int getIsImageUrl() {
        return isImageUrl;
    }

    /**
     * 是否是图片地址作为显示页标签名称
     */
    public void setIsImageUrl(int isImageUrl) {
        this.isImageUrl = isImageUrl;
    }
}
