package nablarch.common.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nablarch.core.ThreadContext;
import nablarch.test.support.SystemRepositoryResource;
import nablarch.test.support.db.helper.DatabaseTestRunner;
import nablarch.test.support.db.helper.VariousDbTestHelper;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * {@link BasicPermissionFactory}のテストクラス。
 *
 * @author Kiyohito Itoh
 */
@RunWith(DatabaseTestRunner.class)
public class BasicPermissionFactoryTest {

    @Rule
    public SystemRepositoryResource repositoryResource = new SystemRepositoryResource(
            "nablarch/common/permission/permission-test.xml");

    @BeforeClass
    public static void classSetup() {
        VariousDbTestHelper.createTable(UgroupSystemAccount.class);
        VariousDbTestHelper.createTable(PermissionUnitRequest.class);
        VariousDbTestHelper.createTable(SystemAccountAuthority.class);
        VariousDbTestHelper.createTable(PermissionUnit.class);
        VariousDbTestHelper.createTable(UgroupAuthority.class);
        VariousDbTestHelper.createTable(Ugroup.class);
        VariousDbTestHelper.createTable(SystemAccount.class);
    }

    /**
     * {@link BasicPermissionFactory#getPermission(String)}のテスト。
     * {@link ThreadContext}にユーザIDが設定されていない場合。
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotSetUserIdToThreadContext() {
        BasicPermissionFactory factory = repositoryResource.getComponentByType(BasicPermissionFactory.class);
        factory.initialize();
        factory.getPermission(null);
    }

    /**
     * {@link BasicPermissionFactory#getPermission(String)}のテスト。
     * グループ権限に関するテスト。
     *
     * @throws Exception 例外
     */
    @Test
    public void testGroupAuthority() throws Exception {

        VariousDbTestHelper.setUpTable(
                new SystemAccount("SA00000001", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000002", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000003", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000004", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000005", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000006", "unknown", "1", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000007", "unknown", "0", "20100930", 0L, "20100730", "99991231"),
                new SystemAccount("SA00000008", "unknown", "0", "19000101", 0L, "20100701", "20100728"),
                new SystemAccount("SA00000009", "unknown", "0", "20100930", 0L, "20100729", "20100729"),
                new SystemAccount("SA00000010", "unknown", "0", "20100930", 0L, "19000101", "99991231"),
                new SystemAccount("SA00000011", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000012", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000013", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA99999999", "unknown", "0", "20100930", 0L, "20100701", "99991231"));

        VariousDbTestHelper.setUpTable(
                new Ugroup("UG00000001"),
                new Ugroup("UG00000002"),
                new Ugroup("UG00000003"),
                new Ugroup("UG00000004"));

        VariousDbTestHelper.setUpTable(
                new UgroupSystemAccount("UG00000001", "SA00000001", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000002", "SA00000002", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000003", "SA00000003", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000004", "SA00000004", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000002", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000003", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000004", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000006", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000007", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000008", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000009", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000010", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000011", "20100730", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000012", "19000101", "20100728"),
                new UgroupSystemAccount("UG00000001", "SA00000013", "20100729", "20100729"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnit("UC00010000"),
                new PermissionUnit("UC00020000"),
                new PermissionUnit("UC00030000"),
                new PermissionUnit("UC00040000"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnitRequest("UC00010000", "R000010001"),
                new PermissionUnitRequest("UC00020000", "R000020001"),
                new PermissionUnitRequest("UC00020000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000010001"),
                new PermissionUnitRequest("UC00030000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000030003"));

        VariousDbTestHelper.setUpTable(
                new UgroupAuthority("UG00000001", "UC00010000"),
                new UgroupAuthority("UG00000002", "UC00010000"),
                new UgroupAuthority("UG00000002", "UC00020000"),
                new UgroupAuthority("UG00000003", "UC00030000"),
                new UgroupAuthority("UG00000003", "UC00040000"));

        VariousDbTestHelper.delete(SystemAccountAuthority.class);

        BasicPermissionFactory factory = repositoryResource.getComponentByType(BasicPermissionFactory.class);
        factory.initialize();

        // 1グループに1UC
        Permission permission = factory.getPermission("SA00000001");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 1グループに複数UC
        permission = factory.getPermission("SA00000002");
        assertTrue(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // リクエスト割り当てがないUC(UC00040000)
        permission = factory.getPermission("SA00000003");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertTrue(permission.permit("R000030003"));

        // UC割り当てがないグループ(UG00000004)
        permission = factory.getPermission("SA00000004");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 全部盛り
        permission = factory.getPermission("SA00000005");
        assertTrue(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertTrue(permission.permit("R000030003"));

        // ユーザIDがロックされている
        permission = factory.getPermission("SA00000006");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がシステムアカウントの有効日前
        permission = factory.getPermission("SA00000007");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がシステムアカウントの有効日後
        permission = factory.getPermission("SA00000008");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がシステムアカウントの有効日内
        permission = factory.getPermission("SA00000009");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // システムアカウントの有効日未指定
        permission = factory.getPermission("SA00000010");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日前
        permission = factory.getPermission("SA00000011");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日後
        permission = factory.getPermission("SA00000012");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日内
        permission = factory.getPermission("SA00000013");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ割り当てがないユーザ
        permission = factory.getPermission("SA99999999");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));
    }

    /**
     * {@link BasicPermissionFactory#getPermission(String)}のテスト。
     * ユーザ権限に関するテスト。
     *
     * @throws Exception 例外
     */
    @Test
    public void testUserAuthority() throws Exception {

        VariousDbTestHelper.setUpTable(
                new SystemAccount("SA00000001", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000002", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000003", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000004", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000005", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000006", "unknown", "1", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000007", "unknown", "0", "20100930", 0L, "20100730", "99991231"),
                new SystemAccount("SA00000008", "unknown", "0", "20100930", 0L, "19000101", "20100728"),
                new SystemAccount("SA00000009", "unknown", "0", "20100930", 0L, "20100729", "20100729"),
                new SystemAccount("SA00000010", "unknown", "0", "20100930", 0L, "19000101", "99991231"));

        VariousDbTestHelper.delete(Ugroup.class);
        VariousDbTestHelper.delete(UgroupSystemAccount.class);

        VariousDbTestHelper.setUpTable(
                new PermissionUnit("UC00010000"),
                new PermissionUnit("UC00020000"),
                new PermissionUnit("UC00030000"),
                new PermissionUnit("UC00040000"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnitRequest("UC00010000", "R000010001"),
                new PermissionUnitRequest("UC00020000", "R000020001"),
                new PermissionUnitRequest("UC00020000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000010001"),
                new PermissionUnitRequest("UC00030000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000030003"));

        VariousDbTestHelper.delete(UgroupAuthority.class);

        VariousDbTestHelper.setUpTable(
                new SystemAccountAuthority("SA00000001", "UC00010000"),
                new SystemAccountAuthority("SA00000002", "UC00010000"),
                new SystemAccountAuthority("SA00000002", "UC00020000"),
                new SystemAccountAuthority("SA00000003", "UC00030000"),
                new SystemAccountAuthority("SA00000003", "UC00040000"),
                new SystemAccountAuthority("SA00000005", "UC00010000"),
                new SystemAccountAuthority("SA00000005", "UC00020000"),
                new SystemAccountAuthority("SA00000005", "UC00030000"),
                new SystemAccountAuthority("SA00000005", "UC00040000"),
                new SystemAccountAuthority("SA00000006", "UC00010000"),
                new SystemAccountAuthority("SA00000007", "UC00010000"),
                new SystemAccountAuthority("SA00000008", "UC00010000"),
                new SystemAccountAuthority("SA00000009", "UC00010000"),
                new SystemAccountAuthority("SA00000010", "UC00010000"));

        BasicPermissionFactory factory = repositoryResource.getComponentByType(BasicPermissionFactory.class);
        factory.initialize();

        // 1ユーザに1UC
        Permission permission = factory.getPermission("SA00000001");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 1ユーザに複数UC
        permission = factory.getPermission("SA00000002");
        assertTrue(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // リクエスト割り当てがないUC(UC00040000)
        permission = factory.getPermission("SA00000003");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertTrue(permission.permit("R000030003"));

        // UC割り当てがないユーザ
        permission = factory.getPermission("SA00000004");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 全部盛り
        permission = factory.getPermission("SA00000005");
        assertTrue(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertTrue(permission.permit("R000030003"));

        // 存在しないユーザ
        permission = factory.getPermission("SA99999999");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // ユーザIDがロックされている
        permission = factory.getPermission("SA00000006");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がシステムアカウントの有効日前
        permission = factory.getPermission("SA00000007");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がシステムアカウントの有効日後
        permission = factory.getPermission("SA00000008");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // システムアカウントの現在日時が有効日内
        permission = factory.getPermission("SA00000009");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // システムアカウントの有効日未指定
        permission = factory.getPermission("SA00000010");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));
    }


    /**
     * {@link BasicPermissionFactory#getPermission(String)}のテスト。
     * グループ権限とユーザ権限を組み合わせたテスト。
     *
     * @throws Exception 例外
     */
    @Test
    public void testGroupAuthorityAndUserAuthority() throws Exception {

        VariousDbTestHelper.setUpTable(
                new SystemAccount("SA00000001", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000002", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000003", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000004", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000005", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000006", "unknown", "1", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000007", "unknown", "0", "20100930", 0L, "20100730", "99991231"),
                new SystemAccount("SA00000008", "unknown", "0", "20100930", 0L, "19000101", "20100728"),
                new SystemAccount("SA00000009", "unknown", "0", "20100930", 0L, "20100729", "20100729"),
                new SystemAccount("SA00000010", "unknown", "0", "20100930", 0L, "19000101", "99991231"),
                new SystemAccount("SA00000011", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000012", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000013", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA99999999", "unknown", "0", "20100930", 0L, "20100701", "99991231"));

        VariousDbTestHelper.setUpTable(
                new Ugroup("UG00000001"),
                new Ugroup("UG00000002"),
                new Ugroup("UG00000003"),
                new Ugroup("UG00000004"));

        VariousDbTestHelper.setUpTable(
                new UgroupSystemAccount("UG00000001", "SA00000001", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000002", "SA00000002", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000003", "SA00000003", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000004", "SA00000004", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000002", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000003", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000004", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000006", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000007", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000008", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000009", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000010", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000011", "20100730", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000012", "19000101", "20100728"),
                new UgroupSystemAccount("UG00000001", "SA00000013", "20100729", "20100729"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnit("UC00010000"),
                new PermissionUnit("UC00020000"),
                new PermissionUnit("UC00030000"),
                new PermissionUnit("UC00040000"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnitRequest("UC00010000", "R000010001"),
                new PermissionUnitRequest("UC00020000", "R000020001"),
                new PermissionUnitRequest("UC00020000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000010001"),
                new PermissionUnitRequest("UC00030000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000030003"));

        VariousDbTestHelper.setUpTable(
                new UgroupAuthority("UG00000001", "UC00010000"),
                new UgroupAuthority("UG00000002", "UC00010000"),
                new UgroupAuthority("UG00000002", "UC00020000"),
                new UgroupAuthority("UG00000003", "UC00030000"),
                new UgroupAuthority("UG00000003", "UC00040000"));

        VariousDbTestHelper.setUpTable(
                new SystemAccountAuthority("SA00000001", "UC00030000"),
                new SystemAccountAuthority("SA00000011", "UC00020000"),
                new SystemAccountAuthority("SA00000012", "UC00020000"),
                new SystemAccountAuthority("SA00000013", "UC00020000"));

        BasicPermissionFactory factory = repositoryResource.getComponentByType(BasicPermissionFactory.class);
        factory.initialize();

        // グループ権限＋ユーザ権限
        Permission permission = factory.getPermission("SA00000001");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertTrue(permission.permit("R000030003"));

        // グループ権限＋ユーザIDがロックされている
        permission = factory.getPermission("SA00000006");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋現在日時がシステムアカウントの有効日前
        permission = factory.getPermission("SA00000007");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋現在日時がシステムアカウントの有効日後
        permission = factory.getPermission("SA00000008");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋現在日時がシステムアカウントの有効日内
        permission = factory.getPermission("SA00000009");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋システムアカウントの有効日未指定
        permission = factory.getPermission("SA00000010");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日前＋ユーザ権限
        permission = factory.getPermission("SA00000011");
        assertFalse(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日後＋ユーザ権限
        permission = factory.getPermission("SA00000012");
        assertFalse(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日内＋ユーザ権限
        permission = factory.getPermission("SA00000013");
        assertTrue(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));
    }

    /**
     * {@link BasicPermissionFactory#getPermission(String)}のテスト。
     * 設定ファイルでトランザクション名が指定された場合。
     *
     * @throws Exception 例外
     */
    @Test
    public void testSpecifiedTransactionName() throws Exception {

        VariousDbTestHelper.setUpTable(
                new SystemAccount("SA00000001", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000002", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000003", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000004", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000005", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000006", "unknown", "1", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000007", "unknown", "0", "20100930", 0L, "20100730", "99991231"),
                new SystemAccount("SA00000008", "unknown", "0", "20100930", 0L, "19000101", "20100728"),
                new SystemAccount("SA00000009", "unknown", "0", "20100930", 0L, "20100729", "20100729"),
                new SystemAccount("SA00000010", "unknown", "0", "20100930", 0L, "19000101", "99991231"),
                new SystemAccount("SA00000011", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000012", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA00000013", "unknown", "0", "20100930", 0L, "20100701", "99991231"),
                new SystemAccount("SA99999999", "unknown", "0", "20100930", 0L, "20100701", "99991231"));

        VariousDbTestHelper.setUpTable(
                new Ugroup("UG00000001"),
                new Ugroup("UG00000002"),
                new Ugroup("UG00000003"),
                new Ugroup("UG00000004"));

        VariousDbTestHelper.setUpTable(
                new UgroupSystemAccount("UG00000001", "SA00000001", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000002", "SA00000002", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000003", "SA00000003", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000004", "SA00000004", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000002", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000003", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000004", "SA00000005", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000006", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000007", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000008", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000009", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000010", "20100701", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000011", "20100730", "99991231"),
                new UgroupSystemAccount("UG00000001", "SA00000012", "19000101", "20100728"),
                new UgroupSystemAccount("UG00000001", "SA00000013", "20100729", "20100729"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnit("UC00010000"),
                new PermissionUnit("UC00020000"),
                new PermissionUnit("UC00030000"),
                new PermissionUnit("UC00040000"));

        VariousDbTestHelper.setUpTable(
                new PermissionUnitRequest("UC00010000", "R000010001"),
                new PermissionUnitRequest("UC00020000", "R000020001"),
                new PermissionUnitRequest("UC00020000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000010001"),
                new PermissionUnitRequest("UC00030000", "R000020002"),
                new PermissionUnitRequest("UC00030000", "R000030003"));

        VariousDbTestHelper.setUpTable(
                new UgroupAuthority("UG00000001", "UC00010000"),
                new UgroupAuthority("UG00000002", "UC00010000"),
                new UgroupAuthority("UG00000002", "UC00020000"),
                new UgroupAuthority("UG00000003", "UC00030000"),
                new UgroupAuthority("UG00000003", "UC00040000"));

        VariousDbTestHelper.setUpTable(
                new SystemAccountAuthority("SA00000001", "UC00030000"),
                new SystemAccountAuthority("SA00000011", "UC00020000"),
                new SystemAccountAuthority("SA00000012", "UC00020000"),
                new SystemAccountAuthority("SA00000013", "UC00020000"));

        BasicPermissionFactory factory = repositoryResource.getComponentByType(BasicPermissionFactory.class);
        factory.initialize();

        // グループ権限＋ユーザ権限
        Permission permission = factory.getPermission("SA00000001");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertTrue(permission.permit("R000030003"));

        // グループ権限＋ユーザIDがロックされている
        permission = factory.getPermission("SA00000006");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋現在日時がシステムアカウントの有効日前
        permission = factory.getPermission("SA00000007");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋現在日時がシステムアカウントの有効日後
        permission = factory.getPermission("SA00000008");
        assertFalse(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋現在日時がシステムアカウントの有効日内
        permission = factory.getPermission("SA00000009");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // グループ権限＋システムアカウントの有効日未指定
        permission = factory.getPermission("SA00000010");
        assertTrue(permission.permit("R000010001"));
        assertFalse(permission.permit("R000020001"));
        assertFalse(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日前＋ユーザ権限
        permission = factory.getPermission("SA00000011");
        assertFalse(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日後＋ユーザ権限
        permission = factory.getPermission("SA00000012");
        assertFalse(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));

        // 現在日時がグループシステムアカウント関連の有効日内＋ユーザ権限
        permission = factory.getPermission("SA00000013");
        assertTrue(permission.permit("R000010001"));
        assertTrue(permission.permit("R000020001"));
        assertTrue(permission.permit("R000020002"));
        assertFalse(permission.permit("R000030003"));
    }
}
