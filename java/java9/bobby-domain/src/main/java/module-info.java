open module com.bobby.domain {
    exports com.bobby.domain;
    exports com.bobby.domain.service;
    exports com.bobby.domain.admin to com.bobby.admin;
//    opens com.bobby.domain;

    provides com.bobby.domain.service.StringRepository with
            com.bobby.domain.service.MemoryStringRepository,
            com.bobby.domain.service.DatabaseStringRepository;
}