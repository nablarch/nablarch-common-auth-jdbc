package nablarch.common.availability;

import nablarch.core.db.connection.AppDbConnection;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.statement.SqlResultSet;
import nablarch.core.db.transaction.SimpleDbTransactionExecutor;
import nablarch.core.db.transaction.SimpleDbTransactionManager;
import nablarch.core.repository.initialization.Initializable;

/**
 * {@link nablarch.common.availability.ServiceAvailability}の基本実装クラス。
 * <br>
 * リクエストIDを元にサービス提供可否状態を判定する。
 * <br>
 *
 * @author Masayuki Fujikuma
 * @author Masato Inoue
 * @see nablarch.common.availability.ServiceAvailability
 */
public class BasicServiceAvailability implements ServiceAvailability, Initializable {

    /** データロードに使用するSimpleDbTransactionManagerのインスタンス。 */
    private SimpleDbTransactionManager dbManager;

    /** リクエストテーブル検索クエリ。 */
    private String query;

    /** リクエストIDを管理するリクエストテーブル名称。 */
    private String tableName;

    /** リクエストテーブルに格納されているリクエストIDを保持する項目名称。 */
    private String requestTableRequestIdColumnName;

    /** リクエストテーブルに格納されているサービス提供可否状態を保持する項目名称。 */
    private String requestTableServiceAvailableColumnName;

    /** リクエストテーブルに格納されているサービス提供可否状態項目の状態：提供可を表す文字列。 */
    private String requestTableServiceAvailableOkStatus = "1";

    /**
     * データベースへの検索に使用するSimpleDbTransactionManagerインスタンスを設定する。
     *
     * @param dbManager SimpleDbTransactionManagerのインスタンス
     */
    public void setDbManager(SimpleDbTransactionManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * リクエストに紐付くリクエストテーブル名称を設定する。
     *
     * @param tableName リクエストテーブル名称
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * リクエストテーブルのリクエストID項目名称を設定する。
     *
     * @param requestTableRequestIdColumnName リクエストID項目名称
     */
    public void setRequestTableRequestIdColumnName(
            String requestTableRequestIdColumnName) {
        this.requestTableRequestIdColumnName = requestTableRequestIdColumnName;
    }

    /**
     * リクエストテーブルのサービス提供可否状態項目名称を設定する。
     *
     * @param requestTableServiceAvailableColumnName サービス提供可否状態項目名称
     */
    public void setRequestTableServiceAvailableColumnName(
            String requestTableServiceAvailableColumnName) {
        this.requestTableServiceAvailableColumnName = requestTableServiceAvailableColumnName;
    }

    /**
     * リクエストテーブルのサービス提供可否状態項目の状態：提供可を表す文字列を設定する。
     *
     * @param requestTableServiceAvailableOkStatus サービス提供可否状態項目の状態：提供可を表す文字列
     */
    public void setRequestTableServiceAvailableOkStatus(
            String requestTableServiceAvailableOkStatus) {
        this.requestTableServiceAvailableOkStatus = requestTableServiceAvailableOkStatus;
    }

    /**
     * パラメータのリクエストIDのサービス提供可否状態を判定し、結果を返却する。<br>
     *
     * @param requestId リクエストID
     * @return サービス提供可否状態を表すboolean （提供可の場合、TRUE）
     */
    public boolean isAvailable(final String requestId) {
        SqlResultSet resultSet = new SimpleDbTransactionExecutor<SqlResultSet>(dbManager) {
            @Override
            public SqlResultSet execute(AppDbConnection connection) {
                SqlPStatement prepared = connection.prepareStatement(query);
                int parameterIndex = 1;
                prepared.setString(parameterIndex++, requestId);
                prepared.setString(parameterIndex,
                        requestTableServiceAvailableOkStatus);
                return prepared.retrieve();
            }
        }
        .doTransaction();
        return !resultSet.isEmpty();
    }

    /** SQL文を初期化する。 */
    public void initialize() {
        query = buildQuery();
    }

    /**
     * リクエストテーブル検索クエリを生成する。
     *
     * @return リクエストテーブル検索クエリ
     */
    protected String buildQuery() {
        return "SELECT " + requestTableRequestIdColumnName
                + " FROM " + tableName + " "
                + " WHERE " + requestTableRequestIdColumnName + " = ?"
                + " AND " + requestTableServiceAvailableColumnName + " = ?";
    }

}
