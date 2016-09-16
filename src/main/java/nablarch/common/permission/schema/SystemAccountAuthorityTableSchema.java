package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * システムアカウント権限テーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class SystemAccountAuthorityTableSchema extends TableSchema {

    /** ユーザIDカラムの名前 */
    private String userIdColumnName;

    /** 認可単位IDカラムの名前 */
    private String permissionUnitIdColumnName;

    /**
     * ユーザIDカラムの名前を設定する。
     * @param userIdColumnName ユーザIDカラムの名前
     */
    public void setUserIdColumnName(String userIdColumnName) {
        this.userIdColumnName = userIdColumnName;
    }

    /**
     * 認可単位IDカラムの名前を設定する。
     * @param permissionUnitIdColumnName 認可単位IDカラムの名前
     */
    public void setPermissionUnitIdColumnName(String permissionUnitIdColumnName) {
        this.permissionUnitIdColumnName = permissionUnitIdColumnName;
    }

    /**
     * ユーザIDカラムの名前を取得する。
     * @return ユーザIDカラムの名前
     */
    public String getUserIdColumnName() {
        return userIdColumnName;
    }

    /**
     * 認可単位IDカラムの名前を取得する。
     * @return 認可単位IDカラムの名前
     */
    public String getPermissionUnitIdColumnName() {
        return permissionUnitIdColumnName;
    }
}
