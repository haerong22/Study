package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {

    Object map(ResultSet rs) throws SQLException;
}
