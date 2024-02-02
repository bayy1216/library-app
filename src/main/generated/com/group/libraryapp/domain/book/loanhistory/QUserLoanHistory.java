package com.group.libraryapp.domain.book.loanhistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLoanHistory is a Querydsl query type for UserLoanHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserLoanHistory extends EntityPathBase<UserLoanHistory> {

    private static final long serialVersionUID = 1157670888L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLoanHistory userLoanHistory = new QUserLoanHistory("userLoanHistory");

    public final com.group.libraryapp.domain.book.QBook book;

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.group.libraryapp.type.LoanType> type = createEnum("type", com.group.libraryapp.type.LoanType.class);

    public final com.group.libraryapp.domain.user.QUser user;

    public QUserLoanHistory(String variable) {
        this(UserLoanHistory.class, forVariable(variable), INITS);
    }

    public QUserLoanHistory(Path<? extends UserLoanHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLoanHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLoanHistory(PathMetadata metadata, PathInits inits) {
        this(UserLoanHistory.class, metadata, inits);
    }

    public QUserLoanHistory(Class<? extends UserLoanHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.group.libraryapp.domain.book.QBook(forProperty("book")) : null;
        this.user = inits.isInitialized("user") ? new com.group.libraryapp.domain.user.QUser(forProperty("user")) : null;
    }

}

