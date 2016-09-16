package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * 認可単位リクエストテーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class PermissionUnitRequestTableSchema extends TableSchema {
    
    /** 認可単位IDカラムの名前 */
    private String permissionUnitIdColumnName;
    
    /** リクエストIDカラムの名前 */
    private String requestIdColumnName;

    /**
     * 認可単位IDカラムの名前を設定する。
     * @param permissionUnitIdColumnName 認可単位IDカラムの名前
     */
    public void setPermissionUnitIdColumnName(String permissionUnitIdColumnName) {
        this.permissionUnitIdColumnName = permissionUnitIdColumnName;
    }

    /**
     * リクエストIDカラムの名前を設定する。
     * @param requestIdColumnName リクエストIDカラムの名前
     */
    public void setRequestIdColumnName(String requestIdColumnName) {
        this.requestIdColumnName = requestIdColumnName;
    }

    /**
     * 認可単位IDカラムの名前を取得する。
     * @return 認可単位IDカラムの名前
     */
    public String getPermissionUnitIdColumnName() {
        return permissionUnitIdColumnName;
    }

    /**
     * リクエストIDカラムの名前を取得する。
     * @return リクエストIDカラムの名前
     */
    public String getRequestIdColumnName() {
        return requestIdColumnName;
    }
}
