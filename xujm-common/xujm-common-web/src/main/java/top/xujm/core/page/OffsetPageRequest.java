package top.xujm.core.page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import top.xujm.common.core.model.ListPage;
import top.xujm.common.core.model.ListSortPage;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Xujm
 */
public class OffsetPageRequest implements Pageable, Serializable {

    private int limit;

    private int offset;

    private Sort sort;

    public OffsetPageRequest(int offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetPageRequest(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, new Sort(direction, properties));
    }

    public OffsetPageRequest(int offset, int limit) {
        this(offset, limit, new Sort(Sort.Direction.DESC,"id"));
    }

    public OffsetPageRequest(ListPage listPage) {
        this(listPage.getIndex(), listPage.getCount(), new Sort(Sort.Direction.DESC,"id"));
    }

    public OffsetPageRequest(ListSortPage listPage) {
        Sort.Direction direction = Sort.Direction.DESC;
        if(StringUtils.equals(listPage.getDirection(),"asc")){
            direction = Sort.Direction.ASC;
        }
        this.sort = new Sort(direction,listPage.getSorts());
        this.limit = listPage.getCount();
        this.offset = listPage.getIndex();
    }

    public OffsetPageRequest(ListPage listPage, String... sorts) {
        this(listPage.getIndex(), listPage.getCount(), new Sort(Sort.Direction.DESC,sorts));
    }

    public OffsetPageRequest(ListPage listPage, Sort sorts) {
        this(listPage.getIndex(), listPage.getCount(), sorts);
    }


    @Override
    public boolean isPaged() {
        return false;
    }

    @Override
    public boolean isUnpaged() {
        return false;
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Optional.empty();
    }
}
