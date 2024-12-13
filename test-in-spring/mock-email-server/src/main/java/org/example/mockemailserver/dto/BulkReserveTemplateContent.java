package org.example.mockemailserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class BulkReserveTemplateContent {
    private List<TemplateValue> values;
    private List<TableRow> tableRows;

    @Data
    @NoArgsConstructor
    static class TableRow {
        private String col1;
        private String col2;
        private String col3;
        private String col4;
        private String col5;
        private String col6;
        private String col7;
        private String col8;
        private String col9;
        private String col10;
    }

    @Data
    @NoArgsConstructor
    static class TemplateValue {
        private String val1;
        private String val2;
        private String val3;
        private String val4;
        private String val5;
        private String val6;
        private String val7;
        private String val8;
        private String val9;
        private String val10;
    }

}