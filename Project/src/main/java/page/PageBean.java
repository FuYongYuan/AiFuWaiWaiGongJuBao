package page;


import java.util.List;

public class PageBean {

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
    public PageBean(long currentPage, long maxNumber, long eachPageNumber, long frontPage, long rearPage, String pageUrl) {
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
    private long homePage;

    /**
     * 尾页
     */
    private long backPage;

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
    private long previousPage;

    /**
     * 下一页
     */
    private long nextPage;

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
    private long currentPage;

    /**
     * 最大数据数量
     */
    private long maxNumber;

    /**
     * 每页多少条
     */
    private long eachPageNumber;

    /**
     * 前方最多有多少页
     */
    private long frontPage;

    /**
     * 后方最多有多少页
     */
    private long rearPage;

    /**
     * 总共显示几个标签页
     */
    private long totalPage;

    /**
     * 页标签
     */
    private PageEntity[] page;

    /**
     * 查询
     */
    private List list;

    /**
     * 带$的跳转地址地址
     */
    private String pageUrl;

    /**
     * 是否是图片地址作为页标签名称.true.是 false.不是
     */
    private boolean isImageUrl;

    /**
     * 首页
     */
    public long getHomePage() {
        return 1;
    }

    /**
     * 尾页
     */
    public long getBackPage() {
        double b = Double.parseDouble(String.valueOf(maxNumber)) / Double.parseDouble(String.valueOf(eachPageNumber));
        long i = (long) b;
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
    public long getPreviousPage() {
        return currentPage <= 1 ? 1 : currentPage - 1;
    }

    /**
     * 下一页
     */
    public long getNextPage() {
        return currentPage == this.getBackPage() ? this.getBackPage() : currentPage < this.getBackPage() ? currentPage + 1 : this.getBackPage();
    }

    /**
     * 当前页
     */
    public long getCurrentPage() {
        return currentPage;
    }

    /**
     * 当前页
     */
    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 最大数据数量
     */
    public long getMaxNumber() {
        return maxNumber;
    }

    /**
     * 最大数据数量
     */
    public void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * 每页多少条
     */
    public long getEachPageNumber() {
        return eachPageNumber;
    }

    /**
     * 每页多少条
     */
    public void setEachPageNumber(long eachPageNumber) {
        this.eachPageNumber = eachPageNumber;
    }

    /**
     * 跳转起始数据
     */
    public long getStartNumber() {
        return (currentPage - 1) * eachPageNumber;
    }

    /**
     * 跳转结束数据
     */
    public long getEndNumber() {
        return currentPage * eachPageNumber;
    }

    /**
     * 前方最多有多少页
     *
     * @param frontPage 页数
     */
    public void setFrontPage(long frontPage) {
        this.frontPage = frontPage;
    }

    /**
     * 前方最多有多少页
     */
    public long getFrontPage() {
        return frontPage;
    }

    /**
     * 后方最多有多少页
     *
     * @param rearPage 页数
     */
    public void setRearPage(long rearPage) {
        this.rearPage = rearPage;
    }

    /**
     * 后方最多有多少页
     */
    public long getRearPage() {
        return rearPage;
    }

    /**
     * 总共多少个标签页
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * 总共多少个标签页
     *
     * @param totalPage =frontPage+rearPage
     */
    public void setTotalPage(long totalPage) {
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
        this.page = new PageEntity[Math.toIntExact(this.getBackPage())];
        for (int i = 1; i < (this.getBackPage() + 1); i++) {
            String name = String.valueOf(i);
            boolean isShow = false;
            boolean isCurrentPage = false;
            if (this.getCurrentPage() > i) {
                isShow = i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage());
            } else if (this.getCurrentPage() == i) {
                isShow = true;
                isCurrentPage = true;
            } else if (this.getCurrentPage() < i) {
                isShow = i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage());
            }
            page[i - 1] = new PageEntity(name, i, pageUrl, isShow, isCurrentPage);
        }
        this.isImageUrl = false;
    }

    /**
     * 页标签
     */
    public void setPage(String pageUrl, String pageShow) {
        this.page = new PageEntity[Math.toIntExact(this.getBackPage())];
        for (int i = 1; i < (this.getBackPage() + 1); i++) {
            boolean isShow = false;
            boolean isCurrentPage = false;
            if (this.getCurrentPage() > i) {
                isShow = i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage());
            } else if (this.getCurrentPage() == i) {
                isShow = true;
                isCurrentPage = true;
            } else if (this.getCurrentPage() < i) {
                isShow = i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage());
            }
            page[i - 1] = new PageEntity(pageShow, i, pageUrl, isShow, isCurrentPage);
        }
        this.isImageUrl = false;
    }

    /**
     * 页标签
     */
    public void setPage(String pageUrl, String pageShowSingular, String pageShowPlural) {
        this.page = new PageEntity[Math.toIntExact(this.getBackPage())];
        for (int i = 1; i < (this.getBackPage() + 1); i++) {
            boolean isShow = false;
            boolean isCurrentPage = false;
            if (this.getCurrentPage() > i) {
                isShow = i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage());
            } else if (this.getCurrentPage() == i) {
                isShow = true;
                isCurrentPage = true;
            } else if (this.getCurrentPage() < i) {
                isShow = i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage());
            }
            String pageShow = pageShowSingular;
            if (i % 2 == 0) {
                pageShow = pageShowPlural;
            }
            page[i - 1] = new PageEntity(pageShow, i, pageUrl, isShow, isCurrentPage);
        }
        this.isImageUrl = false;
    }

    /**
     * 页标签
     */
    public void setPage(String pageUrl, String[] pageShow) {
        this.page = new PageEntity[Math.toIntExact(this.getBackPage())];
        if (pageShow.length == page.length) {
            for (int i = 1; i < (this.getBackPage() + 1); i++) {
                boolean isShow = false;
                boolean isCurrentPage = false;
                if (this.getCurrentPage() > i) {
                    isShow = i < this.getCurrentPage() && i >= (this.getCurrentPage() - this.getFrontPage());
                } else if (this.getCurrentPage() == i) {
                    isShow = true;
                    isCurrentPage = true;
                } else if (this.getCurrentPage() < i) {
                    isShow = i > this.getCurrentPage() && i <= (this.getCurrentPage() + this.getRearPage());
                }
                page[i - 1] = new PageEntity(pageShow[i], i, pageUrl, isShow, isCurrentPage);
            }
        }
        this.isImageUrl = false;
    }

    /**
     * 查询对象的list
     */
    public List getList() {
        return list;
    }

    /**
     * @param list 查询对象的list
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * 下一页地址
     */
    public String getNextPageUrl() {
        return pageUrl.replaceFirst("\\$", String.valueOf(nextPage));
    }

    /**
     * 上一页地址
     */
    public String getPreviousPageUrl() {
        return pageUrl.replaceFirst("\\$", String.valueOf(previousPage));
    }

    /**
     * 首页地址
     */
    public String getHomePageUrl() {
        return pageUrl.replaceFirst("\\$", String.valueOf(homePage));
    }

    /**
     * 尾页地址
     */
    public String getBackPageUrl() {
        return pageUrl.replaceFirst("\\$", String.valueOf(backPage));
    }

    /**
     * 是否是图片地址作为显示页标签名称
     */
    public boolean getIsImageUrl() {
        return isImageUrl;
    }

    /**
     * 是否是图片地址作为显示页标签名称
     */
    public void setIsImageUrl(boolean isImageUrl) {
        this.isImageUrl = isImageUrl;
    }
}
