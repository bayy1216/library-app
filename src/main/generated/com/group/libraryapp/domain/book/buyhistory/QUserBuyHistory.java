package com.group.libraryapp.domain.book.buyhistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBuyHistory is a Querydsl query type for UserBuyHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBuyHistory extends EntityPathBase<UserBuyHistory> {

    private static final long serialVersionUID = -1033146894L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBuyHistory userBuyHistory = new QUserBuyHistory("userBuyHistory");

    public final com.group.libraryapp.domain.book.QBook book;

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.group.libraryapp.domain.user.QUser user;

    public QUserBuyHistory(String variable) {
        this(UserBuyHistory.class, forVariable(variable), INITS);
    }

    public QUserBuyHistory(Path<? extends UserBuyHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBuyHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBuyHistory(PathMetadata metadata, PathInits inits) {
        this(UserBuyHistory.class, metadata, inits);
    }

    public QUserBuyHistory(Class<? extends UserBuyHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.group.libraryapp.domain.book.QBook(forProperty("book")) : null;
        this.user = inits.isInitialized("user") ? new com.group.libraryapp.domain.user.QUser(forProperty("user")) : null;
    }

}

