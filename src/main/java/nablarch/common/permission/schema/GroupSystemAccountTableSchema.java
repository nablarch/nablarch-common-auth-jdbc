package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * グループシステムアカウントテーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class GroupSystemAccountTableSchema extends TableSchema {

    /** グループIDカラムの名前 */
    private String groupIdColumnName;

    /** ユーザIDカラムの名前 */
    private String userIdColumnName;

    /** 有効日(From)カラムの名前 */
    private String effectiveDateFromColumnName;

    /** 有効日(To)カラムの名前 */
    private String effectiveDateToColumnName;

    /**
     * グループIDカラムの名前を設定する。
     * @param groupIdColumnName グループIDカラムの名前
     */
    public void setGroupIdColumnName(String groupIdColumnName) {
        this.groupIdColumnName = groupIdColumnName;
    }

    /**
     * ユーザIDカラムの名前を設定する。
     * @param userIdColumnName ユーザIDカラムの名前
     */
    public void setUserIdColumnName(String userIdColumnName) {
        this.userIdColumnName = userIdColumnName;
    }

    /**
     * 有効日(From)カラムの名前を設定する。
     * @param effectiveDateFromColumnName 有効日(From)カラムの名前
     */
    public void setEffectiveDateFromColumnName(String effectiveDateFromColumnName) {
        this.effectiveDateFromColumnName = effectiveDateFromColumnName;
    }

    /**
     * 有効日(To)カラムの名前を設定する。
     * @param effectiveDateToColumnName 有効日(To)カラムの名前
     */
    public void setEffectiveDateToColumnName(String effectiveDateToColumnName) {
        this.effectiveDateToColumnName = effectiveDateToColumnName;
    }

    /**
     * グループIDカラムの名前を取得する。
     * @return グループIDカラムの名前
     */
    public String getGroupIdColumnName() {
        return groupIdColumnName;
    }

    /**
     * ユーザIDカラムの名前を取得する。
     * @return ユーザIDカラムの名前
     */
    public String getUserIdColumnName() {
        return userIdColumnName;
    }

    /**
     * 有効日(From)カラムの名前を取得する。
     * @return 有効日(From)カラムの名前
     */
    public String getEffectiveDateFromColumnName() {
        return effectiveDateFromColumnName;
    }

    /**
     * 有効日(To)カラムの名前を取得する。
     * @return 有効日(To)カラムの名前
     */
    public String getEffectiveDateToColumnName() {
        return effectiveDateToColumnName;
    }
}
