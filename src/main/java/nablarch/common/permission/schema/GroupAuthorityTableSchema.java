package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * グループ権限テーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class GroupAuthorityTableSchema extends TableSchema {

    /** グループIDカラムの名前 */
    private String groupIdColumnName;

    /** 認可単位IDカラムの名前 */
    private String permissionUnitIdColumnName;

    /**
     * グループIDカラムの名前を設定する。
     * @param groupIdColumnName グループIDカラムの名前
     */
    public void setGroupIdColumnName(String groupIdColumnName) {
        this.groupIdColumnName = groupIdColumnName;
    }

    /**
     * 認可単位IDカラムの名前を設定する。
     * @param permissionUnitIdColumnName 認可単位IDカラムの名前
     */
    public void setPermissionUnitIdColumnName(String permissionUnitIdColumnName) {
        this.permissionUnitIdColumnName = permissionUnitIdColumnName;
    }

    /**
     * グループIDカラムの名前を取得する。
     * @return グループIDカラムの名前
     */
    public String getGroupIdColumnName() {
        return groupIdColumnName;
    }

    /**
     * 認可単位IDカラムの名前を取得する。
     * @return 認可単位IDカラムの名前
     */
    public String getPermissionUnitIdColumnName() {
        return permissionUnitIdColumnName;
    }
}
