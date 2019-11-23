package th.co.cdgs.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import th.co.cdgs.bean.CustomerBean;
@Path("/customer")
public class CustomerController {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCustomer(CustomerBean customer) {
		List <CustomerBean> list = new ArrayList<CustomerBean>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
			pst = conn.prepareStatement("INSERT INTO customer (first_name ,last_name,address,tel,email)"+"VALUES (?,?,?,?,?)");
			int index = 1 ;
			
			pst.setString(index++, customer.getFirstName());
			pst.setString(index++, customer.getLastName());
			pst.setString(index++, customer.getAddress());
			pst.setString(index++, customer.getTel());
			pst.setString(index++, customer.getEmail());
			pst.executeUpdate();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// conn.pr

		return Response.ok().entity(list).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
public Response getCustomer() {
	List<CustomerBean> list = new ArrayList<CustomerBean>();
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
		pst = conn.prepareStatement("SELECT customer_Id ," + "CONCAT(first_name,'',last_name)as full_name,"
				+ "address,tel,email FROM customer");
		rs = pst.executeQuery();
		CustomerBean customerDto = null;
		while (rs.next()) {
			customerDto = new CustomerBean();
			customerDto.setCustomerId(rs.getLong("customer_Id"));
			customerDto.setFullName(rs.getString("full_name"));
			customerDto.setAddress(rs.getString("address"));
			customerDto.setTel(rs.getString("tel"));
			customerDto.setEmail(rs.getString("email"));
			list.add(customerDto);
		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		if (rs != null) {

			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	return Response.ok().entity(list).build();
	}
}