package nablarch.common.availability;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import nablarch.core.db.connection.ConnectionFactory;
import nablarch.core.db.transaction.SimpleDbTransactionManager;
import nablarch.core.transaction.TransactionFactory;
import nablarch.test.support.SystemRepositoryResource;
import nablarch.test.support.db.helper.DatabaseTestRunner;
import nablarch.test.support.db.helper.VariousDbTestHelper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabaseTestRunner.class)
public class BasicServiceAvailabilityTest {

    private final BasicServiceAvailability sut = new BasicServiceAvailability();

    @Rule
    public SystemRepositoryResource repositoryResource = new SystemRepositoryResource("db-default.xml");


    @BeforeClass
    public static void setUpClass() {
        VariousDbTestHelper.createTable(AppliedRequest.class);
    }

    @Before
    public void setUp() {
        final SimpleDbTransactionManager dbManager = new SimpleDbTransactionManager();
        dbManager.setConnectionFactory(repositoryResource.<ConnectionFactory>getComponent("connectionFactory"));
        dbManager.setTransactionFactory(repositoryResource.<TransactionFactory>getComponent("jdbcTransactionFactory"));
        sut.setDbManager(dbManager);
        sut.setTableName("APPLIED_REQUEST");
        sut.setRequestTableRequestIdColumnName("APPLIED_REQUEST_ID");
        sut.setRequestTableServiceAvailableColumnName("APPLIED_SERVICE_AVAILABLE");
        sut.initialize();
    }

    @Test
    public void testServiceAvailableOkStatus() {
        VariousDbTestHelper.setUpTable(new AppliedRequest("REQ0000001", "1"));
        assertThat(sut.isAvailable("REQ0000001"), is(true));
    }

    @Test
    public void testServiceUnAvailableNgStatus() {
        VariousDbTestHelper.setUpTable(new AppliedRequest("REQ0000001", "0"));
        assertThat(sut.isAvailable("REQ0000001"), is(false));
    }

    @Test
    public void testServiceUnAvailableNoRecords() {
        VariousDbTestHelper.delete(AppliedRequest.class);
        assertThat(sut.isAvailable("REQ0000001"), is(false));
    }
    

    @Test
    public void testServiceUnAvailableAnotherRecord() {
        VariousDbTestHelper.setUpTable(new AppliedRequest("REQ0000999", "1"));
        assertThat(sut.isAvailable("REQ0000001"), is(false));
    }

    @Test
    public void testServiceAvailableMuchRecords() {
        VariousDbTestHelper.setUpTable(
                new AppliedRequest("REQ0000000", "0"),
                new AppliedRequest("REQ0000001", "1"),
                new AppliedRequest("REQ0000002", "2"),
                new AppliedRequest("REQ0000003", "3")
        );
        assertThat(sut.isAvailable("REQ0000001"), is(true));
    }

    @Test
    public void testServiceUnAvailableMuchRecords() {
        VariousDbTestHelper.setUpTable(
                new AppliedRequest("REQ0000000", "1"),
                new AppliedRequest("REQ0000001", "0"),
                new AppliedRequest("REQ0000002", "2"),
                new AppliedRequest("REQ0000003", "3")
        );
        assertThat(sut.isAvailable("REQ0000001"), is(false));
    }

    @Test
    public void testServiceAvailableOkStatusInjection_1() {
        VariousDbTestHelper.setUpTable(new AppliedRequest("REQ0000001", "1"));
        
        sut.setRequestTableServiceAvailableOkStatus("1");
        assertThat(sut.isAvailable("REQ0000001"), is(true));
    }

    @Test
    public void testServiceAvailableOkStatusInjection_0() {
        sut.setRequestTableServiceAvailableOkStatus("0");
        VariousDbTestHelper.setUpTable(new AppliedRequest("REQ0000001", "0"));
        assertThat(sut.isAvailable("REQ0000001"), is(true));
    }
}
