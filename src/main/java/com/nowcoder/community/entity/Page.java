package com.nowcoder.community.entity;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.entity
 * @CreateTime: 2022-05-20  18:21
 * @Description: 封装分页相关信息
 */
public class Page {
    // 当前页码
    private int current = 1;
    // 每页显示上限
    private int limit = 10;
    // 数据总数（用于计算总页数）
    private int rows;
    // 查询路径（用于复用分页链接）
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit >= 1 && limit < 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @description: 获取当前页的起始行
     * @date: 2022/5/19 21:48
     * @param: []
     * @return: int
     **/
    public int getOffset() {
        return (current - 1) * limit;
    }

    /**
     * @description: 获取总页数
     * @date: 2022/5/19 21:48
     * @param: []
     * @return: int
     **/
    public int getTotal() {
        if(rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * @description: 获取起始页码
     * @date: 2022/5/19 21:50
     * @param: []
     * @return: int
     **/
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * @description: 获取结束页码
     * @date: 2022/5/19 21:52
     * @param: []
     * @return: int
     **/
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
