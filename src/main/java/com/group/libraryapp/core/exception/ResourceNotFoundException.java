package com.group.libraryapp.core.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String datasource, String id) {
        super(datasource + "에서 ID가 " + id + "인 데이터를 찾을 수 없습니다.");
    }

    public ResourceNotFoundException(String datasource, long id) {
        super(datasource + "에서 ID가 " + id + "인 데이터를 찾을 수 없습니다.");
    }
}
