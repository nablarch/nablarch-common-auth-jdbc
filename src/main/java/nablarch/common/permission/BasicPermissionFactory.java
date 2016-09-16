package nablarch.common.permission;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nablarch.common.permission.schema.GroupAuthorityTableSchema;
import nablarch.common.permission.schema.GroupSystemAccountTableSchema;
import nablarch.common.permission.schema.GroupTableSchema;
import nablarch.common.permission.schema.PermissionUnitRequestTableSchema;
import nablarch.common.permission.schema.PermissionUnitTableSchema;
import nablarch.common.permission.schema.SystemAccountAuthorityTableSchema;
import nablarch.common.permission.schema.SystemAccountTableSchema;
import nablarch.core.date.BusinessDateProvider;
import nablarch.core.db.connection.AppDbConnection;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.statement.SqlResultSet;
import nablarch.core.db.statement.SqlRow;
import nablarch.core.db.transaction.SimpleDbTransactionExecutor;
import nablarch.core.db.transaction.SimpleDbTransactionManager;
import nablarch.core.repository.initialization.Initializable;


/**
 * 認可制御グループをベースにした{@link Permission}を生成するクラス。<br>
 * <br>
 * このクラスでは、データベース上にユーザ及びユーザが属するグループ毎に使用できる認可単位を保持したテーブル構造から、
 * ユーザに紐付く認可情報を取得する。
 * 
 * @author Kiyohito Itoh
 */
public class BasicPermissionFactory implements PermissionFactory, Initializable {
    
    /** データベースへのトランザクション制御を行う{@link SimpleDbTransactionManager} */
    private SimpleDbTransactionManager dbManager;

    /** 認可機能で使用するテーブル名／カラム名 */
    private Map<String, String> dbSchema = new HashMap<String, String>();
    
    /** 業務日付を提供する{@link BusinessDateProvider} */
    private BusinessDateProvider businessDateProvider;
    
    /** リクエストIDを取得するSQL */
    private String selectRequestIdsSql;
    
