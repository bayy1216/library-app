package com.group.libraryapp.domain.book;

import com.group.libraryapp.type.BookCategory;
import com.group.libraryapp.type.GetBookSortType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private static final int PAGE_SIZE = 20;

    /**
     * [page]를 제외한 Nullable한 파라미터들의 검색조건들로 책 목록을 조회합니다.
     */
    public Page<Book> getBookPage(int page, String name, String writer, BookCategory category, GetBookSortType sort){
        BooleanExpression predicate = QBook.book.isNotNull();
        if(name != null) predicate = predicate.and(QBook.book.name.contains(name));
        if(writer != null) predicate = predicate.and(QBook.book.writer.eq(writer));
        if(category != null) predicate = predicate.and(QBook.book.category.eq(category));

        Long totalCount = jpaQueryFactory.from(QBook.book).select(QBook.book.count()).where(predicate).fetchOne();
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sort);

        return new PageImpl<>(
                jpaQueryFactory.from(QBook.book).select(QBook.book)
                        .where(predicate)
                        .offset((long) (page) *PAGE_SIZE).limit(PAGE_SIZE).orderBy(
                                orderSpecifier
                        ).fetch(),
                PageRequest.of(page, PAGE_SIZE),
                totalCount
        );
    }
    private OrderSpecifier<?> getOrderSpecifier(GetBookSortType sort) {
        if (sort == null) {
            return QBook.book.id.desc(); // 기본 정렬
        }

        switch (sort) {
            case STOCK_ASC:
                return QBook.book.stock.asc();
            case STOCK_DESC:
                return QBook.book.stock.desc();
            case PRICE_ASC:
                return QBook.book.price.asc();
            case PRICE_DESC:
                return QBook.book.price.desc();
            case NAME_ASC:
                return QBook.book.name.asc();
            case NAME_DESC:
                return QBook.book.name.desc();
            case DATE_ASC:
                return QBook.book.publishedDate.asc();
            case DATE_DESC:
                return QBook.book.publishedDate.desc();
            default:
                return QBook.book.id.desc(); // 기본 정렬
        }
    }
}
