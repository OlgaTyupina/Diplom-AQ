import java.sql.*;

public class SqlHelper {
        private static final String url = System.getProperty("db.url");
        private static final String user = "app";
        private static final String password = "pass";


        public static void cleanTables() {
            String deleteOrderEntity = "delete from order_entity;";
            String deletePaymentEntity = "delete from payment_entity;";
            String deleteCreditEntity = "delete from credit_request_entity;";

            try {
                Connection connection = DriverManager.getConnection(url,user,password);
                PreparedStatement orderEntity = connection.prepareStatement(deleteOrderEntity);
                PreparedStatement paymentEntity = connection.prepareStatement(deletePaymentEntity);
                PreparedStatement creditEntity = connection.prepareStatement(deleteCreditEntity);
                orderEntity.executeUpdate();
                paymentEntity.executeUpdate();
                creditEntity.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.getErrorCode();
            }
        }

        public static String selectBuyStatus() throws SQLException {
            String stmt = "SELECT status FROM payment_entity";
            String status = "status";
            return getStatus(stmt, status);
        }

        public static String selectCreditStatus() throws SQLException {
            String stmt = "SELECT status FROM credit_request_entity";
            String status = "status";
            return getStatus(stmt, status);
        }
        // Доп

        public static boolean isNotEmpty() throws SQLException{
            String stmt = "select * from order_entity;";
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(stmt);
            ResultSet resultSet = statement.executeQuery();
            boolean dbNotEmpty = resultSet.next();
            connection.close();
            return dbNotEmpty;
        }
        private static String getStatus (String stmt, String status) throws SQLException {
            String result;
            Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement = connection.prepareStatement(stmt);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString(status);
            connection.close();
            return result;
        }
    }