    /**
     * データベースへのトランザクション制御を行う{@link SimpleDbTransactionManager}を設定する。
     * @param dbManager データベースへのトランザクション制御を行う{@link SimpleDbTransactionManager}
     */
    public void setDbManager(SimpleDbTransactionManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * グループテーブルのスキーマ情報を設定する。
     * @param schema グループテーブルのスキーマ情報
     */
    public void setGroupTableSchema(GroupTableSchema schema) {
        dbSchema.put("group", schema.getTableName());
        dbSchema.put("groupGroupId", schema.getGroupIdColumnName());
    }
    
    /**
     * システムアカウントテーブルのスキーマ情報を設定する。
     * @param schema システムアカウントテーブルのスキーマ情報
     */
    public void setSystemAccountTableSchema(SystemAccountTableSchema schema) {
        dbSchema.put("systemAccount", schema.getTableName());
        dbSchema.put("systemAccountUserId", schema.getUserIdColumnName());
        dbSchema.put("systemAccountUserIdLocked", schema.getUserIdLockedColumnName());
        dbSchema.put("systemAccountEffectiveDateFrom", schema.getEffectiveDateFromColumnName());
        dbSchema.put("systemAccountEffectiveDateTo", schema.getEffectiveDateToColumnName());
    }
    
    /**
     * グループシステムアカウントテーブルのスキーマ情報を設定する。
     * @param schema グループシステムアカウントテーブルのスキーマ情報
     */
    public void setGroupSystemAccountTableSchema(GroupSystemAccountTableSchema schema) {
        dbSchema.put("groupSystemAccount", schema.getTableName());
        dbSchema.put("groupSystemAccountGroupId", schema.getGroupIdColumnName());
        dbSchema.put("groupSystemAccountUserId", schema.getUserIdColumnName());
        dbSchema.put("groupSystemAccountEffectiveDateFrom", schema.getEffectiveDateFromColumnName());
        dbSchema.put("groupSystemAccountEffectiveDateTo", schema.getEffectiveDateToColumnName());
    }
    
    /**
     * 認可単位テーブルのスキーマ情報を設定する。
     * @param schema 認可単位テーブルのスキーマ情報
     */
    public void setPermissionUnitTableSchema(PermissionUnitTableSchema schema) {
        dbSchema.put("permissionUnit", schema.getTableName());
        dbSchema.put("puPermissionUnitId", schema.getPermissionUnitIdColumnName());
    }

    /**
     * 認可単位リクエストテーブルのスキーマ情報を設定する。
     * @param schema 認可単位リクエストテーブルのスキーマ情報
     */
    public void setPermissionUnitRequestTableSchema(
            PermissionUnitRequestTableSchema schema) {
        dbSchema.put("permissionUnitRequest", schema.getTableName());
        dbSchema.put("purPermissionUnitId", schema.getPermissionUnitIdColumnName());
        dbSchema.put("purRequestId", schema.getRequestIdColumnName());
    }
    
    /**
     * グループ権限テーブルのスキーマ情報を設定する。
     * @param schema グループ権限テーブルのスキーマ情報
     */
    public void setGroupAuthorityTableSchema(GroupAuthorityTableSchema schema) {
        dbSchema.put("groupAuthority", schema.getTableName());
        dbSchema.put("groupAuthorityGroupId", schema.getGroupIdColumnName());
        dbSchema.put("groupAuthorityPermissionUnitId", schema.getPermissionUnitIdColumnName());
    }
    
    /**
     * システムアカウント権限テーブルのスキーマ情報を設定する。
     * @param schema システムアカウント権限テーブルのスキーマ情報
     */
    public void setSystemAccountAuthorityTableSchema(SystemAccountAuthorityTableSchema schema) {
        dbSchema.put("systemAccountAuthority", schema.getTableName());
        dbSchema.put("systemAccountAuthorityUserId", schema.getUserIdColumnName());
        dbSchema.put("systemAccountAuthorityPermissionUnitId", schema.getPermissionUnitIdColumnName());
    }
    
    /**
     * 業務日付を提供するクラスのインスタンスを設定する。
     * @param businessDateProvider 業務日付を提供するクラスのインスタンス
     */
    public void setBusinessDateProvider(BusinessDateProvider businessDateProvider) {
        this.businessDateProvider = businessDateProvider;
    }
    
    /**
     * {@inheritDoc}
     */
    public Permission getPermission(final String userId) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "must be set a user id to the ThreadContext.");
        }

        SortedSet<String> requestIds = new TreeSet<String>();
        final String businessDate = businessDateProvider.getDate();

        SqlResultSet resultSet = new SimpleDbTransactionExecutor<SqlResultSet>(dbManager) {
            @Override
            public SqlResultSet execute(AppDbConnection connection) {
                SqlPStatement stmt = connection.prepareStatement(
                        selectRequestIdsSql);
                int parameterIndex = 0;
                // for select group authority
                stmt.setString(++parameterIndex, userId);
                stmt.setString(++parameterIndex, businessDate);
                stmt.setString(++parameterIndex, businessDate);
                stmt.setString(++parameterIndex, businessDate);
                stmt.setString(++parameterIndex, businessDate);
                // for select user authority
                stmt.setString(++parameterIndex, userId);
                stmt.setString(++parameterIndex, businessDate);
                stmt.setString(++parameterIndex, businessDate);
                return stmt.retrieve();
            }
        }
        .doTransaction();

        for (SqlRow row : resultSet) {
            requestIds.add(row.getString(dbSchema.get("purRequestId")));
        }

        return new BasicPermission(requestIds);
    }

    /**
     * SQL文を初期化する。
     */
    public void initialize() {
        
        String template =
            "SELECT "
                + "PUR.$purRequestId$ "
            + "FROM "
                + "$systemAccount$ SA "
                + "INNER JOIN $groupSystemAccount$ GSA ON SA.$systemAccountUserId$ = GSA.$groupSystemAccountUserId$ "
                + "INNER JOIN $group$ G ON GSA.$groupSystemAccountGroupId$ = G.$groupGroupId$ "
                + "INNER JOIN $groupAuthority$ GA ON G.$groupGroupId$ = GA.$groupAuthorityGroupId$ "
                + "INNER JOIN $permissionUnit$ PU ON GA.$groupAuthorityPermissionUnitId$ = PU.$puPermissionUnitId$ "
                + "INNER JOIN $permissionUnitRequest$ PUR ON PU.$puPermissionUnitId$ = PUR.$purPermissionUnitId$ "
            + "WHERE "
                + "SA.$systemAccountUserId$ = ? "
                + "AND SA.$systemAccountUserIdLocked$ = '0' "
                + "AND SA.$systemAccountEffectiveDateFrom$ <= ? "
                + "AND SA.$systemAccountEffectiveDateTo$ >= ? "
                + "AND GSA.$groupSystemAccountEffectiveDateFrom$ <= ? "
                + "AND GSA.$groupSystemAccountEffectiveDateTo$ >= ? "
            + "UNION ALL ("
                + "SELECT "
                    + "PUR.$purRequestId$ "
                + "FROM "
                    + "$systemAccount$ SA "
                    + "INNER JOIN $systemAccountAuthority$ SAA ON SA.$systemAccountUserId$ = SAA.$systemAccountAuthorityUserId$ "
                    + "INNER JOIN $permissionUnit$ PU ON SAA.$systemAccountAuthorityPermissionUnitId$ = PU.$puPermissionUnitId$ "
                    + "INNER JOIN $permissionUnitRequest$ PUR ON PU.$puPermissionUnitId$ = PUR.$purPermissionUnitId$ "
                + "WHERE "
                    + "SA.$systemAccountUserId$ = ? "
                    + "AND SA.$systemAccountUserIdLocked$ = '0' "
                    + "AND SA.$systemAccountEffectiveDateFrom$ <= ? "
                    + "AND SA.$systemAccountEffectiveDateTo$ >= ?"
            + ")";
        
        Matcher m = Pattern.compile("\\$([_0-9a-zA-Z]+)\\$").matcher(template);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String replacement = m.group(1);
            m.appendReplacement(sb, dbSchema.get(replacement));
        }
        m.appendTail(sb);
        selectRequestIdsSql = sb.toString();
    }
}
