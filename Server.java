
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Log.LogFilter;
import Log.LogFormatter;
import Log.LogHandler;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import RegisterUser.RegisterUser;
import admin.LoginAdmin;
import admin.UpdateAuctionDateRequest;
import admin.UpdateAuctionWinnerRequest;
import client.FinishAuction;
import client.GetAllAuctionRequest;
import client.GetAllAuctionResponse;
import client.GetAuctionRequest;
import client.GetAuctionResponse;
import client.GetBidsRequest;
import client.GetBidsResponse;
import client.LoginRequest;
import client.LoginResponse;
import client.PlaceBidRequest;
import types.Auction;
import types.AuctionWithImg;
import types.Bid;
import admin.FetchAuctionRequest;
import admin.FetchAuctionResponse;
import admin.FetchUserInfoRequest;
import admin.FetchUserInfoResponse;
import admin.GetImageRequest;
import admin.Image;

class Server {

	static Logger logger = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("./serverLog.log"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		logger.setLevel(Level.FINE);
		logger.addHandler(new ConsoleHandler());
		logger.addHandler(new LogHandler());
		try {
			Handler fileHandler = new FileHandler("./serverLog.log");
			fileHandler.setFormatter(new LogFormatter());
			fileHandler.setFilter(new LogFilter());
			logger.addHandler(fileHandler);
			ServerSocket server = null;

			try {
				server = new ServerSocket(1234);
				server.setReuseAddress(true);
				while (true) {
					Socket client = server.accept();
					logger.log(Level.INFO, "New client connected: " + client.getInetAddress().getHostAddress());
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
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
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
			logger.log(Level.WARNING, "SQLException: " + e);
		}
		return auctionList;
	}

	public static ArrayList<Bid> bidsData(int auctionId) {
		ArrayList<Bid> bidsList = new ArrayList<>();
		try {
			String dbUrl = "jdbc:mysql://localhost:3306/auction_system";
			Connection connection = DriverManager.getConnection(dbUrl, "root", "");
			Statement stat = connection.createStatement();
			String query = "select * from bid where auctionId =" + auctionId;
			ResultSet rs = stat.executeQuery(query);
			Bid bid;
			while (rs.next()) {
				bid = new Bid(
						rs.getInt("id"),
						rs.getString("auctionId"),
						rs.getString("userId"),
						rs.getString("userName"),
						rs.getString("price"),
						rs.getString("createdAt"));
				bidsList.add(bid);
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "SQLException: " + e);
		}
		return bidsList;
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

					FinishAuction finishAuction;
					if (obj.getClass().getName().equals("client.FinishAuction")
							&& (finishAuction = (FinishAuction) obj) != null) {
						try {
							String query = "UPDATE auction SET status = 'finished' WHERE id =" + finishAuction.id;
							PreparedStatement stat = connection.prepareStatement(query);
							int rs = stat.executeUpdate();
							if (rs == 1) {
								logger.log(Level.INFO, "Auction finished with id: " + finishAuction.id);
							} else {
								logger.log(Level.WARNING, "Failed finish auction with id: " + finishAuction.id);
							}
							out.close();
						} catch (Exception e) {
							throw e;
						}
					}

					PlaceBidRequest placeBidRequest;
					if (obj.getClass().getName().equals("client.PlaceBidRequest")
							&& (placeBidRequest = (PlaceBidRequest) obj) != null) {
						try {
							String selectQuery = "SELECT username from user where id =" + placeBidRequest.userId;
							CallableStatement cstmt = connection.prepareCall(selectQuery);
							ResultSet result = cstmt.executeQuery(selectQuery);
							if (result.next()) {
								LocalDateTime now = LocalDateTime.now();
								DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
								String insertQuery = "INSERT INTO bid (auctionId, price, userId, userName, createdAt) values('"
										+ placeBidRequest.auctionId
										+ "','" + placeBidRequest.bidAmount + "','" + placeBidRequest.userId + "','"
										+ result.getString("username")
										+ "', CAST('" + now.format(dateFormat) + "' AS DATETIME))";
								PreparedStatement stat = connection.prepareStatement(insertQuery);
								int rs = stat.executeUpdate();
								if (rs == 1) {
									String query = "UPDATE auction SET endPrice = '" + placeBidRequest.bidAmount
											+ "' WHERE id =" + placeBidRequest.auctionId;
									PreparedStatement preStat = connection.prepareStatement(query);
									int updateRs = preStat.executeUpdate();
									if (updateRs == 1) {
										try {
											GetBidsResponse response = new GetBidsResponse(
												bidsData(placeBidRequest.auctionId));
											logger.log(Level.INFO, "Updated auction : " + placeBidRequest.auctionId
											+ ", with end price =" + placeBidRequest.bidAmount);
											objOut.writeObject(response);
											objOut.flush();
										} catch (Exception e) {
											logger.log(Level.WARNING, "Exception: " + e);
										}
									} else {
										logger.log(Level.WARNING, "Updated auction end price failed");
									}
								}
							}
						} catch (Exception e) {
							logger.log(Level.WARNING, "Exception: " + e);
						}
					}

					LoginRequest loginRequest;
					if (obj.getClass().getName().equals("client.LoginRequest")
							&& (loginRequest = (LoginRequest) obj) != null) {
						try {
							String getUserQuery = "select * from user where username = '" +
									loginRequest.userName + "' and passWord = md5('" + loginRequest.passWord + "');";
							CallableStatement cstmt = connection.prepareCall(getUserQuery);
							ResultSet rs = cstmt.executeQuery(getUserQuery);
							LoginResponse response;
							if (rs.next()) {
								response = new LoginResponse(rs.getInt("id"));
							} else {
								response = new LoginResponse(-1);
							}
							objOut.writeObject(response);
							objOut.flush();
							connection.close();
						} catch (Exception e) {
							out.println(e);
						}
					}

					GetBidsRequest getBidsRequest;
					if (obj.getClass().getName().equals("client.GetBidsRequest")
						&& (getBidsRequest = (GetBidsRequest) obj) != null){
						try {
							GetBidsResponse response = new GetBidsResponse(
									bidsData(getBidsRequest.auctionId));
							try {
								objOut.writeObject(response);
								objOut.flush();
							} catch (Exception e) {
								objOut.close();
								logger.log(Level.WARNING, "Exception: " + e);
							}
						} catch (Exception e) {
							logger.log(Level.WARNING, "Exception: " + e);
						}
					}

					obj = null;
				}
			} catch (SQLException e1) {
				logger.log(Level.WARNING, "SQLException: " + e1);
			} catch (IOException e) {
				logger.log(Level.WARNING, "IOException: " + e);
			} catch (ClassNotFoundException ex) {
				logger.log(Level.WARNING, "ClassNotFoundException: " + ex);
			} finally {
				try {
					if (out != null) {
						out.close();
					}

					if (ois != null) {
						ois.close();
						logger.log(Level.INFO, "Client disconnected");
						clientSocket.close();
					}
				} catch (IOException e) {
					logger.log(Level.INFO, "IOException: " + e);
				}
			}
		}
	}
}