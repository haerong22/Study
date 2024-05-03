module com.bobby.api {
    requires com.bobby.domain;
    requires java.sql;

    uses com.bobby.domain.service.StringRepository;
}