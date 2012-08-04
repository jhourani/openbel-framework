/**
 * Copyright (C) 2012 Selventa, Inc.
 *
 * This file is part of the OpenBEL Framework.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The OpenBEL Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the OpenBEL Framework. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional Terms under LGPL v3:
 *
 * This license does not authorize you and you are prohibited from using the
 * name, trademarks, service marks, logos or similar indicia of Selventa, Inc.,
 * or, in the discretion of other licensors or authors of the program, the
 * name, trademarks, service marks, logos or similar indicia of such authors or
 * licensors, in any marketing or advertising materials relating to your
 * distribution of the program or any covered product. This restriction does
 * not waive or limit your obligation to keep intact all copyright notices set
 * forth in the program as delivered to you.
 *
 * If you distribute the program in whole or in part, or any modified version
 * of the program, and you assume contractual liability to the recipient with
 * respect to the program or modified version, then you will indemnify the
 * authors and licensors of the program for any liabilities that these
 * contractual assumptions directly impose on those licensors and authors.
 */
package org.openbel.framework.api.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openbel.framework.api.internal.KAMCatalogDao;
import org.openbel.framework.api.internal.KAMCatalogDao.KamInfo;
import org.openbel.framework.common.enums.DatabaseType;
import org.openbel.framework.core.df.DBConnection;

public class KamInfoUtil {

    static ResultSet mockRs = new NullResultSet() {

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean next() throws SQLException {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getString(int columnIndex) throws SQLException {
            return "test";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getInt(int columnIndex) throws SQLException {
            return 1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.sql.Date getDate(int columnIndex) throws SQLException {
            return new java.sql.Date(-1);
        }

    };

    static PreparedStatement mockStmt = new NullPreparedStatement() {

        /**
         * {@inheritDoc}
         */
        @Override
        public ResultSet executeQuery() throws SQLException {
            return mockRs;
        }

    };

    static Connection mockConn = new NullConnection() {
        /**
         * {@inheritDoc}
         */
        @Override
        public PreparedStatement prepareStatement(String sql,
                int autoGeneratedKeys) throws SQLException {
            return mockStmt;
        }

    };

    /**
     * Creates a test {@link KamInfo} object.
     *
     * @return a test {@link KamInfo}
     * @throws SQLException Thrown if a SQL error occurred accessing the KAM
     * catalog
     */
    public static KamInfo createKamInfo() throws SQLException {
        DBConnection dbc =
                new DBConnection(mockConn, DatabaseType.DERBY, "", "", "");
        KAMCatalogDao kcdao = new KAMCatalogDao(dbc, "foo", "bar");
        return kcdao.getKamInfoByName("test");
    }
}