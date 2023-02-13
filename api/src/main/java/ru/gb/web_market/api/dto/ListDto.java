package ru.gb.web_market.api.dto;

import java.util.List;

public class ListDto <T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public ListDto(List<T> list) {
        this.list = list;
    }
}
