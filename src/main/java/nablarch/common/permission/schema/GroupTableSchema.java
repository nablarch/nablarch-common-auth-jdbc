package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * グループテーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class GroupTableSchema extends TableSchema {

    /** グループIDカラムの名前 */
    private String groupIdColumnName;

    /**
     * グループIDカラムの名前を設定する。
     * @param groupIdColumnName グループIDカラムの名前
     */
    public void setGroupIdColumnName(String groupIdColumnName) {
        this.groupIdColumnName = groupIdColumnName;
    }

    /**
     * グループIDカラムの名前を取得する。
     * @return グループIDカラムの名前
     */
    public String getGroupIdColumnName() {
        return groupIdColumnName;
    }
}
