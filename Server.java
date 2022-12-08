
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import LoginUser.LoginUser;
import RegisterUser.RegisterUser;
import admin.LoginAdmin;
import admin.UpdateAuctionDateRequest;
import admin.UpdateAuctionWinnerRequest;
import client.GetAllAuctionRequest;
import client.GetAllAuctionResponse;
import client.GetAuctionRequest;
import client.GetAuctionResponse;
import types.Auction;
import types.AuctionWithImg;
import admin.FetchAuctionRequest;
import admin.FetchAuctionResponse;
import admin.FetchUserInfoRequest;
import admin.FetchUserInfoResponse;
import admin.GetImageRequest;
import admin.Image;

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

	public static ArrayList<Auction> auctionData(String filterStatus) {
		ArrayList<Auction> auctionList = new ArrayList<>();
		try {
			String dbUrl = "jdbc:mysql://localhost:3306/auction_system";
			Connection connection = DriverManager.getConnection(dbUrl, "root", "");
			Statement stat = connection.createStatement();
			String query;
			if (filterStatus.length() == 0) {
				query = "select * from auction";
			} else {
				query = "select * from auction where status = '" + filterStatus + "'";
			}
			ResultSet rs = stat.executeQuery(query);
			Auction data;
			while (rs.next()) {
				data = new Auction(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getString("user"),
						rs.getString("userId"),
						rs.getString("startPrice"),
						rs.getString("endPrice"),
						rs.getString("startDateTime"),
						rs.getString("endDateTime"),
						rs.getString("status"),
						rs.getString("img"),
						rs.getString("winner"),
						rs.getString("description"));
				auctionList.add(data);
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return auctionList;
	}

	public static ArrayList<AuctionWithImg> auctionDataWithImg(String filterStatus) throws IOException {
		ArrayList<AuctionWithImg> auctionList = new ArrayList<>();
		try {
			String dbUrl = "jdbc:mysql://localhost:3306/auction_system";
			Connection connection = DriverManager.getConnection(dbUrl, "root", "");
			Statement stat = connection.createStatement();
			String query;
			if (filterStatus.length() == 0) {
				query = "select * from auction";
			} else {
				query = "select * from auction where status = '" + filterStatus + "'";
			}
			ResultSet rs = stat.executeQuery(query);
			AuctionWithImg data;
			while (rs.next()) {
				BufferedImage bImage = ImageIO.read(new File("./images/" + rs.getString("img")));
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", bos);
				byte[] imgData = bos.toByteArray();
				data = new AuctionWithImg(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getString("user"),
						rs.getString("userId"),
						rs.getString("startPrice"),
						rs.getString("endPrice"),
						rs.getString("startDateTime"),
						rs.getString("endDateTime"),
						rs.getString("status"),
						imgData,
						rs.getString("winner"),
						rs.getString("description"));
				auctionList.add(data);
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return auctionList;
	}

	private static class ClientHandler implements Runnable {

		private final Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			PrintWriter out = null;
			ObjectInputStream ois = null;
			ObjectOutputStream objOut = null;

			String dbUrl = "jdbc:mysql://localhost:3306/auction_system";

			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				objOut = new ObjectOutputStream(clientSocket.getOutputStream());

				ois = new ObjectInputStream(clientSocket.getInputStream());

				Object obj = ois.readObject();

				Connection connection;
				connection = DriverManager.getConnection(dbUrl, "root", "");

				RegisterUser regUser;
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

					FetchAuctionRequest fetchAuction;
					if (obj.getClass().getName().equals("admin.FetchAuctionRequest")
							&& (fetchAuction = (FetchAuctionRequest) obj) != null) {
						try {
							if (fetchAuction.str.equals("auctions")) {
								FetchAuctionResponse response = new FetchAuctionResponse(
										auctionData(fetchAuction.filterStatus));
								try {
									objOut.writeObject(response);
									objOut.flush();
								} catch (Exception e) {
									throw e;
								}
							} else {
								out.println("invalid request");
							}
							objOut.close();
						} catch (Exception e) {
							throw e;
						}
					}

					GetImageRequest getImageReq;
					if (obj.getClass().getName().equals("admin.GetImageRequest")
							&& (getImageReq = (GetImageRequest) obj) != null) {
						try {
							BufferedImage bImage = ImageIO.read(new File("./images/" + getImageReq.str));
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							ImageIO.write(bImage, "png", bos);
							byte[] data = bos.toByteArray();
							Image img = new Image(data, data.length);
							objOut.writeObject(img);
							objOut.flush();
							objOut.close();
						} catch (Exception e) {
							throw e;
						}
					}

					FetchUserInfoRequest fetchUserInfoReq;
					if (obj.getClass().getName().equals("admin.FetchUserInfoRequest")
							&& (fetchUserInfoReq = (FetchUserInfoRequest) obj) != null) {
						try {
							Statement stat = connection.createStatement();
							String query = "select * from user where id = " + fetchUserInfoReq.id;
							ResultSet rs = stat.executeQuery(query);
							if (rs.next()) {
								FetchUserInfoResponse res = new FetchUserInfoResponse(rs.getString("userName"),
										rs.getString("email"), rs.getString("phone"), rs.getString("registerNumber"));
								objOut.writeObject(res);
								objOut.flush();
								objOut.close();
							} else {
								out.println("user not found");
							}
						} catch (Exception e) {
							throw e;
						}
					}

					UpdateAuctionDateRequest updateAuctionDateRequest;
					if (obj.getClass().getName().equals("admin.UpdateAuctionDateRequest")
							&& (updateAuctionDateRequest = (UpdateAuctionDateRequest) obj) != null) {
						try {
							String query = "UPDATE auction SET status = 'accepted', startDateTime = CAST('"
									+ updateAuctionDateRequest.startDay + " " + updateAuctionDateRequest.startTime
									+ "' AS DATETIME), endDateTime = CAST('" + updateAuctionDateRequest.endDay + " "
									+ updateAuctionDateRequest.endTime + "' AS DATETIME) where id = "
									+ updateAuctionDateRequest.auctionId;
							PreparedStatement stat = connection.prepareStatement(query);
							int rs = stat.executeUpdate();
							if (rs == 1) {
								out.print("Updated");
							} else {
								out.print("Update action failed");
							}
							out.close();
						} catch (Exception e) {
							throw e;
						}
					}

					UpdateAuctionWinnerRequest updateAuctionWinnerRequest;
					if (obj.getClass().getName().equals("admin.UpdateAuctionWinnerRequest")
							&& (updateAuctionWinnerRequest = (UpdateAuctionWinnerRequest) obj) != null) {
						try {
							String query = "UPDATE auction SET winner = '" + updateAuctionWinnerRequest.winner
									+ "' WHERE id =" + updateAuctionWinnerRequest.id;
							PreparedStatement stat = connection.prepareStatement(query);
							int rs = stat.executeUpdate();
							if (rs == 1) {
								out.print("Updated");
							} else {
								out.print("Update action failed");
							}
							out.close();
						} catch (Exception e) {
							throw e;
						}
					}

					GetAllAuctionRequest getAllAuctionRequest;
					if (obj.getClass().getName().equals("client.GetAllAuctionRequest")
							&& (getAllAuctionRequest = (GetAllAuctionRequest) obj) != null) {
						try {
							if (getAllAuctionRequest.str.equals("auctions")) {
								GetAllAuctionResponse response = new GetAllAuctionResponse(
										auctionDataWithImg(getAllAuctionRequest.filterStatus));
								try {
									objOut.writeObject(response);
									objOut.flush();
								} catch (Exception e) {
									throw e;
								}
							} else {
								out.println("invalid request");
							}
							objOut.close();
						} catch (Exception e) {
							throw e;
						}
					}

					GetAuctionRequest getAuctionRequest;
					if (obj.getClass().getName().equals("client.GetAuctionRequest")
							&& (getAuctionRequest = (GetAuctionRequest) obj) != null) {
						try {
							Statement stat = connection.createStatement();
							String query = "select * from auction where id = " + getAuctionRequest.id;
							ResultSet rs = stat.executeQuery(query);
							if (rs.next()) {
								BufferedImage bImage = ImageIO.read(new File("./images/" +
								rs.getString("img")));
								ByteArrayOutputStream bos = new ByteArrayOutputStream();
								ImageIO.write(bImage, "png", bos);
								byte[] imgData = bos.toByteArray();
								AuctionWithImg data = new AuctionWithImg(
								rs.getInt("id"),
								rs.getString("title"),
								rs.getString("user"),
								rs.getString("userId"),
								rs.getString("startPrice"),
								rs.getString("endPrice"),
								rs.getString("startDateTime"),
								rs.getString("endDateTime"),
								rs.getString("status"),
								imgData,
								rs.getString("winner"),
								rs.getString("description"));
								GetAuctionResponse response = new GetAuctionResponse(data);
								objOut.writeObject(response);
								objOut.flush();
								objOut.close();
							} else {
								out.print("Auction not found");
							}
						} catch (Exception e) {
							throw e;
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