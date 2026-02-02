package taha.labs.project.security;

import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        return "SELECT last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) {
        return "INTEGER";
    }
}
