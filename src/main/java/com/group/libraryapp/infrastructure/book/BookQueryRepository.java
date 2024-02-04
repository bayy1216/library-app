package com.group.libraryapp.infrastructure.book;

import com.group.libraryapp.domain.type.BookCategory;
import com.group.libraryapp.domain.type.GetBookSortType;
import com.group.libraryapp.infrastructure.book.buyhistory.QUserBuyHistoryEntity;
import com.group.libraryapp.infrastructure.book.buyhistory.UserBuyHistoryEntity;
import com.group.libraryapp.infrastructure.book.loanhistory.QUserLoanHistoryEntity;
import com.group.libraryapp.infrastructure.book.loanhistory.UserLoanHistoryEntity;
import com.group.libraryapp.infrastructure.user.QUserEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private static final int PAGE_SIZE = 20;

    /**
     * [page]를 제외한 Nullable한 파라미터들의 검색조건들로 책 목록을 조회합니다.
     */
    public Page<BookEntity> getBookPage(int page, String name, String writer, BookCategory category, GetBookSortType sort){
        BooleanExpression predicate = QBookEntity.bookEntity.isNotNull();
        if(name != null) predicate = predicate.and(QBookEntity.bookEntity.name.contains(name));
        if(writer != null) predicate = predicate.and(QBookEntity.bookEntity.writer.eq(writer));
        if(category != null) predicate = predicate.and(QBookEntity.bookEntity.category.eq(category));

        Long totalCount = jpaQueryFactory.from(QBookEntity.bookEntity).select(QBookEntity.bookEntity.count()).where(predicate).fetchOne();
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sort);

        return new PageImpl<>(
                jpaQueryFactory.from(QBookEntity.bookEntity).select(QBookEntity.bookEntity)
                        .where(predicate)
                        .offset((long) (page) *PAGE_SIZE).limit(PAGE_SIZE).orderBy(
                                orderSpecifier
                        ).fetch(),
                PageRequest.of(page, PAGE_SIZE),
                totalCount
        );
    }

    /**
     * 유저의 대여 이력을 조회합니다.
     * [user]와 일치하는 [userLoanHistory]를 찾은 뒤에
     * [book]과 [userLoanHistory]를 fetchJoin하여 N+1 문제를 해결합니다.
     */
    public Page<UserLoanHistoryEntity> getLoanHistory(Long userId, int page) {
        Long totalCount = jpaQueryFactory.from(QUserLoanHistoryEntity.userLoanHistoryEntity, QUserEntity.userEntity)
                .select(QUserLoanHistoryEntity.userLoanHistoryEntity.count())
                .where(QUserLoanHistoryEntity.userLoanHistoryEntity.user.eq(QUserEntity.userEntity).and(QUserEntity.userEntity.id.eq(userId)))
                .fetchOne();

        List<UserLoanHistoryEntity> userLoanHistories = jpaQueryFactory
                .from(QBookEntity.bookEntity, QUserLoanHistoryEntity.userLoanHistoryEntity, QUserEntity.userEntity)
                .select(QUserLoanHistoryEntity.userLoanHistoryEntity)
                .join(QUserLoanHistoryEntity.userLoanHistoryEntity.book).fetchJoin()
                .where(QUserLoanHistoryEntity.userLoanHistoryEntity.user.eq(QUserEntity.userEntity).and(QUserEntity.userEntity.id.eq(userId)))
                .offset((long) (page) * PAGE_SIZE).limit(PAGE_SIZE).fetch();
        return new PageImpl<>(userLoanHistories, PageRequest.of(page, PAGE_SIZE), totalCount);
    }

    /**
     * 유저의 구매 이력을 조회합니다.
     * [user]와 일치하는 [userBuyHistory]를 찾은 뒤에
     * [book]과 [userBuyHistory]를 fetchJoin하여 N+1 문제를 해결합니다.
     */
    public Page<UserBuyHistoryEntity> getBuyHistory(Long userId, int page) {
        Long totalCount = jpaQueryFactory.from(QUserBuyHistoryEntity.userBuyHistoryEntity, QUserEntity.userEntity)
                .select(QUserBuyHistoryEntity.userBuyHistoryEntity.count())
                .where(QUserBuyHistoryEntity.userBuyHistoryEntity.user.eq(QUserEntity.userEntity).and(QUserEntity.userEntity.id.eq(userId)))
                .fetchOne();

        List<UserBuyHistoryEntity> userBuyHistories = jpaQueryFactory
                .from(QBookEntity.bookEntity, QUserBuyHistoryEntity.userBuyHistoryEntity, QUserEntity.userEntity)
                .select(QUserBuyHistoryEntity.userBuyHistoryEntity)
                .join(QUserBuyHistoryEntity.userBuyHistoryEntity.book).fetchJoin()
                .where(QUserBuyHistoryEntity.userBuyHistoryEntity.user.eq(QUserEntity.userEntity).and(QUserEntity.userEntity.id.eq(userId)))
                .offset((long) (page) * PAGE_SIZE).limit(PAGE_SIZE).fetch();
        return new PageImpl<>(userBuyHistories, PageRequest.of(page, PAGE_SIZE), totalCount);
    }

    private OrderSpecifier<?> getOrderSpecifier(GetBookSortType sort) {
        if (sort == null) {
            return QBookEntity.bookEntity.id.desc(); // 기본 정렬
        }

        switch (sort) {
            case STOCK_ASC:
                return QBookEntity.bookEntity.stock.asc();
            case STOCK_DESC:
                return QBookEntity.bookEntity.stock.desc();
            case PRICE_ASC:
                return QBookEntity.bookEntity.price.asc();
            case PRICE_DESC:
                return QBookEntity.bookEntity.price.desc();
            case NAME_ASC:
                return QBookEntity.bookEntity.name.asc();
            case NAME_DESC:
                return QBookEntity.bookEntity.name.desc();
            case DATE_ASC:
                return QBookEntity.bookEntity.publishedDate.asc();
            case DATE_DESC:
                return QBookEntity.bookEntity.publishedDate.desc();
            default:
                return QBookEntity.bookEntity.id.desc(); // 기본 정렬
        }
    }
}
