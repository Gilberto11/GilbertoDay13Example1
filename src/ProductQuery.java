

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

	public class ProductQuery {


		private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		private static final String USERNAME = "student";
		private static final String PASSWORD = "Password1";
		
		Connection conn;

		private PreparedStatement browseAll;
		private PreparedStatement search;
		private PreparedStatement insert;

		public ProductQuery(){

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				String sql = "SELECT * FROM PRODUCT";
				browseAll = conn.prepareStatement(sql);
				String sql1 = "SELECT * FROM Product WHERE ProductName=?";
				search = conn.prepareStatement(sql1);
				String sql2 = "INSERT INTO PRODUCT" + "(ProductName,Price,Description,QTY)" + "VALUES (?,?,?,?)";
				insert = conn.prepareStatement(sql2);
			} catch (Exception e) {
				System.out.println("You're not connected to the DB");
				e.printStackTrace();
				System.exit(0);
				System.exit(1);
			}

		}

		public List<Product> getAllProduct() {
			List<Product> results = null;
			ResultSet rs = null;

			try {
				rs = browseAll.executeQuery();
				results = new ArrayList<Product>();

				while (rs.next()) {
					results.add(new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getDouble("Price"),
							rs.getString("Description"), rs.getInt("QTY")));
				}

			} catch (SQLException ex) {
				System.out.println("Lost Connection to the DB");
				ex.printStackTrace();
			}finally{
				try {
					rs.close();
				}catch (SQLException ex1) {
					ex1.printStackTrace();
					close();
				}
			}
				return results;
			}
		

		public List<Product> searchProduct(String productName) {
			List<Product> results = null;
			ResultSet rs = null;

			try {
				search.setString(1,productName);
				rs = search.executeQuery();
				results = new ArrayList<Product>();
				while (rs.next()) {
					results.add(new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getDouble("Price"),
							rs.getString("Description"), rs.getInt("QTY")));

				}
			} catch (SQLException ex) {

				ex.printStackTrace();
			} finally {
				try {
					rs.close();
				} catch (SQLException ex1) {
					ex1.printStackTrace();
					close();
				}
			}

			return results;

		}

		public int addProduct(String pn, double p, String d, int q) {

			int results =0;
			try {
				insert.setString(1, pn);
				insert.setDouble(2, p);
				insert.setString(3, d);
				insert.setInt(4,q);
				
				results = insert.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				close();
			}
			return results;
		}

		public void close() {
			try {
				conn.close();
			} catch (SQLException ex2) {
				// TODO Auto-generated catch block
				ex2.printStackTrace();
			}
			
		}
	}



