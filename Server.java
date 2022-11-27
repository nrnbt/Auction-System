import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import LoginUser.LoginUser;
import RegisterUser.RegisterUser;
import admin.LoginAdmin;

class Server {

	public static void main(String[] args) {

		ServerSocket server = null;

		try {
			server = new ServerSocket(1234);
			server.setReuseAddress(true);
			while (true) {
				Socket client = server.accept();
				System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
				ClientHandler clientSock = new ClientHandler(client);
				new Thread(clientSock).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class ClientHandler implements Runnable {

		private final Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			PrintWriter out = null;
			ObjectInputStream ois = null;

			String dbUrl = "jdbc:mysql://localhost:3306/auction_system";

			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				ois = new ObjectInputStream(clientSocket.getInputStream());

				RegisterUser regUser;

				Object obj = ois.readObject();

				Connection connection;
				connection = DriverManager.getConnection(dbUrl, "root", "");

				while (obj != null) {
					if (obj.getClass().getName().equals("RegisterUser.RegisterUser")
							&& (regUser = (RegisterUser) obj) != null) {
						try {
							String checkUserNameQuery = "select passWord from users where userName = '" +
									regUser.userName + "';";
							CallableStatement checkUserNameCstmt = connection.prepareCall(checkUserNameQuery);
							ResultSet checkUserNameRs = checkUserNameCstmt.executeQuery(checkUserNameQuery);
							if (checkUserNameRs.next()) {
								out.println("user already registered");
							} else {
								String checkEmailQuery = "select passWord from users where email = '" +
										regUser.email + "';";
								CallableStatement checkEmailCstmt = connection.prepareCall(checkEmailQuery);
								ResultSet checkEmailRs = checkEmailCstmt.executeQuery(checkEmailQuery);
								if (checkEmailRs.next()) {
									out.println("email already registered");
								} else {
									String checkPhoneQuery = "select passWord from users where phone = '" +
											regUser.phone + "';";
									CallableStatement checkPhoneCstmt = connection.prepareCall(checkPhoneQuery);
									ResultSet checkPhoneRs = checkPhoneCstmt.executeQuery(checkPhoneQuery);
									if (checkPhoneRs.next()) {
										out.println("Phone already registered");
									} else {
										DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
										LocalDateTime now = LocalDateTime.now();
										String insertQuery = "insert into users(userName, passWord, email, firstName, lastName, phone, birthDay, registeredAt, role) values ('"
												+ regUser.userName + "',sha1('" + regUser.passWord + "'),'"
												+ regUser.email
												+ "','" + regUser.firstName
												+ "','" + regUser.lastName
												+ "','" + regUser.phone
												+ "','" + regUser.birthDay + "','" + dtf.format(now) + "', 0)";
										CallableStatement cstmt = connection.prepareCall(insertQuery);
										if (cstmt.executeUpdate() > 0) {
											out.println("User registered");
										} else {
											out.println("Register user failed");
										}
									}
								}
							}
							connection.close();
							obj = null;
						} catch (Exception e) {
							out.println(e);
						}
					}

					LoginUser logUser;

					if (obj.getClass().getName().equals("LoginUser.LoginUser")
							&& (logUser = (LoginUser) obj) != null) {
						try {
							String getUserQuery = "select * from users where email = '" +
									logUser.email + "' and passWord = md5('" + logUser.passWord + "');";
							CallableStatement cstmt = connection.prepareCall(getUserQuery);
							ResultSet rs = cstmt.executeQuery(getUserQuery);
							if (rs.next()) {
								out.println("access successful");
							} else {
								out.println("username or password didn't match");
							}
							connection.close();
							obj = null;
						} catch (Exception e) {
							out.println(e);
						}
					}

					LoginAdmin loginAdmin;

					if (obj.getClass().getName().equals("admin.LoginAdmin")
							&& (loginAdmin = (LoginAdmin) obj) != null) {
						try {
							String getUserQuery = "select * from admin where username = '" +
									loginAdmin.username + "' and password = md5('" + loginAdmin.passWord + "');";
							CallableStatement cstmt = connection.prepareCall(getUserQuery);
							ResultSet rs = cstmt.executeQuery(getUserQuery);
							if (rs.next()) {
								System.out.println();
								out.println("access successful");
							} else {
								out.println("username or password didn't match");
							}
							connection.close();
							obj = null;
						} catch (Exception e) {
							out.println(e);
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				try {
					if (out != null) {
						out.close();
					}

					if (ois != null) {
						ois.close();
						clientSocket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}