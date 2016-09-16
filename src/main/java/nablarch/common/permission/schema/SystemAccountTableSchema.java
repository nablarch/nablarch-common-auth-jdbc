package nablarch.common.permission.schema;

import nablarch.common.schema.TableSchema;

/**
 * システムアカウントテーブルのスキーマ情報を保持するクラス。
 * @author Kiyohito Itoh
 */
public final class SystemAccountTableSchema extends TableSchema {

    /** ユーザIDカラムの名前 */
    private String userIdColumnName;

    /** ユーザIDロックカラムの名前 */
    private String userIdLockedColumnName;

    /** 有効日(From)カラムの名前 */
    private String effectiveDateFromColumnName;

    /** 有効日(To)カラムの名前 */
    private String effectiveDateToColumnName;

    /**
     * ユーザIDカラムの名前を設定する。
     * @param userIdColumnName ユーザIDカラムの名前
     */
    public void setUserIdColumnName(String userIdColumnName) {
        this.userIdColumnName = userIdColumnName;
    }

    /**
     * ユーザIDロックカラムの名前を設定する。
     * @param userIdLockedColumnName ユーザIDロックカラムの名前
     */
    public void setUserIdLockedColumnName(String userIdLockedColumnName) {
        this.userIdLockedColumnName = userIdLockedColumnName;
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
     * ユーザIDカラムの名前を取得する。
     * @return ユーザIDカラムの名前
     */
    public String getUserIdColumnName() {
        return userIdColumnName;
    }

    /**
     * ユーザIDロックカラムの名前を取得する。
     * @return ユーザIDロックカラムの名前
     */
    public String getUserIdLockedColumnName() {
        return userIdLockedColumnName;
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
