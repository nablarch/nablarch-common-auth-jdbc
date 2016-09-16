package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * 認可単位テーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class PermissionUnitTableSchema extends TableSchema {
    
    /** 認可単位IDカラムの名前 */
    private String permissionUnitIdColumnName;

    /**
     * 認可単位IDカラムの名前を設定する。
     * @param permissionUnitIdColumnName 認可単位IDカラムの名前
     */
    public void setPermissionUnitIdColumnName(String permissionUnitIdColumnName) {
        this.permissionUnitIdColumnName = permissionUnitIdColumnName;
    }

    /**
     * 認可単位IDカラムの名前を取得する。
     * @return 認可単位IDカラムの名前
     */
    public String getPermissionUnitIdColumnName() {
        return permissionUnitIdColumnName;
    }
}
