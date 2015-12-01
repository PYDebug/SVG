package edu.tongji.webgis.utils;

public class DataWrapper {

    final public static DataWrapper voidSuccessRet = new DataWrapper(ErrorCode.NO_ERROR, true);

    private ErrorCode               errorCode      = ErrorCode.NO_ERROR;
    private Object                  data;
    private int                     numPerPage;
    private int                     curPageNum;
    private int                     totalItemNum;
    private int                     totalPageNum;

    public DataWrapper() {
    }

    public DataWrapper(ErrorCode errorCode, Object data) {
        this.errorCode = errorCode;
        this.data = data;
    }

    public DataWrapper(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public DataWrapper(Object data) {
        this(ErrorCode.NO_ERROR, data);
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getCurPageNum() {
        return curPageNum;
    }

    public void setCurPageNum(int curPageNum) {
        this.curPageNum = curPageNum;
    }

    public int getTotalItemNum() {
        return totalItemNum;
    }

    public void setTotalItemNum(int totalItemNum) {
        this.totalItemNum = totalItemNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
}
