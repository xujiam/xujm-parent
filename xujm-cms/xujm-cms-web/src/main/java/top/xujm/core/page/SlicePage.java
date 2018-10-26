package top.xujm.core.page;

import org.springframework.data.domain.Slice;

import java.util.List;

/**
 * @author Xujm
 * 2018/10/25
 */
public class SlicePage<T> {

    private int number;

    private int size;

    private List<T> content;

    private boolean isFirst;

    private boolean isLast;

    private boolean hasNext;

    private boolean hasPrevious;

    private int nextNumber;

    private int prevNumber;

    SlicePage(){}

    public SlicePage(Slice<T> slice){
        //页码从0开始
        number = slice.getNumber() + 1;
        size = slice.getSize();
        content = slice.getContent();
        isFirst = slice.isFirst();
        isLast = slice.isLast();
        hasNext = slice.hasNext();
        hasPrevious  =slice.hasPrevious();
        nextNumber = slice.getNumber() + 1;
        prevNumber = slice.getNumber() - 1;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public int getNextNumber() {
        return nextNumber;
    }

    public void setNextNumber(int nextNumber) {
        this.nextNumber = nextNumber;
    }

    public int getPrevNumber() {
        return prevNumber;
    }

    public void setPrevNumber(int prevNumber) {
        this.prevNumber = prevNumber;
    }
}
