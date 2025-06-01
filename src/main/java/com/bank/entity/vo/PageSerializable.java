package com.bank.entity.vo;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageSerializable<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    //总记录数
    protected long total;
    //结果集
    protected List<T> list;

    public PageSerializable() {
    }

    @SuppressWarnings("unchecked")
    public PageSerializable(List<? extends T> list) {
        this.list = (List<T>) list;
        if (list instanceof Page) {
            this.total = ((Page<?>) list).getTotal();
        } else {
            this.total = list.size();
        }
    }
}