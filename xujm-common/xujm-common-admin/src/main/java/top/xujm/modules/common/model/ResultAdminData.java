package top.xujm.modules.common.model;


import top.xujm.common.core.model.ResultData;

public class ResultAdminData<T> extends ResultData<T> {

    private int count;

    public ResultAdminData(int count, T data){
        super(data);
        this.count = count;
    }

    public ResultAdminData(T data){
        super(data);
        this.count = 100;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ResultAdminData() {
    }
}
